/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
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
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.core.ref.IIndexWriteOperation;
import org.remus.infomngmnt.core.ref.IndexSearchOperation;
import org.remus.infomngmnt.core.ref.LuceneStore;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IReferencedUnitStore;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * <p>
 * A Lucene store that keeps track of all referenced items that are stored in
 * the EMF Model via non-contained children. The storage includes the path, the
 * type and the id for the referenced item.
 * </p>
 * <p>
 * This store should provide some search facitlity for referenced items and
 * should keep integrity for items that are referenced and deleted or modified.
 * </p>
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @since 1.0
 */
public class ReferencedUnitStore extends LuceneStore implements IReferencedUnitStore {

	public static final String INDEX_LOCATION = "refUnitIndex/"; //$NON-NLS-1$

	public static final String INFOID = "INFOID"; //$NON-NLS-1$

	public static final String REFINFOID = "REFINFOID"; //$NON-NLS-1$

	public static final String REFINFOPATH = "REFINFOPATH"; //$NON-NLS-1$

	public static final String REFINFONAME = "REFINFONAME"; //$NON-NLS-1$

	public static final String REFINFOTYPE = "REFINFOTYPE"; //$NON-NLS-1$

	public ReferencedUnitStore() {
		super(INDEX_LOCATION);
	}

	public void update(final InformationUnit unit) {
		delete(unit.getId());
		List<InformationUnitListItem> nonContainments = checkNonContainments(unit, unit.getId());
		final List<Document> documents2index = new ArrayList<Document>();
		if (nonContainments.size() > 0) {
			for (InformationUnitListItem informationUnit : nonContainments) {
				final Document document = new Document();

				Field unitField = new Field(INFOID, unit.getId(), Field.Store.YES,
						Field.Index.TOKENIZED);
				document.add(unitField);

				Field refInfoFiled = new Field(REFINFOID, informationUnit.getId(), Field.Store.YES,
						Field.Index.TOKENIZED);
				document.add(refInfoFiled);

				Field refInfoNameField = new Field(REFINFONAME, informationUnit.getLabel(),
						Field.Store.YES, Field.Index.TOKENIZED);
				document.add(refInfoNameField);

				Field refInfoType = new Field(REFINFOTYPE, informationUnit.getType(),
						Field.Store.YES, Field.Index.TOKENIZED);
				document.add(refInfoType);

				documents2index.add(document);
			}
			write(new IIndexWriteOperation() {
				public void write(final IndexWriter indexWriter) {
					try {
						for (Document document : documents2index) {
							indexWriter.addDocument(document);
						}
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

	@SuppressWarnings("unchecked")
	private List<InformationUnitListItem> checkNonContainments(final InformationUnit unit,
			final String id) {
		List<InformationUnitListItem> item = new ArrayList<InformationUnitListItem>();
		EList<Link> links = unit.getLinks();
		IApplicationModel service = InfomngmntEditPlugin.getPlugin().getService(
				IApplicationModel.class);
		for (Link link : links) {
			InformationUnitListItem itemByLink = service.getItemByLink(link);
			if (itemByLink != null) {
				item.add(itemByLink);
			}

		}
		return item;
	}

	public void delete(final String informationUnitId) {
		final Term term = new Term(INFOID, informationUnitId);
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

	public String[] getReferencedInfoUnitIds(final String informationUnitId) {

		final List<String> termList = new ArrayList<String>();
		final List<String> fieldList = new ArrayList<String>();
		final List<Occur> flagList = new ArrayList<Occur>();

		fieldList.add(REFINFOID);
		termList.add(informationUnitId);
		flagList.add(Occur.SHOULD);

		try {

			IndexSearchOperation<List<String>> operation = new IndexSearchOperation<List<String>>(
					getIndexSearcher()) {
				public List<String> call() throws Exception {
					Query tagQuery = MultiFieldQueryParser.parse(termList
							.toArray(new String[termList.size()]), fieldList
							.toArray(new String[fieldList.size()]), flagList
							.toArray(new Occur[flagList.size()]), getAnalyser());
					final List<String> returnValue = new ArrayList<String>();
					try {
						TopDocs search = this.reader.search(tagQuery, null, 1000);
						ScoreDoc[] docs = search.scoreDocs;
						for (ScoreDoc scoreDoc : docs) {
							try {
								Document doc = this.reader.doc(scoreDoc.doc);
								returnValue.add(doc.get(INFOID));
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
					return returnValue;
				}
			};
			List<String> read = read(operation);
			return read.toArray(new String[read.size()]);
		} catch (Exception e) {
			// we do nothing.
		}
		return new String[0];
	}

}
