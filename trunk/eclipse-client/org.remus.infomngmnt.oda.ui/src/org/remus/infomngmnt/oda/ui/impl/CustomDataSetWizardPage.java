/*
 *************************************************************************
 * Copyright (c) 2009 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package org.remus.infomngmnt.oda.ui.impl;

import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.datatools.connectivity.oda.IConnection;
import org.eclipse.datatools.connectivity.oda.IDriver;
import org.eclipse.datatools.connectivity.oda.IParameterMetaData;
import org.eclipse.datatools.connectivity.oda.IQuery;
import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.datatools.connectivity.oda.design.DataSetDesign;
import org.eclipse.datatools.connectivity.oda.design.DataSetParameters;
import org.eclipse.datatools.connectivity.oda.design.DesignFactory;
import org.eclipse.datatools.connectivity.oda.design.ParameterDefinition;
import org.eclipse.datatools.connectivity.oda.design.ResultSetColumns;
import org.eclipse.datatools.connectivity.oda.design.ResultSetDefinition;
import org.eclipse.datatools.connectivity.oda.design.ui.designsession.DesignSessionUtil;
import org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.remus.DynamicStructure;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InformationStructureDefinition;
import org.eclipse.remus.InformationStructureItem;
import org.eclipse.remus.InformationStructureType;
import org.eclipse.remus.core.extension.IInfoType;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.core.services.IInformationTypeHandler;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.oda.core.QuerySerializer;
import org.remus.infomngmnt.oda.core.impl.Driver;
import org.remus.infomngmnt.oda.ui.OdaUiActivator;
import org.remus.oda.Column;
import org.remus.oda.Dataset;
import org.remus.oda.OdaFactory;

/**
 * Auto-generated implementation of an ODA data set designer page for an user to
 * create or edit an ODA data set design instance. This custom page provides a
 * simple Query Text control for user input. It further extends the DTP
 * design-time framework to update an ODA data set design instance based on the
 * query's derived meta-data. <br>
 * A custom ODA designer is expected to change this exemplary implementation as
 * appropriate.
 */
public class CustomDataSetWizardPage extends DataSetWizardPage {

	private static String DEFAULT_MESSAGE = "Select the nodes from the information structure.";

	private Table table;

	private TreeViewer structureTree;

	private Dataset dataSet;

	private TableViewer columnsViewer;

	private StructureDefinitionWithSelectionLabelProvider labelProvider;

	private DataSetLabelProvider dataSetLabelProvider;

	private ToolItem addButton;

	private ToolItem removeButton;

	private RemusServiceTracker serviceTracker;

	private IEditingHandler editingHandler;

	private IInformationTypeHandler informationTypeHandler;

	/**
	 * Constructor
	 * 
	 * @param pageName
	 * @wbp.parser.constructor
	 */
	public CustomDataSetWizardPage(final String pageName) {
		super(pageName);
		setTitle(pageName);
		setMessage(DEFAULT_MESSAGE);
		initServices();
	}

	/**
	 * Constructor
	 * 
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	public CustomDataSetWizardPage(final String pageName, final String title,
			final ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		setMessage(DEFAULT_MESSAGE);
		initServices();
	}

	private void initServices() {
		this.serviceTracker = new RemusServiceTracker(Platform.getBundle(OdaUiActivator.PLUGIN_ID));
		this.editingHandler = this.serviceTracker.getService(IEditingHandler.class);
		this.informationTypeHandler = this.serviceTracker.getService(IInformationTypeHandler.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage
	 * #createPageCustomControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPageCustomControl(final Composite parent) {
		setControl(createPageControl(parent));
		initializeControl();
	}

	/**
	 * Creates custom control for user-defined query text.
	 */
	private Control createPageControl(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		Group group_1 = new Group(composite, SWT.NONE);
		group_1.setLayout(new GridLayout(3, false));
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		this.structureTree = new TreeViewer(group_1, SWT.BORDER);
		Tree tree = this.structureTree.getTree();
		this.structureTree.setContentProvider(new AdapterFactoryContentProvider(this.editingHandler
				.getAdapterFactory()));
		this.labelProvider = new StructureDefinitionWithSelectionLabelProvider(tree.getFont());
		this.structureTree.setLabelProvider(this.labelProvider);

		GridData gridData = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gridData.widthHint = 143;
		tree.setLayoutData(gridData);

		ToolBar toolBar = new ToolBar(group_1, SWT.FLAT | SWT.RIGHT | SWT.VERTICAL);

		this.addButton = new ToolItem(toolBar, SWT.NONE);
		this.addButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_TOOL_FORWARD));
		this.addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event arg0) {
				InformationStructureItem firstElement = (InformationStructureItem) ((IStructuredSelection) CustomDataSetWizardPage.this.structureTree
						.getSelection()).getFirstElement();
				Column createColumn = OdaFactory.eINSTANCE.createColumn();
				createColumn.setName(firstElement.getId());
				CustomDataSetWizardPage.this.dataSet.getColumns().add(createColumn);
				CustomDataSetWizardPage.this.columnsViewer.refresh();
				getWizard().getContainer().updateButtons();
			}
		});

		this.removeButton = new ToolItem(toolBar, SWT.NONE);
		this.removeButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_TOOL_BACK));
		this.removeButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event arg0) {
				IStructuredSelection selection = (IStructuredSelection) CustomDataSetWizardPage.this.columnsViewer
						.getSelection();
				List list = selection.toList();
				for (Object object : list) {
					CustomDataSetWizardPage.this.dataSet.getColumns().remove(object);
				}
				CustomDataSetWizardPage.this.columnsViewer.refresh();
				getWizard().getContainer().updateButtons();
			}

		});

		this.columnsViewer = new TableViewer(group_1, SWT.BORDER | SWT.FULL_SELECTION);
		this.columnsViewer.setContentProvider(ArrayContentProvider.getInstance());
		this.table = this.columnsViewer.getTable();
		TableColumn tc0 = new TableColumn(this.table, SWT.NONE);
		tc0.setResizable(true);
		tc0.setText("Nodename");
		tc0.setWidth(200);
		TableColumn tc1 = new TableColumn(this.table, SWT.NONE);
		tc1.setResizable(true);
		tc1.setText("DataType");
		tc1.setWidth(100);
		this.table.setHeaderVisible(true);
		this.table.setLinesVisible(true);
		this.dataSetLabelProvider = new DataSetLabelProvider();
		this.columnsViewer.setLabelProvider(this.dataSetLabelProvider);

		this.table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		GridData gridData2 = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_FILL);

		setPageComplete(false);
		return composite;
	}

	/**
	 * Initializes the page control with the last edited data set design.
	 */
	private void initializeControl() {
		/*
		 * To optionally restore the designer state of the previous design
		 * session, use getInitializationDesignerState();
		 */

		// Restores the last saved data set design
		DataSetDesign dataSetDesign = getInitializationDesign();
		if (dataSetDesign == null)
			return; // nothing to initialize

		String queryText = dataSetDesign.getQueryText();
		if (queryText == null)
			return;
		bindValuesToUi(queryText);
		validateData();
		setMessage(DEFAULT_MESSAGE);

		/*
		 * To optionally honor the request for an editable or read-only design
		 * session, use isSessionEditable();
		 */
	}

	private void bindValuesToUi(final String queryText) {
		try {
			java.util.Properties connProps = DesignSessionUtil
					.getEffectiveDataSourceProperties(getInitializationDesign()
							.getDataSourceDesign());
			this.dataSet = QuerySerializer.load(queryText, connProps);
			String infoTypeId = this.dataSet.getSelection().getInfoTypeId();
			this.labelProvider.setSelection(this.dataSet.getSelection().getElementSelector());
			IInfoType infoTypeByType = this.informationTypeHandler.getInfoTypeByType(infoTypeId);
			InformationStructureDefinition structureDefinition = (InformationStructureDefinition) EcoreUtil
					.copy(infoTypeByType.getStructureDefinition());
			/*
			 * We have to add label, keywords,...
			 */

			InformationStructureItem description = InfomngmntFactory.eINSTANCE
					.createInformationStructureItem();
			description.setType(InformationStructureType.STRING);
			description.setId("@description");
			structureDefinition.getStructureItems().add(0, description);
			InformationStructureItem keywords = InfomngmntFactory.eINSTANCE
					.createInformationStructureItem();
			keywords.setType(InformationStructureType.STRING);
			keywords.setId("@keywords");
			structureDefinition.getStructureItems().add(0, keywords);
			InformationStructureItem label = InfomngmntFactory.eINSTANCE
					.createInformationStructureItem();
			label.setType(InformationStructureType.STRING);
			label.setId("@label");
			structureDefinition.getStructureItems().add(0, label);

			this.structureTree.setInput(structureDefinition);
			this.structureTree.addFilter(new ViewerFilter() {
				@Override
				public boolean select(final Viewer viewer, final Object parentElement,
						final Object element) {
					return !(element instanceof DynamicStructure)
							|| (element instanceof DynamicStructure && ((InformationStructureItem) element)
									.getId().equals(
											CustomDataSetWizardPage.this.dataSet.getSelection()
													.getElementSelector()));

				}
			});
			this.dataSetLabelProvider.setInfoType(this.dataSet.getSelection().getInfoTypeId());
			this.columnsViewer.setInput(this.dataSet.getColumns());
			this.structureTree.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(final SelectionChangedEvent selectionchangedevent) {
					if (selectionchangedevent.getSelection().isEmpty()
							|| ((InformationStructureItem) ((IStructuredSelection) selectionchangedevent
									.getSelection()).getFirstElement()).getType() == InformationStructureType.NONE) {
						CustomDataSetWizardPage.this.addButton.setEnabled(false);
					} else {
						CustomDataSetWizardPage.this.addButton.setEnabled(true);
					}

				}
			});
			this.columnsViewer.addSelectionChangedListener(new ISelectionChangedListener() {

				public void selectionChanged(final SelectionChangedEvent selectionchangedevent) {
					CustomDataSetWizardPage.this.removeButton.setEnabled(!selectionchangedevent
							.getSelection().isEmpty());

				}
			});
			this.addButton.setEnabled(false);
			this.removeButton.setEnabled(false);

		} catch (OdaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Obtains the user-defined query text of this data set from page control.
	 * 
	 * @return query text
	 */
	private String getQueryText() {
		try {
			return QuerySerializer.serialize(this.dataSet);
		} catch (OdaException e) {

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage
	 * #collectDataSetDesign(org.eclipse.datatools.connectivity.oda.design.
	 * DataSetDesign)
	 */
	@Override
	protected DataSetDesign collectDataSetDesign(final DataSetDesign design) {
		if (getControl() == null) // page control was never created
			return design; // no editing was done
		if (!hasValidData())
			return null; // to trigger a design session error status
		savePage(design);
		return design;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage
	 * #collectResponseState()
	 */
	@Override
	protected void collectResponseState() {
		super.collectResponseState();
		/*
		 * To optionally assign a custom response state, for inclusion in the
		 * ODA design session response, use setResponseSessionStatus(
		 * SessionStatus status ); setResponseDesignerState( DesignerState
		 * customState );
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage
	 * #canLeave()
	 */
	@Override
	protected boolean canLeave() {
		return isPageComplete();
	}

	@Override
	public boolean isPageComplete() {
		return this.dataSet != null && this.dataSet.getColumns().size() > 0;

	};

	/**
	 * Validates the user-defined value in the page control exists and not a
	 * blank text. Set page message accordingly.
	 */
	private void validateData() {

	}

	/**
	 * Indicates whether the custom page has valid data to proceed with defining
	 * a data set.
	 */
	private boolean hasValidData() {
		validateData();

		return canLeave();
	}

	/**
	 * Saves the user-defined value in this page, and updates the specified
	 * dataSetDesign with the latest design definition.
	 */
	private void savePage(final DataSetDesign dataSetDesign) {
		// save user-defined query text
		String queryText = getQueryText();
		dataSetDesign.setQueryText(queryText);

		// obtain query's current runtime metadata, and maps it to the
		// dataSetDesign
		IConnection customConn = null;
		try {
			// instantiate your custom ODA runtime driver class
			/*
			 * Note: You may need to manually update your ODA runtime
			 * extension's plug-in manifest to export its package for visibility
			 * here.
			 */
			IDriver customDriver = new Driver();

			// obtain and open a live connection
			customConn = customDriver.getConnection(null);
			java.util.Properties connProps = DesignSessionUtil
					.getEffectiveDataSourceProperties(getInitializationDesign()
							.getDataSourceDesign());
			customConn.open(connProps);

			// update the data set design with the
			// query's current runtime metadata
			updateDesign(dataSetDesign, customConn, queryText);
		} catch (OdaException e) {
			// not able to get current metadata, reset previous derived metadata
			dataSetDesign.setResultSets(null);
			dataSetDesign.setParameters(null);

			e.printStackTrace();
		} finally {
			closeConnection(customConn);
		}
	}

	/**
	 * Updates the given dataSetDesign with the queryText and its derived
	 * metadata obtained from the ODA runtime connection.
	 */
	private void updateDesign(final DataSetDesign dataSetDesign, final IConnection conn,
			final String queryText) throws OdaException {
		IQuery query = conn.newQuery(null);
		query.prepare(queryText);

		// TODO a runtime driver might require a query to first execute before
		// its metadata is available
		// query.setMaxRows( 1 );
		// query.executeQuery();

		try {
			IResultSetMetaData md = query.getMetaData();
			updateResultSetDesign(md, dataSetDesign);
		} catch (OdaException e) {
			// no result set definition available, reset previous derived
			// metadata
			dataSetDesign.setResultSets(null);
			e.printStackTrace();
		}

		// proceed to get parameter design definition
		try {
			IParameterMetaData paramMd = query.getParameterMetaData();
			updateParameterDesign(paramMd, dataSetDesign);
		} catch (OdaException ex) {
			// no parameter definition available, reset previous derived
			// metadata
			dataSetDesign.setParameters(null);
			ex.printStackTrace();
		}

		/*
		 * See DesignSessionUtil for more convenience methods to define a data
		 * set design instance.
		 */
	}

	/**
	 * Updates the specified data set design's result set definition based on
	 * the specified runtime metadata.
	 * 
	 * @param md
	 *            runtime result set metadata instance
	 * @param dataSetDesign
	 *            data set design instance to update
	 * @throws OdaException
	 */
	private void updateResultSetDesign(final IResultSetMetaData md,
			final DataSetDesign dataSetDesign) throws OdaException {
		ResultSetColumns columns = DesignSessionUtil.toResultSetColumnsDesign(md);

		ResultSetDefinition resultSetDefn = DesignFactory.eINSTANCE.createResultSetDefinition();
		// resultSetDefn.setName( value ); // result set name
		resultSetDefn.setResultSetColumns(columns);

		// no exception in conversion; go ahead and assign to specified
		// dataSetDesign
		dataSetDesign.setPrimaryResultSet(resultSetDefn);
		dataSetDesign.getResultSets().setDerivedMetaData(true);
	}

	/**
	 * Updates the specified data set design's parameter definition based on the
	 * specified runtime metadata.
	 * 
	 * @param paramMd
	 *            runtime parameter metadata instance
	 * @param dataSetDesign
	 *            data set design instance to update
	 * @throws OdaException
	 */
	private void updateParameterDesign(final IParameterMetaData paramMd,
			final DataSetDesign dataSetDesign) throws OdaException {
		DataSetParameters paramDesign = DesignSessionUtil.toDataSetParametersDesign(paramMd,
				DesignSessionUtil.toParameterModeDesign(IParameterMetaData.parameterModeIn));

		// no exception in conversion; go ahead and assign to specified
		// dataSetDesign
		dataSetDesign.setParameters(paramDesign);
		if (paramDesign == null)
			return; // no parameter definitions; done with update

		paramDesign.setDerivedMetaData(true);

		// TODO replace below with data source specific implementation;
		// hard-coded parameter's default value for demo purpose
		if (paramDesign.getParameterDefinitions().size() > 0) {
			ParameterDefinition paramDef = paramDesign.getParameterDefinitions().get(0);
			if (paramDef != null)
				paramDef.setDefaultScalarValue("dummy default value");
		}
	}

	/**
	 * Attempts to close given ODA connection.
	 */
	private void closeConnection(final IConnection conn) {
		try {
			if (conn != null && conn.isOpen())
				conn.close();
		} catch (OdaException e) {
			// ignore
			e.printStackTrace();
		}
	}
}
