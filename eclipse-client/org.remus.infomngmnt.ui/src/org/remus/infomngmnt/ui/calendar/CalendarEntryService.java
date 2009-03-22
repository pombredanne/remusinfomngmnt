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
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.aspencloud.calypso.util.TimeSpan;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.calendar.model.EndEvent;
import org.remus.infomngmnt.calendar.model.ModelFactory;
import org.remus.infomngmnt.calendar.model.StartEvent;
import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.core.ref.LuceneStore;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarEntryService extends LuceneStore {

	public static final String INDEX_LOCATION = "calendarIndex/"; //$NON-NLS-1$

	public static final String STARTDATE = "STARTDATE"; //$NON-NLS-1$
	public static final String ENDATE = "ENDATE"; //$NON-NLS-1$
	public static final String ID = "ID"; //$NON-NLS-1$
	public static final String NAME = "NAME"; //$NON-NLS-1$
	public static final String TYPE = "TYPE"; //$NON-NLS-1$
	public static final String DESCRIPTION = "DESCRIPTION"; //$NON-NLS-1$

	public static final String DATEPATTERN = "yyyyMMddhhmmss"; //$NON-NLS-1$

	protected CalendarEntryService() {
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

	public void add(final InformationUnit unit) {
		Document returnValue = new Document();
		if (unit.getCalendarEntry().getStart() != null) {
			Field startField = new Field(STARTDATE,
					convertDate(unit.getCalendarEntry().getStart()), Field.Store.YES,
					Field.Index.TOKENIZED);
			returnValue.add(startField);
		}
		if (unit.getCalendarEntry().getEnd() != null) {
			Field endField = new Field(STARTDATE, convertDate(unit.getCalendarEntry().getEnd()),
					Field.Store.YES, Field.Index.TOKENIZED);
			returnValue.add(endField);
		}

		Field unitField = new Field(ID, unit.getId(), Field.Store.YES, Field.Index.TOKENIZED);
		returnValue.add(unitField);

		Field typeField = new Field(ID, unit.getType(), Field.Store.YES, Field.Index.TOKENIZED);
		returnValue.add(typeField);

		Field nameField = new Field(ID, unit.getLabel(), Field.Store.YES, Field.Index.TOKENIZED);
		returnValue.add(nameField);

		try {
			getIndexWriter().addDocument(returnValue);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeIndexWriter();
	}

	public void remove(final InformationUnit unit) {
		IndexWriter writer = getIndexWriter();
		writer.setUseCompoundFile(false);

		final Term term = new Term(ID, unit.getId());
		try {
			writer.deleteDocuments(term);
			writer.flush();
			writer.close();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update(final InformationUnit unit) {
		remove(unit);
		add(unit);
	}

	public Task[] getItems(final TimeSpan timespan) {

		List<Task> returnValue = new ArrayList<Task>();
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

		Query tagQuery = null;
		try {
			tagQuery = MultiFieldQueryParser.parse(termList.toArray(new String[termList.size()]),
					fieldList.toArray(new String[fieldList.size()]), flagList
							.toArray(new Occur[flagList.size()]), getAnalyser());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			TopDocs search = getIndexSearcher().search(tagQuery, null, Integer.MAX_VALUE);
			ScoreDoc[] docs = search.scoreDocs;
			for (ScoreDoc scoreDoc : docs) {
				try {
					Document doc = getIndexSearcher().doc(scoreDoc.doc);
					Task task = ModelFactory.eINSTANCE.createTask();
					StartEvent createStartEvent = ModelFactory.eINSTANCE.createStartEvent();
					createStartEvent.setDate(convertDate(doc.get(STARTDATE)));
					EndEvent createEndEvent = ModelFactory.eINSTANCE.createEndEvent();
					createEndEvent.setDate(convertDate(doc.get(ENDATE)));
					task.setName(doc.get(NAME));
					task.setDetails(doc.get(DESCRIPTION));
					returnValue.add(task);
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
		return returnValue.toArray(new Task[returnValue.size()]);

	}
}
