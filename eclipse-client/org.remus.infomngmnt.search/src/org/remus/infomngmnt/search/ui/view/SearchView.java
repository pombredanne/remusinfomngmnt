package org.remus.infomngmnt.search.ui.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.common.ui.view.AbstractScrolledTitledView;

public class SearchView extends AbstractScrolledTitledView {

	private Table table;
	private Combo combo;
	public static final String ID = "org.remus.infomngmnt.search.ui.view.SearchView"; //$NON-NLS-1$
	private static final int SECTION_STYLE = ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED;



	/**
	 * Create contents of the view part
	 * 
	 * @param parent
	 */
	@Override
	public void createViewContents(Composite parent) {
		Composite container = this.toolkit.createComposite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		this.toolkit.paintBordersFor(container);

		this.toolkit.createLabel(container, "Search string", SWT.NONE);

		this.combo = new Combo(container, SWT.NONE);
		this.toolkit.adapt(this.combo, true, true);
		this.combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		this.toolkit.createHyperlink(container, "Learn more about search syntax...", SWT.NONE);

		final Section typeSection = this.toolkit.createSection(container, SECTION_STYLE);
		typeSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		typeSection.setText("Information Type");

		final Composite composite = this.toolkit.createComposite(typeSection, SWT.NONE);
		typeSection.setClient(composite);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		this.toolkit.paintBordersFor(composite);

		this.table = this.toolkit.createTable(composite, SWT.NONE);
		this.table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));

		final Button button_1 = this.toolkit.createButton(composite, "Select All", SWT.NONE);
		button_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Button deselectAllButton = this.toolkit.createButton(composite, "Deselect All", SWT.NONE);
		final GridData gd_deselectAllButton = new GridData(SWT.FILL, SWT.TOP, false, false);
		deselectAllButton.setLayoutData(gd_deselectAllButton);

		final Section dateSection = this.toolkit.createSection(container, SECTION_STYLE);
		final GridData gd_informationTypeSection = new GridData(SWT.FILL, SWT.CENTER, false, false);
		dateSection.setLayoutData(gd_informationTypeSection);
		dateSection.setText("Dates");

		final Composite composite_2 = this.toolkit.createComposite(dateSection, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		composite_2.setLayout(gridLayout_1);
		this.toolkit.paintBordersFor(composite_2);
		dateSection.setClient(composite_2);

		Label fromLabel = this.toolkit.createLabel(composite_2, "from:", SWT.NONE);
		GridData gd_fromLabel = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		fromLabel.setLayoutData(gd_fromLabel);

		CalendarCombo fromDate = new CalendarCombo(composite_2, SWT.READ_ONLY);
		fromDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label toLabel = this.toolkit.createLabel(composite_2, "to:", SWT.NONE);
		final GridData gd_toLabel = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		toLabel.setLayoutData(gd_toLabel);

		CalendarCombo toDate = new CalendarCombo(composite_2, SWT.READ_ONLY);
		toDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));


		final Section scopeSection = this.toolkit.createSection(container, SECTION_STYLE);
		scopeSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		scopeSection.setText("Scope");

		final Composite composite_1 = this.toolkit.createComposite(scopeSection, SWT.NONE);
		this.toolkit.paintBordersFor(composite_1);
		scopeSection.setClient(composite_1);

		final Label label = this.toolkit.createSeparator(container, SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Button button = this.toolkit.createButton(container, "Search", SWT.NONE);
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		//
		createActions();
		initializeToolBar();
		initializeMenu();

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
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu
	 */
	private void initializeMenu() {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}



}
