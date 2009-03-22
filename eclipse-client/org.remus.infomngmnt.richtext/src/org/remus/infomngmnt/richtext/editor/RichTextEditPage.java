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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.RichTextBindingWidget;
import org.remus.infomngmnt.common.ui.richtext.ActionConfiguration;
import org.remus.infomngmnt.common.ui.richtext.RichTextWidget;
import org.remus.infomngmnt.common.ui.richtext.actions.HrAction;
import org.remus.infomngmnt.common.ui.richtext.actions.InsertEditAnchorAction;
import org.remus.infomngmnt.common.ui.richtext.actions.InsertEditImageAction;
import org.remus.infomngmnt.common.ui.richtext.actions.InsertEditLinkAction;
import org.remus.infomngmnt.common.ui.richtext.actions.UnlinkAction;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

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

		this.richtext = new RichTextWidget(client, SWT.NONE, true, toolkit);

		//
		this.textFormatToolbarManager = this.richtext.crateToolbar();
		this.toolsActionsToolbarManager = this.richtext.crateToolbar();
		this.fontsToolbarManager = this.richtext.crateToolbar();
		this.layerToolbarManager = this.richtext.crateToolbar();
		this.tableToolbarManager = this.richtext.crateToolbar();

		String html = getModelObject().getStringValue() == null ? "" : getModelObject()
				.getStringValue();
		this.richtext.setHtml(html);

		GridData gridData2 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData2.heightHint = 500;
		this.richtext.setLayoutData(gridData2);

		initActions();

		this.richtext.adjustBars();

		doCreateSemanticSection(body, toolkit);

		form.reflow(true);

		bindValuesToUi();
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
		this.toolsActionsToolbarManager.add(new HrAction(this.richtext.getComposer()));
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
	protected void bindValuesToUi() {
		super.bindValuesToUi();
		RichTextBindingWidget widget = BindingWidgetFactory.createRichText(this.richtext, this);
		widget.bindModel(getModelObject(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

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
