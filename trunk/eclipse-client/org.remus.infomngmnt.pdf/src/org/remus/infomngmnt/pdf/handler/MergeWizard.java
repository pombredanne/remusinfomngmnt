/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.pdf.handler;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.pdf.ui.NewPdfWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MergeWizard extends NewPdfWizard {

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		this.page1 = new MergeWizardPage(selection.toArray());
		setCategoryToPage();
	}

	@Override
	protected IFile getTmpFile() {
		List<InformationUnitListItem> elements = ((MergeWizardPage) this.page1).getElements();
		PDFMergerUtility merger = new PDFMergerUtility();

		for (InformationUnitListItem informationUnitListItem : elements) {
			IFile binaryReferenceFile = InformationUtil
					.getBinaryReferenceFile((InformationUnit) informationUnitListItem
							.getAdapter(InformationUnit.class));
			if (binaryReferenceFile != null) {
				merger.addSource(binaryReferenceFile.getLocation().toOSString());
			}

		}
		IFile createTempFile = ResourceUtil.createTempFile("pdf");
		merger.setDestinationFileName(createTempFile.getLocation().toOSString());
		try {
			merger.mergeDocuments();
			createTempFile.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (COSVisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createTempFile;
	}
}
