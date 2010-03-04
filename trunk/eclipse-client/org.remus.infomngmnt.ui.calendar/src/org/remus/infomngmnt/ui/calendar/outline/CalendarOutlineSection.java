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

package org.remus.infomngmnt.ui.calendar.outline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.ui.calendar.CalendarActivator;
import org.remus.infomngmnt.ui.calendar.CalendarEditor;
import org.remus.infomngmnt.ui.calendar.CalendarEditorInput;
import org.remus.infomngmnt.ui.calendar.CalendarEntryUtil;
import org.remus.infomngmnt.ui.calendar.NewCalendarEntryDialog;
import org.remus.infomngmnt.ui.calendar.internal.ResourceManager;
import org.remus.infomngmnt.ui.editors.outline.IOutlineSection;
import org.remus.infomngmnt.ui.viewer.ViewerActivator;
import org.remus.infomngmnt.util.IdFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarOutlineSection implements IOutlineSection {

	private Section eventSection;
	private FormText eventFormText;
	private EditingDomain domain;
	private InformationUnit infoUnit;
	private IEditingHandler editService;
	private ScrolledForm sform;

	private final AdapterImpl eventListChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification msg) {
			if (msg.getFeature() == InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY) {
				buildEventList();
			}
		}
	};

	/**
	 * 
	 */
	public CalendarOutlineSection() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.editors.outline.IOutlineSection#bindValuesToUi
	 * (org.eclipse.emf.edit.domain.EditingDomain,
	 * org.remus.infomngmnt.InformationUnit)
	 */
	public void bindValuesToUi(final EditingDomain domain, final InformationUnit infoUnit) {
		this.domain = domain;
		this.infoUnit = infoUnit;
		this.editService = ViewerActivator.getDefault().getEditService();
		buildEventList();
		this.infoUnit.eAdapters().add(this.eventListChangeAdapter);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.editors.outline.IOutlineSection#createControl
	 * (org.eclipse.ui.forms.widgets.ScrolledForm,
	 * org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	public void createControl(final ScrolledForm sform, final FormToolkit toolkit) {
		this.sform = sform;
		this.eventSection = toolkit.createSection(sform.getBody(), ExpandableComposite.TWISTIE
				| ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);

		this.eventSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		this.eventSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		this.eventSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(final ExpansionEvent e) {
				sform.reflow(false);
			}
		});

		TableWrapData td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.grabHorizontal = true;
		this.eventSection.setLayoutData(td);

		this.eventFormText = toolkit.createFormText(this.eventSection, false);
		this.eventFormText.setLayoutData(new TableWrapData(TableWrapData.FILL, TableWrapData.TOP));
		this.eventFormText.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				String string = e.getHref().toString();
				if ("addEvent".equals(string)) {
					openNewCalendarEntryDialog();
				} else if ("openCalendar".equals(string)) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.openEditor(new CalendarEditorInput(), CalendarEditor.EDITOR_ID);
					} catch (PartInitException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					EObject itemByValue = ModelUtil.getItemByValue(
							CalendarOutlineSection.this.infoUnit.getCalendarEntry(),
							InfomngmntPackage.Literals.CALENDAR_ENTRY__ID, string);
					if (itemByValue != null) {
						NewCalendarEntryDialog diag = new NewCalendarEntryDialog(sform.getShell(),
								(CalendarEntry) EcoreUtil.copy(itemByValue),
								CalendarOutlineSection.this.editService.createNewEditingDomain(),
								CalendarOutlineSection.this.infoUnit);
						if (diag.open() == IDialogConstants.OK_ID) {
							CompoundCommand cc = new CompoundCommand();
							cc.append(new RemoveCommand(CalendarOutlineSection.this.domain,
									CalendarOutlineSection.this.infoUnit,
									InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY,
									Collections.singleton(itemByValue)));
							cc.append(new CreateChildCommand(CalendarOutlineSection.this.domain,
									CalendarOutlineSection.this.infoUnit,
									InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY,
									diag.getNewObject(), Collections.EMPTY_LIST));
							cc.setLabel("Edit Calendar-Entry");
							CalendarOutlineSection.this.domain.getCommandStack().execute(cc);
						}
					}
				}
			}

		});
		initializeEventToolBar(this.eventSection, toolkit);
		this.eventSection.setClient(this.eventFormText);

	}

	private void buildEventList() {
		List<CalendarEntry> calendarEntry = new ArrayList<CalendarEntry>(this.infoUnit
				.getCalendarEntry());
		Collections.sort(calendarEntry, new Comparator<CalendarEntry>() {

			public int compare(final CalendarEntry o1, final CalendarEntry o2) {
				return o1.getStart().compareTo(o2.getStart());
			}
		});
		this.eventSection.setText(NLS.bind("Associated Events ({0})", calendarEntry.size()));
		if (calendarEntry.size() == 0) {
			this.eventFormText
					.setText(
							"<form><p>No Events associated. You can <a href=\"addEvent\">add a new Event</a> or use the <a href=\"openCalendar\">Calendar</a> to create new events.</p></form>",
							true, false);
			return;
		}

		this.eventFormText.setImage("alarm", ResourceManager.getPluginImage(CalendarActivator
				.getDefault(), "icons/iconexperience/16/alarmclock.png"));
		this.eventFormText.setImage("refresh", ResourceManager.getPluginImage(CalendarActivator
				.getDefault(), "icons/iconexperience/16/refresh.png"));
		StringBuilder sw = new StringBuilder();
		sw.append("<form>");
		for (CalendarEntry entry : calendarEntry) {
			sw.append("<p>");
			// sw.append("<li>");
			sw.append(CalendarEntryUtil.setFormTextRepresentation(entry, true));
			// sw.append("</li>");
			sw.append("</p>");
		}
		sw.append("</form>");
		this.eventFormText.setText(sw.toString(), true, false);
		this.sform.reflow(false);
	}

	/**
	 * Initialize the toolbar
	 */
	private void initializeEventToolBar(final Section section, final FormToolkit toolkit) {

		UIUtil.createSectionToolbar(section, toolkit, new Action("Add calendar entry") {
			@Override
			public void run() {
				openNewCalendarEntryDialog();
			}

			@Override
			public ImageDescriptor getImageDescriptor() {

				Image baseImage = ResourceManager.getPluginImage(CalendarActivator.getDefault(),
						"icons/iconexperience/16/calendar.png");
				Image decoratorImage = ResourceManager.getPluginImage(CalendarActivator
						.getDefault(), "icons/iconexperience/decorator/new_decorator.png");
				return ImageDescriptor.createFromImage(ResourceManager.decorateImage(baseImage,
						decoratorImage, ResourceManager.TOP_RIGHT));

			}
		}, new Action("Remove calendar entries") {
			@Override
			public void run() {
				LabelProvider labelProvider = new LabelProvider() {
					@Override
					public Image getImage(final Object object) {

						return ResourceManager.getPluginImage(CalendarActivator.getDefault(),
								"icons/iconexperience/16/calendar.png");

					}

					@Override
					public String getText(final Object object) {
						return ((CalendarEntry) object).getTitle();
					}

				};
				ListSelectionDialog diag = new ListSelectionDialog(section.getShell(),
						CalendarOutlineSection.this.infoUnit.getCalendarEntry(), UIUtil
								.getArrayContentProviderInstance(), labelProvider,
						"Select the entries to delete");
				if (diag.open() == IDialogConstants.OK_ID) {
					Command create = RemoveCommand.create(CalendarOutlineSection.this.domain,
							CalendarOutlineSection.this.infoUnit,
							InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY, Arrays
									.asList(diag.getResult()));
					CalendarOutlineSection.this.domain.getCommandStack().execute(create);
				}
			}

			@Override
			public ImageDescriptor getImageDescriptor() {
				Image baseImage = ResourceManager.getPluginImage(CalendarActivator.getDefault(),
						"icons/iconexperience/16/calendar.png");
				Image decoratorImage = ResourceManager.getPluginImage(CalendarActivator
						.getDefault(), "icons/iconexperience/decorator/delete.png");
				return ImageDescriptor.createFromImage(ResourceManager.decorateImage(baseImage,
						decoratorImage, ResourceManager.TOP_RIGHT));

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.ui.editors.outline.IOutlineSection#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.editors.outline.IOutlineSection#disposeModel()
	 */
	public void disposeModel() {
		this.infoUnit.eAdapters().remove(this.eventListChangeAdapter);

	}

	/**
	 * 
	 */
	private void openNewCalendarEntryDialog() {
		CalendarEntry calendarEntry = InfomngmntFactory.eINSTANCE.createCalendarEntry();
		calendarEntry.setId(IdFactory.createNewId(null));
		calendarEntry.setStart(new Date());
		calendarEntry.setEnd(new Date(calendarEntry.getStart().getTime() + (15 * 60 * 1000)));
		calendarEntry.setReminder(-1);
		NewCalendarEntryDialog diag = new NewCalendarEntryDialog(this.sform.getShell(),
				calendarEntry, this.domain, this.infoUnit);
		if (diag.open() == IDialogConstants.OK_ID) {
			Command create = AddCommand.create(this.domain, this.infoUnit,
					InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY, diag
							.getNewObject());
			this.domain.getCommandStack().execute(create);
		}
	}

	public int getSortRanking() {
		return 10;
	}

}
