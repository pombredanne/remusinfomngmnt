package org.remus.infomngmnt.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.LinkUtil;
import org.remus.infomngmnt.ui.commands.CommandFactory;
import org.remus.infomngmnt.ui.dnd.CustomDropTargetListener;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.link.NewLinkWizardPage;

public class LinkOutline extends ContentOutlinePage {

	private final class CustomDropTargetListenerExtension extends
	CustomDropTargetListener {
		private List objectSelection = null;

		CustomDropTargetListenerExtension(Object target) {
			super(target);
		}

		public void dragOver(DropTargetEvent event) {
			Object data = event.data;
			if (LocalTransfer.getInstance().isSupportedType(event.currentDataType)) {
				Object nativeToJava = LocalTransfer.getInstance().nativeToJava(event.currentDataType);
				if (nativeToJava instanceof IStructuredSelection) {
					this.objectSelection = ((IStructuredSelection) nativeToJava).toList();
					event.detail = DND.DROP_COPY;
				} else {
					event.detail = DND.DROP_NONE;
				}

			}
		}

		public void drop(DropTargetEvent event) {
			List<AbstractInformationUnit> sources = new ArrayList<AbstractInformationUnit>();
			for (Object obj : this.objectSelection) {
				if (obj instanceof AbstractInformationUnit) {
					sources.add((AbstractInformationUnit) obj);
				}
			}
			List<Link> createLinks = createLinks(sources);
			for (Link link : createLinks) {
				CreateChildCommand create_link = CommandFactory.CREATE_LINK(LinkOutline.this.info, link, LinkOutline.this.adapterFactoryEditingDomain);

				LinkOutline.this.adapterFactoryEditingDomain.getCommandStack().execute(create_link);
			}
			//((EList<Link>)this.targetObject).addAll(createLinks);
		}

		public List<Link> createLinks(List<AbstractInformationUnit> sources) {
			List<Link> returnValue = new ArrayList<Link>();
			for (AbstractInformationUnit source : sources) {
				Link newLink = InfomngmntFactory.eINSTANCE.createLink();
				newLink.setLinktype(LinkUtil.getInstance().getLinkTypes().getAvailableLinkTypes().get(0).getValue());
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

	public static final String ID = "org.remus.infomngmnt.ui.views.Test"; //$NON-NLS-1$
	private final InformationUnit info;

	private Composite noLinkComposite;
	private Composite linkListingComposite;
	private FormText linkListingForm;
	private final EditingDomain adapterFactoryEditingDomain;

	public LinkOutline(InformationUnit info, EditingDomain adapterFactoryEditingDomain) {
		this.info = info;
		this.adapterFactoryEditingDomain = adapterFactoryEditingDomain;

	}

	private final AdapterImpl linkListChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(org.eclipse.emf.common.notify.Notification msg) {
			buildList();
		}
	};
	private StackLayout stackLayout;
	private Composite container;

	@Override
	public Control getControl() {
		return this.container;
	}

	/**
	 * Create contents of the view part
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {

		FormToolkit toolkit = new FormToolkit(Display.getCurrent());
		//ScrolledForm createScrolledForm = toolkit.createScrolledForm(parent);

		this.container = toolkit.createComposite(parent, SWT.NONE);
		this.container.setLayout(new GridLayout());
		toolkit.paintBordersFor(this.container);



		final Section section = toolkit.createSection(this.container, Section.TITLE_BAR);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		section.setText("Links");

		final Composite composite = toolkit.createComposite(section, SWT.NONE);
		toolkit.paintBordersFor(composite);
		section.setClient(composite);

		this.stackLayout = new StackLayout();
		composite.setLayout(this.stackLayout);

		this.noLinkComposite = toolkit.createComposite(composite);
		this.noLinkComposite.setLayout(new TableWrapLayout());

		FormText formText = new FormText(this.noLinkComposite, SWT.NONE);
		formText.setText("<form><p>No Links attached. You can <a href=\"addLink\">add a new Link</a> or drop a link target into this part.</p></form>", true, false);
		formText.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				NewLinkWizardPage newLinkWizardPage = new NewLinkWizardPage(getSite().getShell(),LinkOutline.this.info);
				newLinkWizardPage.open();
			}
		});
		this.stackLayout.topControl = this.noLinkComposite;

		this.linkListingComposite = toolkit.createComposite(composite);
		this.linkListingComposite.setLayout(new TableWrapLayout());

		this.linkListingForm = toolkit.createFormText(this.linkListingComposite, true);
		buildList();
		this.info.eAdapters().add(this.linkListChangeAdapter);


		//
		createActions();
		initializeToolBar();
		initializeMenu();
		initDrop();
	}

	/**
	 * 
	 */
	private void initDrop() {
		final int dndOperations = DND.DROP_MOVE | DND.DROP_LINK | DND.DROP_COPY;
		final Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		DropTarget abstractDropZone = new DropTarget(this.noLinkComposite,dndOperations);
		abstractDropZone.setTransfer(transfers);
		abstractDropZone.addDropListener(new CustomDropTargetListenerExtension(this.info.getLinks()));

		DropTarget abstractDropZone2 = new DropTarget(this.linkListingComposite,dndOperations);
		abstractDropZone2.setTransfer(transfers);
		abstractDropZone2.addDropListener(new CustomDropTargetListenerExtension(this.info.getLinks()));
	}

	protected void buildList() {
		List<String> addedImages = new ArrayList<String>();
		EList<Link> links = this.info.getLinks();
		if (links.size() > 0) {
			this.stackLayout.topControl = this.linkListingComposite;
		} else {
			this.stackLayout.topControl = this.noLinkComposite;
		}
		StringBuilder sw = new StringBuilder();
		sw.append("<form>");
		for (Link link : links) {
			sw.append("<p>");
			//sw.append("<li>");
			sw.append("<img href=\"").append(link.getTarget().getType()).append("\" /> ");
			sw.append("<a href=\"").append(link.getTarget().getId()).append("\">");
			sw.append(link.getTarget().getLabel());
			sw.append("</a>");
			sw.append("<br />");
			//sw.append("</li>");
			if (!addedImages.contains(link.getTarget().getType())) {
				Object image2 = ((IItemLabelProvider) EditingUtil.getInstance().getAdapterFactory().adapt(link.getTarget(),IItemLabelProvider.class)).getImage(link.getTarget());
				if (image2 instanceof Image) {
					this.linkListingForm.setImage(link.getTarget().getType(), (Image) image2);
					addedImages.add(link.getTarget().getType());
				}
			}
			sw.append("</p>");
		}
		sw.append("</form>");
		this.linkListingForm.setText(sw.toString(), true, false);
		this.linkListingForm.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
							new InformationEditorInput(ApplicationModelPool.getInstance().getItemById(e.getHref().toString(), null)), InformationEditor.ID);
				} catch (PartInitException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				super.linkActivated(e);
			}
		});
		this.container.layout(true, true);
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
	private void initializeToolBar() {
		IToolBarManager tbm = getSite().getActionBars().getToolBarManager();
		tbm.add(new Action("Edit") {
			@Override
			public void run() {
				NewLinkWizardPage page = new NewLinkWizardPage(getSite().getShell(), LinkOutline.this.info);
				page.open();
			}
		});
		tbm.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		tbm.update(true);
	}

	/**
	 * Initialize the menu
	 */
	private void initializeMenu() {
		IMenuManager manager = getSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

}
