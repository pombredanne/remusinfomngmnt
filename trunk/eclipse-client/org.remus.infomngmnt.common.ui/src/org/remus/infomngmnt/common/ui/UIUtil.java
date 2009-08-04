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

package org.remus.infomngmnt.common.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UIUtil {

	public static WorkbenchWindowAdvisor fgPrimaryApplicationWorkbenchWindowAdvisor;

	private static ArrayContentProvider arcontentprovider = new ArrayContentProvider();

	/**
	 * Attempts to select and reveal the specified resource in all parts within
	 * the supplied workbench window's active page.
	 * <p>
	 * Checks all parts in the active page to see if they implement
	 * <code>ISetSelectionTarget</code>, either directly or as an adapter. If
	 * so, tells the part to select and reveal the specified resource.
	 * </p>
	 * 
	 * @param object
	 *            the resource to be selected and revealed
	 * @param window
	 *            the workbench window to select and reveal the resource
	 * 
	 * @see ISetSelectionTarget
	 */
	public static void selectAndReveal(final Object object, final IWorkbenchWindow window) {
		// validate the input
		if (window == null || object == null) {
			return;
		}
		IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return;
		}

		// get all the view and editor parts
		List parts = new ArrayList();
		IWorkbenchPartReference refs[] = page.getViewReferences();
		for (int i = 0; i < refs.length; i++) {
			IWorkbenchPart part = refs[i].getPart(false);
			if (part != null) {
				parts.add(part);
			}
		}
		refs = page.getEditorReferences();
		for (int i = 0; i < refs.length; i++) {
			if (refs[i].getPart(false) != null) {
				parts.add(refs[i].getPart(false));
			}
		}

		final ISelection selection = new StructuredSelection(object);
		Iterator itr = parts.iterator();
		while (itr.hasNext()) {
			IWorkbenchPart part = (IWorkbenchPart) itr.next();

			// get the part's ISetSelectionTarget implementation
			ISetSelectionTarget target = null;
			if (part instanceof ISetSelectionTarget) {
				target = (ISetSelectionTarget) part;
			} else {
				target = (ISetSelectionTarget) part.getAdapter(ISetSelectionTarget.class);
			}

			if (target != null) {
				// select and reveal resource
				final ISetSelectionTarget finalTarget = target;
				window.getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						finalTarget.selectReveal(selection);
					}
				});
			}
		}
	}

	/**
	 * Attempts to find the primary <code>IWorkbenchWindow</code> from the
	 * PlatformUI facade. Otherwise, returns <code>NULL</code> if none.
	 * 
	 * @return the primary <code>IWorkbenchWindow</code> from the PlatformUI
	 *         facade or <code>NULL</code> if none.
	 */
	public static IWorkbenchWindow getPrimaryWindow() {

		/* Return the first Window of the Workbench */
		IWorkbenchWindow windows[] = PlatformUI.getWorkbench().getWorkbenchWindows();
		if (windows.length > 0) {
			return windows[0];
		}

		return null;
	}

	public static IDialogSettings getDialogSettings(final String sectionName,
			final IDialogSettings settings) {
		IDialogSettings section = settings.getSection(sectionName);
		if (section == null) {
			settings.addNewSection(sectionName);
			section = settings.getSection(sectionName);
		}
		return section;
	}

	public static ArrayContentProvider getArrayContentProviderInstance() {
		return arcontentprovider;
	}

	public static Display getDisplay() {
		if (getPrimaryWindow() != null && getPrimaryWindow().getShell().getDisplay() != null) {
			return getPrimaryWindow().getShell().getDisplay();
		} else if (Display.getCurrent() != null) {
			return Display.getCurrent();
		}
		return Display.getDefault();

	}

	public static boolean isSelectionInstanceOf(final IStructuredSelection selection,
			final Class<?> clazz) {
		List list = selection.toList();
		for (Object object : list) {
			if (!clazz.isAssignableFrom(object.getClass())) {
				return false;
			}
		}
		return true;
	}

	public static void setTextChecked(final String value, final Text text) {
		text.setText(value == null ? "" : value);
	}

	/**
	 * @param section
	 * @param toolkit
	 */
	public static void createSectionToolbar(final Section section, final FormToolkit toolkit,
			final Action... actions) {

		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(final DisposeEvent e) {
				if ((handCursor != null) && (handCursor.isDisposed() == false)) {
					handCursor.dispose();
				}
			}
		});
		// Add sort action to the tool bar

		for (Action action : actions) {
			toolBarManager.add(action);
		}

		toolBarManager.update(true);

		section.setTextClient(toolbar);
	}

	public static IContributionItem getCommandContribution(final String commandId,
			final ImageDescriptor image, final ImageDescriptor disabledImage, final String label,
			final String tooltip, final String helpContextId) {
		CommandContributionItemParameter commandParm = new CommandContributionItemParameter(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null, commandId, null, image,
				disabledImage, null, label, null, tooltip, CommandContributionItem.STYLE_PUSH,
				null, false);
		return new CommandContributionItem(commandParm);
	}

	public static GridLayout createMarginLessGridLayout(final int columns) {
		GridLayout returnValue = new GridLayout(columns, false);
		returnValue.marginWidth = 0;
		returnValue.marginHeight = 0;
		return returnValue;
	}

	/**
	 * Get the preference node with pageId.
	 * 
	 * @param pageId
	 * @return IPreferenceNode
	 */
	public static IPreferenceNode getPreferenceNode(final String pageId) {
		Iterator iterator = PlatformUI.getWorkbench().getPreferenceManager().getElements(
				PreferenceManager.PRE_ORDER).iterator();
		while (iterator.hasNext()) {
			IPreferenceNode next = (IPreferenceNode) iterator.next();
			if (next.getId().equals(pageId)) {
				return next;
			}
		}
		return null;
	}

	public static void saveDialogSettings(final AbstractUIPlugin plugin, final Dialog dialog) {
		IDialogSettings addNewSection = plugin.getDialogSettings().addNewSection(
				dialog.getClass().getName());
		addNewSection.put("x", dialog.getShell().getLocation().x);
		addNewSection.put("y", dialog.getShell().getLocation().y);
		addNewSection.put("width", dialog.getShell().getSize().x);
		addNewSection.put("height", dialog.getShell().getSize().y);

	}

	public static Point getDialogSettingsInitialLocation(final AbstractUIPlugin plugin,
			final Dialog dialog, final Point defaultValue) {
		IDialogSettings section = plugin.getDialogSettings()
				.getSection(dialog.getClass().getName());
		if (section == null) {
			return defaultValue;
		} else {
			return new Point(section.getInt("x"), section.getInt("y"));
		}
	}

	/**
	 * Gets the highest possible width on the given y position
	 * 
	 * @param y
	 * @return
	 */
	public static int getHighestDisplayWidth(final int y) {
		int returnValue = 0;
		Monitor[] monitors = getDisplay().getMonitors();
		for (Monitor monitor : monitors) {
			if (monitor.getBounds().y <= y && (y + monitor.getBounds().height) >= y) {
				returnValue = Math.max(returnValue, monitor.getBounds().x
						+ monitor.getBounds().width);
			}
		}
		return returnValue;
	}

	/**
	 * Gets the highest possible height for the given x position
	 * 
	 * @param y
	 * @return
	 */
	public static int getHighestDisplayHeight(final int x) {
		int returnValue = 0;
		Monitor[] monitors = getDisplay().getMonitors();
		for (Monitor monitor : monitors) {
			if (monitor.getBounds().x <= x && (x + monitor.getBounds().width) >= x) {
				returnValue = Math.max(returnValue, monitor.getBounds().height);
			}
		}
		return returnValue;
	}

	public static Point getDialogSettingsInitialSize(final AbstractUIPlugin plugin,
			final Dialog dialog, final Point defaultValue) {
		IDialogSettings section = plugin.getDialogSettings()
				.getSection(dialog.getClass().getName());
		if (section == null) {
			return defaultValue;
		} else {
			return new Point(section.getInt("width"), section.getInt("height"));
		}
	}
}
