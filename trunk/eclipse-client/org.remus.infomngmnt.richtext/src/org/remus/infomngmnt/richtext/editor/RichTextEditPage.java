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

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.mylyn.htmltext.HtmlComposer;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.remus.ui.widgets.databinding.HtmlComposerObservable;
import org.eclipse.remus.ui.widgets.databinding.RichTextWidget;
import org.eclipse.remus.ui.widgets.richtext.ActionConfiguration;
import org.eclipse.remus.ui.widgets.richtext.actions.BoldAction;
import org.eclipse.remus.ui.widgets.richtext.actions.BulletlistAction;
import org.eclipse.remus.ui.widgets.richtext.actions.IndentAction;
import org.eclipse.remus.ui.widgets.richtext.actions.InsertEditAnchorAction;
import org.eclipse.remus.ui.widgets.richtext.actions.InsertEditImageAction;
import org.eclipse.remus.ui.widgets.richtext.actions.InsertEditLinkAction;
import org.eclipse.remus.ui.widgets.richtext.actions.InsertHrAction;
import org.eclipse.remus.ui.widgets.richtext.actions.ItalicAction;
import org.eclipse.remus.ui.widgets.richtext.actions.JustifyBlockAction;
import org.eclipse.remus.ui.widgets.richtext.actions.JustifyCenterAction;
import org.eclipse.remus.ui.widgets.richtext.actions.JustifyLeftAction;
import org.eclipse.remus.ui.widgets.richtext.actions.JustifyRightAction;
import org.eclipse.remus.ui.widgets.richtext.actions.NumlistAction;
import org.eclipse.remus.ui.widgets.richtext.actions.OutdentAction;
import org.eclipse.remus.ui.widgets.richtext.actions.SetFontfamilyDropdownAction;
import org.eclipse.remus.ui.widgets.richtext.actions.SetFormatDropdownAction;
import org.eclipse.remus.ui.widgets.richtext.actions.SetSizeDropdownAction;
import org.eclipse.remus.ui.widgets.richtext.actions.StrikeAction;
import org.eclipse.remus.ui.widgets.richtext.actions.SubscriptAction;
import org.eclipse.remus.ui.widgets.richtext.actions.SuperscriptAction;
import org.eclipse.remus.ui.widgets.richtext.actions.UnderlineAction;
import org.eclipse.remus.ui.widgets.richtext.actions.UnlinkAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RichTextEditPage extends AbstractInformationFormPage {

	private ToolBarManager textFormatToolbarManager;
	private ToolBarManager toolsActionsToolbarManager;
	private ToolBarManager fontsToolbarManager;
	private ToolBarManager layerToolbarManager;
	private ToolBarManager tableToolbarManager;
	private RichTextWidget richtext;
	private HtmlComposer composer;

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section generalSection = toolkit.createSection(body,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE
						| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL,
				SWT.BEGINNING, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite client = toolkit.createComposite(generalSection,
				SWT.NONE);
		client.setLayout(new GridLayout());
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.minimumHeight = 400;
		client.setLayoutData(gridData);
		generalSection.setClient(client);

		// richtext = new RichTextWidget(client, SWT.NONE, true, toolkit);
		//
		// //
		// textFormatToolbarManager = richtext.crateToolbar();
		// toolsActionsToolbarManager = richtext.crateToolbar();
		// fontsToolbarManager = richtext.crateToolbar();
		// layerToolbarManager = richtext.crateToolbar();
		// tableToolbarManager = richtext.crateToolbar();
		//
		// GridData gridData2 = new GridData(SWT.FILL, SWT.BEGINNING, true,
		// false);
		// gridData2.heightHint = 500;
		// richtext.setLayoutData(gridData2);
		//
		// initActions();
		//
		// richtext.adjustBars();
		createWidget(client);

		doCreateSemanticSection(body, toolkit);

		form.reflow(true);

	}

	private void createWidget(Composite parent) {
		final Composite comp = new Composite(parent, SWT.NO_FOCUS);
		comp.setLayout(new GridLayout(1, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		composer = new HtmlComposer(comp, SWT.NONE);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.heightHint = 400;
		composer.setLayoutData(layoutData);

		CoolBar coolbar = new CoolBar(comp, SWT.NONE);

		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.widthHint = 100;
		coolbar.setLayoutData(gd);

		coolbar.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				comp.getShell().layout();
			}
		});

		ToolBar menu = new ToolBar(coolbar, SWT.HORIZONTAL | SWT.FLAT);
		ToolBarManager manager = new ToolBarManager(menu);
		CoolItem item = new CoolItem(coolbar, SWT.NONE);
		item.setControl(menu);

		manager.add(new SetFormatDropdownAction("--[Format]--", composer));
		manager.add(new SetSizeDropdownAction("--[Size]--", composer));
		manager.add(new SetFontfamilyDropdownAction("--[Font]--", composer));
		manager.update(true);

		menu = new ToolBar(coolbar, SWT.HORIZONTAL | SWT.FLAT);
		manager = new ToolBarManager(menu);
		item = new CoolItem(coolbar, SWT.NONE);
		item.setControl(menu);

		//
		//

		manager.add(new BoldAction(composer));
		manager.add(new ItalicAction(composer));
		manager.add(new UnderlineAction(composer));
		manager.add(new StrikeAction(composer));
		manager.add(new Separator());
		manager.add(new JustifyLeftAction(composer));
		manager.add(new JustifyCenterAction(composer));
		manager.add(new JustifyRightAction(composer));
		manager.add(new JustifyBlockAction(composer));
		manager.add(new Separator());
		manager.add(new BulletlistAction(composer));
		manager.add(new NumlistAction(composer));
		manager.add(new Separator());
		manager.add(new OutdentAction(composer));
		manager.add(new IndentAction(composer));
		manager.add(new Separator());
		manager.add(new SubscriptAction(composer));
		manager.add(new SuperscriptAction(composer));
		manager.update(true);

		menu = new ToolBar(coolbar, SWT.HORIZONTAL | SWT.FLAT);
		manager = new ToolBarManager(menu);
		item = new CoolItem(coolbar, SWT.NONE);
		item.setControl(menu);

		manager.add(new InsertEditImageAction(composer));
		manager.add(new InsertEditAnchorAction(composer));
		manager.add(new InsertEditLinkAction(composer));
		manager.add(new UnlinkAction(composer));
		manager.add(new InsertHrAction(composer));
		// manager.add(new InsertNonBreakingWhitespace(composer));
		// manager.add(new Separator());
		// manager.add(new CleanupAction(composer));
		// manager.add(new RemoveFormatAction(composer));
		// manager.add(new ToggleVisualAidAction(composer));
		// manager.add(new Separator());
		// manager.add(new UndoAction(composer));
		// manager.add(new RedoAction(composer));
		manager.update(true);

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
			}
			coolItem.setMinimumSize(size);
			coolItem.setPreferredSize(coolSize);
			coolItem.setSize(coolSize);
		}

	}

	private void initActions() {
		ActionConfiguration.fillTextFormattingToolbar(richtext.getComposer(),
				textFormatToolbarManager);
		textFormatToolbarManager.update(true);

		toolsActionsToolbarManager.add(new InsertEditImageAction(richtext
				.getComposer()));
		toolsActionsToolbarManager.add(new InsertEditAnchorAction(richtext
				.getComposer()));
		toolsActionsToolbarManager.add(new InsertEditLinkAction(richtext
				.getComposer()));
		toolsActionsToolbarManager
				.add(new UnlinkAction(richtext.getComposer()));
		// this.toolsActionsToolbarManager.add(new
		// HrAction(this.richtext.getComposer()));
		ActionConfiguration.fillToolsToolbar(richtext.getComposer(),
				toolsActionsToolbarManager);
		toolsActionsToolbarManager.update(true);

		ActionConfiguration.fillColorFormattingToolbar(richtext.getComposer(),
				fontsToolbarManager);
		fontsToolbarManager.update(true);

		ActionConfiguration.fillLayerFormattingToolbar(richtext.getComposer(),
				layerToolbarManager);
		layerToolbarManager.update(true);

		ActionConfiguration.fillTableFormattingToolbar(richtext.getComposer(),
				tableToolbarManager);
		tableToolbarManager.update(true);

	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		HtmlComposerObservable htmlComposerObservable = new HtmlComposerObservable(
				composer);
		IObservableValue emfContent = EMFEditObservables.observeValue(
				getEditingDomain(), getModelObject(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		getDatabindingContext().bindValue(htmlComposerObservable, emfContent);

	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		monitor.setTaskName("Pre-save formatting...");
		doSaveEditor();
		final DOMParser parser = new DOMParser();

		try {
			parser.parse(new org.apache.xerces.xni.parser.XMLInputSource(null,
					null, null, new StringReader(getModelObject()
							.getStringValue()), "UTF-8"));
			final Document document = parser.getDocument();
			NodeList elementsByTagName = document.getElementsByTagName("a");
			for (int i = 0; i < elementsByTagName.getLength(); i++) {
				final Node node = elementsByTagName.item(i);
				((Element) node).setAttribute("target", "_blank");
			}
			final Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
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

	private void doSaveEditor() {
		// final String[] html = new String[1];
		// Listener listener = new Listener() {
		// public void handleEvent(final Event event) {
		// Properties evtProps = (Properties) event.data;
		// if (evtProps.getProperty(PropertyConstants.COMMAND).equals(
		// AllActionConstants.GET_HTML)) {
		//					html[0] = evtProps.getProperty(PropertyConstants.VALUE) == null ? "" : evtProps.getProperty(PropertyConstants.VALUE); //$NON-NLS-1$ 
		// }
		// }
		// };
		// this.richtext.getComposer().addListener(EventConstants.ALL,
		// listener);
		// this.richtext.getComposer().execute(JavaScriptCommands.GET_HTML);
		// while (html[0] == null) {
		// if (!getSite().getShell().getDisplay().readAndDispatch()) {
		// getSite().getShell().getDisplay().sleep();
		// }
		// }
		// getModelObject().setStringValue(html[0]);
		// this.richtext.getComposer().removeListener(EventConstants.ALL,
		// listener);

	}

}
