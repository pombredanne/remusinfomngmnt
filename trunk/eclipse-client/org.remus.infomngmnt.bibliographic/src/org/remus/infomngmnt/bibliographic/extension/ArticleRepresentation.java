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

import org.eclipse.emf.common.util.EList;
import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.core.model.InformationStructureRead;


/**
 * Concrete representation for article
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 *
 */
public class ArticleRepresentation extends BibliographicRepresentation {

	public ArticleRepresentation() {
		transformationFile = "template/article.flt";
	}
	
	public static String getXMLExportString(InformationUnit iu) {
		String tstr, retString = "";
		InformationStructureRead read = InformationStructureRead.newSession(iu);
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_BIBTEXKEY);
		if (tstr != null) 
			retString = retString.concat("<bibtex:entry id=\"" + tstr + "\">\n");
		else
			retString = retString.concat("<bibtex:entry id=\"\">\n");
		retString = retString.concat("<bibtex:article>\n");
		tstr = (String) iu.getLabel();
		if (tstr != null) retString = retString.concat("<bibtex:title>" + tstr + "</bibtex:title>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_AUTHOR);
		if (tstr != null) retString = retString.concat("<bibtex:author>" + tstr + "</bibtex:author>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_JOURNAL);
		if (tstr != null) retString = retString.concat("<bibtex:journal>" + tstr + "</bibtex:journal>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_YEAR);
		if (tstr != null) retString = retString.concat("<bibtex:year>" + tstr + "</bibtex:year>\n");
		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_VOLUME);
		if (tstr != null) retString = retString.concat("<bibtex:volume>" + tstr + "</bibtex:volume>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_NUMBER);
		if (tstr != null) retString = retString.concat("<bibtex:number>" + tstr + "</bibtex:number>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_PAGES);
		if (tstr != null) retString = retString.concat("<bibtex:pages>" + tstr + "</bibtex:pages>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_MONTH);
		if (tstr != null) retString = retString.concat("<bibtex:month>" + tstr + "</bibtex:month>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_NOTE);
		if (tstr != null) retString = retString.concat("<bibtex:note>" + tstr + "</bibtex:note>\n");
		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ABSTRACT);
		if (tstr != null) retString = retString.concat("<bibtex:abstract>" + tstr + "</bibtex:abstract>\n");
		
		tstr = (String) iu.getKeywords();
		if (tstr != null) retString = retString.concat("<bibtex:keywords>" + tstr + "</bibtex:keywords>\n");
		tstr = (String) iu.getDescription();
		if (tstr != null) retString = retString.concat("<bibtex:description>" + tstr + "</bibtex:description>\n");
		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_URL);
		if (tstr != null) retString = retString.concat("<bibtex:url>" + tstr + "</bibtex:url>\n");		
		List<BinaryReference> binaryReferences = read.getBinaryReferences(iu.getType(), true);
		EList<InformationUnit> fileLabels = read.getDynamicList(BibliographicActivator.NODE_NAME_FILES); 
		for (int i = 0; i < binaryReferences.size(); i++) {
			String projectRelativePath = binaryReferences.get(i).getProjectRelativePath();
			tstr = (String) fileLabels.get(i).getChildValues().get(0).getStringValue();
			String ext = tstr.substring(tstr.lastIndexOf(".") + 1);
			if ((tstr != null) && (ext != null)) retString = retString.concat("<bibtex:nstandard name=\"file\" type=\"" + ext + "\" description=\"" + tstr + "\">"
                    						+ projectRelativePath + "</bibtex:nstandard>\n");
		}

		retString = retString.concat("</bibtex:article>\n");
		retString = retString.concat("</bibtex:entry>");
		return retString;	
	}

}
