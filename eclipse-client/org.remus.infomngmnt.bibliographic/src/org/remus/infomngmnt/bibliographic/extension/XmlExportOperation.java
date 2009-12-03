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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.EditingUtil;

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
						
					if (objectFromFile.getType().toString().equals("BOOK")) {						
						String tmp = BookRepresentation.getXMLExportString(objectFromFile);						
						outStream.println(tmp);
					}
					
					if (objectFromFile.getType().toString().equals("ARTICLE")) {						
						String tmp = ArticleRepresentation.getXMLExportString(objectFromFile);						
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
}
