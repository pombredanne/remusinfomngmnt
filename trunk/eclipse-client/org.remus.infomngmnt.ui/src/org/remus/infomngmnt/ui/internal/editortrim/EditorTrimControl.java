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

package org.remus.infomngmnt.ui.internal.editortrim;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandImageService;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.handlers.IHandlerService;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.extension.UIExtensionManager;
import org.remus.infomngmnt.ui.perspective.Perspective;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EditorTrimControl extends Composite {

	private final ImageHyperlink hyperLink;
	private Command currentCommand;
	private Image contributedImage;
	private boolean backMode;
	private InformationUnitListItem itemById;

	public EditorTrimControl(final Composite parent, final int style) {
		super(parent, style);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		setLayout(layout);
		this.hyperLink = new ImageHyperlink(this, SWT.NONE);

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, true).applyTo(
				this.hyperLink);
		this.hyperLink.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(final MouseEvent e) {
				((ImageHyperlink) e.widget).setUnderlined(true);
			}

			@Override
			public void mouseExit(final MouseEvent e) {
				((ImageHyperlink) e.widget).setUnderlined(false);
			}
		});
		this.hyperLink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				if (EditorTrimControl.this.backMode) {
					executeBack(EditorTrimControl.this.itemById);
				} else {
					if (EditorTrimControl.this.currentCommand != null) {
						IHandlerService service = (IHandlerService) PlatformUI.getWorkbench()
								.getService(IHandlerService.class);
						try {
							service.executeCommand(EditorTrimControl.this.currentCommand.getId(),
									new Event());
						} catch (ExecutionException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NotDefinedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NotEnabledException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NotHandledException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
	}

	@Override
	public void setBackground(final Color color) {
		super.setBackground(color);
		this.hyperLink.setBackground(color);
	}

	public void buildHyperLink(final String typeId) {
		String commandId = UIExtensionManager.getInstance().getEditorTrimCommandIdByTypeId(typeId);

		if (commandId != null) {
			ICommandService commandService = (ICommandService) PlatformUI.getWorkbench()
					.getService(ICommandService.class);
			ICommandImageService commandImageService = (ICommandImageService) PlatformUI
					.getWorkbench().getService(ICommandImageService.class);
			Command command = commandService.getCommand(commandId);
			if (command.isDefined()) {
				this.currentCommand = command;
				ImageDescriptor imageDescriptor = commandImageService.getImageDescriptor(commandId);
				if (imageDescriptor != null) {
					this.contributedImage = ResourceManager.getImage(imageDescriptor);
				} else {
					this.contributedImage = null;
				}
				String name = "Not defined";
				try {
					name = command.getName();
				} catch (NotDefinedException e) {
					// do nothing
				}
				setHyperLink(this.contributedImage, name);
				return;
			}
		}
		this.currentCommand = null;
		setHyperLink(null, "No actions available");
		this.backMode = false;

	}

	private void checkDisposeImage() {
		if (this.contributedImage != null && !this.contributedImage.isDisposed()) {
			this.contributedImage.dispose();
		}
	}

	private void setHyperLink(final Image image, final String text) {
		if (!this.hyperLink.isDisposed()) {
			this.hyperLink.setImage(image);
			this.hyperLink.setText(text);
			layout();
		}
	}

	public void buildBackButton(final String infoId) {
		this.itemById = ApplicationModelPool.getInstance().getItemById(infoId,
				new NullProgressMonitor());
		Image backImage = PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_TOOL_BACK);
		setHyperLink(backImage, NLS.bind("Back to {0}", this.itemById.getLabel()));
		this.backMode = true;
	}

	private void executeBack(final InformationUnitListItem item) {
		final IPerspectiveDescriptor perspectiveDescriptor = PlatformUI.getWorkbench()
				.getPerspectiveRegistry().findPerspectiveWithId(Perspective.PERSPECTIVE_ID);
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.getActiveEditor(), true);
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.setPerspective(perspectiveDescriptor);
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.openEditor(new InformationEditorInput(item), InformationEditor.ID);
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.backMode = false;
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}
