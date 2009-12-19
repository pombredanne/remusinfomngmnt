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
import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.util.CategoryUtil;

public class BibtexExportOperation implements IRunnableWithProgress {

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
	
	public BibtexExportOperation(Category cat, String dest) {
		category = cat;
		destinationFilename = dest;
	}
	
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		
		if (Platform.isRunning()) {
			try {
				monitor.beginTask("BibtexExportJob", IProgressMonitor.UNKNOWN);
//				Job.getJobManager().beginRule(ROOT_SCHEDULING_RULE, new SubProgressMonitor(monitor, 1));
				
				File destFile = new File(destinationFilename);
				PrintWriter outStream = new PrintWriter(new BufferedWriter(new FileWriter(destFile)));				
				outStream.println("% This file was created with RIM.");
				outStream.println("% Encoding: Cp1252\n");
								
				InformationUnitListItem[] allInfoUnitItems = CategoryUtil.getAllInfoUnitItems(category);				
				for (InformationUnitListItem informationUnitListItem : allInfoUnitItems) {
					InformationUnit objectFromFile = (InformationUnit) informationUnitListItem.getAdapter(InformationUnit.class);
						
					if (objectFromFile.getType().toString().equals("BOOK")) {						
						String tmp = getBibtexExportString(objectFromFile, "BOOK");						
						outStream.println(tmp);
					}					
					if (objectFromFile.getType().toString().equals("ARTICLE")) {						
						String tmp = getBibtexExportString(objectFromFile, "ARTICLE");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("BOOKLET")) {						
						String tmp = getBibtexExportString(objectFromFile, "BOOKLET");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("CONFERENCE")) {						
						String tmp = getBibtexExportString(objectFromFile, "CONFERENCE");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("INBOOK")) {						
						String tmp = getBibtexExportString(objectFromFile, "INBOOK");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("INCOLLECTION")) {						
						String tmp = getBibtexExportString(objectFromFile, "INCOLLECTION");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("INPROCEEDINGS")) {						
						String tmp = getBibtexExportString(objectFromFile, "INPROCEEDINGS");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("MANUAL")) {						
						String tmp = getBibtexExportString(objectFromFile, "MANUAL");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("MASTERSTHESIS")) {						
						String tmp = getBibtexExportString(objectFromFile, "MASTERSTHESIS");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("MISC")) {						
						String tmp = getBibtexExportString(objectFromFile, "MISC");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("PHDTHESIS")) {						
						String tmp = getBibtexExportString(objectFromFile, "PHDTHESIS");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("PROCEEDINGS")) {						
						String tmp = getBibtexExportString(objectFromFile, "PROCEEDINGS");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("TECHREPORT")) {						
						String tmp = getBibtexExportString(objectFromFile, "TECHREPORT");						
						outStream.println(tmp);
					}
					if (objectFromFile.getType().toString().equals("UNPUBLISHED")) {						
						String tmp = getBibtexExportString(objectFromFile, "UNPUBLISHED");						
						outStream.println(tmp);
					}
				}

				outStream.close();
			} catch (Exception e) {
				Status status = new Status(IStatus.ERROR, BibliographicActivator.PLUGIN_ID, "Could not perform bibtex export", e);
				BibliographicActivator.getDefault().getLog().log(status);
			} finally {
//				Job.getJobManager().endRule(ROOT_SCHEDULING_RULE);
				monitor.done();
			}
		}
	}
	
	public static String getBibtexExportString(InformationUnit iu, String type) {
		String tstr, retString = "";
		InformationStructureRead read = InformationStructureRead.newSession(iu);
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_BIBTEXKEY);
		if (tstr != null) 
			retString = retString.concat("@" + type + "{" + tstr + ",\n");
		else
			retString = retString.concat("@" + type + "{,\n");

		tstr = (String) iu.getLabel();
		if (tstr != null) retString = retString.concat("  title = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_AUTHOR);
		if (tstr != null) retString = retString.concat("  author = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_BOOKTITLE);
		if (tstr != null) retString = retString.concat("  booktitle = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_CHAPTER);		
		if (tstr != null) retString = retString.concat("  chapter = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_PUBLISHER);
		if (tstr != null) retString = retString.concat("  publisher = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_YEAR);
		if (tstr != null) retString = retString.concat("  year = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_JOURNAL);
		if (tstr != null) retString = retString.concat("  journal = {" + tstr + "},\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_SCHOOL);
		if (tstr != null) retString = retString.concat("  school = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_INSTITUTION);
		if (tstr != null) retString = retString.concat("  institution = {" + tstr + "},\n");

		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_VOLUME);
		if (tstr != null) retString = retString.concat("  volume = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_SERIES);
		if (tstr != null) retString = retString.concat("  series = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ADDRESS);
		if (tstr != null) retString = retString.concat("  address = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_EDITION);
		if (tstr != null) retString = retString.concat("  edition = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_MONTH);
		if (tstr != null) retString = retString.concat("  month = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_NOTE);
		if (tstr != null) retString = retString.concat("  note = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_PAGES);
		if (tstr != null) retString = retString.concat("  pages = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ANNOTE);
		if (tstr != null) retString = retString.concat("  annote = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_EDITOR);
		if (tstr != null) retString = retString.concat("  editor = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_EPRINT);
		if (tstr != null) retString = retString.concat("  eprint = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_HOWPUBLISHED);
		if (tstr != null) retString = retString.concat("  howpublished = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_KEY);
		if (tstr != null) retString = retString.concat("  key = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_NUMBER);
		if (tstr != null) retString = retString.concat("  number = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ORGANIZATION);
		if (tstr != null) retString = retString.concat("  organization = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_TYPE);
		if (tstr != null) retString = retString.concat("  type = {" + tstr + "},\n");		
		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ABSTRACT);
		if (tstr != null) retString = retString.concat("  abstract = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_CONTENTS);
		if (tstr != null) retString = retString.concat("  contents = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_SUMMARY);
		if (tstr != null) retString = retString.concat("  summary = {" + tstr + "},\n");		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ISBN);
		if (tstr != null) retString = retString.concat("  isbn = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_ISSN);
		if (tstr != null) retString = retString.concat("  issn = {" + tstr + "},\n");
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_DOI);
		if (tstr != null) retString = retString.concat("  doi = {" + tstr + "},\n");		
		
		tstr = (String) iu.getKeywords();
		if (tstr != null) retString = retString.concat("  keywords = {" + tstr + "},\n");
		tstr = (String) iu.getDescription();
		if (tstr != null) retString = retString.concat("  description = {" + tstr + "},\n");
		
		tstr = (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_URL);
		if (tstr != null) retString = retString.concat("  url = {" + tstr + "},\n");	
		List<BinaryReference> binaryReferences = read.getBinaryReferences(iu.getType(), true);
		EList<InformationUnit> fileLabels = read.getDynamicList(BibliographicActivator.NODE_NAME_FILES); 
		for (int i = 0; i < binaryReferences.size(); i++) {
			String projectRelativePath = binaryReferences.get(i).getProjectRelativePath();
			tstr = (String) fileLabels.get(i).getChildValues().get(0).getStringValue();
			String ext = tstr.substring(tstr.lastIndexOf(".") + 1);
			if ((tstr != null) && (ext != null)) retString = retString.concat("  file = {" + tstr + ":"
                    						+ projectRelativePath + ":" + ext + "}\n");
		}		
		
		retString = retString.concat("}\n\n");
		return retString;	
	}

}
