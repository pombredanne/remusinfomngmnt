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

package org.remus.infomngmnt.search.local;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.service.ILuceneCustomizer;
import org.remus.infomngmnt.search.service.LuceneSearchService;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DefaultLuceneCustomizer implements ILuceneCustomizer {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.search.service.ILuceneCustomizer#getAnalyser()
	 */
	public Analyzer getAnalyser() {
		return new StandardAnalyzer();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.search.service.ILuceneCustomizer#getIndexDirectory(org.eclipse.core.resources.IProject)
	 */
	public Directory getIndexDirectory(IProject project) {
		File file = Activator.getDefault().getStateLocation().append(project.getName()).toFile();
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			return FSDirectory.getDirectory(file);
		} catch (IOException e) {
			// FIXME error handling??
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.search.service.ILuceneCustomizer#getLuceneDocument(org.remus.infomngmnt.InformationUnit, org.eclipse.core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public Document getLuceneDocument(InformationUnit document, IProject project, IProgressMonitor monitor) {
		IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(document.getType());
		AbstractInformationRepresentation informationRepresentation = infoTypeByType.getInformationRepresentation();
		Document returnValue = new Document();

		Field idField = new Field(
				LuceneSearchService.SEARCHINDEX_ITEM_ID, document.getId(),Field.Store.YES,Field.Index.UN_TOKENIZED);
		returnValue.add(idField);

		try {
			Field titleField = new Field(
					LuceneSearchService.SEARCHINDEX_LABEL, checkNull(informationRepresentation.getTitleForIndexing(monitor)),Field.Store.YES, Field.Index.TOKENIZED);
			titleField.setBoost(2.0f);
			returnValue.add(titleField);
		} catch (CoreException e) {
			// no title will be added...
		}
		try {
			Field contentField = new Field(
					LuceneSearchService.SEARCHINDEX_CONTENT, checkNull(informationRepresentation.getBodyForIndexing(monitor)),Field.Store.YES, Field.Index.TOKENIZED);
			contentField.setBoost(1.5f);
			returnValue.add(contentField);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Field additionalField = new Field(
					LuceneSearchService.SEARCHINDEX_ADDITIONALS, checkNull(informationRepresentation.getAdditionalsForIndexing(monitor)),Field.Store.YES, Field.Index.TOKENIZED);
			additionalField.setBoost(1.2f);
			returnValue.add(additionalField);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Field descriptionField = new Field(
				LuceneSearchService.SEARCHINDEX_DESCRIPTION, checkNull(document.getDescription()),Field.Store.YES,Field.Index.TOKENIZED);
		descriptionField.setBoost(1.5f);
		returnValue.add(descriptionField);

		Field keywordField = new Field(
				LuceneSearchService.SEARCHINDEX_KEYWORDS, checkNull(document.getKeywords()),Field.Store.YES,Field.Index.TOKENIZED);
		descriptionField.setBoost(1.9f);
		returnValue.add(keywordField);

		Field dateField = new Field(
				LuceneSearchService.SEARCHINDEX_CREATIONDATE, DATE_FORMAT.format(document.getCreationDate()),Field.Store.YES,Field.Index.TOKENIZED);
		returnValue.add(dateField);

		Field projectField = new Field(
				LuceneSearchService.SEARCHINDEX_PROJECT, project.getName(),Field.Store.YES,Field.Index.UN_TOKENIZED);
		returnValue.add(projectField);

		Field infoTypeField = new Field(
				LuceneSearchService.SEARCHINDEX_INFOTYPE_ID, document.getType(),Field.Store.YES,Field.Index.UN_TOKENIZED);
		returnValue.add(infoTypeField);

		return returnValue;
	}


	private static String checkNull(String str) {
		return str == null ? "" : str;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.search.service.ILuceneCustomizer#getQueryStringFromSearchQuery(org.remus.search.Search, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public Query getQueryStringFromSearchQuery(Search search,
			IProgressMonitor monitor) {

		List<String> termList = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();
		List<Occur> flagList = new ArrayList<Occur>();
		if (search.getSearchString() != null) {
			fieldList.add(LuceneSearchService.SEARCHINDEX_LABEL);
			termList.add(search.getSearchString());
			flagList.add(Occur.SHOULD);

			fieldList.add(LuceneSearchService.SEARCHINDEX_CONTENT);
			termList.add(search.getSearchString());
			flagList.add(Occur.SHOULD);

			fieldList.add(LuceneSearchService.SEARCHINDEX_ADDITIONALS);
			termList.add(search.getSearchString());
			flagList.add(Occur.SHOULD);

			fieldList.add(LuceneSearchService.SEARCHINDEX_DESCRIPTION);
			termList.add(search.getSearchString());
			flagList.add(Occur.SHOULD);

			fieldList.add(LuceneSearchService.SEARCHINDEX_KEYWORDS);
			termList.add(search.getSearchString());
			flagList.add(Occur.SHOULD);


		}

		Query searchWordQuery = null;
		try {
			searchWordQuery = MultiFieldQueryParser.parse(
					termList.toArray(new String[termList.size()]),
					fieldList.toArray(new String[fieldList.size()]),
					flagList.toArray(new Occur[flagList.size()]), getAnalyser());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Must part
		termList = new ArrayList<String>();
		fieldList = new ArrayList<String>();
		flagList = new ArrayList<Occur>();

		// Dates set? - if yes this a must clause.
		if(search.getDateStart() != null && search.getEndDate() != null) {
			StringBuilder termStringBuilder = new StringBuilder();
			fieldList.add(LuceneSearchService.SEARCHINDEX_CREATIONDATE);
			termList.add(termStringBuilder.append("[").append(DATE_FORMAT.format(search.getDateStart())).append(" TO ").append(DATE_FORMAT.format(search.getEndDate())).append("]").toString()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			flagList.add(Occur.MUST);
		}

		int size = InformationExtensionManager.getInstance().getTypes().size();
		if (search.getInfoType() != null && search.getInfoType().size() > 0 && search.getInfoType().size() != size) {
			StringBuilder termStringBuilder = new StringBuilder();
			fieldList.add(LuceneSearchService.SEARCHINDEX_INFOTYPE_ID);
			Iterator<String> iterator = search.getInfoType().iterator();
			while (iterator.hasNext()) {
				String string = iterator.next();
				termStringBuilder.append("\"").append(string).append("\"").append(iterator.hasNext() ? " AND " :"");
			}
			termList.add(termStringBuilder.toString());
			flagList.add(Occur.MUST);
		}
		switch (search.getScope()) {
		case SELECTED_INFO_UNIT:
			// later --> Thread issues
			/*
			ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				((IStructuredSelection) selection).toList();
			}
			 */
			break;
		case OPEN_EDITORS:
			// later --> Thread issues
			/*
			IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
			for (IEditorReference editorReference : editorReferences) {

			}
			 */
			break;
		default:
			break;
		}
		Query mustHaveQuery = null;
		try {
			mustHaveQuery = MultiFieldQueryParser.parse(
					termList.toArray(new String[termList.size()]),
					fieldList.toArray(new String[fieldList.size()]),
					flagList.toArray(new Occur[flagList.size()]), getAnalyser());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BooleanQuery booleanQuery = new BooleanQuery();
		if (searchWordQuery != null) {
			booleanQuery.add(searchWordQuery, Occur.MUST);
		}
		if (mustHaveQuery != null && termList.size() > 0) {
			booleanQuery.add(mustHaveQuery, Occur.MUST);
		}

		return booleanQuery;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.search.service.ILuceneCustomizer#getMaxResults()
	 */
	public int getMaxResults() {
		return 200;
	}

	public SimpleDateFormat getDateFormat() {
		return DATE_FORMAT;
	}

	public IProject[] getProjectsToSearch(org.remus.infomngmnt.search.Search search) {
		// scope comes later.
		return ResourceUtil.getRelevantProjects();
	}

}
