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
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class LuceneStore {

	private final String fileUrl;

	private IndexSearcher indexSearcher;

	private IndexWriter writer;

	final ISchedulingRule mutexRule = new ISchedulingRule() {
		public boolean isConflicting(final ISchedulingRule rule) {
			return rule == LuceneStore.this.mutexRule;
		}

		public boolean contains(final ISchedulingRule rule) {
			return rule == LuceneStore.this.mutexRule;
		}
	};

	protected LuceneStore(final String fileUrl) {
		this.fileUrl = fileUrl;
	}

	protected final void read(final IIndexSearchOperation operation) {
		IndexSearcher indexSearcher2 = getIndexSearcher();
		operation.read(indexSearcher2);
	}

	protected final synchronized void readAndWait(final IIndexSearchOperation operation) {
		// job.schedule();
		IndexSearcher indexSearcher2 = getIndexSearcher();
		operation.read(indexSearcher2);

	}

	protected final void write(final IIndexWriteOperation operation) {
		IndexWriter indexWriter = getIndexWriter();
		operation.write(indexWriter);
		try {
			indexWriter.flush();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		relaseIndexSearcher();
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
		File file = InfomngmntEditPlugin.getPlugin().getStateLocation().append(this.fileUrl)
				.toFile();
		if (!file.exists()) {
			file.mkdirs();
			try {
				this.writer = new IndexWriter(file, getAnalyser(), true);
				closeIndexWriter();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			return FSDirectory.getDirectory(file);
		} catch (IOException e) {
			throw new IllegalStateException("Could not initiaize indexdirectory", e);
		}
	}

	protected IndexWriter getIndexWriter() {
		if (this.writer == null) {
			try {

				if (IndexReader.isLocked(getIndexDirectory())) {
					IndexReader.unlock(getIndexDirectory());
				}
				this.writer = new IndexWriter(getIndexDirectory(), getAnalyser());
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
		if (this.indexSearcher != null) {
			try {
				this.indexSearcher.close();
			} catch (IOException e) {
				// do nothing
			}
			this.indexSearcher = null;
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