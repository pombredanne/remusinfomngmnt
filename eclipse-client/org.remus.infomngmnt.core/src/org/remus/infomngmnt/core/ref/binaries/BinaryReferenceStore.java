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
package org.remus.infomngmnt.core.ref.binaries;

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
import org.eclipse.core.resources.IFile;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.ref.IndexSearchOperation;
import org.remus.infomngmnt.core.ref.IIndexWriteOperation;
import org.remus.infomngmnt.core.ref.LuceneStore;
import org.remus.infomngmnt.core.services.IBinaryReferenceStore;

/**
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @since 1.0
 */
public class BinaryReferenceStore extends LuceneStore implements IBinaryReferenceStore {

	public static final String INDEX_LOCATION = "binaryRef/"; //$NON-NLS-1$

	public static final String INFOID = "INFOID"; //$NON-NLS-1$

	public static final String PATH = "PATH"; //$NON-NLS-1$

	public static final String PROJECTNAME = "PROJECTNAME"; //$NON-NLS-1$

	public BinaryReferenceStore() {
		super(INDEX_LOCATION);
	}

	public synchronized void update(final InformationUnit unit) {

		delete(unit.getId());
		final List<Document> documents = new ArrayList<Document>();

		InformationStructureRead read = InformationStructureRead.newSession(unit);
		List<BinaryReference> binaryReferences = read.getBinaryReferences();
		for (BinaryReference binaryReference : binaryReferences) {
			final Document document = new Document();

			Field unitField = new Field(INFOID, unit.getId(), Field.Store.YES,
					Field.Index.TOKENIZED);
			document.add(unitField);

			Field pathField = new Field(PATH, binaryReference.getProjectRelativePath(),
					Field.Store.YES, Field.Index.TOKENIZED);
			document.add(pathField);

			Field projectField = new Field(PROJECTNAME, ((IFile) unit.getAdapter(IFile.class))
					.getProject().getName(), Field.Store.YES, Field.Index.TOKENIZED);
			document.add(projectField);
			documents.add(document);
		}

		write(new IIndexWriteOperation() {
			public void write(final IndexWriter indexWriter) {
				try {
					for (Document doc : documents) {
						indexWriter.addDocument(doc);
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

	public synchronized void delete(final String informationUnitId) {
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

	public String getReferencedInfoUnitIdByPath(final String projectName, final String fileName) {
		List<String> termList = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();
		List<Occur> flagList = new ArrayList<Occur>();

		fieldList.add(PROJECTNAME);
		termList.add(projectName);
		flagList.add(Occur.MUST);
		fieldList.add(PATH);
		termList.add(fileName);
		flagList.add(Occur.MUST);

		try {
			final Query tagQuery = MultiFieldQueryParser.parse(termList.toArray(new String[termList
					.size()]), fieldList.toArray(new String[fieldList.size()]), flagList
					.toArray(new Occur[flagList.size()]), getAnalyser());
			IndexSearchOperation<String> operation = new IndexSearchOperation<String>(
					getIndexSearcher()) {

				public String call() throws Exception {
					try {

						TopDocs search = this.reader.search(tagQuery, null, 1000);
						ScoreDoc[] docs = search.scoreDocs;
						for (ScoreDoc scoreDoc : docs) {
							try {
								Document doc = this.reader.doc(scoreDoc.doc);
								return doc.get(INFOID);
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
			// we do nothing.
		}
		return null;
	}

}
