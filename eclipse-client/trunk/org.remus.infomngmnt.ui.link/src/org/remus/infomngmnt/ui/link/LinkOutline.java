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

package org.remus.infomngmnt.ui.link;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
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

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.editors.outline.IOutlineSection;
import org.remus.infomngmnt.ui.viewer.ViewerActivator;
import org.remus.infomngmnt.ui.viewer.dnd.CustomDropTargetListener;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkOutline implements IOutlineSection {

	private Section linkSection;
	private FormText linkFormText;
	private EditingDomain domain;
	private InformationUnit infoUnit;
	private ScrolledForm form;
	private DropTarget abstractDropZone;

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
					if (this.objectSelection.contains(LinkOutline.this.infoUnit
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
				CreateChildCommand create_link = CommandFactory.CREATE_LINK(
						LinkOutline.this.infoUnit, link, LinkOutline.this.domain);
				LinkOutline.this.domain.getCommandStack().execute(create_link);
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

	private final AdapterImpl linkListChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification msg) {
			if (msg.getFeature() == InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS) {
				buildList();
			}
		}
	};
	private CustomDropTargetListenerExtension dropTargetListenerExtension;
	private IApplicationModel applicationService;
	private IEditingHandler editService;

	/**
	 * 
	 */
	public LinkOutline() {
		// TODO Auto-generated constructor stub
	}

	public void bindValuesToUi(final EditingDomain domain, final InformationUnit infoUnit) {
		this.domain = domain;
		this.infoUnit = infoUnit;
		this.applicationService = ViewerActivator.getDefault().getApplicationService();
		this.editService = ViewerActivator.getDefault().getEditService();
		buildList();
		this.infoUnit.eAdapters().add(this.linkListChangeAdapter);
		this.dropTargetListenerExtension = new CustomDropTargetListenerExtension(this.infoUnit
				.getLinks());
		this.abstractDropZone.addDropListener(this.dropTargetListenerExtension);

	}

	public void createControl(final ScrolledForm form, final FormToolkit toolkit) {
		this.form = form;
		createLinkSection(form, toolkit);

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	private void createLinkSection(final ScrolledForm sform, final FormToolkit toolkit) {

		this.linkSection = toolkit.createSection(sform.getBody(), ExpandableComposite.TWISTIE
				| ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);

		this.linkSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		this.linkSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		this.linkSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(final ExpansionEvent e) {
				sform.reflow(false);
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
					NewLinkWizardPage newLinkWizardPage = new NewLinkWizardPage(sform.getShell(),
							LinkOutline.this.infoUnit, LinkOutline.this.domain);
					int open = newLinkWizardPage.open();
					if (open == IDialogConstants.OK_ID) {
						performResult(newLinkWizardPage.getResult());
					}
				} else {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.openEditor(
										new InformationEditorInput(
												LinkOutline.this.applicationService.getItemById(e
														.getHref().toString(), null)),
										InformationEditor.ID);
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

	/**
	 * Initialize the toolbar
	 */
	private void initializeLinkToolBar(final Section section, final FormToolkit toolkit) {

		UIUtil.createSectionToolbar(section, toolkit, new Action("Edit Links") {
			@Override
			public void run() {
				NewLinkWizardPage page = new NewLinkWizardPage(LinkOutline.this.form.getShell(),
						LinkOutline.this.infoUnit, LinkOutline.this.domain);
				if (page.open() == IDialogConstants.OK_ID) {
					performResult(page.getResult());
				}
			}

			@Override
			public ImageDescriptor getImageDescriptor() {
				// FIXME
				return null;
				// return
				// ResourceManager.getPluginImageDescriptor(UIPlugin.getDefault(),
				// "icons/iconexperience/16/edit.png");
			}
		});

	}

	protected void performResult(final Collection result) {
		Command command = SetCommand.create(this.domain, this.infoUnit,
				InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS, result);
		((AbstractCommand) command).setLabel("Edit Links");
		this.domain.getCommandStack().execute(command);
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
		EList<Link> links = this.infoUnit.getLinks();
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
				Object image2 = ((IItemLabelProvider) this.editService.getAdapterFactory().adapt(
						link.getTarget(), IItemLabelProvider.class)).getImage(link.getTarget());
				if (image2 instanceof Image) {
					this.linkFormText.setImage(link.getTarget().getType(), (Image) image2);
					addedImages.add(link.getTarget().getType());
				}
			}
			sw.append("</p>");
		}
		sw.append("</form>");
		this.linkFormText.setText(sw.toString(), true, false);
		this.form.reflow(false);
	}

	public void disposeModel() {
		this.infoUnit.eAdapters().remove(this.linkListChangeAdapter);
		if (!this.abstractDropZone.isDisposed()) {
			this.abstractDropZone.removeDropListener(this.dropTargetListenerExtension);
		}
	}

	public int getSortRanking() {
		return 50;
	}
}
