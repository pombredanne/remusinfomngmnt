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

package org.remus.infomngmnt.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.progress.CancelableJob;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.SearchResult;
import org.remus.infomngmnt.search.provider.SearchPlugin;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopFieldDocs;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LuceneSearchService {

	private static LuceneSearchService INSTANCE;

	public static final String SEARCHINDEX_ITEM_ID = "document_id"; //$NON-NLS-1$
	public static final String SEARCHINDEX_INFOTYPE_ID = "document_infotype_id"; //$NON-NLS-1$
	public static final String SEARCHINDEX_KEYWORDS = "document_keywords"; //$NON-NLS-1$
	public static final String SEARCHINDEX_DESCRIPTION = "document_description"; //$NON-NLS-1$
	public static final String SEARCHINDEX_LABEL = "document_label"; //$NON-NLS-1$
	public static final String SEARCHINDEX_CONTENT = "document_content"; //$NON-NLS-1$
	public static final String SEARCHINDEX_ADDITIONALS = "document_additionals"; //$NON-NLS-1$
	public static final String SEARCHINDEX_CREATIONDATE = "document_creationdate"; //$NON-NLS-1$
	public static final String SEARCHINDEX_PROJECT = "document_project"; //$NON-NLS-1$


	public static LuceneSearchService getInstance() {
		if (LuceneSearchService.INSTANCE == null) {
			synchronized (LuceneSearchService.class) {
				if (LuceneSearchService.INSTANCE == null) {
					LuceneSearchService.INSTANCE = new LuceneSearchService();
				}
			}
		}
		return LuceneSearchService.INSTANCE;
	}
	private final WriteQueueJob writeQueueJob;

	private final Map<IProject, IndexSearcher> projectToSearcherMap = new HashMap<IProject, IndexSearcher>();

	private LuceneSearchService() {
		this.writeQueueJob = new WriteQueueJob();
		this.writeQueueJob.schedule();
	}

	private class WriteQueueJob extends Job
	{
		IFolder indexLockFolder;

		private final Map<IProject, List<IFile>> indexQueue = new HashMap<IProject, List<IFile>>();
		private final Map<IProject, List<IFile>> removIndexQueue = new HashMap<IProject, List<IFile>>();

		public WriteQueueJob()
		{
			super("Writing queue to index");
			setSystem(true);
		}

		private long lastAdding;

		private int currentSize = 0;

		private final long maxNonWriteTime = 20000;

		private boolean writeImmediately = false;

		/* (non-Javadoc)
		 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			if (((System.currentTimeMillis() - this.lastAdding > this.maxNonWriteTime && this.currentSize > 0)
					|| this.currentSize > 100000
					|| (this.writeImmediately && this.currentSize > 0)
					|| (Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_BUILD).length == 0 && this.currentSize > 0))) {
				final Job writeIndexQueue = new Job("Write queue") {
					@Override
					protected IStatus run(final IProgressMonitor monitor)
					{
						final Map<IProject, List<IFile>> clonedMap = new HashMap<IProject, List<IFile>>(WriteQueueJob.this.indexQueue);
						WriteQueueJob.this.indexQueue.clear();
						final Map<IProject, List<IFile>> clonedDeleteMap = new HashMap<IProject, List<IFile>>(WriteQueueJob.this.removIndexQueue);
						WriteQueueJob.this.removIndexQueue.clear();
						int newCurrentSize = WriteQueueJob.this.currentSize;
						WriteQueueJob.this.currentSize = 0;
						final Set<IProject> keySet = clonedMap.keySet();
						final Set<IProject> deleteKeySet = clonedDeleteMap.keySet();

						IndexReader reader = null;
						if (deleteKeySet.size() > 0) {
							monitor.beginTask("Deleting obsolete entries from index...", IProgressMonitor.UNKNOWN);
							try {
								for (final IProject project : deleteKeySet) {
									reader = IndexReader.open(getSearchService().getIndexDirectory(project));
									final List<IFile> list = clonedDeleteMap.get(project);
									for (final IFile path : list) {
										final InformationUnit document = EditingUtil.getInstance().getObjectFromFile(path, InfomngmntPackage.Literals.INFORMATION_UNIT,false);
										monitor.setTaskName(NLS.bind("Deleting document \"{0}\"", document.getLabel()));
										final Term term = new Term(SEARCHINDEX_ITEM_ID,
												document.getId());
										reader.deleteDocuments(term);
									}
								}
							} catch (final Exception e1) {
								System.out.println(e1);
								//
							} finally {
								if (reader != null) {
									try {
										reader.flush();
										reader.close();
									} catch (final IOException e) {
										//we've done our best...
									}
								}
							}
						}
						monitor.beginTask("Queued documents: {0}", newCurrentSize);
						for (final IProject project : keySet)
						{
							IndexWriter writer = null;
							try
							{
								final List<IFile> list = clonedMap.get(project);
								final long timeMillis = System.currentTimeMillis();
								if (list.size() > 0)
								{
									relaseIndexSearcher(project);
									writer = new IndexWriter(getSearchService().getIndexDirectory(project),
											getSearchService().getAnalyser());
									writer.setUseCompoundFile(false);
									for (final IFile path : list)
									{
										try {
											final InformationUnit infoUnit = EditingUtil.getInstance().getObjectFromFile(path, InfomngmntPackage.Literals.INFORMATION_UNIT,false);
											monitor.setTaskName(NLS.bind(
													"Adding {0} to queue",
													infoUnit.getLabel()));
											writer.addDocument(getSearchService().getLuceneDocument(infoUnit,
													project, monitor));
											infoUnit.eResource().unload();
											monitor.worked(1);
										} catch (final Exception e) {
											e.printStackTrace();
										}
										if (monitor.isCanceled()) {
											return Status.CANCEL_STATUS;
										}
									}
									if (WriteQueueJob.this.currentSize > 0) {
										monitor.setTaskName(NLS.bind(
												"Still queued: {0} documents",
												WriteQueueJob.this.currentSize));
									} else {
										monitor.setTaskName("Done");
									}
									final IStatus status = StatusCreator.newStatus(NLS.bind(
											"Indexed {0} documents in {1} ms",
											list.size(), (System.currentTimeMillis() - timeMillis) ));
									SearchPlugin.getPlugin().getLog().log(status);
									newCurrentSize-= list.size();
									WriteQueueJob.this.writeImmediately = false;
								}
							}
							catch (final Exception e)
							{
								e.printStackTrace();
							}
							finally
							{
								if (writer != null) {
									try {
										writer.flush();
										//writer.optimize();
										writer.close();
									} catch (final Exception e) {
										// do nothing. we've done our best.
									}
								}
							}

						}
						return Status.OK_STATUS;
					}

				};
				writeIndexQueue.setUser(true);
				writeIndexQueue.setPriority(Job.INTERACTIVE);
				writeIndexQueue.schedule();
			}
			schedule(500);
			return Status.OK_STATUS;
		}

		public void addToQueue(final List<IFile> removeDocuments, final List<IFile> addDocuments, final IProject project) {
			this.lastAdding = System.currentTimeMillis();
			if (this.indexQueue.get(project) == null) {
				this.indexQueue.put(project, new ArrayList<IFile>());
			}
			this.indexQueue.get(project).addAll(addDocuments);
			if (this.removIndexQueue.get(project) == null) {
				this.removIndexQueue.put(project, new ArrayList<IFile>());
			}
			this.removIndexQueue.get(project).addAll(removeDocuments);
			this.currentSize+=addDocuments.size();
		}



	}

	protected ILuceneCustomizer getSearchService() {
		ILuceneCustomizer service = SearchPlugin.getPlugin().getService();
		if (service == null) {
			throw new IllegalStateException("No service implementation for local search found...");
		}
		return service;
	}
	protected void relaseIndexSearcher(IProject project) {
		IndexSearcher indexSearcher = this.projectToSearcherMap.get(project);
		if (indexSearcher != null) {
			try {
				indexSearcher.close();
			} catch (IOException e) {
				// do nothing
			}
			indexSearcher = null;
		}
		this.projectToSearcherMap.remove(project);

	}

	protected IndexSearcher acquireIndexSearcher(IProject project) {
		IndexSearcher indexSearcher = this.projectToSearcherMap.get(project);
		if (indexSearcher == null) {
			try {
				indexSearcher = new IndexSearcher(getSearchService().getIndexDirectory(project));
				this.projectToSearcherMap.put(project, indexSearcher);
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return indexSearcher;
	}


	public void addToIndex(List<IFile> filesTodeleteFromIndex,
			List<IFile> filesToAddToIndex, IProject project) {
		this.writeQueueJob.addToQueue(filesTodeleteFromIndex, filesToAddToIndex, project);

	}
	public String search(Search currentSearch, boolean openEditor) {
		SearchJob newSearch = new SearchJob(currentSearch, openEditor);
		newSearch.schedule();
		return newSearch.getTicket();
	}

	private class SearchJob extends CancelableJob  {

		private final Search currentSearch;
		private final boolean openEditor;

		public String getTicket() {
			return this.currentSearch.getId();
		}

		public SearchJob(Search currentSearch, boolean openEditor) {
			super(NLS.bind("Search \"{0}\"", currentSearch.getSearchString()));
			this.currentSearch = currentSearch;
			this.openEditor = openEditor;
			currentSearch.setId(String.valueOf(System.currentTimeMillis()));
		}

		@Override
		protected IStatus runCancelable(IProgressMonitor monitor) {
			BooleanQuery.setMaxClauseCount(4096);
			if (this.currentSearch != null) {
				this.currentSearch.getResult().clear();
			}
			Query queryStringFromSearchQuery =
				getSearchService().getQueryStringFromSearchQuery(this.currentSearch, monitor);
			IProject[] projectsToSearch = getSearchService().getProjectsToSearch(this.currentSearch);
			for (IProject project : projectsToSearch) {
				try {
					TopFieldDocs search = acquireIndexSearcher(project).search(
							queryStringFromSearchQuery,null,getSearchService().getMaxResults(),new Sort());
					final ScoreDoc[] scoreDocs = search.scoreDocs;
					for (ScoreDoc scoreDoc : scoreDocs) {
						Document doc = acquireIndexSearcher(project).doc(scoreDoc.doc);
						this.currentSearch.getResult().add(createSearchResult(doc));
					}
				} catch (Exception e) {
					// do nothign...we continue..
				}
			}
			return Status.OK_STATUS;
		}

	}

	public SearchResult createSearchResult(Document doc) {
		SearchResult newSearchResult = SearchFactory.eINSTANCE.createSearchResult();
		newSearchResult.setInfoId(doc.getField(SEARCHINDEX_ITEM_ID).stringValue());
		newSearchResult.setInfoType(doc.getField(SEARCHINDEX_INFOTYPE_ID).stringValue());
		newSearchResult.setText(doc.getField(SEARCHINDEX_CONTENT).stringValue());
		newSearchResult.setTitle(doc.getField(SEARCHINDEX_LABEL).stringValue());
		newSearchResult.setKeywords(doc.getField(SEARCHINDEX_KEYWORDS).stringValue());
		return newSearchResult;
	}
}
