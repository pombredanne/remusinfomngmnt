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

package org.remus.infomngmnt.core.ref.infounit;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.ref.IIndexWriteOperation;
import org.remus.infomngmnt.core.ref.IndexSearchOperation;
import org.remus.infomngmnt.core.ref.LuceneStore;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.ISynchronizationItemCache;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizationCache extends LuceneStore implements ISynchronizationItemCache {

	public static final String INDEX_LOCATION = "syncCache/"; //$NON-NLS-1$

	public static final String INFOID = "INFOID"; //$NON-NLS-1$

	public static final String URL = "URL"; //$NON-NLS-1$

	public SynchronizationCache() {
		super(INDEX_LOCATION);
	}

	public synchronized void delete(final String informationId) {
		final Term term = new Term(INFOID, informationId);
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

	public InformationUnitListItem getItemByUrl(final String url) {
		if (url == null) {
			return null;
		}
		try {

			final Query tagQuery = new TermQuery(new Term(URL, url.replaceAll("\\\\", "/")));
			IndexSearchOperation<InformationUnitListItem> operation = new IndexSearchOperation<InformationUnitListItem>(
					getIndexSearcher()) {

				public InformationUnitListItem call() throws Exception {
					try {

						TopDocs search = this.reader.search(tagQuery, null, 1000);
						ScoreDoc[] docs = search.scoreDocs;
						for (ScoreDoc scoreDoc : docs) {
							try {
								Document doc = this.reader.doc(scoreDoc.doc);
								String string = doc.get(INFOID);
								if (string != null) {
									IApplicationModel service = InfomngmntEditPlugin.getPlugin()
											.getServiceTracker()
											.getService(IApplicationModel.class);
									InformationUnitListItem itemById = service.getItemById(string,
											null);
									InfomngmntEditPlugin.getPlugin().getServiceTracker()
											.ungetService(service);
									return itemById;
								}
							} catch (CorruptIndexException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

			};
			return read(operation);
		} catch (Exception e) {
			e.printStackTrace();
			// we do nothing.
		}
		return null;
	}

	public void update(final InformationUnitListItem unit) {
		delete(unit.getId());
		String url = "";
		if (unit.getSynchronizationMetaData() != null
				&& unit.getSynchronizationMetaData().getSyncState() != SynchronizationState.LOCAL_DELETED
				&& unit.getSynchronizationMetaData().getUrl() != null) {
			url = unit.getSynchronizationMetaData().getUrl();
			url = url.replaceAll("\\\\", "/");
		}
		System.out.println("Update syncstate with -->" + url);
		String id = unit.getId();
		final Document document = new Document();
		Field unitField = new Field(INFOID, id, Field.Store.YES, Field.Index.NO_NORMS);
		document.add(unitField);

		Field pathField = new Field(URL, url, Field.Store.YES, Field.Index.NO_NORMS);
		document.add(pathField);
		write(new IIndexWriteOperation() {
			public void write(final IndexWriter indexWriter) {
				try {
					indexWriter.addDocument(document);
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

}
