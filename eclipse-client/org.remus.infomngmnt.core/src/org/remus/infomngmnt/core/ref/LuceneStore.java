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

package org.remus.infomngmnt.core.ref;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class LuceneStore {

	

	private final String fileUrl;

	private IndexSearcher indexSearcher;

	private IndexWriter writer;

	protected LuceneStore(final String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public IndexSearcher getIndexSearcher() {
		if (this.indexSearcher == null) {
			try {
				this.indexSearcher = new IndexSearcher(getIndexDirectory());
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.indexSearcher;
	}

	protected Directory getIndexDirectory() {
		File file = InfomngmntEditPlugin.getPlugin().getStateLocation().append(this.fileUrl).toFile();
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			return FSDirectory.getDirectory(file);
		} catch (IOException e) {
			throw new IllegalStateException("Could not initiaize indexdirectory",e);
		}
	}

	protected IndexWriter getIndexWriter() {
		if (this.writer == null) {
			try {
				relaseIndexSearcher();
				this.writer = new IndexWriter(getIndexDirectory(),
						getAnalyser());
				this.writer.setUseCompoundFile(false);
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LockObtainFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.writer;
	}


	protected Analyzer getAnalyser() {
		return new StandardAnalyzer();
	}

	protected void relaseIndexSearcher() {
		IndexSearcher indexSearcher = this.indexSearcher;
		if (indexSearcher != null) {
			try {
				indexSearcher.close();
			} catch (IOException e) {
				// do nothing
			}
			indexSearcher = null;
		}
	}

	protected void closeIndexWriter() {
		try {
			this.writer.flush();
			this.writer.close();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.writer = null;

	}

}
