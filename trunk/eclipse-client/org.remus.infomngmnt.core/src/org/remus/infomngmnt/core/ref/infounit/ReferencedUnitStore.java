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
import java.util.Collection;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.ref.IIndexSearchOperation;
import org.remus.infomngmnt.core.ref.IIndexWriteOperation;
import org.remus.infomngmnt.core.ref.LuceneStore;
import org.remus.infomngmnt.core.services.IReferencedUnitStore;

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
		List<InformationUnit> nonContainments = checkNonContainments(unit, unit.getId());
		final List<Document> documents2index = new ArrayList<Document>();
		if (nonContainments.size() > 0) {
			for (InformationUnit informationUnit : nonContainments) {
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
	private List<InformationUnit> checkNonContainments(final EObject unit, final String id) {
		List<InformationUnit> returnValue = new ArrayList<InformationUnit>();
		EList<EReference> eAllReferences = unit.eClass().getEAllReferences();
		for (EReference eReference : eAllReferences) {
			if (!eReference.isContainment()) {
				Object eGet = unit.eGet(eReference);
				if (eGet instanceof Collection) {
					for (Object obj : (Collection) eGet) {
						if (obj instanceof InformationUnit) {
							returnValue.add((InformationUnit) obj);
						}
					}
				} else if (eGet instanceof InformationUnit) {
					returnValue.add((InformationUnit) eGet);
				}
			} else {
				Object eGet = unit.eGet(eReference);
				if (eGet instanceof Collection) {
					for (Object obj : (Collection) eGet) {
						if (obj instanceof EObject) {
							returnValue.addAll(checkNonContainments((EObject) obj, id));
						}
					}
				} else if (eGet instanceof EObject) {
					returnValue.addAll(checkNonContainments((EObject) eGet, id));
				}
			}
		}
		return returnValue;
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
		final List<String> returnValue = new ArrayList<String>();
		List<String> termList = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();
		List<Occur> flagList = new ArrayList<Occur>();

		fieldList.add(REFINFOID);
		termList.add(informationUnitId);
		flagList.add(Occur.SHOULD);

		try {
			final Query tagQuery = MultiFieldQueryParser.parse(termList.toArray(new String[termList
					.size()]), fieldList.toArray(new String[fieldList.size()]), flagList
					.toArray(new Occur[flagList.size()]), getAnalyser());
			IIndexSearchOperation operation = new IIndexSearchOperation() {

				public void read(final IndexSearcher reader) {
					try {

						TopDocs search = reader.search(tagQuery, null, 1000);
						ScoreDoc[] docs = search.scoreDocs;
						for (ScoreDoc scoreDoc : docs) {
							try {
								Document doc = getIndexSearcher().doc(scoreDoc.doc);
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

				}

			};
			readAndWait(operation);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue.toArray(new String[returnValue.size()]);
	}

}
