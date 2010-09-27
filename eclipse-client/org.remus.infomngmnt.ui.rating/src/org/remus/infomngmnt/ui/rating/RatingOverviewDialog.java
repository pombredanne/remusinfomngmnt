/*******************************************************************************
 * Copyright (c) 2010 Andreas Deinlein, Deasw
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
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemProvider;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.Comment;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.Rating;
import org.remus.infomngmnt.ui.editor.EditorActivator;
import org.remus.infomngmnt.ui.rating.internal.ResourceManager;

public class RatingOverviewDialog extends TitleAreaDialog {

	private Text text;
	private Table table;
	private Text ratingComment;
	private Text ratingAuthor;
	private InformationUnit infoUnit;
	private Label imageLabel;
	private TableViewer tableViewer;
	private Button deleteButton;
	private Button addButton;
	private final ItemProvider itemProvider;
	private WritableList input;

	private Rating currentRating = Rating.USELESS;
	private Button btnFirst;
	private Button btnSecond;
	private Button btnThird;
	private Button btnFourth;
	private Button btnFifth;

	public RatingOverviewDialog(final Shell parentShell, final InformationUnit infoUnit,
			final EditingDomain domain) {
		super(parentShell);
		this.infoUnit = infoUnit;
		this.itemProvider = new ItemProvider(infoUnit.getComments());
		setTitle("Wizard Page title");
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Edit Rating");
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		setTitle("Edit Rating");
		setMessage(NLS.bind("Rating for unit {0}", this.infoUnit.getLabel()));

		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);

		final Label sourceLabel = new Label(container, SWT.NONE);
		sourceLabel.setText("Source");

		this.imageLabel = new Label(container, SWT.NONE);
		this.imageLabel.setLayoutData(new GridData(16, 16));

		this.text = new Text(container, SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.text.setEnabled(false);

		// --------------------------------------------------------------------------------------

		final Group ratingsEditGroup = new Group(container, SWT.NONE);
		ratingsEditGroup.setText("Edit Rating");
		final GridData gd_EditGroup = new GridData(SWT.FILL, SWT.FILL, false, true, 3, 1);
		ratingsEditGroup.setLayoutData(gd_EditGroup);
		final GridLayout gridLayout_3 = new GridLayout();
		gridLayout_3.numColumns = 2;
		ratingsEditGroup.setLayout(gridLayout_3);

		final Label ratingLabel = new Label(ratingsEditGroup, SWT.NONE);
		ratingLabel.setText("Select Rating");

		Composite innerRatingCmp = new Composite(ratingsEditGroup, SWT.NONE);
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = false;
		innerRatingCmp.setLayout(rowLayout);
		this.btnFirst = new Button(innerRatingCmp, SWT.FLAT | SWT.ICON);
		this.btnFirst.setImage(ResourceManager.getPluginImage(RatingActivator.getDefault(),
				"icons/star_yellow.png"));
		this.btnFirst.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				RatingOverviewDialog.this.currentRating = Rating.USELESS;
				RatingOverviewDialog.this.btnSecond.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_grey.png"));
				RatingOverviewDialog.this.btnThird.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_grey.png"));
				RatingOverviewDialog.this.btnFourth.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_grey.png"));
				RatingOverviewDialog.this.btnFifth.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_grey.png"));
			}
		});
		this.btnSecond = new Button(innerRatingCmp, SWT.FLAT | SWT.ICON);
		this.btnSecond.setImage(ResourceManager.getPluginImage(RatingActivator.getDefault(),
				"icons/star_grey.png"));
		this.btnSecond.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				RatingOverviewDialog.this.currentRating = Rating.POOR;
				RatingOverviewDialog.this.btnSecond.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_yellow.png"));
				RatingOverviewDialog.this.btnThird.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_grey.png"));
				RatingOverviewDialog.this.btnFourth.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_grey.png"));
				RatingOverviewDialog.this.btnFifth.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_grey.png"));
			}
		});
		this.btnThird = new Button(innerRatingCmp, SWT.FLAT | SWT.ICON);
		this.btnThird.setImage(ResourceManager.getPluginImage(RatingActivator.getDefault(),
				"icons/star_grey.png"));
		this.btnThird.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				RatingOverviewDialog.this.currentRating = Rating.AVERAGE;
				RatingOverviewDialog.this.btnSecond.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_yellow.png"));
				RatingOverviewDialog.this.btnThird.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_yellow.png"));
				RatingOverviewDialog.this.btnFourth.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_grey.png"));
				RatingOverviewDialog.this.btnFifth.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_grey.png"));
			}
		});
		this.btnFourth = new Button(innerRatingCmp, SWT.FLAT | SWT.ICON);
		this.btnFourth.setImage(ResourceManager.getPluginImage(RatingActivator.getDefault(),
				"icons/star_grey.png"));
		this.btnFourth.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				RatingOverviewDialog.this.currentRating = Rating.AVERAGE;
				RatingOverviewDialog.this.btnSecond.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_yellow.png"));
				RatingOverviewDialog.this.btnThird.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_yellow.png"));
				RatingOverviewDialog.this.btnFourth.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_yellow.png"));
				RatingOverviewDialog.this.btnFifth.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_grey.png"));
			}
		});
		this.btnFifth = new Button(innerRatingCmp, SWT.FLAT | SWT.ICON);
		this.btnFifth.setImage(ResourceManager.getPluginImage(RatingActivator.getDefault(),
				"icons/star_grey.png"));
		this.btnFifth.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				RatingOverviewDialog.this.currentRating = Rating.AVERAGE;
				RatingOverviewDialog.this.btnSecond.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_yellow.png"));
				RatingOverviewDialog.this.btnThird.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_yellow.png"));
				RatingOverviewDialog.this.btnFourth.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_yellow.png"));
				RatingOverviewDialog.this.btnFifth.setImage(ResourceManager.getPluginImage(
						RatingActivator.getDefault(), "icons/star_yellow.png"));
			}
		});
		final Label authorLabel = new Label(ratingsEditGroup, SWT.NONE);
		authorLabel.setText("Author");
		this.ratingAuthor = new Text(ratingsEditGroup, SWT.BORDER | SWT.MULTI);
		this.ratingAuthor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		IDialogSettings dlgset = RatingActivator.getDefault().getDialogSettings();
		if (dlgset.get("AuthorSetting") != null) {
			this.ratingAuthor.setText(dlgset.get("AuthorSetting"));
		}

		final Label commentLabel = new Label(ratingsEditGroup, SWT.NONE);
		commentLabel.setText("Comment");
		this.ratingComment = new Text(ratingsEditGroup, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.WRAP);
		final GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.heightHint = 40;
		this.ratingComment.setLayoutData(gd);

		@SuppressWarnings("unused")
		final Label dummyLabel = new Label(ratingsEditGroup, SWT.NONE);
		this.addButton = new Button(ratingsEditGroup, SWT.NONE);
		this.addButton.setLayoutData(new GridData(SWT.FILL, SWT.RIGHT, false, false));
		this.addButton.setText("Add");
		this.addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Comment createComment = InfomngmntFactory.eINSTANCE.createComment();
				createComment.setRating(RatingOverviewDialog.this.currentRating);
				createComment.setComment(RatingOverviewDialog.this.ratingComment.getText());
				createComment.setAuthor(RatingOverviewDialog.this.ratingAuthor.getText());
				RatingOverviewDialog.this.input.add(createComment);
				IDialogSettings dlgset = RatingActivator.getDefault().getDialogSettings();
				dlgset.put("AuthorSetting", RatingOverviewDialog.this.ratingAuthor.getText());
			}
		});

		// --------------------------------------------------------------------------------------

		final Group ratingsListGroup = new Group(container, SWT.NONE);
		ratingsListGroup.setText("All ratings");
		final GridData gd_ratingsGroup = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		ratingsListGroup.setLayoutData(gd_ratingsGroup);
		final GridLayout gridLayout_1 = new GridLayout();
		ratingsListGroup.setLayout(gridLayout_1);

		this.tableViewer = new TableViewer(ratingsListGroup, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);
		this.tableViewer.setContentProvider(new ObservableListContentProvider());
		this.tableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				Comment comment = (Comment) element;
				if (element != null) {
					return comment.getRating().toString() + ", " + comment.getComment();
				}
				return "Target not found";
			}

			@Override
			public Image getImage(final Object element) {
				return null;
			}
		});

		this.tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				handleSelectionChanged((IStructuredSelection) event.getSelection());
			}
		});

		this.table = this.tableViewer.getTable();
		this.table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.deleteButton = new Button(ratingsListGroup, SWT.NONE);
		this.deleteButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		this.deleteButton.setText("Delete");
		this.deleteButton.addListener(SWT.Selection, new Listener() {
			@SuppressWarnings("unchecked")
			public void handleEvent(final Event event) {
				List list = ((IStructuredSelection) RatingOverviewDialog.this.tableViewer
						.getSelection()).toList();
				RatingOverviewDialog.this.input.removeAll(list);
			}
		});
		this.deleteButton.setEnabled(false);

		// --------------------------------------------------------------------------------------

		setResult(this.infoUnit, false);

		return area;
	}

	protected void handleSelectionChanged(final IStructuredSelection selection) {
		this.deleteButton.setEnabled(!selection.isEmpty());
	}

	protected void setResult(final AbstractInformationUnit result, final boolean notify) {
		this.text.setText(result.getLabel()); // fill read only source field

		Object image2 = ((IItemLabelProvider) EditorActivator.getDefault().getEditService()
				.getAdapterFactory().adapt(result, IItemLabelProvider.class)).getImage(result);
		if (image2 instanceof Image) {
			this.imageLabel.setImage((Image) image2);
		}
		if (result instanceof InformationUnit) {
			this.infoUnit = (InformationUnit) result;
		} else {
			this.infoUnit = (InformationUnit) result.getAdapter(InformationUnit.class);
		}
		populateTreeContent();

	}

	private void populateTreeContent() {
		this.tableViewer.setInput(this.input = new WritableList(this.itemProvider.getChildren(),
				null));

	}

	@SuppressWarnings("unchecked")
	public Collection getResult() {
		return this.input;
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 500);
	}

}
