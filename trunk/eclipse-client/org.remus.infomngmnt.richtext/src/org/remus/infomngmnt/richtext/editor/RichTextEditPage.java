/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.richtext.editor;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;
import de.spiritlink.richhtml4eclipse.widgets.JavaScriptCommands;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.richtext.actions.BackColorAction;
import org.remus.infomngmnt.richtext.actions.BoldAction;
import org.remus.infomngmnt.richtext.actions.BulletListAction;
import org.remus.infomngmnt.richtext.actions.CleanupAction;
import org.remus.infomngmnt.richtext.actions.DeleteColumnAction;
import org.remus.infomngmnt.richtext.actions.DeleteRowAction;
import org.remus.infomngmnt.richtext.actions.ForegroundAction;
import org.remus.infomngmnt.richtext.actions.HrAction;
import org.remus.infomngmnt.richtext.actions.IndentAction;
import org.remus.infomngmnt.richtext.actions.InsertColumnAfterAction;
import org.remus.infomngmnt.richtext.actions.InsertColumnBeforeAction;
import org.remus.infomngmnt.richtext.actions.InsertEditAnchorAction;
import org.remus.infomngmnt.richtext.actions.InsertEditImageAction;
import org.remus.infomngmnt.richtext.actions.InsertEditLinkAction;
import org.remus.infomngmnt.richtext.actions.InsertEditTableAction;
import org.remus.infomngmnt.richtext.actions.InsertLayerAction;
import org.remus.infomngmnt.richtext.actions.InsertNonBreakingWhitespace;
import org.remus.infomngmnt.richtext.actions.InsertRowAfterAction;
import org.remus.infomngmnt.richtext.actions.InsertRowBeforeAction;
import org.remus.infomngmnt.richtext.actions.ItalicAction;
import org.remus.infomngmnt.richtext.actions.JustifyCenterAction;
import org.remus.infomngmnt.richtext.actions.JustifyFullAction;
import org.remus.infomngmnt.richtext.actions.JustifyLeftAction;
import org.remus.infomngmnt.richtext.actions.JustifyRightAction;
import org.remus.infomngmnt.richtext.actions.MakeLayerAbsoluteAction;
import org.remus.infomngmnt.richtext.actions.MoveLayerBackwardAction;
import org.remus.infomngmnt.richtext.actions.MoveLayerForwardAction;
import org.remus.infomngmnt.richtext.actions.NumListAction;
import org.remus.infomngmnt.richtext.actions.OutdentAction;
import org.remus.infomngmnt.richtext.actions.RemoveFormatAction;
import org.remus.infomngmnt.richtext.actions.SelectFontAction;
import org.remus.infomngmnt.richtext.actions.SelectFontSizeAction;
import org.remus.infomngmnt.richtext.actions.SelectFormatAction;
import org.remus.infomngmnt.richtext.actions.StrikeThroughAction;
import org.remus.infomngmnt.richtext.actions.SubAction;
import org.remus.infomngmnt.richtext.actions.SupAction;
import org.remus.infomngmnt.richtext.actions.ToggleVisualAidAction;
import org.remus.infomngmnt.richtext.actions.UnderLineAction;
import org.remus.infomngmnt.richtext.actions.UnlinkAction;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RichTextEditPage extends AbstractInformationFormPage {

	private HtmlComposer composer;
	private Combo formatCombo;
	private Combo fontCombo;
	private Combo fontsizeCombo;
	private ToolBarManager textFormatToolbarManager;
	private ToolBarManager toolsActionsToolbarManager;
	private ToolBarManager fontsToolbarManager;
	private ToolBarManager layerToolbarManager;
	private ToolBarManager tableToolbarManager;

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout());
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.minimumHeight = 500;
		client.setLayoutData(gridData);
		generalSection.setClient(client);

		CoolBar coolbar = new CoolBar(client, SWT.NONE);
		toolkit.adapt(coolbar);
		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.widthHint = 100;
		coolbar.setLayoutData(gd);

		coolbar.addListener(SWT.Resize, new Listener() {
			public void handleEvent(final Event event) {
				client.getShell().layout();
			}
		});
		final Composite comboComp = toolkit.createComposite(coolbar, SWT.NONE);
		comboComp.setLayout(new GridLayout(3, false));

		this.formatCombo = new Combo(comboComp, SWT.SINGLE);

		this.formatCombo
				.setItems(SelectFormatAction.formatMappings.values().toArray(new String[0]));
		this.formatCombo.add("--[Format]--", 0);
		this.formatCombo.select(0);
		toolkit.adapt(this.formatCombo);

		Point formatTextSize = this.formatCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		formatTextSize = this.formatCombo.computeSize(formatTextSize.x, formatTextSize.y);

		this.fontCombo = new Combo(comboComp, SWT.SINGLE);

		this.fontCombo.setItems(getFontList());
		this.fontCombo.add("--[Font]--", 0);
		this.fontCombo.select(0);
		toolkit.adapt(this.fontCombo);

		Point fontTextSize = this.fontCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		fontTextSize = this.fontCombo.computeSize(fontTextSize.x, fontTextSize.y);

		this.fontsizeCombo = new Combo(comboComp, SWT.SINGLE);
		this.fontsizeCombo.setItems(new String[] { "--[Size]--", "1", "2", "3", "4", "5", "6" });
		this.fontsizeCombo.select(0);
		toolkit.adapt(this.fontsizeCombo);

		CoolItem comboItem = new CoolItem(coolbar, SWT.NONE);
		comboItem.setControl(comboComp);

		Point fontSizetextSize = this.fontsizeCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		fontSizetextSize = this.fontsizeCombo.computeSize(fontSizetextSize.x, fontSizetextSize.y);
		comboItem.setMinimumSize(formatTextSize.x + fontSizetextSize.x + fontTextSize.x,
				formatTextSize.y + fontSizetextSize.y + fontTextSize.y);
		comboItem.setPreferredSize(formatTextSize.x + fontSizetextSize.x + fontTextSize.x,
				formatTextSize.y + fontSizetextSize.y + fontTextSize.y);
		comboItem.setSize(formatTextSize.x + fontSizetextSize.x + fontTextSize.x, formatTextSize.y
				+ fontSizetextSize.y + fontTextSize.y);

		// Init Toolbar for text-formatting
		ToolBar menu = new ToolBar(coolbar, SWT.HORIZONTAL | SWT.FLAT);
		this.textFormatToolbarManager = new ToolBarManager(menu);
		CoolItem item = new CoolItem(coolbar, SWT.NONE);
		item.setControl(menu);

		//       
		//        

		menu = new ToolBar(coolbar, SWT.HORIZONTAL | SWT.FLAT);
		this.toolsActionsToolbarManager = new ToolBarManager(menu);
		item = new CoolItem(coolbar, SWT.NONE);
		item.setControl(menu);

		menu = new ToolBar(coolbar, SWT.HORIZONTAL | SWT.FLAT);
		this.fontsToolbarManager = new ToolBarManager(menu);
		item = new CoolItem(coolbar, SWT.NONE);
		item.setControl(menu);

		menu = new ToolBar(coolbar, SWT.HORIZONTAL | SWT.FLAT);
		this.layerToolbarManager = new ToolBarManager(menu);
		item = new CoolItem(coolbar, SWT.NONE);
		item.setControl(menu);

		//        
		menu = new ToolBar(coolbar, SWT.HORIZONTAL | SWT.FLAT);
		this.tableToolbarManager = new ToolBarManager(menu);
		item = new CoolItem(coolbar, SWT.NONE);
		item.setControl(menu);

		this.composer = new HtmlComposer(client, SWT.NONE);
		String html = getModelObject().getStringValue() == null ? "" : getModelObject()
				.getStringValue();
		/*
		 * I don't know if this will work. It can be that this line is executed
		 * before the whole browser-widget is loaded.
		 * 
		 * If not we have to add a PaintListener.
		 */
		this.composer.execute(JavaScriptCommands.SET_HTML(html));

		GridData gridData2 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData2.heightHint = 300;
		this.composer.setLayoutData(gridData2);

		initActions();

		// /* Set the sizes after adding all cool items */
		CoolItem[] coolItems = coolbar.getItems();
		for (int i = 0; i < coolItems.length; i++) {
			CoolItem coolItem = coolItems[i];
			Control control = coolItem.getControl();
			Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Point coolSize = coolItem.computeSize(size.x, size.y);
			if (control instanceof ToolBar) {
				ToolBar bar = (ToolBar) control;
				for (int j = 0, n = bar.getItemCount(); j < n; j++) {
					size.x += bar.getSize().x;
				}
				toolkit.adapt(bar);
			}
			coolItem.setMinimumSize(size);
			coolItem.setPreferredSize(coolSize);
			coolItem.setSize(coolSize);
		}

		doCreateSemanticSection(body, toolkit);

		form.reflow(true);

		bindValuesToUi();
	}

	private void initActions() {
		this.formatCombo.addSelectionListener(new SelectionAdapter() {
			private final Action action = new SelectFormatAction(RichTextEditPage.this.composer,
					RichTextEditPage.this.formatCombo);

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				this.action.run();
			}
		});

		this.fontCombo.addSelectionListener(new SelectionAdapter() {
			private final Action action = new SelectFontAction(RichTextEditPage.this.composer,
					RichTextEditPage.this.fontCombo);

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				this.action.run();
			}
		});

		this.fontsizeCombo.addSelectionListener(new SelectionAdapter() {
			private final Action action = new SelectFontSizeAction(RichTextEditPage.this.composer,
					RichTextEditPage.this.fontsizeCombo);

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				this.action.run();
			}
		});

		this.textFormatToolbarManager.add(new BoldAction(this.composer));
		this.textFormatToolbarManager.add(new ItalicAction(this.composer));
		this.textFormatToolbarManager.add(new UnderLineAction(this.composer));
		this.textFormatToolbarManager.add(new StrikeThroughAction(this.composer));
		this.textFormatToolbarManager.add(new Separator());
		this.textFormatToolbarManager.add(new JustifyLeftAction(this.composer));
		this.textFormatToolbarManager.add(new JustifyCenterAction(this.composer));
		this.textFormatToolbarManager.add(new JustifyRightAction(this.composer));
		this.textFormatToolbarManager.add(new JustifyFullAction(this.composer));
		this.textFormatToolbarManager.add(new Separator());
		this.textFormatToolbarManager.add(new BulletListAction(this.composer));
		this.textFormatToolbarManager.add(new NumListAction(this.composer));
		this.textFormatToolbarManager.add(new Separator());
		this.textFormatToolbarManager.add(new OutdentAction(this.composer));
		this.textFormatToolbarManager.add(new IndentAction(this.composer));
		this.textFormatToolbarManager.add(new Separator());
		this.textFormatToolbarManager.add(new SubAction(this.composer));
		this.textFormatToolbarManager.add(new SupAction(this.composer));
		this.textFormatToolbarManager.update(true);

		this.toolsActionsToolbarManager.add(new InsertEditImageAction(this.composer));
		this.toolsActionsToolbarManager.add(new InsertEditAnchorAction(this.composer));
		this.toolsActionsToolbarManager.add(new InsertEditLinkAction(this.composer));
		this.toolsActionsToolbarManager.add(new UnlinkAction(this.composer));
		this.toolsActionsToolbarManager.add(new HrAction(this.composer));
		this.toolsActionsToolbarManager.add(new InsertNonBreakingWhitespace(this.composer));
		this.toolsActionsToolbarManager.add(new Separator());
		this.toolsActionsToolbarManager.add(new CleanupAction(this.composer));
		this.toolsActionsToolbarManager.add(new RemoveFormatAction(this.composer));
		this.toolsActionsToolbarManager.add(new ToggleVisualAidAction(this.composer));
		this.toolsActionsToolbarManager.update(true);

		this.fontsToolbarManager.add(new ForegroundAction(this.composer));
		this.fontsToolbarManager.add(new BackColorAction(this.composer));
		this.fontsToolbarManager.update(true);

		this.layerToolbarManager.add(new InsertLayerAction(this.composer));
		this.layerToolbarManager.add(new MoveLayerBackwardAction(this.composer));
		this.layerToolbarManager.add(new MoveLayerForwardAction(this.composer));
		this.layerToolbarManager.add(new MakeLayerAbsoluteAction(this.composer));
		this.layerToolbarManager.update(true);

		this.tableToolbarManager.add(new InsertEditTableAction(this.composer));
		this.tableToolbarManager.add(new Separator());
		this.tableToolbarManager.add(new InsertRowBeforeAction(this.composer));
		this.tableToolbarManager.add(new InsertRowAfterAction(this.composer));
		this.tableToolbarManager.add(new DeleteRowAction(this.composer));
		this.tableToolbarManager.add(new Separator());
		this.tableToolbarManager.add(new InsertColumnBeforeAction(this.composer));
		this.tableToolbarManager.add(new InsertColumnAfterAction(this.composer));
		this.tableToolbarManager.add(new DeleteColumnAction(this.composer));
		this.tableToolbarManager.update(true);

	}

	@Override
	protected void bindValuesToUi() {
		super.bindValuesToUi();

		HtmlComposerObservable swtContent = new HtmlComposerObservable(this.composer);
		IObservableValue emfContent = EMFEditObservables.observeValue(this.editingDomain,
				getModelObject(), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		this.dataBindingContext.bindValue(swtContent, emfContent, null, null);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		monitor.setTaskName("Pre-save formatting...");
		final DOMParser parser = new DOMParser();

		try {
			parser.parse(new org.apache.xerces.xni.parser.XMLInputSource(null, null, null,
					new StringReader(getModelObject().getStringValue()), "UTF-8"));
			final Document document = parser.getDocument();
			NodeList elementsByTagName = document.getElementsByTagName("a");
			for (int i = 0; i < elementsByTagName.getLength(); i++) {
				final Node node = elementsByTagName.item(i);
				((Element) node).setAttribute("target", "_blank");
			}
			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			final DOMSource source = new DOMSource(document);
			final StringWriter writer = new StringWriter();

			final StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			getModelObject().setStringValue(writer.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.doSave(monitor);
	}

	private static String[] getFontList() {
		Set s = new HashSet();
		// Add names of all bitmap fonts.
		FontData[] fds = Display.getCurrent().getFontList(null, false);
		for (int i = 0; i < fds.length; ++i) {
			s.add(fds[i].getName());
		}
		// Add names of all scalable fonts.
		fds = Display.getCurrent().getFontList(null, true);
		for (int i = 0; i < fds.length; ++i) {
			s.add(fds[i].getName());
		}
		// Sort the result and print it.
		String[] answer = new String[s.size()];
		s.toArray(answer);
		Arrays.sort(answer);
		return answer;
	}

}
