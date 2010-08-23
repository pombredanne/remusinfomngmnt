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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.util.CategoryUtil;

import org.remus.infomngmnt.bibliographic.BibliographicActivator;

public class XmlExportOperation implements IRunnableWithProgress {

	private final String destinationFilename;
	private final Category category;

//	public static final ISchedulingRule ROOT_SCHEDULING_RULE = new RootSchedulingRule();
	
//	public static class RootSchedulingRule implements ISchedulingRule {
//
//		protected RootSchedulingRule() {
//		}
//
//		public boolean contains(ISchedulingRule rule) {
//			return rule instanceof RootSchedulingRule;
//		}
//
//		public boolean isConflicting(ISchedulingRule rule) {
//			return rule instanceof RootSchedulingRule;
//		}
//	}
	
	public XmlExportOperation(Category cat, String dest) {
		category = cat;
		destinationFilename = dest;
	}
	
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		
		if (Platform.isRunning()) {
			try {
				monitor.beginTask("XmlExportJob", IProgressMonitor.UNKNOWN);
//				Job.getJobManager().beginRule(ROOT_SCHEDULING_RULE, new SubProgressMonitor(monitor, 1));
				
				File destFile = new File(destinationFilename);
				PrintWriter outStream = new PrintWriter(new BufferedWriter(new FileWriter(destFile)));				
				outStream.println("<?xml version=\"1.0\" ?>");
				outStream.println("<bibtex:file xmlns:bibtex=\"http://bibtexml.sf.net/\">");
								
				InformationUnitListItem[] allInfoUnitItems = CategoryUtil.getAllInfoUnitItems(category);				
				for (InformationUnitListItem informationUnitListItem : allInfoUnitItems) {
					InformationUnit objectFromFile = (InformationUnit) informationUnitListItem.getAdapter(InformationUnit.class);	
					
					if (objectFromFile.getType().toString().equals("ARTICLE")) {						
						String tmp = getXMLExportString(objectFromFile, "article");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("BOOK")) {						
						String tmp = getXMLExportString(objectFromFile, "book");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("BOOKLET")) {						
						String tmp = getXMLExportString(objectFromFile, "booklet");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("CONFERENCE")) {						
						String tmp = getXMLExportString(objectFromFile, "conference");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("INBOOK")) {						
						String tmp = getXMLExportString(objectFromFile, "inbook");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("INCOLLECTION")) {						
						String tmp = getXMLExportString(objectFromFile, "incollection");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("INPROCEEDINGS")) {						
						String tmp = getXMLExportString(objectFromFile, "inproceedings");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("MANUAL")) {						
						String tmp = getXMLExportString(objectFromFile, "manual");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("MASTERSTHESIS")) {						
						String tmp = getXMLExportString(objectFromFile, "mastersthesis");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("MISC")) {						
						String tmp = getXMLExportString(objectFromFile, "misc");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("PHDTHESIS")) {						
						String tmp = getXMLExportString(objectFromFile, "phdthesis");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("PROCEEDINGS")) {						
						String tmp = getXMLExportString(objectFromFile, "proceedings");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("TECHREPORT")) {						
						String tmp = getXMLExportString(objectFromFile, "techreport");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("UNPUBLISHED")) {						
						String tmp = getXMLExportString(objectFromFile, "unpublished");						
						outStream.println(tmp);
					}
				}
				outStream.println("</bibtex:file>");
				outStream.close();
			} catch (Exception e) {
				Status status = new Status(IStatus.ERROR, BibliographicActivator.PLUGIN_ID, "Could not perform xml export", e);
				BibliographicActivator.getDefault().getLog().log(status);
			} finally {
//				Job.getJobManager().endRule(ROOT_SCHEDULING_RULE);
				monitor.done();
			}
		}
	}
	
	private String getXMLExportString(InformationUnit iu, String type) {
		String tstr, retString = "";
		InformationStructureRead read = InformationStructureRead.newSession(iu);
		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_BIBTEXKEY);
		if (tstr != null) 
			retString = retString.concat("<bibtex:entry id=\"" + tstr + "\">\n");
		else
			retString = retString.concat("<bibtex:entry id=\"\">\n");
		retString = retString.concat("<bibtex:" + type + ">\n");
		
		tstr = (String) iu.getLabel();
		if (tstr != null) retString = retString.concat("<bibtex:title>" + tstr + "</bibtex:title>\n");	
		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_AUTHOR);
		if (tstr != null) retString = retString.concat("<bibtex:author>" + tstr + "</bibtex:author>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_BOOKTITLE);
		if (tstr != null) retString = retString.concat("<bibtex:booktitle>" + tstr + "</bibtex:booktitle>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_CHAPTER);
		if (tstr != null) retString = retString.concat("<bibtex:chapter>" + tstr + "</bibtex:chapter>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_JOURNAL);
		if (tstr != null) retString = retString.concat("<bibtex:journal>" + tstr + "</bibtex:journal>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_PUBLISHER);
		if (tstr != null) retString = retString.concat("<bibtex:publisher>" + tstr + "</bibtex:publisher>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_YEAR);
		if (tstr != null) retString = retString.concat("<bibtex:year>" + tstr + "</bibtex:year>\n");	
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_SCHOOL);
		if (tstr != null) retString = retString.concat("<bibtex:school>" + tstr + "</bibtex:school>\n");	
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_INSTITUTION);
		if (tstr != null) retString = retString.concat("<bibtex:institution>" + tstr + "</bibtex:institution>\n");	

		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_VOLUME);
		if (tstr != null) retString = retString.concat("<bibtex:volume>" + tstr + "</bibtex:volume>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_SERIES);
		if (tstr != null) retString = retString.concat("<bibtex:series>" + tstr + "</bibtex:series>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ADDRESS);
		if (tstr != null) retString = retString.concat("<bibtex:address>" + tstr + "</bibtex:address>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_EDITION);
		if (tstr != null) retString = retString.concat("<bibtex:edition>" + tstr + "</bibtex:edition>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_MONTH);
		if (tstr != null) retString = retString.concat("<bibtex:month>" + tstr + "</bibtex:month>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_NOTE);
		if (tstr != null) retString = retString.concat("<bibtex:note>" + tstr + "</bibtex:note>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_PAGES);
		if (tstr != null) retString = retString.concat("<bibtex:pages>" + tstr + "</bibtex:pages>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ANNOTE);
		if (tstr != null) retString = retString.concat("<bibtex:annote>" + tstr + "</bibtex:annote>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_EDITOR);
		if (tstr != null) retString = retString.concat("<bibtex:editor>" + tstr + "</bibtex:editor>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_EPRINT);
		if (tstr != null) retString = retString.concat("<bibtex:eprint>" + tstr + "</bibtex:eprint>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_HOWPUBLISHED);
		if (tstr != null) retString = retString.concat("<bibtex:howpublished>" + tstr + "</bibtex:howpublished>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_NUMBER);
		if (tstr != null) retString = retString.concat("<bibtex:number>" + tstr + "</bibtex:number>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ORGANIZATION);
		if (tstr != null) retString = retString.concat("<bibtex:organization>" + tstr + "</bibtex:organization>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_TYPE);
		if (tstr != null) retString = retString.concat("<bibtex:type>" + tstr + "</bibtex:type>\n");		
		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ABSTRACT);
		if (tstr != null) retString = retString.concat("<bibtex:abstract>" + tstr + "</bibtex:abstract>\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_CONTENTS);
		if (tstr != null) retString = retString.concat("<bibtex:contents>" + tstr + "</bibtex:contents>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_SUMMARY);
		if (tstr != null) retString = retString.concat("<bibtex:summary>" + tstr + "</bibtex:summary>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ISBN);
		if (tstr != null) retString = retString.concat("<bibtex:isbn>" + tstr + "</bibtex:isbn>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ISSN);
		if (tstr != null) retString = retString.concat("<bibtex:issn>" + tstr + "</bibtex:issn>\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_DOI);
		if (tstr != null) retString = retString.concat("<bibtex:doi>" + tstr + "</bibtex:doi>\n");		
		
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

		retString = retString.concat("</bibtex:" + type + ">\n");
		retString = retString.concat("</bibtex:entry>");
		return retString;	
	}
	

}
