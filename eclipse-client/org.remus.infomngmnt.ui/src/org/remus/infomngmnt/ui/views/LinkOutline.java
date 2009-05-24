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
package org.remus.infomngmnt.ui.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.ManagedForm;
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
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.IdFactory;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.calendar.CalendarEditor;
import org.remus.infomngmnt.ui.calendar.CalendarEditorInput;
import org.remus.infomngmnt.ui.calendar.CalendarEntryUtil;
import org.remus.infomngmnt.ui.calendar.NewCalendarEntryDialog;
import org.remus.infomngmnt.ui.dnd.CustomDropTargetListener;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.link.NewLinkWizardPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkOutline extends ContentOutlinePage {

	private final class CustomDropTargetListenerExtension extends CustomDropTargetListener {
		private List objectSelection = null;

		CustomDropTargetListenerExtension(final Object target) {
			super(target);
		}

		public void dragOver(final DropTargetEvent event) {
			Object data = event.data;
			if (LocalTransfer.getInstance().isSupportedType(event.currentDataType)) {
				Object nativeToJava = LocalTransfer.getInstance().nativeToJava(
						event.currentDataType);
				if (nativeToJava instanceof IStructuredSelection) {
					this.objectSelection = ((IStructuredSelection) nativeToJava).toList();
					if (this.objectSelection.contains(LinkOutline.this.info
							.getAdapter(InformationUnitListItem.class))) {
						event.detail = DND.DROP_NONE;
					} else {
						event.detail = DND.DROP_COPY;
					}
				} else {
					event.detail = DND.DROP_NONE;
				}

			}
		}

		public void drop(final DropTargetEvent event) {
			List<AbstractInformationUnit> sources = new ArrayList<AbstractInformationUnit>();
			for (Object obj : this.objectSelection) {
				if (obj instanceof AbstractInformationUnit) {
					sources.add((AbstractInformationUnit) obj);
				}
			}
			List<Link> createLinks = createLinks(sources);
			for (Link link : createLinks) {
				CreateChildCommand create_link = CommandFactory.CREATE_LINK(LinkOutline.this.info,
						link, getAdapterFactoryEditingDomain());
				getAdapterFactoryEditingDomain().getCommandStack().execute(create_link);
			}
			// ((EList<Link>)this.targetObject).addAll(createLinks);
		}

		public List<Link> createLinks(final List<AbstractInformationUnit> sources) {
			List<Link> returnValue = new ArrayList<Link>();
			for (AbstractInformationUnit source : sources) {
				Link newLink = InfomngmntFactory.eINSTANCE.createLink();
				if (source instanceof InformationUnit) {
					newLink.setTarget((InformationUnit) source);
					returnValue.add(newLink);
				} else if (source instanceof InformationUnitListItem) {
					Object adapter = source.getAdapter(InformationUnit.class);
					if (adapter != null) {
						newLink.setTarget((InformationUnit) adapter);
						returnValue.add(newLink);
					}
				}
			}
			return returnValue;
		}
	}

	public LinkOutline(final InformationUnit info, final EditingDomain adapterFactoryEditingDomain) {
		this.info = info;
		this.adapterFactoryEditingDomain = adapterFactoryEditingDomain;

	}

	private final AdapterImpl linkListChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification msg) {
			if (msg.getFeature() == InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS) {
				buildList();
			}
		}
	};
	private final AdapterImpl eventListChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification msg) {
			if (msg.getFeature() == InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY) {
				buildEventList();
			}
		}
	};

	public static final String ID = "org.remus.infomngmnt.ui.views.Test"; //$NON-NLS-1$

	private InformationUnit info;

	private ScrolledForm sform;

	private FormText eventFormText;

	private FormText linkFormText;

	private EditingDomain adapterFactoryEditingDomain;

	private Section linkSection;
	private Section eventSection;
	private FormToolkit toolkit;
	private CustomDropTargetListenerExtension dropTargetListenerExtension;
	private DropTarget abstractDropZone;

	@Override
	public Control getControl() {
		return this.sform;
	}

	/**
	 * Create contents of the view part
	 * 
	 * @param parent
	 */
	@Override
	public void createControl(final Composite parent) {

		this.toolkit = new FormToolkit(parent.getDisplay());
		this.sform = this.toolkit.createScrolledForm(parent);
		this.sform.setLayoutData(new GridData(GridData.FILL_BOTH));
		ManagedForm managedForm = new ManagedForm(this.toolkit, this.sform);

		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		this.sform.getBody().setLayout(layout);

		createEventSection(this.sform, this.toolkit);
		createLinkSection(this.sform, this.toolkit);
		bindValuesToUi();

	}

	private void createLinkSection(final ScrolledForm sform2, final FormToolkit toolkit) {

		this.linkSection = toolkit.createSection(this.sform.getBody(), ExpandableComposite.TWISTIE
				| ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);

		this.linkSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		this.linkSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		this.linkSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(final ExpansionEvent e) {
				LinkOutline.this.sform.reflow(false);
			}
		});

		TableWrapData td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.grabHorizontal = true;
		this.linkSection.setLayoutData(td);

		this.linkFormText = toolkit.createFormText(this.linkSection, false);
		this.linkFormText.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				String string = e.getHref().toString();
				if ("addLink".equals(string)) {
					NewLinkWizardPage newLinkWizardPage = new NewLinkWizardPage(getSite()
							.getShell(), LinkOutline.this.info, LinkOutline.this
							.getAdapterFactoryEditingDomain());
					int open = newLinkWizardPage.open();
					if (open == IDialogConstants.OK_ID) {
						performResult(newLinkWizardPage.getResult());
					}
				} else {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.openEditor(
										new InformationEditorInput(ApplicationModelPool
												.getInstance().getItemById(e.getHref().toString(),
														null)), InformationEditor.ID);
					} catch (PartInitException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		this.linkSection.setClient(this.linkFormText);

		//

		initializeLinkToolBar(this.linkSection, toolkit);
		initDrop();

	}

	private void createEventSection(final ScrolledForm sform, final FormToolkit toolkit) {
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
					EObject itemByValue = ModelUtil.getItemByValue(LinkOutline.this.info
							.getCalendarEntry(), InfomngmntPackage.Literals.CALENDAR_ENTRY__ID,
							string);
					if (itemByValue != null) {
						NewCalendarEntryDialog diag = new NewCalendarEntryDialog(getSite()
								.getShell(), (CalendarEntry) EcoreUtil.copy(itemByValue),
								EditingUtil.getInstance().createNewEditingDomain(),
								LinkOutline.this.info);
						if (diag.open() == IDialogConstants.OK_ID) {
							CompoundCommand cc = new CompoundCommand();
							cc.append(new RemoveCommand(getAdapterFactoryEditingDomain(),
									LinkOutline.this.info,
									InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY,
									Collections.singleton(itemByValue)));
							cc.append(new CreateChildCommand(getAdapterFactoryEditingDomain(),
									LinkOutline.this.info,
									InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY,
									diag.getNewObject(), Collections.EMPTY_LIST));
							cc.setLabel("Edit Calendar-Entry");
							getAdapterFactoryEditingDomain().getCommandStack().execute(cc);
						}
					}
				}
			}

		});
		initializeEventToolBar(this.eventSection, toolkit);
		this.eventSection.setClient(this.eventFormText);

	}

	protected void performResult(final Collection result) {
		Command command = SetCommand.create(getAdapterFactoryEditingDomain(), this.info,
				InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS, result);
		((AbstractCommand) command).setLabel("Edit Links");
		getAdapterFactoryEditingDomain().getCommandStack().execute(command);
	}

	public void bindValuesToUi() {
		buildEventList();
		buildList();
		this.info.eAdapters().add(this.eventListChangeAdapter);
		this.info.eAdapters().add(this.linkListChangeAdapter);
		this.dropTargetListenerExtension = new CustomDropTargetListenerExtension(this.info
				.getLinks());
		this.abstractDropZone.addDropListener(this.dropTargetListenerExtension);

	}

	public void disposeModel() {
		this.info.eAdapters().remove(this.eventListChangeAdapter);
		this.info.eAdapters().remove(this.linkListChangeAdapter);
		if (!this.abstractDropZone.isDisposed()) {
			this.abstractDropZone.removeDropListener(this.dropTargetListenerExtension);
		}
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
		NewCalendarEntryDialog diag = new NewCalendarEntryDialog(getSite().getShell(),
				calendarEntry, getAdapterFactoryEditingDomain(), LinkOutline.this.info);
		if (diag.open() == IDialogConstants.OK_ID) {
			Command create = AddCommand.create(getAdapterFactoryEditingDomain(),
					LinkOutline.this.info,
					InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY, diag
							.getNewObject());
			getAdapterFactoryEditingDomain().getCommandStack().execute(create);
		}
	}

	/**
	 * 
	 */
	private void initDrop() {
		final int dndOperations = DND.DROP_MOVE | DND.DROP_LINK | DND.DROP_COPY;
		final Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		this.abstractDropZone = new DropTarget(this.linkFormText, dndOperations);
		this.abstractDropZone.setTransfer(transfers);

	}

	protected void buildList() {
		List<String> addedImages = new ArrayList<String>();
		EList<Link> links = this.info.getLinks();
		this.linkSection.setText(NLS.bind("Links ({0})", links.size()));
		if (links.size() == 0) {
			this.linkFormText
					.setText(
							"<form><p>No Links attached. You can <a href=\"addLink\">add a new Link</a> or drop a link target into this part.</p></form>",
							true, false);
			return;
		}
		StringBuilder sw = new StringBuilder();
		sw.append("<form>");
		for (Link link : links) {
			sw.append("<p>");
			// sw.append("<li>");
			sw.append("<img href=\"").append(link.getTarget().getType()).append("\" /> ");
			sw.append("<a href=\"").append(link.getTarget().getId()).append("\">");
			sw.append(StringEscapeUtils.escapeXml(link.getTarget().getLabel()));
			sw.append("</a>");
			sw.append("<br />");
			// sw.append("</li>");
			if (!addedImages.contains(link.getTarget().getType())) {
				Object image2 = ((IItemLabelProvider) EditingUtil.getInstance().getAdapterFactory()
						.adapt(link.getTarget(), IItemLabelProvider.class)).getImage(link
						.getTarget());
				if (image2 instanceof Image) {
					this.linkFormText.setImage(link.getTarget().getType(), (Image) image2);
					addedImages.add(link.getTarget().getType());
				}
			}
			sw.append("</p>");
		}
		sw.append("</form>");
		this.linkFormText.setText(sw.toString(), true, false);
		this.sform.reflow(false);
	}

	private void buildEventList() {
		List<CalendarEntry> calendarEntry = new ArrayList<CalendarEntry>(this.info
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
		this.eventFormText.setImage("alarm", ResourceManager.getPluginImage(UIPlugin.getDefault(),
				"icons/iconexperience/16/alarmclock.png"));
		this.eventFormText.setImage("refresh", ResourceManager.getPluginImage(
				UIPlugin.getDefault(), "icons/iconexperience/16/refresh.png"));
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
	 * Create the actions
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar
	 */
	private void initializeLinkToolBar(final Section section, final FormToolkit toolkit) {

		UIUtil.createSectionToolbar(section, toolkit, new Action("Edit Links") {
			@Override
			public void run() {
				NewLinkWizardPage page = new NewLinkWizardPage(getSite().getShell(),
						LinkOutline.this.info, getAdapterFactoryEditingDomain());
				if (page.open() == IDialogConstants.OK_ID) {
					performResult(page.getResult());
				}
			}

			@Override
			public ImageDescriptor getImageDescriptor() {
				return ResourceManager.getPluginImageDescriptor(UIPlugin.getDefault(),
						"icons/iconexperience/16/edit.png");
			}
		});

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
				Image baseImage = ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/iconexperience/16/calendar.png");
				Image decoratorImage = ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/iconexperience/decorator/new_decorator.png");
				return ImageDescriptor.createFromImage(ResourceManager.decorateImage(baseImage,
						decoratorImage, ResourceManager.TOP_RIGHT));
			}
		}, new Action("Remove calendar entries") {
			@Override
			public void run() {
				LabelProvider labelProvider = new LabelProvider() {
					@Override
					public Image getImage(final Object object) {
						return ResourceManager.getPluginImage(UIPlugin.getDefault(),
								"icons/iconexperience/16/calendar.png");
					}

					@Override
					public String getText(final Object object) {
						return ((CalendarEntry) object).getTitle();
					}

				};
				ListSelectionDialog diag = new ListSelectionDialog(getSite().getShell(),
						LinkOutline.this.info.getCalendarEntry(), UIUtil
								.getArrayContentProviderInstance(), labelProvider,
						"Select the entries to delete");
				if (diag.open() == IDialogConstants.OK_ID) {
					Command create = RemoveCommand.create(getAdapterFactoryEditingDomain(),
							LinkOutline.this.info,
							InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY, Arrays
									.asList(diag.getResult()));
					getAdapterFactoryEditingDomain().getCommandStack().execute(create);
				}
			}

			@Override
			public ImageDescriptor getImageDescriptor() {
				Image baseImage = ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/iconexperience/16/calendar.png");
				Image decoratorImage = ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/iconexperience/decorator/delete.png");
				return ImageDescriptor.createFromImage(ResourceManager.decorateImage(baseImage,
						decoratorImage, ResourceManager.TOP_RIGHT));
			}
		});

	}

	protected Layout createScrolledFormLayout() {
		final GridLayout layout = new GridLayout();

		layout.marginHeight = 0;
		layout.marginWidth = 0;

		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;

		return layout;
	}

	/**
	 * Initialize the menu
	 */
	private void initializeLinkMenu() {
		IMenuManager manager = getSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(final InformationUnit info) {
		this.info = info;
	}

	/**
	 * @param adapterFactoryEditingDomain
	 *            the adapterFactoryEditingDomain to set
	 */
	public void setAdapterFactoryEditingDomain(final EditingDomain adapterFactoryEditingDomain) {
		this.adapterFactoryEditingDomain = adapterFactoryEditingDomain;
	}

	public EditingDomain getAdapterFactoryEditingDomain() {
		return this.adapterFactoryEditingDomain;
	}

}
