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

package org.remus.infomngmnt.mail.ui;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.common.fieldassist.SmartField;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ContactsWithEmailSmartField extends SmartField {

	public ContactsWithEmailSmartField(final Control control) {
		super(control, new TextContentAdapter());
	}

	@Override
	public boolean hasContentAssist() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.common.fieldassist.SmartField#isValid()
	 */
	@Override
	public boolean isValid() {
		return true;

	}

	@Override
	protected IContentProposalProvider getContentProposalProvider() {
		return new IContentProposalProvider() {

			public IContentProposal[] getProposals(final String contents, final int position) {
				Set<? extends EObject> allItemsByType = InformationUtil
						.getAllItemsByType(ContactActivator.TYPE_ID);
				IContentProposal[] proposals = new IContentProposal[allItemsByType.size()];
				int i = 0;
				for (EObject eObject : allItemsByType) {
					InformationUnitListItem item = (InformationUnitListItem) eObject;
					proposals[i++] = new ContactContentProposal(item);
				}
				return proposals;
			}
		};
	}

	@Override
	protected ILabelProvider getLabelProvider() {
		return new LabelProvider() {
			@Override
			public Image getImage(final Object element) {
				return InformationExtensionManager.getInstance().getInfoTypeByType(
						ContactActivator.TYPE_ID).getImage();
			}

			@Override
			public String getText(final Object element) {

				return ((ContactContentProposal) element).getLabel();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.common.fieldassist.SmartField#isWarning()
	 */
	@Override
	public boolean isWarning() {
		return false;
	}

	private static class ContactContentProposal implements IContentProposal {

		public ContactContentProposal(final InformationUnitListItem listItem) {
			this.listItem = listItem;
		}

		private final InformationUnitListItem listItem;

		public String getContent() {
			InformationUnit adapter = (InformationUnit) this.listItem
					.getAdapter(InformationUnit.class);
			if (adapter != null) {
				InformationStructureRead read = InformationStructureRead.newSession(adapter);
				String defaultAdress = (String) read
						.getValueByNodeId(ContactActivator.NODE_MAIL_DEF);
				if (defaultAdress != null && defaultAdress.trim().length() > 0) {
					return StringUtils.join(this.listItem.getLabel(), " <", defaultAdress, ">");
				}
			}
			return this.listItem.getLabel();

		}

		public int getCursorPosition() {
			return this.listItem.getLabel().length();
		}

		public String getDescription() {
			return null;
		}

		public String getLabel() {
			return this.listItem.getLabel();
		}

	}

}
