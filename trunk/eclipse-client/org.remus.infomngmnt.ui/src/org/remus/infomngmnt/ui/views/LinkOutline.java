package org.remus.infomngmnt.ui.views;

import java.util.ArrayList;
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
import org.remus.infomngmnt.ui.calendar.CalendarEntryUtil;
import org.remus.infomngmnt.ui.calendar.NewCalendarEntryDialog;
import org.remus.infomngmnt.ui.dnd.CustomDropTargetListener;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.link.NewLinkWizardPage;

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
						link, LinkOutline.this.adapterFactoryEditingDomain);
				LinkOutline.this.adapterFactoryEditingDomain.getCommandStack().execute(create_link);
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
			if (msg.getFeature() instanceof Link) {
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

	private final InformationUnit info;

	private ScrolledForm sform;

	private FormText eventFormText;

	private FormText linkFormText;

	private final EditingDomain adapterFactoryEditingDomain;

	private Section linkSection;
	private Section eventSection;

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

		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		this.sform = toolkit.createScrolledForm(parent);
		this.sform.setLayoutData(new GridData(GridData.FILL_BOTH));
		ManagedForm managedForm = new ManagedForm(toolkit, this.sform);

		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		this.sform.getBody().setLayout(layout);

		createEventSection(this.sform, toolkit);
		createLinkSection(this.sform, toolkit);

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
							.getShell(), LinkOutline.this.info,
							LinkOutline.this.adapterFactoryEditingDomain);
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
		buildList();

		this.info.eAdapters().add(this.linkListChangeAdapter);

		//
		createActions();
		initializeToolBar(this.linkSection, toolkit);
		initializeLinkMenu();
		initDrop();
		bindValuesToUi();

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
					CalendarEntry calendarEntry = InfomngmntFactory.eINSTANCE.createCalendarEntry();
					calendarEntry.setId(IdFactory.createNewId(null));
					calendarEntry.setStart(new Date());
					calendarEntry.setEnd(new Date(calendarEntry.getStart().getTime()
							+ (15 * 60 * 1000)));
					calendarEntry.setReminder(-1);
					NewCalendarEntryDialog diag = new NewCalendarEntryDialog(getSite().getShell(),
							calendarEntry, LinkOutline.this.adapterFactoryEditingDomain,
							LinkOutline.this.info);
					if (diag.open() == IDialogConstants.OK_ID) {
						Command create = AddCommand.create(
								LinkOutline.this.adapterFactoryEditingDomain,
								LinkOutline.this.info,
								InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY, diag
										.getNewObject());
						LinkOutline.this.adapterFactoryEditingDomain.getCommandStack().execute(
								create);
					}
				} else if ("openCalendar".equals(string)) {
					// TODO open calendar.
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
							cc.append(new RemoveCommand(
									LinkOutline.this.adapterFactoryEditingDomain,
									LinkOutline.this.info,
									InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY,
									Collections.singleton(itemByValue)));
							cc.append(new CreateChildCommand(
									LinkOutline.this.adapterFactoryEditingDomain,
									LinkOutline.this.info,
									InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY,
									diag.getNewObject(), Collections.EMPTY_LIST));
							cc.setLabel("Edit Calendar-Entry");
							LinkOutline.this.adapterFactoryEditingDomain.getCommandStack().execute(
									cc);
						}
					}
				}
			}
		});
		this.info.eAdapters().add(this.eventListChangeAdapter);
		buildEventList();
		this.eventSection.setClient(this.eventFormText);

	}

	protected void performResult(final Collection result) {
		Command command = SetCommand.create(this.adapterFactoryEditingDomain, this.info,
				InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS, result);
		((AbstractCommand) command).setLabel("Edit Links");
		this.adapterFactoryEditingDomain.getCommandStack().execute(command);
	}

	private void bindValuesToUi() {

	}

	protected void fillCalendarEntries() {

	}

	/**
	 * 
	 */
	private void initDrop() {
		final int dndOperations = DND.DROP_MOVE | DND.DROP_LINK | DND.DROP_COPY;
		final Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		DropTarget abstractDropZone = new DropTarget(this.linkFormText, dndOperations);
		abstractDropZone.setTransfer(transfers);
		abstractDropZone
				.addDropListener(new CustomDropTargetListenerExtension(this.info.getLinks()));

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
	private void initializeToolBar(final Section section, final FormToolkit toolkit) {

		UIUtil.createSectionToolbar(section, toolkit, new Action("Edit Links") {
			@Override
			public void run() {
				NewLinkWizardPage page = new NewLinkWizardPage(getSite().getShell(),
						LinkOutline.this.info, LinkOutline.this.adapterFactoryEditingDomain);
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

}
