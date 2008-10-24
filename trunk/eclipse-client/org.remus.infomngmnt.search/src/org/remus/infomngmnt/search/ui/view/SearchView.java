package org.remus.infomngmnt.search.ui.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.set.ISetChangeListener;
import org.eclipse.core.databinding.observable.set.SetChangeEvent;
import org.eclipse.core.databinding.observable.set.SetDiff;
import org.eclipse.core.databinding.observable.value.IObservableValue;
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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.nebula.widgets.calendarcombo.DefaultSettings;
import org.eclipse.nebula.widgets.calendarcombo.ISettings;
import org.eclipse.swt.SWT;
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
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.common.ui.databinding.CalendarComboModelToTarget;
import org.remus.infomngmnt.common.ui.databinding.CalendarComboTargetToModel;
import org.remus.infomngmnt.common.ui.databinding.RadioButtonGroupEnumBinding;
import org.remus.infomngmnt.common.ui.view.AbstractScrolledTitledView;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
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
	private static final int SECTION_STYLE = ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED;
	private CalendarCombo fromDate;
	private CalendarCombo toDate;
	private final ISettings calendarSettings = new DefaultSettings();
	private Button allScopeButton;
	private Button selectedInfoUnit;
	private Button openEditorScopeButton;
	private LatestSearchStrings latestSearchStrings;
	private CheckboxTableViewer viewer;
	private HashSet<IInfoType> checkedElements;



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
		typeSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		typeSection.setText("Information Type");

		final Composite composite = this.toolkit.createComposite(typeSection, SWT.NONE);
		typeSection.setClient(composite);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		this.toolkit.paintBordersFor(composite);

		this.table = this.toolkit.createTable(composite, SWT.CHECK | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd_table = new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 2);
		gd_table.heightHint = 50;
		this.table.setLayoutData(gd_table);

		this.viewer = new CheckboxTableViewer(this.table);
		this.viewer.setContentProvider(new ArrayContentProvider());
		this.viewer.setInput(InformationExtensionManager.getInstance().getTypes());
		this.viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IInfoType) element).getType();
			}
			@Override
			public Image getImage(Object element) {
				return ((IInfoType) element).getImage();
			}
		});

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

		this.fromDate = new CalendarCombo(composite_2, SWT.READ_ONLY, this.calendarSettings, null, false);
		this.fromDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label toLabel = this.toolkit.createLabel(composite_2, "to:", SWT.NONE);
		final GridData gd_toLabel = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		toLabel.setLayoutData(gd_toLabel);

		this.toDate = new CalendarCombo(composite_2, SWT.READ_ONLY, this.calendarSettings, null, false);
		this.toDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));


		final Section scopeSection = this.toolkit.createSection(container, SECTION_STYLE);
		scopeSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		scopeSection.setText("Scope");

		final Composite composite_1 = this.toolkit.createComposite(scopeSection, SWT.NONE);
		this.toolkit.paintBordersFor(composite_1);
		composite_1.setLayout(new GridLayout(1,false));
		scopeSection.setClient(composite_1);

		GridData gd_scopeButtons = new GridData(SWT.FILL, SWT.BEGINNING, true, false);


		this.allScopeButton = this.toolkit.createButton(composite_1, SearchScope.ALL.getLiteral(), SWT.RADIO);
		this.allScopeButton.setLayoutData(gd_scopeButtons);
		this.selectedInfoUnit = this.toolkit.createButton(composite_1, SearchScope.SELECTED_INFO_UNIT.getLiteral(), SWT.RADIO);
		this.selectedInfoUnit.setLayoutData(gd_scopeButtons);
		this.openEditorScopeButton = this.toolkit.createButton(composite_1, SearchScope.OPEN_EDITORS.getLiteral(), SWT.RADIO);
		this.openEditorScopeButton.setLayoutData(gd_scopeButtons);

		final Label label = this.toolkit.createSeparator(container, SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Button button = this.toolkit.createButton(container, "Search", SWT.NONE);
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
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
		IObservableValue mObsFrom = EMFEditObservables.observeValue(
				Realm.getDefault(), this.editingDomain, this.currentSearch, SearchPackage.Literals.SEARCH__DATE_START);
		ISWTObservableValue uObsFrom = SWTObservables.observeText(this.fromDate.getCombo());
		this.ctx.bindValue(uObsFrom, mObsFrom,
				new CalendarComboTargetToModel(this.fromDate, this.calendarSettings),
				new CalendarComboModelToTarget(this.fromDate,this.calendarSettings));

		// to-date
		IObservableValue mObsTo = EMFEditObservables.observeValue(
				Realm.getDefault(), this.editingDomain, this.currentSearch, SearchPackage.Literals.SEARCH__END_DATE);
		ISWTObservableValue uObsTo = SWTObservables.observeText(this.toDate.getCombo());
		this.ctx.bindValue(uObsTo, mObsTo,
				new CalendarComboTargetToModel(this.toDate, this.calendarSettings),
				new CalendarComboModelToTarget(this.toDate,this.calendarSettings));

		// scope
		IObservableValue mObsAllScope = EMFEditObservables.observeValue(
				Realm.getDefault(), this.editingDomain, this.currentSearch, SearchPackage.Literals.SEARCH__SCOPE);

		Map<Button,Enumerator> buttonMapping = new HashMap<Button, Enumerator>();
		buttonMapping.put(this.allScopeButton, SearchScope.ALL);
		buttonMapping.put(this.selectedInfoUnit, SearchScope.SELECTED_INFO_UNIT);
		buttonMapping.put(this.openEditorScopeButton, SearchScope.OPEN_EDITORS);
		RadioButtonGroupEnumBinding radioButtonCtx = new RadioButtonGroupEnumBinding(buttonMapping,this.ctx);
		radioButtonCtx.setDefault(this.currentSearch.getScope());
		radioButtonCtx.bind(mObsAllScope);

		// informationtype
		EList<String> infoType = this.currentSearch.getInfoType();
		final IObservableList list = new WritableList();
		for (String string : infoType) {
			// at first we have to create a list for the binding to the emf list.
			list.add(InformationExtensionManager.getInstance().getInfoTypeByType(string));
		}
		// we have to set the initial selection manually because we are syncing just
		// in one direction.
		this.viewer.setCheckedElements(list.toArray());
		IViewerObservableSet elements = ViewersObservables.observeCheckedElements(this.viewer, new HashSet());
		// we convert all changes from the set to the list
		elements.addSetChangeListener(new ISetChangeListener() {
			public void handleSetChange(SetChangeEvent event) {
				SetDiff diff = event.diff;
				list.removeAll(diff.getRemovals());
				list.addAll(diff.getAdditions());
			}
		});
		IObservableList observeList2 = EMFObservables.observeList(this.currentSearch, SearchPackage.Literals.SEARCH__INFO_TYPE);
		this.ctx.bindList(observeList2, list,  new UpdateListStrategy() {
			@Override
			public Object convert(Object element) {
				return InformationExtensionManager.getInstance().getInfoTypeByType((String) element);
			}
		}, new UpdateListStrategy() {
			@Override
			public Object convert(Object element) {
				return ((IInfoType) element).getType();
			}
		});


		// latest search entries
		IObservableList observeList = EMFEditObservables.observeList(Realm.getDefault(), this.editingDomain , this.latestSearchStrings, SearchPackage.Literals.LATEST_SEARCH_STRINGS__STRINGS);
		IObservableList observeItems = SWTObservables.observeItems(this.combo);
		this.ctx.bindList(observeItems, observeList, new UpdateListStrategy() {
			@Override
			public Object convert(Object element) {
				// TODO Auto-generated method stub
				return super.convert(element);
			}
		}, null);

		// search-text
		IObservableValue mObsText = EMFEditObservables.observeValue(
				Realm.getDefault(), this.editingDomain, this.currentSearch, SearchPackage.Literals.SEARCH__SEARCH_STRING);
		ISWTObservableValue uObsText = SWTObservables.observeText(this.combo);
		this.ctx.bindValue(uObsText, mObsText,null,null);

	}

	/**
	 * Initalizes the editing domain for the search package. This domain is not
	 * intended to be reused in other contexts than for manipulating the objects
	 * which are displayed in this viewpart.
	 */
	private void initEditingDomain() {
		this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		this.adapterFactory.addAdapterFactory(new SearchItemProviderAdapterFactory());
		this.adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		this.editingDomain = new AdapterFactoryEditingDomain(this.adapterFactory, new BasicCommandStack());
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		this.currentSearch = getSavedSearchHandler().getLatestSearch();
		this.latestSearchStrings = getSavedSearchHandler().getLatestSearchStrings();
		super.init(site);
	}

	@Override
	public void saveState(IMemento memento) {
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



}
