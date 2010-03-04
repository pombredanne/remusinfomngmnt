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

package org.remus.infomngmnt.ui.notification;

import java.util.Collection;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.common.service.ITrayService;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IInformationTypeHandler;
import org.remus.infomngmnt.ui.infotypes.service.IInformationTypeImage;
import org.remus.infomngmnt.ui.notification.view.NotificationView;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NotificationControl extends Composite {

	private final FormToolkit toolkit;

	private Window window;

	public static final int VISIBLE_NOTIFICATIONS = 5;
	private final Collection<Notification> notifications2Show;

	private final IApplicationModel appService;

	private final IInformationTypeImage imageService;

	private IInformationTypeHandler infoTypeService;

	public NotificationControl(final Composite parent, final FormToolkit toolkit,
			final Collection<Notification> notifications2Show2) {
		super(parent, SWT.NONE);
		this.toolkit = toolkit;
		this.notifications2Show = notifications2Show2;
		toolkit.adapt(this);
		this.appService = NotificationActivator.getDefault().getServiceTracker().getService(
				IApplicationModel.class);
		this.imageService = NotificationActivator.getDefault().getServiceTracker().getService(
				IInformationTypeImage.class);

		addListeners();

		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 0;
		layout.rightMargin = 0;
		layout.topMargin = 0;
		layout.bottomMargin = 0;

		createContents();

		setLayout(layout);
		setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB));
	}

	private void createContents() {
		int count = 0;
		if (this.notifications2Show.size() == 0) {
			Label createText = this.toolkit.createLabel(this, "No unread notifications");
			TableWrapData layoutData = new TableWrapData(TableWrapData.CENTER);
			layoutData.grabHorizontal = true;
			createText.setLayoutData(layoutData);
		}
		for (final Notification notification : this.notifications2Show) {
			if (count < VISIBLE_NOTIFICATIONS && !notification.isNoticed()) {
				final NotificationLink itemLink = new NotificationLink(this, SWT.BEGINNING
						| SWT.NO_FOCUS);

				itemLink.setText(notification.getMessage());
				if (notification.getAffectedInfoUnitIds().size() > 0
						&& this.appService.getItemById(
								notification.getAffectedInfoUnitIds().get(0), null) != null) {
					InformationUnitListItem itemById = this.appService.getItemById(notification
							.getAffectedInfoUnitIds().get(0), null);

					Image image = this.imageService.getImageByInfoType(itemById.getType());
					if (image != null) {
						itemLink.setImage(image);
					}
				} else {

					itemLink.setImage(NotificationUtil.getImageBySeverity(notification
							.getSeverity()));
				}
				itemLink.setBackground(getBackground());
				itemLink.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(final HyperlinkEvent e) {
						notification.setNoticed(true);
						// FIXME
						// EditorUtil.openInfoUnit(notification.getAffectedInfoUnitIds().get(0));
						ITrayService trayService = getTrayService();
						if (trayService != null && trayService.isMinimized()) {
							trayService.restoreFromTray(UIUtil.getPrimaryWindow().getShell());
						}
						if (NotificationControl.this.window != null) {
							NotificationControl.this.window.close();
						}
					}
				});
				count++;
			}
		}
		final NotificationLink itemLink = new NotificationLink(this, SWT.BEGINNING | SWT.NO_FOCUS);
		TableWrapData layoutData = new TableWrapData(TableWrapData.RIGHT, TableWrapData.BOTTOM);
		layoutData.grabHorizontal = true;
		layoutData.grabVertical = true;
		itemLink.setLayoutData(layoutData);
		itemLink.setUnderlined(true);
		itemLink.setBackground(getBackground());
		itemLink.setText("Manage...");
		itemLink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				try {
					IViewPart findView = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().showView(NotificationView.VIEW_ID);
					ITrayService trayService = getTrayService();
					if (trayService != null && trayService.isMinimized()) {
						trayService.restoreFromTray(UIUtil.getPrimaryWindow().getShell());
					}
					if (NotificationControl.this.window != null) {
						NotificationControl.this.window.close();
					}
				} catch (PartInitException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

	}

	private void addListeners() {
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(final DisposeEvent e) {
				doDispose();
			}
		});
	}

	public void setWindow(final Window window) {
		this.window = window;
	}

	protected void doDispose() {
		dispose();

	}

	public ITrayService getTrayService() {
		final BundleContext bundleContext = NotificationActivator.getDefault().getBundle()
				.getBundleContext();
		final ServiceReference serviceReference = bundleContext
				.getServiceReference(ITrayService.class.getName());
		if (serviceReference != null) {
			return (ITrayService) bundleContext.getService(serviceReference);
		}
		return null;

	}

}
