/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.task.tray;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.remus.common.core.util.CollectionFilter;
import org.eclipse.remus.common.core.util.CollectionUtils;
import org.eclipse.remus.common.service.ITrayService;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.ui.desktop.extension.AbstractTraySection;
import org.eclipse.remus.ui.editors.EditorUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.remus.infomngmnt.task.TaskActivator;
import org.remus.infomngmnt.task.navigation.TaskDecorationObject;
import org.remus.infomngmnt.task.navigation.TaskStateStore;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DueDateSection extends AbstractTraySection {

	private TreeViewer treeViewer;

	public static final String DAY_TYPE = "D";

	public static final String WEEK_TYPE = "W";

	class ContainerObject {

		private String type;

		private final Collection<TaskDecorationObject> elements;

		private String label;

		public ContainerObject(Collection<TaskDecorationObject> elements) {
			this.elements = elements;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getLabel() {
			return this.label;
		}

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Collection<TaskDecorationObject> getElements() {
			return this.elements;
		}
	}

	class TaskLabelProvider extends LabelProvider implements IColorProvider {

		@Override
		public String getText(Object element) {
			if (element instanceof TaskDecorationObject) {
				return ((TaskDecorationObject) element).getLabel();
			} else if (element instanceof ContainerObject) {
				return ((ContainerObject) element).getLabel();
			}
			return super.getText(element);
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof ContainerObject) {
				if (DAY_TYPE.equals(((ContainerObject) element).getType())) {

					return ResourceManager.getPluginImage(
							TaskActivator.getDefault(),
							"icons/schedule-day.png");
				}
				if (WEEK_TYPE.equals(((ContainerObject) element).getType())) {
					return ResourceManager.getPluginImage(
							TaskActivator.getDefault(),
							"icons/schedule-week.png");
				}
			}
			if (element instanceof TaskDecorationObject) {

				return ResourceManager.getPluginImage(
						TaskActivator.getDefault(),
						"icons/iconexperience/task.png");
			}
			return super.getImage(element);
		}

		public Color getForeground(Object element) {
			if (element instanceof TaskDecorationObject) {
				if (((TaskDecorationObject) element).getDueDate().before(
						new Date())) {
					return UIUtil.getDisplay().getSystemColor(SWT.COLOR_RED);
				}
			}
			return null;
		}

		public Color getBackground(Object element) {
			return null;
		}

	}

	public class TaskLabelContentProvider implements ITreeContentProvider {

		private ContainerObject today;
		private ContainerObject thisWeek;
		private ContainerObject nextWeek;

		public void dispose() {
			// TODO Auto-generated method stub

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			this.today = null;
			this.thisWeek = null;
			this.nextWeek = null;

		}

		public Object[] getElements(Object inputElement) {

			TaskStateStore store = TaskActivator.getDefault().getStore();
			List<TaskDecorationObject> decorationsForDesktop = store
					.getDecorationsForDesktop();
			List<TaskDecorationObject> todayList = CollectionUtils.filter(
					decorationsForDesktop,
					new CollectionFilter<TaskDecorationObject>() {

						public boolean select(TaskDecorationObject item) {
							return item.getDueDate() != null
									&& (item.getDueDate().before(new Date()) || DateUtils
											.isSameDay(item.getDueDate(),
													new Date()));
						}
					});

			this.today = new ContainerObject(todayList);
			this.today.setType(DAY_TYPE);
			this.today.setLabel("Today");

			List<TaskDecorationObject> thisWeekList = CollectionUtils.filter(
					decorationsForDesktop,
					new CollectionFilter<TaskDecorationObject>() {

						public boolean select(TaskDecorationObject item) {
							return item.getDueDate() != null
									&& (item.getDueDate().after(new Date()) && item
											.getDueDate()
											.before(getEndofWeekDate(new Date())));
						}
					});

			this.thisWeek = new ContainerObject(thisWeekList);
			this.thisWeek.setType(WEEK_TYPE);
			this.thisWeek.setLabel("This week");
			List<TaskDecorationObject> nextWeekList = CollectionUtils.filter(
					decorationsForDesktop,
					new CollectionFilter<TaskDecorationObject>() {

						public boolean select(TaskDecorationObject item) {
							return item.getDueDate() != null
									&& (item.getDueDate().after(
											getEndofWeekDate(new Date())) && item
											.getDueDate()
											.before(getEndofNextWeekDate(new Date())));
						}
					});

			this.nextWeek = new ContainerObject(nextWeekList);
			this.nextWeek.setType(WEEK_TYPE);
			this.nextWeek.setLabel("Next week");

			return new Object[] { this.today, this.thisWeek, this.nextWeek };
		}

		public Object[] getChildren(Object parentElement) {
			Collection<TaskDecorationObject> elements = ((ContainerObject) parentElement)
					.getElements();
			return elements.toArray(new TaskDecorationObject[elements.size()]);
		}

		public Object getParent(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean hasChildren(Object element) {
			return element instanceof ContainerObject
					&& getChildren(element).length > 0;
		}

	}

	/**
	 * 
	 */
	public DueDateSection() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.desktop.extension.AbstractTraySection#createDetailsPart
	 * (org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createDetailsPart(Composite parent) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 0;
		gridLayout.marginWidth = 0;
		parent.setLayout(gridLayout);
		this.treeViewer = new TreeViewer(parent, SWT.NONE);
		this.treeViewer.setLabelProvider(new TaskLabelProvider());
		TaskLabelContentProvider provider = new TaskLabelContentProvider();
		this.treeViewer.setContentProvider(provider);
		this.treeViewer.setInput(this);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.minimumHeight = 100;
		this.treeViewer.getTree().setLayoutData(layoutData);
		this.treeViewer.setExpandedElements(new Object[] { provider.today });
		this.treeViewer.addOpenListener(new IOpenListener() {

			public void open(OpenEvent event) {
				List list = ((IStructuredSelection) event.getSelection())
						.toList();
				for (Object object : list) {
					if (object instanceof TaskDecorationObject) {
						EditorUtil.openInfoUnit(((TaskDecorationObject) object)
								.getId());
						ITrayService trayService = getTrayService();
						if (trayService != null) {
							trayService.restoreFromTray(UIUtil
									.getPrimaryWindow().getShell());
						}
					} else if (object instanceof ContainerObject) {
						if (DueDateSection.this.treeViewer
								.getExpandedState(object)) {
							DueDateSection.this.treeViewer.collapseToLevel(
									object, 1);
						} else {
							DueDateSection.this.treeViewer.expandToLevel(
									object, 1);
						}
					}
				}

			}
		});
	}

	private Date getEndofWeekDate(Date now) {
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(now);
		endDate.add(Calendar.WEEK_OF_YEAR, 1);
		endDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		endDate.set(Calendar.HOUR, 11);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.AM_PM, Calendar.PM);
		return endDate.getTime();
	}

	private Date getEndofNextWeekDate(Date now) {
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(now);
		endDate.add(Calendar.WEEK_OF_YEAR, 2);
		endDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		endDate.set(Calendar.HOUR, 11);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.AM_PM, Calendar.PM);
		return endDate.getTime();
	}

	public ITrayService getTrayService() {
		final BundleContext bundleContext = TaskActivator.getDefault()
				.getBundle().getBundleContext();
		final ServiceReference serviceReference = bundleContext
				.getServiceReference(ITrayService.class.getName());
		if (serviceReference != null) {
			return (ITrayService) bundleContext.getService(serviceReference);
		}
		return null;

	}
}
