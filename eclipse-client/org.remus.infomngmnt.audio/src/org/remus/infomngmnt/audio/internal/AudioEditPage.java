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

package org.remus.infomngmnt.audio.internal;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.audio.AudioActivator;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AudioEditPage extends AbstractInformationFormPage {

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
		generalSection.setText("General");

		final Composite composite = toolkit.createComposite(generalSection, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 5;
		composite.setLayout(gridLayout);
		toolkit.paintBordersFor(composite);
		generalSection.setClient(composite);

		toolkit.createLabel(composite, "Audio-Name", SWT.NONE);

		this.text = toolkit.createText(composite, null, SWT.READ_ONLY);
		this.text.setEditable(false);

		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));

		new Label(composite, SWT.NONE);

		final Hyperlink openImageWithExternalApp = toolkit.createHyperlink(composite,
				"Open Audio file with the default external application", SWT.NONE);
		openImageWithExternalApp.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4,
				1));
		openImageWithExternalApp.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				IFile adapter = (IFile) getModelObject().getAdapter(IFile.class);
				IFolder folder = adapter.getParent().getFolder(new Path(getModelObject().getId()));
				if (folder.exists()) {
					InformationStructureRead read = InformationStructureRead
							.newSession(getModelObject());
					String origString = (String) read
							.getValueByNodeId(AudioActivator.NODE_NAME_MEDIATYPE);
					IPath path = new Path(origString);
					IFile imageFile = folder.getFile(new Path(getModelObject().getId())
							.addFileExtension(path.getFileExtension()));
					Program.launch(imageFile.getLocation().toOSString());
				}
			}
		});
		new Label(composite, SWT.NONE);

		final Hyperlink changeImageHyperlink = toolkit.createHyperlink(composite,
				"Change audio file", SWT.NONE);
		changeImageHyperlink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		changeImageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				FileDialog fd = new FileDialog(getSite().getShell());
				fd.setFilterExtensions(new String[] { "*.mp3;*.wav" });
				fd.setFilterNames(new String[] { "Supported Audios (MP3)" });
				String open = fd.open();
				if (open != null) {

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
				InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL);
		this.dataBindingContext.bindValue(swtLink, emfLink, null, null);

		addControl(this.text);
	}
}