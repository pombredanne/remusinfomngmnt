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
import java.util.Properties;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.remus.ui.widgets.databinding.AdditionalBindingWidgetFactory;
import org.eclipse.remus.ui.widgets.databinding.RichTextBindingWidget;
import org.eclipse.remus.ui.widgets.databinding.RichTextWidget;
import org.eclipse.remus.ui.widgets.richtext.ActionConfiguration;
import org.eclipse.remus.ui.widgets.richtext.actions.InsertEditAnchorAction;
import org.eclipse.remus.ui.widgets.richtext.actions.InsertEditImageAction;
import org.eclipse.remus.ui.widgets.richtext.actions.InsertEditLinkAction;
import org.eclipse.remus.ui.widgets.richtext.actions.UnlinkAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.spiritlink.richhtml4eclipse.widgets.AllActionConstants;
import de.spiritlink.richhtml4eclipse.widgets.PropertyConstants;

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

	@Override
	protected void renderPage(final IManagedForm managedForm) {
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

		this.richtext = new RichTextWidget(client, SWT.NONE, true, toolkit);

		//
		this.textFormatToolbarManager = this.richtext.crateToolbar();
		this.toolsActionsToolbarManager = this.richtext.crateToolbar();
		this.fontsToolbarManager = this.richtext.crateToolbar();
		this.layerToolbarManager = this.richtext.crateToolbar();
		this.tableToolbarManager = this.richtext.crateToolbar();

		GridData gridData2 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData2.heightHint = 500;
		this.richtext.setLayoutData(gridData2);

		initActions();

		this.richtext.adjustBars();

		doCreateSemanticSection(body, toolkit);

		form.reflow(true);

	}

	private void initActions() {
		ActionConfiguration.fillTextFormattingToolbar(this.richtext.getComposer(),
				this.textFormatToolbarManager);
		this.textFormatToolbarManager.update(true);

		this.toolsActionsToolbarManager.add(new InsertEditImageAction(this.richtext.getComposer()));
		this.toolsActionsToolbarManager
				.add(new InsertEditAnchorAction(this.richtext.getComposer()));
		this.toolsActionsToolbarManager.add(new InsertEditLinkAction(this.richtext.getComposer()));
		this.toolsActionsToolbarManager.add(new UnlinkAction(this.richtext.getComposer()));
		// this.toolsActionsToolbarManager.add(new
		// HrAction(this.richtext.getComposer()));
		ActionConfiguration.fillToolsToolbar(this.richtext.getComposer(),
				this.toolsActionsToolbarManager);
		this.toolsActionsToolbarManager.update(true);

		ActionConfiguration.fillColorFormattingToolbar(this.richtext.getComposer(),
				this.fontsToolbarManager);
		this.fontsToolbarManager.update(true);

		ActionConfiguration.fillLayerFormattingToolbar(this.richtext.getComposer(),
				this.layerToolbarManager);
		this.layerToolbarManager.update(true);

		ActionConfiguration.fillTableFormattingToolbar(this.richtext.getComposer(),
				this.tableToolbarManager);
		this.tableToolbarManager.update(true);

	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();

		String html = getModelObject().getStringValue() == null ? "" : getModelObject()
				.getStringValue();
		this.richtext.setHtml(html);

		RichTextBindingWidget widget = AdditionalBindingWidgetFactory.createRichText(this.richtext,
				this);
		widget.bindModel(getModelObject(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		monitor.setTaskName("Pre-save formatting...");
		doSaveEditor();
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

	private void doSaveEditor() {
		final String[] html = new String[1];
		Listener listener = new Listener() {
			public void handleEvent(final Event event) {
				Properties evtProps = (Properties) event.data;
				if (evtProps.getProperty(PropertyConstants.COMMAND).equals(
						AllActionConstants.GET_HTML)) {
					html[0] = evtProps.getProperty(PropertyConstants.VALUE) == null ? "" : evtProps.getProperty(PropertyConstants.VALUE); //$NON-NLS-1$ 
				}
			}
		};
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
