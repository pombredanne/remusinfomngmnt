/*******************************************************************************
 * Copyright (c) 2010 Andreas Deinlein, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein - initial version
 *******************************************************************************/

package org.remus.infomngmnt.ui.rating;

import java.util.Collection;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
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
import org.remus.infomngmnt.Comment;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.Rating;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.ui.editors.outline.IOutlineSection;
import org.remus.infomngmnt.ui.rating.internal.ResourceManager;
import org.remus.infomngmnt.ui.viewer.ViewerActivator;

/**
 * Outline Section for rating of Information Units. Implements extension point "informationUi.outlineSection"
 * Is managed by class ui.editor.oultine.LinkOutline.java.
 * 
 * @author Andreas Deinlein
 *
 */
public class RatingOutline implements IOutlineSection {

	private InformationUnit infoUnit;
	
	private EditingDomain domain;	
//TODO: remove later on, if not needed	
//	private IApplicationModel applicationService;
//	private IEditingHandler editService;
	
	private ScrolledForm form;
	private Section commentSection;
	private FormText commentFormText;
		
	private final AdapterImpl ratingListChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification msg) {
			if (msg.getFeature() == InfomngmntPackage.Literals.INFORMATION_UNIT__COMMENTS) {
				buildList();
			}
		}
	};	
	
	public RatingOutline() {
		// constructor
	}
	
	@Override
	public void bindValuesToUi(EditingDomain domain, InformationUnit infoUnit) {
		this.domain = domain;
		this.infoUnit = infoUnit;
//		this.applicationService = ViewerActivator.getDefault().getApplicationService();
//		this.editService = ViewerActivator.getDefault().getEditService();
		
		buildList();

		this.infoUnit.eAdapters().add(this.ratingListChangeAdapter);
	}

	@Override
	public void createControl(ScrolledForm form, FormToolkit toolkit) {
		this.form = form;
		createCommentSection(form, toolkit);

	}

	private void createCommentSection(final ScrolledForm sform, final FormToolkit toolkit) {
		this.commentSection = toolkit.createSection(sform.getBody(), ExpandableComposite.TWISTIE
				| ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);

		this.commentSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		this.commentSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		this.commentSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(final ExpansionEvent e) {
				sform.reflow(false);
			}
		});

		TableWrapData td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.grabHorizontal = true;
		this.commentSection.setLayoutData(td);

		this.commentFormText = toolkit.createFormText(this.commentSection, false);
		this.commentFormText.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				String string = e.getHref().toString();
				if ("addRating".equals(string)) {
					RatingOverviewDialog ratingDlg = new RatingOverviewDialog(sform.getShell(),
							RatingOutline.this.infoUnit, RatingOutline.this.domain);
					if (ratingDlg.open() == IDialogConstants.OK_ID) {
						performResult(ratingDlg.getResult());
					}
				}
			}
		});

		this.commentSection.setClient(this.commentFormText);

		// initialize toolbar
		initializeLinkToolBar(this.commentSection, toolkit);	
	}
	
	/**
	 * Initialize the toolbar
	 */
	private void initializeLinkToolBar(final Section section, final FormToolkit toolkit) {

		UIUtil.createSectionToolbar(section, toolkit, new Action("Edit Ratings") {
			@Override
			public void run() {
				RatingOverviewDialog ratingDlg = new RatingOverviewDialog(RatingOutline.this.form.getShell(),
						RatingOutline.this.infoUnit, RatingOutline.this.domain);
				if (ratingDlg.open() == IDialogConstants.OK_ID) {
					performResult(ratingDlg.getResult());
				}
			}
			@Override
			public ImageDescriptor getImageDescriptor() {
				return null;
			}
		});

	}

	@SuppressWarnings("unchecked")
	protected void performResult(final Collection result) {
		Command command = SetCommand.create(this.domain, this.infoUnit,
				InfomngmntPackage.Literals.INFORMATION_UNIT__COMMENTS, result);
		((AbstractCommand) command).setLabel("Edit Comments");
		this.domain.getCommandStack().execute(command);
	}
	
	@Override
	public void dispose() {

	}

	@Override
	public void disposeModel() {
		this.infoUnit.eAdapters().remove(this.ratingListChangeAdapter);
	}

	@Override
	public int getSortRanking() {
		// TODO Auto-generated method stub getSortRanking
		return 0;
	}

	
	private void buildList() {
		EList<Comment> comments = this.infoUnit.getComments();
		// calculate average for headline
		int average = 0;
		String avStr = "";
		if (comments.size() != 0) {			
			for (Comment comment : comments) {
				average += comment.getRating().ordinal()+1;
			}
			average = average / comments.size();			
		}
		switch (average) {
			case 1:
				avStr = "*";
				break;
			case 2:
				avStr = "**";
				break;
			case 3:
				avStr = "***";
				break;
			case 4:
				avStr = "****";
				break;
			case 5:
				avStr = "*****";
				break;
		}
		// set headline
		this.commentSection.setText(NLS.bind("Ratings ({0}) - average: "+ avStr, comments.size()));
		if (comments.size() == 0) {
			this.commentFormText.setText(
					"<form><p>No Ratings done so far for this unit. You can <a href=\"addRating\">add a new Rating</a>.</p></form>",
					true, false);
			return;
		}
		// set content
		StringBuilder sw = new StringBuilder();
		sw.append("<form>");
		for (Comment comment : comments) {
			sw.append("<p>");
			switch (comment.getRating()) {
				case USELESS:
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_grey").append("\" /> ");
					sw.append("<img href=\"").append("star_grey").append("\" /> ");
					sw.append("<img href=\"").append("star_grey").append("\" /> ");
					sw.append("<img href=\"").append("star_grey").append("\" /> ");
					break;
				case POOR:
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_grey").append("\" /> ");
					sw.append("<img href=\"").append("star_grey").append("\" /> ");
					sw.append("<img href=\"").append("star_grey").append("\" /> ");
					break;
				case AVERAGE:
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_grey").append("\" /> ");
					sw.append("<img href=\"").append("star_grey").append("\" /> ");
					break;
				case HELPFUL:
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_grey").append("\" /> ");
					break;
				case FANTASTIC:
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					sw.append("<img href=\"").append("star_yellow").append("\" /> ");
					break;
			}
						
			sw.append("  , " + comment.getAuthor() + "<br></br>");
			sw.append(comment.getComment());
			sw.append("</p>");
		}
		sw.append("</form>");
		this.commentFormText.setText(sw.toString(), true, false);
		this.commentFormText.setImage("star_yellow", ResourceManager.getScaledPluginImage(RatingActivator.getDefault(), "icons/star_yellow.png", 0.75));
		this.commentFormText.setImage("star_grey", ResourceManager.getScaledPluginImage(RatingActivator.getDefault(), "icons/star_grey.png", 0.75));
		this.form.reflow(false);	
	}
}
