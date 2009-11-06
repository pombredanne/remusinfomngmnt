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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
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

	private final Lock lock = new ReentrantLock();

	protected Logger log = Logger.getLogger(getClass());

	private final ExecutorService executor;

	protected LuceneStore(final String fileUrl) {
		this.fileUrl = fileUrl;
		this.executor = Executors.newCachedThreadPool();
	}

	protected final <T> T read(final IndexSearchOperation<T> operation) {
		this.log.debug("Reading from Lucene store");
		this.lock.lock();
		Future<T> submit = this.executor.submit(operation);
		T returnValue = null;
		try {
			returnValue = submit.get();
			this.log.debug("Returned result --> " + returnValue.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
		} finally {
			this.lock.unlock();
		}
		return returnValue;
	}

	protected final void write(final IIndexWriteOperation operation) {
		this.lock.lock();
		IndexWriter indexWriter = getIndexWriter();
		try {
			this.log.debug("Writung to index");
			operation.write(indexWriter);
			indexWriter.flush();
		} catch (Exception e) {
			this.log.error("Error writing to lucene store", e);
		} finally {
			relaseIndexSearcher();
			this.log.debug("Released index searcher.");
			this.lock.unlock();
		}
	}

	public synchronized IndexSearcher getIndexSearcher() {
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
			// try {
			// this.writer = new IndexWriter(file, getAnalyser(), true);
			// closeIndexWriter();
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
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
