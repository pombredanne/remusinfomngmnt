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

package org.remus.infomngmnt.core.ref.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EContentAdapter;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Tag;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.ref.LuceneStore;
import org.remus.infomngmnt.core.services.ITagService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TagService extends LuceneStore implements ITagService {

	private final class EContentAdapterExtension extends EContentAdapter {
		@Override
		public void notifyChanged(final Notification notification) {
			switch (notification.getEventType()) {
			case Notification.ADD:
				if (notification.getNotifier() instanceof Tag) {
					addTag((Tag) notification.getNotifier());
				} else if (notification.getNotifier() instanceof InformationUnitListItem) {
					checkNewItems(Collections
							.<InformationUnitListItem> singleton((InformationUnitListItem) notification
									.getNotifier()));
				}
				break;
			case Notification.REMOVE:
				if (notification.getNotifier() instanceof Tag) {
					removeTag(((Tag) notification.getNotifier()));
				} else if (notification.getNotifier() instanceof InformationUnitListItem) {
					removeItems(Collections
							.<InformationUnitListItem> singleton((InformationUnitListItem) notification
									.getNotifier()));
				}
				break;
			default:
				break;
			}
			super.notifyChanged(notification);
		}

		private void checkNewItems(final Collection<InformationUnitListItem> singleton) {

		}

	}

	public static final String INDEX_LOCATION = "tagreferences/"; //$NON-NLS-1$

	public static final String TAGNAME = "TAGNAME"; //$NON-NLS-1$
	public static final String INFOUNITID = "TAGNAME"; //$NON-NLS-1$

	public TagService() {
		super(INDEX_LOCATION);
	}

	public void startListening() {
		ApplicationModelPool.getInstance().getModel().getAvailableTags().eAdapters().add(
				new EContentAdapterExtension());
	}

	public void addItems(final Collection<InformationUnitListItem> infoUnit, final Tag tagByName) {
		for (InformationUnitListItem informationUnitListItem : infoUnit) {
			Document returnValue = new Document();

			Field idField = new Field(TAGNAME, tagByName.getName(), Field.Store.YES,
					Field.Index.TOKENIZED);
			returnValue.add(idField);

			Field unitField = new Field(INFOUNITID, informationUnitListItem.getId(),
					Field.Store.YES, Field.Index.TOKENIZED);
			returnValue.add(unitField);
			try {
				getIndexWriter().addDocument(returnValue);
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		closeIndexWriter();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.services.ITagService#addTag(org.remus.infomngmnt
	 * .Tag)
	 */
	public void addTag(final Tag tag) {

		addTag(tag);

		addItems(tag.getInfoUnits(), tag);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.services.ITagService#getAllTags()
	 */
	public EList<Tag> getAllTags() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.services.ITagService#getTagsByInformationUnit
	 * (org.remus.infomngmnt.InformationUnitListItem)
	 */
	public Tag[] getTagsByInformationUnit(final InformationUnitListItem item) {
		List<String> termList = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();
		List<Occur> flagList = new ArrayList<Occur>();
		if (item != null) {
			fieldList.add(INFOUNITID);
			termList.add(item.getId());
			flagList.add(Occur.MUST);
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
				Document doc = getIndexSearcher().doc(scoreDoc.doc);
				// doc.get(arg0)
			}
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Tag[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.core.services.ITagService#removeItem(org.remus.
	 * infomngmnt.InformationUnitListItem)
	 */
	public void removeItems(final Collection<InformationUnitListItem> infoUnit) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.services.ITagService#removeTag(org.remus.infomngmnt
	 * .Tag)
	 */
	public void removeTag(final Tag tag) {
		// TODO Auto-generated method stub

	}

	public InformationUnitListItem[] getItemsForTag(final Tag tag) {
		List<InformationUnitListItem> returnValue = new ArrayList<InformationUnitListItem>();
		List<String> termList = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();
		List<Occur> flagList = new ArrayList<Occur>();
		if (tag != null) {
			fieldList.add(TAGNAME);
			termList.add(tag.getName());
			flagList.add(Occur.MUST);
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
					String infoUnit = doc.get(INFOUNITID);
					returnValue.add(ApplicationModelPool.getInstance().getItemById(infoUnit, null));
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
		return returnValue.toArray(new InformationUnitListItem[returnValue.size()]);
	}

}
