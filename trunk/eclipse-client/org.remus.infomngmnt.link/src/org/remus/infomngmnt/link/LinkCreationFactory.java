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

package org.remus.infomngmnt.link;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.commands.CreateBinaryReferenceCommand;
import org.eclipse.remus.core.create.PostCreationHandler;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.util.InformationUtil;

import org.remus.infomngmnt.link.preferences.LinkPreferenceInitializer;
import org.remus.infomngmnt.link.webshot.WebshotUtil;
import org.remus.infomngmnt.operation.IndexWebPageRunnable;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkCreationFactory extends PostCreationHandler {

	@Override
	public Command handlePreSaving(final InformationUnit unit, final IProgressMonitor monitor) {
		IPreferenceStore preferenceStore = LinkActivator.getDefault().getPreferenceStore();
		String string = preferenceStore.getString(LinkPreferenceInitializer.SCREENSHOT_CMD);

		final boolean indexWebContent = preferenceStore
				.getBoolean(LinkPreferenceInitializer.INDEX_DOCUMENT);
		final boolean makeWebShot = preferenceStore
				.getBoolean(LinkPreferenceInitializer.MAKE_SCREENSHOT)
				&& string.length() > 0;
		if (indexWebContent) {
			monitor.beginTask("Indexing web-content", IProgressMonitor.UNKNOWN);
			IndexWebPageRunnable runnable = new IndexWebPageRunnable(unit.getStringValue());
			try {
				runnable.run(monitor);
				InformationUnit childByType = InformationUtil.getChildByType(unit,
						LinkActivator.NODE_INDEX);
				childByType.setStringValue(runnable.getContent());
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (makeWebShot) {
			IEditingHandler service = LinkActivator.getDefault().getServiceTracker().getService(
					IEditingHandler.class);
			monitor.beginTask("Webshotting the link", IProgressMonitor.UNKNOWN);
			IFile tmpFile = ResourceUtil.createTempFile("png");
			WebshotUtil.performWebShot(unit.getStringValue(), tmpFile.getLocation().toOSString());
			CreateBinaryReferenceCommand addFileToInfoUnit = CommandFactory.addFileToInfoUnit(
					tmpFile, unit, service.getNavigationEditingDomain());
			LinkActivator.getDefault().getServiceTracker().ungetService(service);
			return addFileToInfoUnit;

		}
		return null;
	}

}
