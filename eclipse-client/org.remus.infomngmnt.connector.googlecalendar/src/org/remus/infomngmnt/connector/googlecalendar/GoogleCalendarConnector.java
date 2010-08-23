package org.remus.infomngmnt.connector.googlecalendar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.remus.Category;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.RemoteContainer;
import org.eclipse.remus.RemoteObject;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.SynchronizableObject;
import org.eclipse.remus.common.core.util.StringUtils;
import org.eclipse.remus.core.remote.AbstractExtensionRepository;
import org.eclipse.remus.core.remote.RemoteException;
import org.eclipse.remus.model.remote.ILoginCallBack;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.remus.util.StatusCreator;

import org.remus.infomngmnt.meetingminutes.MeetingMinutesActivator;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GoogleCalendarConnector extends AbstractExtensionRepository implements IRepository {

	private static final String EVENT_FEED_URL_SUFFIX = "/private/full";

	private static final String METAFEED_URL_BASE = "http://www.google.com/calendar/feeds/";

	// The string to add to the user's metafeedUrl to access the owncalendars
	// feed.
	private static final String OWNCALENDARS_FEED_URL_SUFFIX = "/owncalendars/full";

	private final CalendarConverter converter;

	/**
	 * 
	 */
	public GoogleCalendarConnector() {
		this.converter = new CalendarConverter();
	}

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};
	private CalendarService api;
	private String eventFeed;

	private URL metafeedUrl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.model.remote.IRepository#addToRepository(org.remus
	 * .infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject addToRepository(final SynchronizableObject item,
			final IProgressMonitor monitor) throws RemoteException {
		if (item instanceof InformationUnitListItem) {
			CalendarEventEntry repo = this.converter.toRepo((InformationUnitListItem) item,
					new CalendarEventEntry());
			if (repo != null) {
				try {
					RemoteObject remoteObjectBySynchronizableObject = getRemoteObjectBySynchronizableObject(
							(SynchronizableObject) item.eContainer(), monitor);
					if (remoteObjectBySynchronizableObject != null) {
						CalendarEntry wrappedObject = (CalendarEntry) remoteObjectBySynchronizableObject
								.getWrappedObject();
						String lastSegment = new Path(wrappedObject.getId()).lastSegment()
								.replaceFirst("%40", "@");

						CalendarEventEntry insert = getApi().insert(
								new URL(METAFEED_URL_BASE + lastSegment + EVENT_FEED_URL_SUFFIX),
								repo);
						String lastSegment2 = new Path(insert.getId()).lastSegment();
						IPath append2 = new Path(insert.getId()).removeLastSegments(2).append(
								"private/full/").append(lastSegment2);
						CalendarEventEntry entry = getApi().getEntry(new URL(append2.toString()),
								CalendarEventEntry.class);
						if (entry != null) {
							return buildEventEntry(entry, wrappedObject.getId());
						}

					}
				} catch (Exception e) {
					throw new RemoteException(StatusCreator.newStatus("Error adding contact", e));
				}
			}
		} else if (item instanceof Category) {
			CalendarEntry contactGroupEntry = new CalendarEntry();
			contactGroupEntry.setTitle(new PlainTextConstruct(((Category) item).getLabel()));
			try {
				CalendarEntry insert = getApi().insert(
						new URL(METAFEED_URL_BASE + getCredentialProvider().getUserName()
								+ OWNCALENDARS_FEED_URL_SUFFIX), contactGroupEntry);
				return buildCalendar(insert);
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus("Error creating new group", e));
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.model.remote.IRepository#commit(org.remus.infomngmnt
	 * .SynchronizableObject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject commit(final SynchronizableObject item2commit,
			final IProgressMonitor monitor) throws CoreException {
		RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(item2commit, monitor);
		if (remoteObject != null) {
			if (item2commit instanceof InformationUnitListItem
					&& remoteObject.getWrappedObject() instanceof ContactEntry) {
				CalendarEventEntry repo = this.converter.toRepo(
						(InformationUnitListItem) item2commit, (CalendarEventEntry) remoteObject
								.getWrappedObject());
				try {
					CalendarEventEntry update = repo.update();
					RemoteObject remoteParent = getRemoteObjectBySynchronizableObject(
							(SynchronizableObject) item2commit.eContainer(), monitor);
					return buildEventEntry(update, remoteParent.getId());
				} catch (Exception e) {
					throw new RemoteException(StatusCreator.newStatus(
							"Error updating a calendar-event-entry", e));
				}
			} else if (item2commit instanceof Category
					&& remoteObject.getWrappedObject() instanceof CalendarEntry) {
				CalendarEntry wrappedObject = (CalendarEntry) remoteObject.getWrappedObject();
				wrappedObject
						.setTitle(TextConstruct.plainText(((Category) item2commit).getLabel()));
				try {
					CalendarEntry update = wrappedObject.update();
					return buildCalendar(update);
				} catch (Exception e) {
					throw new RemoteException(StatusCreator.newStatus("Error updating category", e));
				}

			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.model.remote.IRepository#deleteFromRepository(org
	 * .remus.infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void deleteFromRepository(final SynchronizableObject item, final IProgressMonitor monitor)
			throws CoreException {
		RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(item, monitor);
		if (remoteObject != null) {
			Object wrappedObject = remoteObject.getWrappedObject();
			try {
				if (wrappedObject instanceof CalendarEntry) {
					((CalendarEntry) wrappedObject).delete();
				} else if (wrappedObject instanceof CalendarEventEntry) {
					((CalendarEventEntry) wrappedObject).delete();
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus("Error deleting remoteobject"));
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.model.remote.IRepository#getChildren(org.eclipse
	 * .core.runtime.IProgressMonitor, org.remus.infomngmnt.RemoteContainer,
	 * boolean)
	 */
	public RemoteObject[] getChildren(final IProgressMonitor monitor,
			final RemoteContainer container, final boolean showOnlyContainers)
			throws RemoteException {
		if (container instanceof RemoteRepository) {
			return buildCalendars();
		} else if (container instanceof RemoteContainer && !showOnlyContainers) {
			return buildCalendarEntries(container);
		}
		return new RemoteObject[0];
	}

	private RemoteObject[] buildCalendarEntries(final RemoteContainer container) {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		try {
			CalendarEntry calendar = (CalendarEntry) container.getWrappedObject();
			String lastSegment = new Path(calendar.getId()).lastSegment();
			CalendarQuery query = new CalendarQuery(new URL(StringUtils.join(METAFEED_URL_BASE,
					lastSegment, EVENT_FEED_URL_SUFFIX)));
			int startTime = ((GoogleCalendarCredentialProvider) getCredentialProvider())
					.getStartTime();
			int endTime = ((GoogleCalendarCredentialProvider) getCredentialProvider()).getEndTime();
			Calendar instance = Calendar.getInstance();
			instance.setTime(new Date());
			instance.add(Calendar.MONTH, -startTime);
			Calendar instance2 = Calendar.getInstance();
			instance2.setTime(new Date());
			instance2.add(Calendar.MONTH, endTime);
			query.setMinimumStartTime(new DateTime(instance.getTime()));
			query.setMaximumStartTime(new DateTime(instance2.getTime()));
			CalendarEventFeed resultFeed = getApi().query(query, CalendarEventFeed.class);

			for (CalendarEventEntry entry : resultFeed.getEntries()) {
				returnValue.add(buildEventEntry(entry, container.getId()));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private RemoteObject buildEventEntry(final CalendarEventEntry entry, final String parentId) {
		RemoteObject createRemoteObject = InfomngmntFactory.eINSTANCE.createRemoteObject();
		createRemoteObject.setHash(entry.getUpdated().toString());
		createRemoteObject.setId(entry.getId());
		createRemoteObject.setWrappedObject(entry);
		createRemoteObject.setName(entry.getTitle().getPlainText());
		createRemoteObject.setUrl(StringUtils.join(parentId, "_", entry.getId()));
		return createRemoteObject;
	}

	private RemoteContainer[] buildCalendars() {
		List<RemoteContainer> returnValue = new ArrayList<RemoteContainer>();
		try {
			CalendarFeed resultFeed = getApi().getFeed(this.metafeedUrl, CalendarFeed.class);
			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				CalendarEntry entry = resultFeed.getEntries().get(i);
				returnValue.add(buildCalendar(entry));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue.toArray(new RemoteContainer[returnValue.size()]);
	}

	private RemoteContainer buildCalendar(final CalendarEntry entry) {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setHash(entry.getUpdated().toString());
		createRemoteContainer.setId(entry.getId());
		createRemoteContainer.setWrappedObject(entry);
		createRemoteContainer.setName(entry.getTitle().getPlainText());
		createRemoteContainer.setUrl(entry.getId());
		return createRemoteContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.model.remote.IRepository#getFullObject(org.remus
	 * .infomngmnt.InformationUnitListItem,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public InformationUnit getFullObject(final InformationUnitListItem informationUnitListItem,
			final IProgressMonitor monitor) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		CalendarEventEntry wrappedObject = (CalendarEventEntry) remoteObject.getWrappedObject();
		return this.converter.fromRepo(wrappedObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.model.remote.IRepository#
	 * getRemoteObjectBySynchronizableObject
	 * (org.remus.infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject getRemoteObjectBySynchronizableObject(final SynchronizableObject object,
			final IProgressMonitor monitor) throws RemoteException {
		String url = object.getSynchronizationMetaData().getUrl();
		if (url.equals(getRepositoryUrl())) {
			getApi();
			return getRepositoryById(object.getSynchronizationMetaData().getRepositoryId());
		}
		if (object instanceof Category) {
			try {
				String lastSegment = new Path(url).lastSegment();
				IPath append = new Path(url).removeLastSegments(2).append(lastSegment);
				System.out.println(lastSegment + "");
				CalendarEntry entry = getApi().getEntry(new URL(append.toString()),
						CalendarEntry.class);
				if (entry != null) {
					return buildCalendar(entry);
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error resolving online calendar", e));
			}
		} else if (object instanceof InformationUnitListItem) {
			try {
				String[] split = url.split("_");
				String lastSegment = new Path(split[0]).lastSegment();
				IPath append = new Path(split[0]).removeLastSegments(2).append(lastSegment);
				CalendarEntry calendarEntry = getApi().getEntry(new URL(append.toString()),
						CalendarEntry.class);
				String lastSegment2 = new Path(split[1]).lastSegment();
				IPath append2 = new Path(split[1]).removeLastSegments(2).append("private/full/")
						.append(lastSegment2);
				CalendarEventEntry entry = getApi().getEntry(new URL(append2.toString()),
						CalendarEventEntry.class);
				if (entry != null) {
					return buildEventEntry(entry, calendarEntry.getId());
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error resolving online calendar", e));
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.model.remote.IRepository#getTypeIdByObject(org.remus
	 * .infomngmnt.RemoteObject)
	 */
	public String getTypeIdByObject(final RemoteObject remoteObject) {
		return MeetingMinutesActivator.INFO_TYPE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.model.remote.IRepository#login(org.remus.infomngmnt
	 * .model.remote.ILoginCallBack, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void login(final ILoginCallBack callback, final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getRepositoryUrl() {
		return METAFEED_URL_BASE;
	}

	@Override
	public IStatus validate() {
		try {
			getApi();
			return Status.OK_STATUS;
		} catch (RemoteException e) {
			return StatusCreator.newStatus("Error conecting to service", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.model.remote.IRepository#reset()
	 */
	public void reset() {
		this.api = null;
		getCredentialProvider().removePropertyChangeListener(this.credentialsMovedListener);

	}

	public synchronized CalendarService getApi() throws RemoteException {
		if (this.api == null) {
			getCredentialProvider().setIdentifier(getLocalRepositoryId());
			this.api = new CalendarService("Remus Information Management");
			try {
				this.api.setUserCredentials(getCredentialProvider().getUserName(),
						getCredentialProvider().getPassword());
				this.eventFeed = StringUtils.join(getRepositoryUrl(), getCredentialProvider()
						.getUserName(), EVENT_FEED_URL_SUFFIX);
				try {
					this.metafeedUrl = new URL(METAFEED_URL_BASE
							+ getCredentialProvider().getUserName());
				} catch (MalformedURLException e) {
					throw new RemoteException(StatusCreator.newStatus(
							"Error while validating credentials", e));
				}
			} catch (AuthenticationException e) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error while validating credentials", e));
			}
			getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);

		}
		return this.api;
	}

}
