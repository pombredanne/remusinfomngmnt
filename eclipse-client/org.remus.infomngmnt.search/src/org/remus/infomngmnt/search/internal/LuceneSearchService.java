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

package org.remus.infomngmnt.search.internal;

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
import org.apache.lucene.document.Field;
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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.operation.CancelableJob;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.core.services.IInformationTypeHandler;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchActivator;
import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.SearchResult;
import org.remus.infomngmnt.search.SecondaryGroupContainer;
import org.remus.infomngmnt.search.internal.builder.SearchBuilder;
import org.remus.infomngmnt.search.save.SavedSearchesHandler;
import org.remus.infomngmnt.search.service.ILuceneCustomizer;
import org.remus.infomngmnt.search.service.ISearchCallBack;
import org.remus.infomngmnt.search.service.ISearchService;
import org.remus.infomngmnt.search.service.IndexConstants;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LuceneSearchService implements ISearchService {

	private static ISearchService INSTANCE;

	private final WriteQueueJob writeQueueJob;

	private final Map<IProject, IndexSearcher> projectToSearcherMap = new HashMap<IProject, IndexSearcher>();

	private IEditingHandler editService;

	private IInformationTypeHandler informationTypeHandler;

	public LuceneSearchService() {
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
											final Term term = new Term(
													IndexConstants.SEARCHINDEX_ITEM_ID, id);
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
											final Term term = new Term(
													IndexConstants.SEARCHINDEX_ITEM_ID, id);
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
											final InformationUnit infoUnit = LuceneSearchService.this.editService
													.getObjectFromUri(
															path.getFullPath(),
															InfomngmntPackage.Literals.INFORMATION_UNIT,
															false, null, false);
											if (infoUnit != null) {
												monitor.setTaskName(NLS.bind("Adding {0} to queue",
														infoUnit.getLabel()));
												IInfoType infoTypeByType = LuceneSearchService.this.informationTypeHandler
														.getInfoTypeByType(infoUnit.getType());
												if (!infoTypeByType.isExcludeFromIndex()) {
													Document[] luceneDocument = getSearchService()
															.getLuceneDocument(infoUnit, project,
																	monitor);
													for (Document document : luceneDocument) {
														writer.addDocument(document);
													}
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
									SearchActivator.getDefault().getLog().log(status);
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
				writeIndexQueue.setUser(false);
				writeIndexQueue.setPriority(Job.BUILD);
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
		ILuceneCustomizer service = SearchActivator.getDefault().getServiceTracker().getService(
				ILuceneCustomizer.class);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.service.ISearchService#addToIndex(java.util
	 * .List, java.util.List, org.eclipse.core.resources.IProject)
	 */
	public void addToIndex(final List<IFile> filesTodeleteFromIndex,
			final List<IFile> filesToAddToIndex, final IProject project) {
		this.writeQueueJob.addToQueue(filesTodeleteFromIndex, filesToAddToIndex, project);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.search.service.ISearchService#search(org.remus.
	 * infomngmnt.search.Search, boolean)
	 */
	public String search(final Search currentSearch) {
		return search(currentSearch, true, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.search.service.ISearchService#search(org.remus.
	 * infomngmnt.search.Search, boolean, boolean)
	 */
	public String search(final Search currentSearch, final boolean saveAsFile) {
		return search(currentSearch, saveAsFile, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.search.service.ISearchService#search(org.remus.
	 * infomngmnt.search.Search, boolean, boolean,
	 * org.remus.infomngmnt.search.service.ISearchCallBack)
	 */
	public String search(final Search currentSearch, final boolean save,
			final ISearchCallBack callback) {
		SearchJob newSearch = new SearchJob(currentSearch, save, callback);
		newSearch.schedule();
		return newSearch.getTicket();
	}

	private class SearchJob extends CancelableJob {
		private final Search currentSearch;
		private final boolean saveAsFile;
		private final ISearchCallBack callback;

		public String getTicket() {
			return this.currentSearch.getId();
		}

		public SearchJob(final Search currentSearch, final boolean saveAsFile,
				final ISearchCallBack callback) {
			super(NLS.bind("Search \"{0}\"", currentSearch.getSearchString()));
			this.callback = callback;
			currentSearch.setId(String.valueOf(System.nanoTime()));
			this.currentSearch = (Search) EcoreUtil.copy(currentSearch);
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
					if (org.remus.infomngmnt.common.core.util.ResourceUtil.hasBuilder(
							projectsToSearch[i].getDescription(), SearchBuilder.BUILDER_ID)) {
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
					SearchResult createSearchResult = createSearchResult(doc, highlighter);
					EList<SearchResult> addedresult = this.currentSearch.getResult();
					for (SearchResult searchResult : addedresult) {
						if (searchResult.getInfoId().equals(createSearchResult.getInfoId())) {
							if (createSearchResult instanceof SecondaryGroupContainer) {
								searchResult.getSecondaryResults().addAll(
										createSearchResult.getSecondaryResults());
								createSearchResult = null;
							} else if (searchResult instanceof SecondaryGroupContainer) {
								createSearchResult.getSecondaryResults().addAll(
										searchResult.getSecondaryResults());
								this.currentSearch.getResult().set(
										this.currentSearch.getResult().indexOf(searchResult),
										createSearchResult);
							}
							break;
						}
					}
					if (createSearchResult != null) {
						this.currentSearch.getResult().add(createSearchResult);
					}

				}
			} catch (Exception e) {
				// do nothing...we continue..
			}

			if (this.saveAsFile) {
				final IPath savePath = SearchActivator.getDefault().getStateLocation().append(
						"localsearches").append(this.currentSearch.getId());
				synchronized (this) {
					new SavedSearchesHandler().saveObjectToResource(savePath, this.currentSearch);
					SearchActivator.getDefault().getSearchHistory().getSearch().add(
							this.currentSearch);
				}
				// if (this.openEditor) {
				// Display.getDefault().asyncExec(new Runnable() {
				// public void run() {
				// try {
				// SearchPlugin.getPlugin().getSearchContext().deactivate();
				// PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				// .getActivePage().openEditor(
				// new URIEditorInput(URI.createFileURI(savePath
				// .toOSString())),
				// SearchResultEditor.class.getName());
				// } catch (PartInitException e) {
				// SearchPlugin.getPlugin().getLog().log(
				// StatusCreator.newStatus("Error opening search-result", e));
				// }
				// }
				// });
				// }
			}
			if (this.callback != null) {
				this.callback.afterSearch(monitor, this.currentSearch);
			}
			return Status.OK_STATUS;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.service.ISearchService#createSearchResult
	 * (org.apache.lucene.document.Document,
	 * org.apache.lucene.search.highlight.Highlighter)
	 */
	public SearchResult createSearchResult(final Document doc, final Highlighter highlighter) {
		Field field = doc.getField(IndexConstants.SEARCHINDEX_CHILD_INDEX);
		boolean secondaryResult = field != null ? Boolean.valueOf(field.stringValue()) : false;
		SearchResult newSearchResult = SearchFactory.eINSTANCE.createSearchResult();

		newSearchResult.setInfoId(doc.getField(IndexConstants.SEARCHINDEX_ITEM_ID).stringValue());
		newSearchResult.setInfoType(doc.getField(IndexConstants.SEARCHINDEX_INFOTYPE_ID)
				.stringValue());
		String text = doc.get(IndexConstants.SEARCHINDEX_CONTENT);
		TokenStream tokenStream = getSearchService().getAnalyser().tokenStream(
				IndexConstants.SEARCHINDEX_CONTENT, new StringReader(text));
		try {
			String bestFragments = highlighter.getBestFragments(tokenStream, text, 2, "...");
			if (bestFragments.trim().length() != 0) {
				newSearchResult.setText(bestFragments);
				newSearchResult.getHighlightAttributes().add(
						SearchPackage.Literals.SEARCH_RESULT__TEXT);
			} else {
				String additionalString = doc.get(IndexConstants.SEARCHINDEX_ADDITIONALS);
				TokenStream additionTokenStream = getSearchService().getAnalyser().tokenStream(
						IndexConstants.SEARCHINDEX_ADDITIONALS, new StringReader(additionalString));
				String additionalBestFragments = highlighter.getBestFragments(additionTokenStream,
						additionalString, 2, "...");
				if (additionalBestFragments.trim().length() != 0) {
					newSearchResult.setText(additionalBestFragments);
				} else {
					if (text.length() > 200) {
						text = StringUtils.left(text, 200) + "...";
					} else if (text.length() == 0) {
						text = "No further description available";
					}
					newSearchResult.setText(text);
				}

			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (secondaryResult) {
			newSearchResult.setTitle(doc.getField(IndexConstants.SEARCHINDEX_CHILD_LABEL)
					.stringValue());
		} else {
			newSearchResult.setTitle(doc.getField(IndexConstants.SEARCHINDEX_LABEL).stringValue());
		}
		newSearchResult
				.setKeywords(doc.getField(IndexConstants.SEARCHINDEX_KEYWORDS).stringValue());

		newSearchResult.setPath(ResourcesPlugin.getWorkspace().getRoot().getProject(
				doc.getField(IndexConstants.SEARCHINDEX_PROJECT).stringValue()).getFile(
				new Path(doc.getField(IndexConstants.SEARCHINDEX_ITEM_ID).stringValue())
						.addFileExtension(ResourceUtil.FILE_EXTENSION)).getFullPath().toString());
		try {
			newSearchResult.setDate(getSearchService().getDateFormat().parse(
					doc.getField(IndexConstants.SEARCHINDEX_CREATIONDATE).stringValue()));
		} catch (ParseException e) {
			// do nothing
		}
		if (secondaryResult) {
			SecondaryGroupContainer createSecondaryGroupContainer = SearchFactory.eINSTANCE
					.createSecondaryGroupContainer();
			createSecondaryGroupContainer
					.setPath(ResourcesPlugin.getWorkspace().getRoot().getProject(
							doc.getField(IndexConstants.SEARCHINDEX_PROJECT).stringValue())
							.getFile(
									new Path(doc.getField(IndexConstants.SEARCHINDEX_ITEM_ID)
											.stringValue())
											.addFileExtension(ResourceUtil.FILE_EXTENSION))
							.getFullPath().toString());
			createSecondaryGroupContainer.setTitle(doc.getField(IndexConstants.SEARCHINDEX_LABEL)
					.stringValue());
			createSecondaryGroupContainer.setInfoId(doc
					.getField(IndexConstants.SEARCHINDEX_ITEM_ID).stringValue());
			createSecondaryGroupContainer.setInfoType(doc.getField(
					IndexConstants.SEARCHINDEX_INFOTYPE_ID).stringValue());
			createSecondaryGroupContainer.getSecondaryResults().add(newSearchResult);
			try {
				createSecondaryGroupContainer.setDate(getSearchService().getDateFormat().parse(
						doc.getField(IndexConstants.SEARCHINDEX_CREATIONDATE).stringValue()));
			} catch (ParseException e) {
				// do nothing
			}
			return createSecondaryGroupContainer;
		}
		return newSearchResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.service.ISearchService#clean(org.eclipse.
	 * core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
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

	public void setEditHandler(final IEditingHandler service) {
		this.editService = service;

	}

	public void setInformationTypeHandler(final IInformationTypeHandler service) {
		this.informationTypeHandler = service;

	}
}
