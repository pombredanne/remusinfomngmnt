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

package org.remus.infomngmnt.ui.calendar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.aspencloud.calypso.util.TimeSpan;

import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.calendar.model.EndEvent;
import org.remus.infomngmnt.calendar.model.ModelFactory;
import org.remus.infomngmnt.calendar.model.StartEvent;
import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.Tasklist;
import org.remus.infomngmnt.ccalendar.service.ICalendarChangeSupport;
import org.remus.infomngmnt.ccalendar.service.IDirtyTimespanListener;
import org.remus.infomngmnt.core.ref.IIndexSearchOperation;
import org.remus.infomngmnt.core.ref.IIndexWriteOperation;
import org.remus.infomngmnt.core.ref.LuceneStore;
import org.remus.infomngmnt.ui.service.ICalendarStoreService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarEntryService extends LuceneStore implements ICalendarStoreService,
		ICalendarChangeSupport {

	public static final String INDEX_LOCATION = "calendarIndex/"; //$NON-NLS-1$

	public static final String STARTDATE = "STARTDATE"; //$NON-NLS-1$
	public static final String ENDATE = "ENDATE"; //$NON-NLS-1$
	public static final String INFOID = "INFOID"; //$NON-NLS-1$
	public static final String ENTRYID = "ENTRYID"; //$NON-NLS-1$
	public static final String NAME = "NAME"; //$NON-NLS-1$
	public static final String TYPE = "TYPE"; //$NON-NLS-1$
	public static final String DESCRIPTION = "DESCRIPTION"; //$NON-NLS-1$

	public static final String DATEPATTERN = "yyyyMMddHHmmss"; //$NON-NLS-1$

	private final List<IDirtyTimespanListener> listenerList = new ArrayList<IDirtyTimespanListener>();

	public CalendarEntryService() {
		super(INDEX_LOCATION);

	}

	private static String convertDate(final Date date) {
		return new SimpleDateFormat(DATEPATTERN).format(date);
	}

	private static Date convertDate(final String date) {
		try {
			return new SimpleDateFormat(DATEPATTERN).parse(date);
		} catch (java.text.ParseException e) {

		}
		return null;
	}

	public void add(final InformationUnit unit, final CalendarEntry entry) {
		final Document returnValue = new Document();
		if (entry.getStart() != null) {
			Field startField = new Field(STARTDATE, convertDate(entry.getStart()), Field.Store.YES,
					Field.Index.TOKENIZED);
			returnValue.add(startField);
		}
		if (entry.getEnd() != null) {
			Field endField = new Field(ENDATE, convertDate(entry.getEnd()), Field.Store.YES,
					Field.Index.TOKENIZED);
			returnValue.add(endField);
		}

		Field unitField = new Field(INFOID, unit.getId(), Field.Store.YES, Field.Index.TOKENIZED);
		returnValue.add(unitField);

		Field typeField = new Field(TYPE, unit.getType(), Field.Store.YES, Field.Index.TOKENIZED);
		returnValue.add(typeField);

		Field nameField = new Field(NAME, entry.getTitle(), Field.Store.YES, Field.Index.TOKENIZED);
		returnValue.add(nameField);

		Field entryIdFiled = new Field(ENTRYID, entry.getId(), Field.Store.YES,
				Field.Index.TOKENIZED);
		returnValue.add(entryIdFiled);

		write(new IIndexWriteOperation() {
			public void write(final IndexWriter indexWriter) {
				try {
					indexWriter.addDocument(returnValue);
					fireDirtyTimeSpan(new TimeSpan(entry.getStart(), entry.getEnd()));
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

	public void removeCalendarEntry(final CalendarEntry calendarEntry) {
		final Term term = new Term(ENTRYID, calendarEntry.getId());
		write(new IIndexWriteOperation() {
			public void write(final IndexWriter indexWriter) {
				indexWriter.setUseCompoundFile(false);
				try {
					indexWriter.deleteDocuments(term);
					fireDirtyTimeSpan(new TimeSpan(calendarEntry.getStart(), calendarEntry.getEnd()));
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

	public void removeInfoUnit(final String informationUnitId) {
		final List<TimeSpan> timespansForInfoUnitId = getTimespansForInfoUnitId(informationUnitId);
		final Term term = new Term(INFOID, informationUnitId);
		write(new IIndexWriteOperation() {
			public void write(final IndexWriter indexWriter) {
				indexWriter.setUseCompoundFile(false);
				try {
					indexWriter.deleteDocuments(term);
					for (TimeSpan timeSpan : timespansForInfoUnitId) {
						fireDirtyTimeSpan(timeSpan);
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

	public void update(final InformationUnit unit, final CalendarEntry entry) {
		removeCalendarEntry(entry);
		add(unit, entry);
	}

	private List<TimeSpan> getTimespansForInfoUnitId(final String infoUnitId) {
		final List<TimeSpan> returnValue = new ArrayList<TimeSpan>();
		List<String> termList = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();
		List<Occur> flagList = new ArrayList<Occur>();

		fieldList.add(INFOID);
		termList.add(infoUnitId);
		flagList.add(Occur.MUST);

		try {
			final Query tagQuery = MultiFieldQueryParser.parse(termList.toArray(new String[termList
					.size()]), fieldList.toArray(new String[fieldList.size()]), flagList
					.toArray(new Occur[flagList.size()]), getAnalyser());
			IIndexSearchOperation operation = new IIndexSearchOperation() {
				public void read(final IndexSearcher reader) {
					try {
						TopDocs search = getIndexSearcher().search(tagQuery, null, 1000);
						ScoreDoc[] docs = search.scoreDocs;
						for (ScoreDoc scoreDoc : docs) {
							try {
								Document doc = getIndexSearcher().doc(scoreDoc.doc);
								TimeSpan ts = new TimeSpan(convertDate(doc.get(STARTDATE)),
										convertDate(doc.get(ENDATE)));
								returnValue.add(ts);
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

		return returnValue;
	}

	public Tasklist getItems(final TimeSpan timespan) {
		final Tasklist taskList = ModelFactory.eINSTANCE.createTasklist();
		List<String> termList = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();
		List<Occur> flagList = new ArrayList<Occur>();
		if (timespan.getStartDate() != null) {
			fieldList.add(STARTDATE);
			termList.add("[" + convertDate(timespan.getStartDate()) + " TO "
					+ convertDate(timespan.getEndDate()) + "]");
			flagList.add(Occur.SHOULD);
		}
		if (timespan.getEndDate() != null) {
			fieldList.add(ENDATE);
			termList.add("[" + convertDate(timespan.getStartDate()) + " TO "
					+ convertDate(timespan.getEndDate()) + "]");
			flagList.add(Occur.SHOULD);
		}

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
								Task task = ModelFactory.eINSTANCE.createTask();
								StartEvent createStartEvent = ModelFactory.eINSTANCE
										.createStartEvent();
								createStartEvent.setDate(convertDate(doc.get(STARTDATE)));
								EndEvent createEndEvent = ModelFactory.eINSTANCE.createEndEvent();
								createEndEvent.setDate(convertDate(doc.get(ENDATE)));
								task.setName(doc.get(NAME));
								task.setId(doc.get(INFOID) + "_" + doc.get(ENTRYID));
								task.setDetails(doc.get(DESCRIPTION));
								task.setStart(createStartEvent);
								task.setEnd(createEndEvent);
								taskList.getTasks().add(task);
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
		return taskList;

	}

	protected void fireDirtyTimeSpan(final TimeSpan ts) {
		for (IDirtyTimespanListener element : this.listenerList) {
			element.handleDirtyTimeSpan(ts);
		}
	}

	public void addTimeSpanListener(final IDirtyTimespanListener listener) {
		this.listenerList.add(listener);

	}

	public void removeTimeSpanListener(final IDirtyTimespanListener listener) {
		this.listenerList.remove(listener);
	}

}
