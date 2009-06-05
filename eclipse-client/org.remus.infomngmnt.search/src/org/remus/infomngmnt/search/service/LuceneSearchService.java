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
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ParallelMultiSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searchable;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.LockObtainFailedException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.progress.CancelableJob;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.SearchResult;
import org.remus.infomngmnt.search.builder.SearchBuilder;
import org.remus.infomngmnt.search.editor.SearchResultEditor;
import org.remus.infomngmnt.search.preferences.SearchPreferenceInitializer;
import org.remus.infomngmnt.search.provider.SearchPlugin;
import org.remus.infomngmnt.search.save.SavedSearchesHandler;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LuceneSearchService {

	private static LuceneSearchService INSTANCE;

	public static Object JOB_FAMILY = new Object();

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

	private class WriteQueueJob extends Job {
		IFolder indexLockFolder;

		private final Map<IProject, List<IFile>> indexQueue = new HashMap<IProject, List<IFile>>();
		private final Map<IProject, List<IFile>> removIndexQueue = new HashMap<IProject, List<IFile>>();

		public WriteQueueJob() {
			super("Writing queue to index");
			setSystem(true);
		}

		private long lastAdding;

		private int currentAddSize = 0;

		private int currentDeleteSize = 0;

		private final long maxNonWriteTime = 20000;

		private boolean writeImmediately = false;

		/*
		 * (non-Javadoc)
		 * 
		 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
		 * IProgressMonitor)
		 */
		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			if (((System.currentTimeMillis() - this.lastAdding > this.maxNonWriteTime && (this.currentAddSize > 0 || this.currentDeleteSize > 0))
					|| this.currentAddSize > 100000
					|| (this.writeImmediately && (this.currentAddSize > 0 || this.currentDeleteSize > 0)) || (Job
					.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_BUILD).length == 0 && (this.currentAddSize > 0 || this.currentDeleteSize > 0)))) {
				final Job writeIndexQueue = new Job("Write queue") {
					@Override
					protected IStatus run(final IProgressMonitor monitor) {
						final Map<IProject, List<IFile>> clonedMap = new HashMap<IProject, List<IFile>>(
								WriteQueueJob.this.indexQueue);
						WriteQueueJob.this.indexQueue.clear();
						final Map<IProject, List<IFile>> clonedDeleteMap = new HashMap<IProject, List<IFile>>(
								WriteQueueJob.this.removIndexQueue);
						WriteQueueJob.this.removIndexQueue.clear();
						int newCurrentSize = WriteQueueJob.this.currentAddSize;
						WriteQueueJob.this.currentAddSize = 0;
						WriteQueueJob.this.currentDeleteSize = 0;
						final Set<IProject> keySet = clonedMap.keySet();
						final Set<IProject> deleteKeySet = clonedDeleteMap.keySet();

						IndexReader reader = null;
						if (deleteKeySet.size() > 0) {
							monitor.beginTask("Deleting obsolete entries from index...",
									IProgressMonitor.UNKNOWN);
							try {
								for (final IProject project : deleteKeySet) {
									if (IndexReader.indexExists(getSearchService()
											.getIndexDirectory(project))) {
										reader = IndexReader.open(getSearchService()
												.getIndexDirectory(project));
										final List<IFile> list = clonedDeleteMap.get(project);
										for (final IFile path : list) {
											String id = path.getFullPath().removeFileExtension()
													.lastSegment();
											monitor.setTaskName(NLS.bind(
													"Deleting document \"{0}\"", id));
											final Term term = new Term(SEARCHINDEX_ITEM_ID, id);
											reader.deleteDocuments(term);
										}
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
										// we've done our best...
									}
								}
							}
						}
						monitor.beginTask(NLS.bind("Queued documents: {0}", newCurrentSize),
								newCurrentSize);
						for (final IProject project : keySet) {
							IndexWriter writer = null;
							try {
								final List<IFile> list = clonedMap.get(project);
								final long timeMillis = System.currentTimeMillis();
								if (list.size() > 0) {
									if (IndexReader.indexExists(getSearchService()
											.getIndexDirectory(project))) {
										reader = IndexReader.open(getSearchService()
												.getIndexDirectory(project));
										for (final IFile path : list) {
											String id = path.getFullPath().removeFileExtension()
													.lastSegment();
											monitor.setTaskName(NLS.bind(
													"Deleting document \"{0}\"", id));
											final Term term = new Term(SEARCHINDEX_ITEM_ID, id);
											reader.deleteDocuments(term);
										}
										reader.flush();
										reader.close();
										relaseIndexSearcher(project);
									}
									if (IndexReader.isLocked(getSearchService().getIndexDirectory(
											project))) {
										IndexReader.unlock(getSearchService().getIndexDirectory(
												project));
									}
									writer = new IndexWriter(getSearchService().getIndexDirectory(
											project), getSearchService().getAnalyser());
									writer.setUseCompoundFile(false);
									for (final IFile path : list) {
										try {
											final InformationUnit infoUnit = EditingUtil
													.getInstance()
													.getObjectFromUri(
															path.getFullPath(),
															InfomngmntPackage.Literals.INFORMATION_UNIT,
															false, null, false);
											if (infoUnit != null) {
												monitor.setTaskName(NLS.bind("Adding {0} to queue",
														infoUnit.getLabel()));
												IInfoType infoTypeByType = InformationExtensionManager
														.getInstance().getInfoTypeByType(
																infoUnit.getType());
												if (!infoTypeByType.isExcludeFromIndex()) {
													writer.addDocument(getSearchService()
															.getLuceneDocument(infoUnit, project,
																	monitor));
												}
												infoUnit.eResource().unload();
												monitor.worked(1);
											}
										} catch (final Exception e) {
											e.printStackTrace();
										}
										if (monitor.isCanceled()) {
											return Status.CANCEL_STATUS;
										}
									}
									if (WriteQueueJob.this.currentAddSize > 0) {
										monitor.setTaskName(NLS.bind("Still queued: {0} documents",
												WriteQueueJob.this.currentAddSize));
									} else {
										monitor.setTaskName("Done");
									}
									final IStatus status = StatusCreator.newStatus(NLS.bind(
											"Indexed {0} documents in {1} ms", list.size(), (System
													.currentTimeMillis() - timeMillis)));
									SearchPlugin.getPlugin().getLog().log(status);
									newCurrentSize -= list.size();
									WriteQueueJob.this.writeImmediately = false;
								}
							} catch (final Exception e) {
								e.printStackTrace();
							} finally {
								if (writer != null) {
									try {
										writer.flush();
										// writer.optimize();
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

		public void addToQueue(final List<IFile> removeDocuments, final List<IFile> addDocuments,
				final IProject project) {
			this.lastAdding = System.currentTimeMillis();
			if (this.indexQueue.get(project) == null) {
				this.indexQueue.put(project, new ArrayList<IFile>());
			}
			this.indexQueue.get(project).addAll(addDocuments);
			if (this.removIndexQueue.get(project) == null) {
				this.removIndexQueue.put(project, new ArrayList<IFile>());
			}
			this.removIndexQueue.get(project).addAll(removeDocuments);
			this.currentAddSize += addDocuments.size();
			this.currentDeleteSize += removeDocuments.size();
		}

	}

	protected ILuceneCustomizer getSearchService() {
		ILuceneCustomizer service = SearchPlugin.getPlugin().getLuceneCustomizationService();
		if (service == null) {
			throw new IllegalStateException("No service implementation for local search found...");
		}
		return service;
	}

	protected void relaseIndexSearcher(final IProject project) {
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

	protected IndexSearcher acquireIndexSearcher(final IProject project) {
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

	public void addToIndex(final List<IFile> filesTodeleteFromIndex,
			final List<IFile> filesToAddToIndex, final IProject project) {
		this.writeQueueJob.addToQueue(filesTodeleteFromIndex, filesToAddToIndex, project);

	}

	public String search(final Search currentSearch, final boolean openEditor) {
		return search(currentSearch, openEditor, true, null);
	}

	public String search(final Search currentSearch, final boolean openEditor,
			final boolean saveAsFile) {
		return search(currentSearch, openEditor, saveAsFile, null);
	}

	public String search(final Search currentSearch, final boolean openEditor, final boolean save,
			final ISearchCallBack callback) {
		SearchJob newSearch = new SearchJob(currentSearch, openEditor, save, callback);
		newSearch.schedule();
		return newSearch.getTicket();
	}

	private class SearchJob extends CancelableJob {
		private final Search currentSearch;
		private final boolean openEditor;
		private final boolean saveAsFile;
		private final ISearchCallBack callback;

		public String getTicket() {
			return this.currentSearch.getId();
		}

		public SearchJob(final Search currentSearch, final boolean openEditor) {
			this(currentSearch, openEditor, true, null);
		}

		public SearchJob(final Search currentSearch, final boolean openEditor,
				final boolean saveAsFile, final ISearchCallBack callback) {
			super(NLS.bind("Search \"{0}\"", currentSearch.getSearchString()));
			this.callback = callback;
			currentSearch.setId(String.valueOf(System.currentTimeMillis()));
			this.currentSearch = currentSearch;
			this.openEditor = openEditor;
			this.saveAsFile = saveAsFile;
		}

		@Override
		public boolean belongsTo(final Object family) {
			return JOB_FAMILY == family;
		}

		@Override
		protected IStatus runCancelable(final IProgressMonitor monitor) {
			BooleanQuery.setMaxClauseCount(4096);
			if (this.callback != null) {
				this.callback.beforeSearch(monitor, this.currentSearch);
			}
			if (this.currentSearch != null) {
				this.currentSearch.getResult().clear();
			}
			Query queryStringFromSearchQuery = getSearchService().getQueryStringFromSearchQuery(
					this.currentSearch, monitor);
			IProject[] projectsToSearch = getSearchService()
					.getProjectsToSearch(this.currentSearch);
			List<Searchable> searchables = new ArrayList<Searchable>();
			for (int i = 0, n = projectsToSearch.length; i < n; i++) {
				try {
					if (ResourceUtil.hasBuilder(projectsToSearch[i].getDescription(),
							SearchBuilder.BUILDER_ID)) {
						IndexSearcher acquireIndexSearcher = acquireIndexSearcher(projectsToSearch[i]);
						if (acquireIndexSearcher != null) {
							searchables.add(acquireIndexSearcher);
						}
					}
				} catch (CoreException e) {
					// do nothing.
				}
			}
			try {
				ParallelMultiSearcher searcher = new ParallelMultiSearcher(searchables
						.toArray(new Searchable[searchables.size()]));
				TopFieldDocs search = searcher.search(queryStringFromSearchQuery, null,
						getSearchService().getMaxResults(), new Sort());
				Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter(
						"{highlight-start}", "{highlight-end}"), new QueryScorer(
						queryStringFromSearchQuery));
				final ScoreDoc[] scoreDocs = search.scoreDocs;
				for (ScoreDoc scoreDoc : scoreDocs) {
					Document doc = searcher.doc(scoreDoc.doc);
					this.currentSearch.getResult().add(createSearchResult(doc, highlighter));
				}
			} catch (Exception e) {
				// do nothing...we continue..
			}

			if (this.callback != null) {
				this.callback.afterSearch(monitor, this.currentSearch);
			}
			if (this.saveAsFile) {
				final IPath savePath = SearchPlugin.getPlugin().getStateLocation().append(
						SearchPlugin.getPlugin().getPreferenceStore().getString(
								SearchPreferenceInitializer.LOCAL_SEARCH_FOLDER)).append(
						this.currentSearch.getId());
				new SavedSearchesHandler().saveObjectToResource(savePath, this.currentSearch);
				SearchPlugin.getPlugin().getSearchHistory().getSearch().add(this.currentSearch);
				if (this.openEditor) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							try {
								SearchPlugin.getPlugin().getSearchContext().deactivate();
								PlatformUI.getWorkbench().getActiveWorkbenchWindow()
										.getActivePage().openEditor(
												new URIEditorInput(URI.createFileURI(savePath
														.toOSString())),
												SearchResultEditor.class.getName());
							} catch (PartInitException e) {
								SearchPlugin.getPlugin().getLog().log(
										StatusCreator.newStatus("Error opening search-result", e));
							}
						}
					});
				}
			}
			return Status.OK_STATUS;
		}

	}

	public SearchResult createSearchResult(final Document doc, final Highlighter highlighter) {
		SearchResult newSearchResult = SearchFactory.eINSTANCE.createSearchResult();
		newSearchResult.setInfoId(doc.getField(SEARCHINDEX_ITEM_ID).stringValue());
		newSearchResult.setInfoType(doc.getField(SEARCHINDEX_INFOTYPE_ID).stringValue());
		String text = doc.get(SEARCHINDEX_CONTENT);
		TokenStream tokenStream = getSearchService().getAnalyser().tokenStream(SEARCHINDEX_CONTENT,
				new StringReader(text));
		try {
			String bestFragments = highlighter.getBestFragments(tokenStream, text, 2, "...");
			if (bestFragments.trim().length() != 0) {
				newSearchResult.setText(bestFragments);
				newSearchResult.getHighlightAttributes().add(
						SearchPackage.Literals.SEARCH_RESULT__TEXT);
			} else {
				String additionalString = doc.get(SEARCHINDEX_ADDITIONALS);
				TokenStream additionTokenStream = getSearchService().getAnalyser().tokenStream(
						SEARCHINDEX_ADDITIONALS, new StringReader(additionalString));
				String additionalBestFragments = highlighter.getBestFragments(additionTokenStream,
						additionalString, 2, "...");
				if (additionalBestFragments.trim().length() != 0) {
					newSearchResult.setText(additionalBestFragments);
				} else {
					if (text.length() > 200) {
						text = StringUtils.left(text, 200) + "...";
					}
					newSearchResult.setText(text);
				}

			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		newSearchResult.setTitle(doc.getField(SEARCHINDEX_LABEL).stringValue());
		newSearchResult.setKeywords(doc.getField(SEARCHINDEX_KEYWORDS).stringValue());

		newSearchResult.setPath(ResourcesPlugin.getWorkspace().getRoot().getProject(
				doc.getField(SEARCHINDEX_PROJECT).stringValue()).getFile(
				new Path(doc.getField(SEARCHINDEX_ITEM_ID).stringValue())
						.addFileExtension(ResourceUtil.FILE_EXTENSION)).getFullPath().toString());
		try {
			newSearchResult.setDate(getSearchService().getDateFormat().parse(
					doc.getField(SEARCHINDEX_CREATIONDATE).stringValue()));
		} catch (ParseException e) {
			// do nothing
		}
		return newSearchResult;
	}

	public void clean(final IProject project, final IProgressMonitor monitor) {
		relaseIndexSearcher(project);
		try {
			IndexWriter writer = new IndexWriter(getSearchService().getIndexDirectory(project),
					getSearchService().getAnalyser(), true);
			writer.optimize();
			writer.flush();
			writer.close();
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
		// getSearchService().clear(project, monitor);
	}
}
