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

package org.remus.infomngmnt.ui.category;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.common.fieldassist.SmartField;
import org.remus.infomngmnt.core.model.CategoryUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CategorySmartField extends SmartField {

	public CategorySmartField(Control control) {
		super(control, new TextContentAdapter());
	}

	@Override
	public boolean hasContentAssist() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.fieldassist.SmartField#isValid()
	 */
	@Override
	public boolean isValid() {
		return CategoryUtil.isCategoryNameValid(((Text) this.control).getText()).isOK();

	}

	@Override
	protected IContentProposalProvider getContentProposalProvider() {
		return new IContentProposalProvider() {

			public IContentProposal[] getProposals(String contents, int position) {
				Category[] findCatetegories = CategoryUtil.findCatetegories(contents, false);
				IContentProposal[] proposals = new IContentProposal[findCatetegories.length];
				for (int i = 0; i < findCatetegories.length; i++) {
					final Category category = findCatetegories[i];
					proposals[i] = new CategoryContentProposal(category);
				}
				return proposals;
			}
		};
	}

	@Override
	protected ILabelProvider getLabelProvider() {
		return new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEF_VIEW);
			}

			@Override
			public String getText(Object element) {
				return ((CategoryContentProposal) element).getCategoryToString();
			}
		};
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.fieldassist.SmartField#isWarning()
	 */
	@Override
	public boolean isWarning() {
		return false;
	}

	private static class CategoryContentProposal implements IContentProposal {

		public CategoryContentProposal(Category category) {
			this.category = category;
		}
		private final Category category;

		private String categoryToString;

		public String getContent() {
			return getCategoryToString();
		}
		public int getCursorPosition() {
			return getCategoryToString().length();
		}
		public String getDescription() {
			return this.category.getDescription();
		}
		public String getLabel() {
			return getCategoryToString();
		}
		public Category getCategory() {
			return this.category;
		}

		public String getCategoryToString() {
			if (this.categoryToString == null) {
				this.categoryToString = CategoryUtil.categoryToString(this.category);
			}
			return this.categoryToString;
		}

	}

}
