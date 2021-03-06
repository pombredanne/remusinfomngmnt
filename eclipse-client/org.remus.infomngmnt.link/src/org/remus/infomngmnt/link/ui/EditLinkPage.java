/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.link.ui;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.commands.DeleteBinaryReferenceCommand;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.link.LinkActivator;
import org.remus.infomngmnt.link.messsage.Messages;
import org.remus.infomngmnt.link.webshot.WebshotUtil;
import org.remus.infomngmnt.operation.IndexWebPageRunnable;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EditLinkPage extends AbstractInformationFormPage {

	private Text text;

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.CENTER, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText(Messages.EditLinkPage_General);

		final Composite composite = toolkit.createComposite(generalSection, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		toolkit.paintBordersFor(composite);
		generalSection.setClient(composite);

		toolkit.createLabel(composite, Messages.EditLinkPage_Url, SWT.NONE);

		this.text = toolkit.createText(composite, null, SWT.NONE);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new Label(composite, SWT.NONE);

		Hyperlink createHyperlink = toolkit.createHyperlink(composite,
				Messages.EditLinkPage_OpenUrlInSystemBrowser, SWT.NONE);
		createHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				Program.launch(getModelObject().getStringValue());
			}
		});
		new Label(composite, SWT.NONE);

		final ImageHyperlink refreshWebshotImageHyperlink = toolkit.createImageHyperlink(composite,
				SWT.NONE);
		refreshWebshotImageHyperlink.setText(Messages.EditLinkPage_RefreshWebshot);
		refreshWebshotImageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				IFile tmpFile = ResourceUtil.createTempFile("png"); //$NON-NLS-1$
				WebshotUtil.performWebShot(getModelObject().getStringValue(), tmpFile.getLocation()
						.toOSString());
				CompoundCommand cc = new CompoundCommand();
				if (getModelObject().getBinaryReferences() != null) {
					DeleteBinaryReferenceCommand command = new DeleteBinaryReferenceCommand(
							getModelObject().getBinaryReferences(), getEditingDomain());
					cc.append(command);
				}
				cc.append(CommandFactory.addFileToInfoUnit(tmpFile, getModelObject(),
						getEditingDomain()));
				cc.setLabel(Messages.EditLinkPage_RefreshWebshot);
				getEditingDomain().getCommandStack().execute(cc);
			}
		});
		new Label(composite, SWT.NONE);

		final ImageHyperlink refreshSearchableContentImageHyperlink = toolkit.createImageHyperlink(
				composite, SWT.NONE);
		refreshSearchableContentImageHyperlink.setText(Messages.EditLinkPage_RefreshSearchablecontent);
		refreshSearchableContentImageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				ProgressMonitorDialog pmd = new ProgressMonitorDialog(getSite().getShell());
				IndexWebPageRunnable runnable = new IndexWebPageRunnable(getModelObject()
						.getStringValue());
				try {
					pmd.run(true, true, runnable);
					String content = runnable.getContent();
					InformationUnit childByType = InformationUtil.getChildByType(getModelObject(),
							LinkActivator.NODE_INDEX);
					Command create = SetCommand.create(getEditingDomain(), childByType,
							InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE, content);
					((AbstractCommand) create).setLabel(Messages.EditLinkPage_RefreshSearchablecontent);
					getEditingDomain().getCommandStack().execute(create);
				} catch (InterruptedException e1) {
					// do nothing.
				} catch (Exception e1) {
					ErrorDialog.openError(getSite().getShell(), Messages.EditLinkPage_ErrorRefresh,
							Messages.EditLinkPage_ErrorRefreshDescription, StatusCreator
									.newStatus(Messages.EditLinkPage_ErrorGrabbingContent, e1));
				}
			}
		});

		doCreateSemanticSection(body, toolkit);

	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		ISWTObservableValue swtLink = SWTObservables.observeDelayedValue(500, SWTObservables
				.observeText(this.text, SWT.Modify));
		IObservableValue emfLink = EMFEditObservables.observeValue(Realm.getDefault(),
				this.editingDomain, getModelObject(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		this.dataBindingContext.bindValue(swtLink, emfLink, null, null);
	}

}
