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

package org.remus.infomngmnt.pdf.index;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.eclipse.core.resources.IFile;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.search.analyzer.ISecondaryAnalyzer;
import org.eclipse.remus.search.analyzer.ISecondaryIndex;
import org.eclipse.remus.search.analyzer.SecondaryIndex;
import org.eclipse.remus.util.InformationUtil;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PDFTextExtractor implements ISecondaryAnalyzer {

	/**
	 * 
	 */
	public PDFTextExtractor() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.analyzer.ISecondaryAnalyzer#analyze(org.remus
	 * .infomngmnt.InformationUnit, java.lang.String)
	 */
	public ISecondaryIndex[] analyze(final InformationUnit unit, final String node) {
		IFile binaryReferenceFile = InformationUtil.getBinaryReferenceFile(unit);
		List<ISecondaryIndex> returnValue = new ArrayList<ISecondaryIndex>();
		if (binaryReferenceFile != null) {
			PDDocument document = null;
			try {
				document = PDDocument.load(binaryReferenceFile.getLocationURI().toURL());
				PDFTextStripper stripper = new PDFTextStripper();
				stripper.setShouldSeparateByBeads(false);
				int numberOfPages = document.getNumberOfPages();
				for (int i = 1; i <= numberOfPages; i++) {

					try {
						stripper.setStartPage(i);
						stripper.setEndPage(i);
						StringWriter stringOutputStream = new StringWriter();
						stripper.writeText(document, stringOutputStream);
						returnValue.add(SecondaryIndex.CREATE("Page " + i, stringOutputStream
								.toString(), null));
					} catch (IOException e) {
						// skip
					}

				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (document != null) {
					try {
						document.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return returnValue.toArray(new ISecondaryIndex[returnValue.size()]);
	}

}
