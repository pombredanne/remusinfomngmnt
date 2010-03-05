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
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.InformationStructureItem;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.services.IInformationTypeHandler;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchScope;
import org.remus.infomngmnt.search.analyzer.IAnalyzer;
import org.remus.infomngmnt.search.analyzer.ISecondaryAnalyzer;
import org.remus.infomngmnt.search.analyzer.ISecondaryIndex;
import org.remus.infomngmnt.search.service.IIndexService;
import org.remus.infomngmnt.search.service.ILuceneCustomizer;
import org.remus.infomngmnt.search.service.ISearchAnalyzerService;
import org.remus.infomngmnt.search.service.IndexConstants;
import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DefaultLuceneCustomizer implements ILuceneCustomizer {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	private final RemusServiceTracker serviceTracker;
	private IIndexService indexService;
	private ISearchAnalyzerService analyzerService;
	private IInformationTypeHandler infoTypeService;

	public DefaultLuceneCustomizer() {
		this.serviceTracker = new RemusServiceTracker(Platform.getBundle(Activator.PLUGIN_ID));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.search.service.ILuceneCustomizer#getAnalyser()
	 */
	public Analyzer getAnalyser() {
		return new SimpleAnalyzer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.service.ILuceneCustomizer#getIndexDirectory
	 * (org.eclipse.core.resources.IProject)
	 */
	public Directory getIndexDirectory(final IProject project) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.service.ILuceneCustomizer#getLuceneDocument
	 * (org.remus.infomngmnt.InformationUnit,
	 * org.eclipse.core.resources.IProject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public Document[] getLuceneDocument(final InformationUnit document, final IProject project,
			final IProgressMonitor monitor) {
		IInfoType infoTypeByType = getInfoTypeHandler().getInfoTypeByType(document.getType());
		if (infoTypeByType == null) {
			return new Document[0];
		}
		List<Document> documentsToIndex = new ArrayList<Document>();
		String typeId = infoTypeByType.getType();
		InformationStructureDefinition structureDefinition = infoTypeByType
				.getStructureDefinition();
		SELECT select = new SELECT(new FROM(structureDefinition), new WHERE(
				new EObjectAttributeValueCondition(
						InfomngmntPackage.Literals.INFORMATION_STRUCTURE_ITEM__ID, new Condition() {

							@Override
							public boolean isSatisfied(final Object arg0) {
								return arg0 != null;
							}
						})));
		IQueryResult execute = select.execute();
		Set<? extends EObject> eObjects = execute.getEObjects();
		StringWriter contentString = new StringWriter();
		StringWriter additionalString = new StringWriter();
		Document returnValue = new Document();
		for (EObject eObject : eObjects) {
			if (eObject instanceof InformationStructureItem) {
				String id = ((InformationStructureItem) eObject).getId();
				IAnalyzer analyzerByTypeAndNodeId = getIndexService().getAnalyzerByTypeAndNodeId(
						typeId, id);
				if (analyzerByTypeAndNodeId != null) {
					String analyze = null;
					try {
						analyze = analyzerByTypeAndNodeId.analyze(document, id);
					} catch (Throwable e) {
						// do nothing
					}
					if (analyze != null) {
						if (analyzerByTypeAndNodeId.isStandardAnalyzer()) {
							contentString.append(analyze).append("\u0020");
						} else {
							additionalString.append(analyze).append("\u0020");
						}
					}

				}
			}
		}
		fillStandardFields(returnValue, document, contentString.toString(), additionalString
				.toString(), document.getLabel(), false, project);
		documentsToIndex.add(returnValue);
		for (EObject eObject : eObjects) {
			if (eObject instanceof InformationStructureItem) {
				String id = ((InformationStructureItem) eObject).getId();
				ISecondaryAnalyzer[] secondaryAnalyzer = getIndexService()
						.getSecondaryAnalyerByTypeAndNodeId(typeId, id);
				for (ISecondaryAnalyzer iSecondaryAnalyzer : secondaryAnalyzer) {
					ISecondaryIndex[] analyze = iSecondaryAnalyzer.analyze(document, id);
					for (ISecondaryIndex iSecondaryIndex : analyze) {
						Document subDocument = new Document();
						String contents = iSecondaryIndex.getContents();
						String label = iSecondaryIndex.getLabel();
						String query = iSecondaryIndex.getQuery();
						fillStandardFields(subDocument, document, contents, null, document
								.getLabel(), true, project);

						Field childLabelField = new Field(IndexConstants.SEARCHINDEX_CHILD_LABEL,
								checkNull(label), Field.Store.YES, Field.Index.TOKENIZED);
						childLabelField.setBoost(2.0f);
						subDocument.add(childLabelField);

						Field queryField = new Field(IndexConstants.SEARCHINDEX_QUERY,
								checkNull(query), Field.Store.YES, Field.Index.UN_TOKENIZED);
						subDocument.add(queryField);
						documentsToIndex.add(subDocument);

					}
				}
			}
		}
		return documentsToIndex.toArray(new Document[documentsToIndex.size()]);
	}

	private void fillStandardFields(final Document returnValue, final InformationUnit document,
			final String content, final String additionals, final String label,
			final boolean child, final IProject project) {

		Field idField = new Field(IndexConstants.SEARCHINDEX_ITEM_ID, document.getId(),
				Field.Store.YES, Field.Index.UN_TOKENIZED);
		returnValue.add(idField);

		try {
			Field titleField = new Field(IndexConstants.SEARCHINDEX_LABEL, checkNull(label),
					Field.Store.YES, Field.Index.TOKENIZED);
			titleField.setBoost(2.0f);
			returnValue.add(titleField);
		} catch (Exception e) {
			// no title will be added...
		}
		try {

			Field contentField = new Field(IndexConstants.SEARCHINDEX_CONTENT, checkNull(content),
					Field.Store.YES, Field.Index.TOKENIZED);
			contentField.setBoost(1.5f);
			returnValue.add(contentField);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Field additionalField = new Field(IndexConstants.SEARCHINDEX_ADDITIONALS,
					checkNull(additionals), Field.Store.YES, Field.Index.TOKENIZED);
			additionalField.setBoost(1.2f);
			returnValue.add(additionalField);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Field descriptionField = new Field(IndexConstants.SEARCHINDEX_DESCRIPTION,
				checkNull(document.getDescription()), Field.Store.YES, Field.Index.TOKENIZED);
		descriptionField.setBoost(1.5f);
		returnValue.add(descriptionField);

		Field keywordField = new Field(IndexConstants.SEARCHINDEX_KEYWORDS, checkNull(document
				.getKeywords()), Field.Store.YES, Field.Index.TOKENIZED);
		descriptionField.setBoost(1.9f);
		returnValue.add(keywordField);

		Field dateField = new Field(IndexConstants.SEARCHINDEX_CREATIONDATE, DATE_FORMAT
				.format(document.getCreationDate()), Field.Store.YES, Field.Index.UN_TOKENIZED);
		returnValue.add(dateField);

		Field projectField = new Field(IndexConstants.SEARCHINDEX_PROJECT, project.getName(),
				Field.Store.YES, Field.Index.UN_TOKENIZED);
		returnValue.add(projectField);

		Field infoTypeField = new Field(IndexConstants.SEARCHINDEX_INFOTYPE_ID, document.getType()
				.toLowerCase(), Field.Store.YES, Field.Index.UN_TOKENIZED);
		returnValue.add(infoTypeField);

		Field isChildField = new Field(IndexConstants.SEARCHINDEX_CHILD_INDEX, Boolean
				.toString(child), Field.Store.YES, Field.Index.UN_TOKENIZED);
		returnValue.add(isChildField);

		if (!child) {
			Field childLabelField = new Field(IndexConstants.SEARCHINDEX_CHILD_LABEL, "",
					Field.Store.YES, Field.Index.TOKENIZED);
			returnValue.add(childLabelField);

		}

	}

	private static String checkNull(final String str) {
		return str == null ? "" : str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.search.service.ILuceneCustomizer#
	 * getQueryStringFromSearchQuery(org.remus.search.Search,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public Query getQueryStringFromSearchQuery(final Search search, final IProgressMonitor monitor) {

		List<String> termList = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();
		List<Occur> flagList = new ArrayList<Occur>();

		if (search.getSearchString() != null) {
			if (search.isIdSearch()) {
				String[] split = search.getSearchString().split(ID_SEPARATOR);
				for (String string : split) {
					fieldList.add(IndexConstants.SEARCHINDEX_ITEM_ID);
					termList.add(string);
					flagList.add(Occur.SHOULD);
				}
			} else {
				if (search.getSearchString() != null && search.getSearchString().length() > 0) {
					fieldList.add(IndexConstants.SEARCHINDEX_LABEL);
					termList.add(search.getSearchString());
					flagList.add(Occur.SHOULD);

					fieldList.add(IndexConstants.SEARCHINDEX_CHILD_LABEL);
					termList.add(search.getSearchString());
					flagList.add(Occur.SHOULD);

					fieldList.add(IndexConstants.SEARCHINDEX_CONTENT);
					termList.add(search.getSearchString());
					flagList.add(Occur.SHOULD);

					fieldList.add(IndexConstants.SEARCHINDEX_ADDITIONALS);
					termList.add(search.getSearchString());
					flagList.add(Occur.SHOULD);

					fieldList.add(IndexConstants.SEARCHINDEX_DESCRIPTION);
					termList.add(search.getSearchString());
					flagList.add(Occur.SHOULD);

					fieldList.add(IndexConstants.SEARCHINDEX_KEYWORDS);
					termList.add(search.getSearchString());
					flagList.add(Occur.SHOULD);
				}
			}

		}

		Query searchWordQuery = null;
		try {
			if (search.isIdSearch()) {
				searchWordQuery = MultiFieldQueryParser.parse(termList.toArray(new String[termList
						.size()]), fieldList.toArray(new String[fieldList.size()]), flagList
						.toArray(new Occur[flagList.size()]), new StandardAnalyzer());
			} else {
				if (search.getSearchString() != null && search.getSearchString().length() > 0) {
					searchWordQuery = MultiFieldQueryParser.parse(termList
							.toArray(new String[termList.size()]), fieldList
							.toArray(new String[fieldList.size()]), flagList
							.toArray(new Occur[flagList.size()]), getAnalyser());
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Must part
		termList = new ArrayList<String>();
		fieldList = new ArrayList<String>();
		flagList = new ArrayList<Occur>();

		// Dates set? - if yes this a must clause.
		if (search.getDateStart() != null && search.getEndDate() != null) {
			StringBuilder termStringBuilder = new StringBuilder();
			fieldList.add(IndexConstants.SEARCHINDEX_CREATIONDATE);
			termList
					.add(termStringBuilder
							.append("[\"").append(DATE_FORMAT.format(search.getDateStart())).append("\" TO \"").append(DATE_FORMAT.format(search.getEndDate())).append("\"]").toString()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			flagList.add(Occur.MUST);
		}

		int size = getInfoTypeHandler().getTypes().size();
		if (search.getInfoType() != null && search.getInfoType().size() > 0
				&& search.getInfoType().size() != size) {
			StringBuilder termStringBuilder = new StringBuilder();
			Iterator<String> iterator = search.getInfoType().iterator();
			fieldList.add(IndexConstants.SEARCHINDEX_INFOTYPE_ID);
			while (iterator.hasNext()) {
				String string = iterator.next();
				termStringBuilder.append("\"").append(string).append("\"").append(
						iterator.hasNext() ? " OR " : "");
			}
			termList.add(termStringBuilder.toString());
			flagList.add(Occur.MUST);
		}

		Query mustHaveQuery = null;
		try {
			mustHaveQuery = MultiFieldQueryParser.parse(termList
					.toArray(new String[termList.size()]), fieldList.toArray(new String[fieldList
					.size()]), flagList.toArray(new Occur[flagList.size()]), getAnalyser());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.service.ILuceneCustomizer#getMaxResults()
	 */
	public int getMaxResults() {
		return 200;
	}

	public SimpleDateFormat getDateFormat() {
		return DATE_FORMAT;
	}

	public IProject[] getProjectsToSearch(final org.remus.infomngmnt.search.Search search) {
		if (search.getScope() == SearchScope.PROJECTS) {
			EList<String> projects = search.getProjects();
			List<IProject> returnValue = new ArrayList<IProject>();
			for (String string : projects) {
				IProject project = ResourceUtil.getProject(string);
				if (project != null) {
					returnValue.add(project);
				}
			}
			if (returnValue.size() > 0) {
				return returnValue.toArray(new IProject[returnValue.size()]);
			}
		}
		return ResourceUtil.getRelevantProjects();
	}

	public void clear(final IProject project, final IProgressMonitor monitor) {
		File file = Activator.getDefault().getStateLocation().append(project.getName()).toFile();
		file.delete();
	}

	private IIndexService getIndexService() {
		if (this.indexService == null) {
			this.indexService = this.serviceTracker.getService(IIndexService.class);
		}
		return this.indexService;

	}

	private ISearchAnalyzerService getAnalyzerService() {
		if (this.analyzerService == null) {
			this.analyzerService = this.serviceTracker.getService(ISearchAnalyzerService.class);
		}
		return this.analyzerService;
	}

	private IInformationTypeHandler getInfoTypeHandler() {
		if (this.infoTypeService == null) {
			this.infoTypeService = this.serviceTracker.getService(IInformationTypeHandler.class);
		}
		return this.infoTypeService;
	}

}
