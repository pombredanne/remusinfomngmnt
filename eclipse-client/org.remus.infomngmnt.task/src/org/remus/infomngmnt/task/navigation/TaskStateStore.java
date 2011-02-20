/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.task.navigation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.ref.IIndexWriteOperation;
import org.eclipse.remus.core.ref.IndexSearchOperation;
import org.eclipse.remus.core.ref.LuceneStore;
import org.remus.infomngmnt.task.TaskActivator;
import org.remus.infomngmnt.task.TaskStatus;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TaskStateStore extends LuceneStore {

	public static final String INDEX_LOCATION = "taskStates/"; //$NON-NLS-1$

	public static final String DUE_DATE = "DUE_DATE";

	public static final String STATUS = "STATUS";

	public static final String COMPLETED = "COMPLETED";

	public static final String INFO_ID = "INFO_ID";

	public static final String LABEL = "LABEL";

	public static final String PROJECT = "PROJECT";

	public static final String DATEPATTERN = "yyyyMMddHHmmss"; //$NON-NLS-1$

	public TaskStateStore() {
		super(INDEX_LOCATION);
	}

	public void update(final InformationUnit unit) {

		delete(unit.getId());
		InformationStructureRead read = InformationStructureRead
				.newSession(unit);
		final Document document = new Document();
		Boolean completed = Boolean.valueOf((String) read
				.getValueByNodeId(TaskActivator.NODE_NAME_COMPLETED));
		String status = (String) read
				.getValueByNodeId(TaskActivator.NODE_NAME_STATUS);
		String id = unit.getId();
		String project = ((IResource) unit.getAdapter(IFile.class))
				.getProject().getName();
		Date dueDate = (Date) read
				.getValueByNodeId(TaskActivator.NODE_NAME_DUE_DATE);
		String dateString = "";
		String label = unit.getLabel();
		if (dueDate != null) {
			dateString = convertDate(dueDate);
		}

		Field unitField = new Field(INFO_ID, id, Field.Store.YES,
				Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(unitField);
		Field projectField = new Field(PROJECT, project, Field.Store.YES,
				Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(projectField);
		Field statusField = new Field(STATUS, status == null ? "" : status,
				Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(statusField);
		Field completedField = new Field(COMPLETED, String.valueOf(completed),
				Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(completedField);
		Field dueDateField = new Field(DUE_DATE, dateString, Field.Store.YES,
				Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(dueDateField);
		Field labelField = new Field(LABEL, label, Field.Store.YES,
				Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(labelField);

		write(new IIndexWriteOperation() {
			public void write(final IndexWriter indexWriter) {
				try {
					indexWriter.addDocument(document);
					UIUtil.getDisplay().asyncExec(new Runnable() {

						public void run() {
							InformationUnitListItem adapter = (InformationUnitListItem) unit
									.getAdapter(InformationUnitListItem.class);
							EList<Adapter> eAdapters = adapter.eAdapters();
							for (Adapter adapter2 : eAdapters) {
								NotificationImpl notification = new NotificationImpl(
										InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__UNREAD,
										null, null) {
									@Override
									public int getFeatureID(
											java.lang.Class<?> expectedClass) {
										return InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__UNREAD;
									}
								};
								adapter2.notifyChanged(notification);
							}

						}

					});

				} catch (CorruptIndexException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	public List<TaskDecorationObject> getDecorationsForDesktop() {
		IndexSearchOperation<List<TaskDecorationObject>> operation = new IndexSearchOperation<List<TaskDecorationObject>>(
				this) {

			public List<TaskDecorationObject> call() throws Exception {
				List<TaskDecorationObject> returnValue = new ArrayList<TaskDecorationObject>();
				final Term term = new Term(COMPLETED,
						String.valueOf(Boolean.FALSE));
				IndexSearcher indexReader = getIndexReader();
				TopDocs search = indexReader.search(new TermQuery(term), null,
						1000);
				ScoreDoc[] docs = search.scoreDocs;
				for (ScoreDoc scoreDoc : docs) {
					try {
						Document doc = indexReader.doc(scoreDoc.doc);
						TaskDecorationObject item = new TaskDecorationObject(
								Boolean.valueOf(doc.get(COMPLETED)),
								TaskStatus.fromKey(doc.get(STATUS)),
								convertDate(doc.get(DUE_DATE)),
								doc.get(INFO_ID), doc.get(LABEL));
						returnValue.add(item);

					} catch (CorruptIndexException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return returnValue;

			}

		};
		return read(operation);
	}

	public TaskDecorationObject getDecorationObjectByInfoUnit(
			final String infoUnit) {

		IndexSearchOperation<TaskDecorationObject> indexSearchOperation = new IndexSearchOperation<TaskDecorationObject>(
				this) {

			public TaskDecorationObject call() throws Exception {
				final Term term = new Term(INFO_ID, infoUnit);
				IndexSearcher indexReader = getIndexReader();
				TopDocs search = indexReader.search(new TermQuery(term), null,
						1000);
				ScoreDoc[] docs = search.scoreDocs;
				for (ScoreDoc scoreDoc : docs) {
					try {
						Document doc = indexReader.doc(scoreDoc.doc);
						TaskDecorationObject returnValue = new TaskDecorationObject(
								Boolean.valueOf(doc.get(COMPLETED)),
								TaskStatus.fromKey(doc.get(STATUS)),
								convertDate(doc.get(DUE_DATE)),
								doc.get(INFO_ID), doc.get(LABEL));
						return returnValue;

					} catch (CorruptIndexException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}
		};
		return read(indexSearchOperation);

	}

	public void delete(String infoId) {
		final Term term = new Term(INFO_ID, infoId);
		write(new IIndexWriteOperation() {
			public void write(final IndexWriter indexWriter) {
				indexWriter.setUseCompoundFile(false);
				try {
					indexWriter.deleteDocuments(term);
				} catch (CorruptIndexException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private static String convertDate(final Date date) {
		return new SimpleDateFormat(DATEPATTERN).format(date);
	}

	private static Date convertDate(final String date) {
		try {
			return new SimpleDateFormat(DATEPATTERN).parse(date);
		} catch (java.text.ParseException e) {

		}
		return null;
	}

}
