package org.remus.infomngmnt.search.ui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.set.ISetChangeListener;
import org.eclipse.core.databinding.observable.set.SetChangeEvent;
import org.eclipse.core.databinding.observable.set.SetDiff;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.IViewerObservableSet;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.nebula.widgets.calendarcombo.DefaultSettings;
import org.eclipse.nebula.widgets.calendarcombo.ISettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.DatePickerBindingWidget;
import org.remus.infomngmnt.common.ui.databinding.List2SetConverter;
import org.remus.infomngmnt.common.ui.databinding.RadioButtonGroupEnumBinding;
import org.remus.infomngmnt.common.ui.swt.DateCombo;
import org.remus.infomngmnt.common.ui.view.AbstractScrolledTitledView;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.search.LatestSearchStrings;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.SearchScope;
import org.remus.infomngmnt.search.provider.SearchItemProviderAdapterFactory;
import org.remus.infomngmnt.search.save.SavedSearchesHandler;
import org.remus.infomngmnt.search.service.LuceneSearchService;

public class SearchView extends AbstractScrolledTitledView {

	private Table table;
	private Combo combo;
	private SavedSearchesHandler savedSearchHandler;
	private Search currentSearch;
	private EMFDataBindingContext ctx;
	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryEditingDomain editingDomain;
	public static final String ID = "org.remus.infomngmnt.search.ui.view.SearchView"; //$NON-NLS-1$
	private static final int SECTION_STYLE = ExpandableComposite.TITLE_BAR
			| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED;
	private DateCombo fromDate;
	private DateCombo toDate;
	private final ISettings calendarSettings = new DefaultSettings();
	private Button allScopeButton;
	private Button selectedInfoUnit;
	private LatestSearchStrings latestSearchStrings;
	private CheckboxTableViewer viewer;
	private HashSet<IInfoType> checkedElements;
	private CheckboxTableViewer newCheckList;

	/**
	 * Create contents of the view part
	 * 
	 * @param parent
	 */
	@Override
	public void createViewContents(final Composite parent) {

		this.toolkit.createLabel(parent, "Search string", SWT.NONE);

		this.combo = new Combo(parent, SWT.NONE);
		this.toolkit.adapt(this.combo, true, true);
		this.combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.combo.addListener(SWT.DefaultSelection, new Listener() {

			public void handleEvent(final Event event) {
				handleSearchButtonPressed();
			}

		});

		this.toolkit.createHyperlink(parent, "Learn more about search syntax...", SWT.NONE);

		final Section typeSection = this.toolkit.createSection(parent, SECTION_STYLE);
		typeSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		typeSection.setText("Information Type");

		final Composite composite = this.toolkit.createComposite(typeSection, SWT.NONE);
		typeSection.setClient(composite);
		final GridLayout gridLayout = new GridLayout();
		// gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		this.toolkit.paintBordersFor(composite);
		GridData layoutData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		layoutData.heightHint = 80;
		composite.setLayoutData(layoutData);

		Composite tableComposite = this.toolkit.createComposite(composite);
		this.table = this.toolkit.createTable(tableComposite, SWT.CHECK);
		TableColumn singleColumn = new TableColumn(this.table, SWT.NONE);
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		tableColumnLayout.setColumnData(singleColumn, new ColumnWeightData(100));
		tableComposite.setLayout(tableColumnLayout);

		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);

		gd_table.widthHint = 1; // https://bugs.eclipse.org/bugs/show_bug.cgi?id=215997
		gd_table.heightHint = 120;
		tableComposite.setLayoutData(gd_table);

		this.viewer = new CheckboxTableViewer(this.table);
		this.viewer.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.viewer.setInput(InformationExtensionManager.getInstance().getTypes());
		this.viewer.setSorter(new ViewerSorter());
		this.viewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(final Viewer viewer, final Object parentElement,
					final Object element) {
				return element instanceof IInfoType && !((IInfoType) element).isExcludeFromIndex();
			}

		});
		this.viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				System.out.println("\"" + ((IInfoType) element).getName() + "\"");
				return ((IInfoType) element).getName();
			}

			@Override
			public Image getImage(final Object element) {
				return ((IInfoType) element).getImage();
			}
		});

		Composite buttonComposite = this.toolkit.createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		buttonComposite.setLayout(layout);
		buttonComposite.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, false, false));

		final Button button_1 = this.toolkit.createButton(buttonComposite, "Select All", SWT.NONE);
		button_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		button_1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				SearchView.this.viewer.setCheckedElements(InformationExtensionManager.getInstance()
						.getTypes().toArray());
			}
		});

		final Button deselectAllButton = this.toolkit.createButton(buttonComposite, "Deselect All",
				SWT.NONE);
		final GridData gd_deselectAllButton = new GridData(SWT.FILL, SWT.TOP, false, false);
		deselectAllButton.setLayoutData(gd_deselectAllButton);
		deselectAllButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				SearchView.this.viewer.setCheckedElements(new Object[0]);
			}
		});

		final Section dateSection = this.toolkit.createSection(parent, SECTION_STYLE);
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

		GridLayout dateLayout = new GridLayout(2, false);
		dateLayout.marginHeight = 2;
		dateLayout.marginWidth = 1;
		dateLayout.verticalSpacing = 2;
		dateLayout.horizontalSpacing = 2;
		Composite fromParent = this.toolkit.createComposite(composite_2);
		fromParent.setLayout(dateLayout);
		this.fromDate = new DateCombo(fromParent, SWT.FLAT);
		GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false).applyTo(
				this.fromDate);
		this.fromDate.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		this.toolkit.adapt(this.fromDate, false, false);
		this.toolkit.paintBordersFor(fromParent);
		GridData dueDateLayoutData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		fromParent.setLayoutData(dueDateLayoutData);
		this.toolkit.adapt(this.fromDate);

		final Label toLabel = this.toolkit.createLabel(composite_2, "to:", SWT.NONE);
		final GridData gd_toLabel = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		toLabel.setLayoutData(gd_toLabel);

		Composite toParent = this.toolkit.createComposite(composite_2);
		toParent.setLayout(dateLayout);
		this.toDate = new DateCombo(toParent, SWT.FLAT);
		GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false)
				.applyTo(this.toDate);
		this.toDate.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		this.toolkit.adapt(this.toDate, false, false);
		this.toolkit.paintBordersFor(toParent);
		toParent.setLayoutData(dueDateLayoutData);
		this.toolkit.adapt(this.toDate);

		final Section scopeSection = this.toolkit.createSection(parent, SECTION_STYLE);
		scopeSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		scopeSection.setText("Scope");

		final Composite composite_1 = this.toolkit.createComposite(scopeSection, SWT.NONE);
		this.toolkit.paintBordersFor(composite_1);
		composite_1.setLayout(new GridLayout(1, false));
		scopeSection.setClient(composite_1);

		GridData gd_scopeButtons = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		this.allScopeButton = this.toolkit.createButton(composite_1, SearchScope.ALL.getLiteral(),
				SWT.RADIO);
		this.allScopeButton.setLayoutData(gd_scopeButtons);
		this.selectedInfoUnit = this.toolkit.createButton(composite_1, SearchScope.PROJECTS
				.getLiteral(), SWT.RADIO);
		this.selectedInfoUnit.setLayoutData(gd_scopeButtons);

		this.newCheckList = CheckboxTableViewer.newCheckList(composite_1, SWT.BORDER);
		this.newCheckList.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.newCheckList.setLabelProvider(new LabelProvider());

		GridDataFactory.createFrom(new GridData(SWT.FILL, SWT.BEGINNING, true, false)).hint(-1, 50)
				.applyTo(this.newCheckList.getControl());
		this.selectedInfoUnit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				SearchView.this.newCheckList.getControl().setEnabled(
						((Button) e.widget).getSelection());
			}
		});

		final Label label = this.toolkit.createSeparator(parent, SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Button button = this.toolkit.createButton(parent, "Search", SWT.NONE);
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				handleSearchButtonPressed();
			}
		});

		initEditingDomain();
		initDataBinding();

		createActions();
		initializeToolBar();
		initializeMenu();

	}

	protected void handleSearchButtonPressed() {
		String text = this.combo.getText();
		if (!this.latestSearchStrings.getStrings().contains(text)) {
			this.latestSearchStrings.getStrings().add(text);
			this.combo.setText(text);
		}
		LuceneSearchService.getInstance().search(this.currentSearch, true);

	}

	private void initDataBinding() {
		this.ctx = new EMFDataBindingContext();

		// from-date
		DatePickerBindingWidget dateComboBinding = BindingWidgetFactory.createDateComboBinding(
				this.fromDate, this.ctx, this.editingDomain);
		dateComboBinding.bindModel(this.currentSearch, SearchPackage.Literals.SEARCH__DATE_START);

		// to-date
		DatePickerBindingWidget dateComboBinding2 = BindingWidgetFactory.createDateComboBinding(
				this.toDate, this.ctx, this.editingDomain);
		dateComboBinding2.bindModel(this.currentSearch, SearchPackage.Literals.SEARCH__END_DATE);

		// scope
		IObservableValue mObsAllScope = EMFEditObservables.observeValue(Realm.getDefault(),
				this.editingDomain, this.currentSearch, SearchPackage.Literals.SEARCH__SCOPE);

		Map<Button, Enumerator> buttonMapping = new HashMap<Button, Enumerator>();
		buttonMapping.put(this.allScopeButton, SearchScope.ALL);
		buttonMapping.put(this.selectedInfoUnit, SearchScope.PROJECTS);
		RadioButtonGroupEnumBinding radioButtonCtx = new RadioButtonGroupEnumBinding(buttonMapping,
				this.ctx);
		radioButtonCtx.setDefault(this.currentSearch.getScope());
		radioButtonCtx.bind(mObsAllScope);

		// informationtype
		EList<String> infoType = this.currentSearch.getInfoType();
		final IObservableList list = new WritableList();
		for (String string : infoType) {
			// at first we have to create a list for the binding to the emf
			// list.
			list.add(InformationExtensionManager.getInstance().getInfoTypeByType(string));
		}
		// we have to set the initial selection manually because we are syncing
		// just
		// in one direction.
		this.viewer.setCheckedElements(list.toArray());
		IViewerObservableSet elements = ViewersObservables.observeCheckedElements(this.viewer,
				new HashSet());
		// we convert all changes from the set to the list
		elements.addSetChangeListener(new ISetChangeListener() {
			public void handleSetChange(final SetChangeEvent event) {
				SetDiff diff = event.diff;
				list.removeAll(diff.getRemovals());
				list.addAll(diff.getAdditions());
			}
		});
		IObservableList observeList2 = EMFObservables.observeList(this.currentSearch,
				SearchPackage.Literals.SEARCH__INFO_TYPE);
		this.ctx.bindList(observeList2, list, new UpdateListStrategy() {
			@Override
			public Object convert(final Object element) {
				return InformationExtensionManager.getInstance()
						.getInfoTypeByType((String) element);
			}
		}, new UpdateListStrategy() {
			@Override
			public Object convert(final Object element) {
				return ((IInfoType) element).getType();
			}
		});

		// latest search entries
		IObservableList observeList = EMFEditObservables.observeList(Realm.getDefault(),
				this.editingDomain, this.latestSearchStrings,
				SearchPackage.Literals.LATEST_SEARCH_STRINGS__STRINGS);
		IObservableList observeItems = SWTObservables.observeItems(this.combo);
		this.ctx.bindList(observeItems, observeList, new UpdateListStrategy() {
			@Override
			public Object convert(final Object element) {
				// TODO Auto-generated method stub
				return super.convert(element);
			}
		}, null);

		// search-text
		IObservableValue mObsText = EMFEditObservables.observeValue(Realm.getDefault(),
				this.editingDomain, this.currentSearch,
				SearchPackage.Literals.SEARCH__SEARCH_STRING);
		ISWTObservableValue uObsText = SWTObservables.observeText(this.combo);
		this.ctx.bindValue(uObsText, mObsText, null, null);

		IProject[] relevantProjects = ResourceUtil.getRelevantProjects();
		List<String> projects = new ArrayList<String>();
		for (IProject iProject : relevantProjects) {
			projects.add(iProject.getName());
		}
		this.newCheckList.setInput(projects);
		IViewerObservableSet observeCheckedElements = ViewersObservables.observeCheckedElements(
				this.newCheckList, String.class);

		this.newCheckList.getControl().setEnabled(this.selectedInfoUnit.getSelection());
		IObservableList observeList3 = EMFObservables.observeList(this.currentSearch,
				SearchPackage.Literals.SEARCH__PROJECTS);
		this.ctx.bindSet(observeCheckedElements, List2SetConverter.create(observeList3));

	}

	/**
	 * Initalizes the editing domain for the search package. This domain is not
	 * intended to be reused in other contexts than for manipulating the objects
	 * which are displayed in this viewpart.
	 */
	private void initEditingDomain() {
		this.adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		this.adapterFactory.addAdapterFactory(new SearchItemProviderAdapterFactory());
		this.adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		this.editingDomain = new AdapterFactoryEditingDomain(this.adapterFactory,
				new BasicCommandStack());
	}

	@Override
	public void init(final IViewSite site) throws PartInitException {
		this.currentSearch = getSavedSearchHandler().getLatestSearch();
		this.latestSearchStrings = getSavedSearchHandler().getLatestSearchStrings();
		super.init(site);
	}

	@Override
	public void saveState(final IMemento memento) {
		getSavedSearchHandler().saveLatestSearch(this.currentSearch);
		getSavedSearchHandler().saveSearchStrings(this.latestSearchStrings);
		super.saveState(memento);
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

	private SavedSearchesHandler getSavedSearchHandler() {
		if (this.savedSearchHandler == null) {
			this.savedSearchHandler = new SavedSearchesHandler();
		}
		return this.savedSearchHandler;
	}

	public void setCurrentSearch(final Search search) {
		if (this.ctx != null) {
			this.ctx.dispose();
		}
		this.currentSearch = search;
		initDataBinding();

	}

}
