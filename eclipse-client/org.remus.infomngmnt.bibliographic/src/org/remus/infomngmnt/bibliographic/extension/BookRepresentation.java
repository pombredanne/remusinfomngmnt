/*******************************************************************************
 * Copyright (c) 2009 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein - bibliographic extensions
 *******************************************************************************/
package org.remus.infomngmnt.bibliographic.extension;

import java.util.List;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.core.model.InformationStructureRead;


/**
 * Concrete representation for book
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 *
 */
public class BookRepresentation extends BibliographicRepresentation {

	public BookRepresentation() {
		transformationFile = "template/book.flt";
	}
	
	public static String getXMLExportString(InformationUnit iu) {
		String retString = "";
		retString = "<bibtex:book>\n";
		InformationStructureRead read = InformationStructureRead.newSession(iu);
		//retString = retString.concat("<bibtex:title>" + read.getValueByNodeId("label") + "</bibtex:title>\n");
		
		String tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_AUTHOR);
		if (tstr != null) retString = retString.concat("<bibtex:author>" + tstr + "</bibtex:author>\n");
//		retString = retString.concat("<bibtex:publisher>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_PUBLISHER).toString() + "</bibtex:publisher>\n");
//		retString = retString.concat("<bibtex:year>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_YEAR).toString() + "</bibtex:year>\n");
//		retString = retString.concat("<bibtex:volume>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_VOLUME).toString() + "</bibtex:volume>\n");
//		retString = retString.concat("<bibtex:series>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_SERIES).toString() + "</bibtex:series>\n");
//		retString = retString.concat("<bibtex:address>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_ADDRESS).toString() + "</bibtex:address>\n");
//		retString = retString.concat("<bibtex:edition>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_EDITION).toString() + "</bibtex:edition>\n");
//		retString = retString.concat("<bibtex:month>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_MONTH).toString() + "</bibtex:month>\n");
//		retString = retString.concat("<bibtex:note>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_NOTE).toString() + "</bibtex:note>\n");
//		retString = retString.concat("<bibtex:key>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_KEY).toString() + "</bibtex:key>\n");
//		retString = retString.concat("<bibtex:pages>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_PAGES).toString() + "</bibtex:pages>\n");
//		retString = retString.concat("<bibtex:abstract>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_ABSTRACT).toString() + "</bibtex:abstract>\n");
//		retString = retString.concat("<bibtex:keywords>" + read.getValueByNodeId("keywords").toString() + "</bibtex:keywords>\n");
//		retString = retString.concat("<bibtex:description>" + read.getValueByNodeId("description").toString() + "</bibtex:description>\n");
//		retString = retString.concat("<bibtex:url>" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_URL).toString() + "</bibtex:url>\n");
//		
//		List<BinaryReference> binaryReferences = read.getBinaryReferences(iu.getType(), false);
//		if (binaryReferences.size() > 0 && binaryReferences.get(0) != null) {
//			String projectRelativePath = binaryReferences.get(0).getProjectRelativePath();
//			retString = retString.concat("<bibtex:nstandard name=\"file\" description=\"" + read.getValueByNodeId(BibliographicActivator.NODE_NAME_FILES_LABEL).toString() + "\">"
//                    						+ projectRelativePath + "</bibtex:nstandard>\n");
//		}
				
		retString = retString.concat("</bibtex:book>");
		return retString;	
	}

}
