package org.remus.infomngmnt.ui.project;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.common.core.util.ResourceUtil;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * ProjectContentsLocationArea is a convenience class for area that handle entry
 * of locations using URIs.
 * 
 * @since 3.2
 * 
 */
public class ProjectContentsLocationArea {
	/**
	 * IErrorMessageReporter is an interface for type that allow message
	 * reporting.
	 * 
	 */
	public interface IErrorMessageReporter {
		/**
		 * Report the error message
		 * 
		 * @param errorMessage
		 *            String or <code>null</code>. If the errorMessage is null
		 *            then clear any error state.
		 * @param infoOnly
		 *            the message is an informational message, but the dialog
		 *            cannot continue
		 * 
		 */
		public void reportError(String errorMessage, boolean infoOnly);
	}

	private static String BROWSE_LABEL = "Browse";

	private static final int SIZING_TEXT_FIELD_WIDTH = 250;

	private static final String FILE_SCHEME = "file"; //$NON-NLS-1$

	private static final String SAVED_LOCATION_ATTR = "OUTSIDE_LOCATION"; //$NON-NLS-1$

	protected static final Object PROJECT_LOCATION_EMPTY = "Empty location";

	private Label locationLabel;

	private Text locationPathField;

	private Button browseButton;

	private final IErrorMessageReporter errorReporter;

	private String projectName = "";

	private String userPath = "";

	private Button useDefaultsButton;

	private IProject existingProject;

	/**
	 * Create a new instance of the receiver.
	 * 
	 * @param reporter
	 * @param composite
	 * @param startProject
	 */
	public ProjectContentsLocationArea(final IErrorMessageReporter reporter,
			final Composite composite, final IProject startProject) {

		this.errorReporter = reporter;
		this.projectName = startProject.getName();
		this.existingProject = startProject;
		createContents(composite, false);
	}

	/**
	 * Set the project to base the contents off of.
	 * 
	 * @param existingProject
	 */
	public void setExistingProject(final IProject existingProject) {
		this.projectName = existingProject.getName();
		this.existingProject = existingProject;
	}

	/**
	 * Create a new instance of a ProjectContentsLocationArea.
	 * 
	 * @param reporter
	 * @param composite
	 */
	public ProjectContentsLocationArea(final IErrorMessageReporter reporter,
			final Composite composite) {
		this.errorReporter = reporter;

		// If it is a new project always start enabled
		createContents(composite, true);
	}

	/**
	 * Create the contents of the receiver.
	 * 
	 * @param composite
	 * @param defaultEnabled
	 */
	private void createContents(final Composite composite, final boolean defaultEnabled) {

		int columns = 4;

		// project specification group
		Composite projectGroup = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = columns;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		this.useDefaultsButton = new Button(projectGroup, SWT.CHECK | SWT.RIGHT);
		this.useDefaultsButton.setText("Use default");
		this.useDefaultsButton.setSelection(defaultEnabled);
		GridData buttonData = new GridData();
		buttonData.horizontalSpan = columns;
		this.useDefaultsButton.setLayoutData(buttonData);

		createUserEntryArea(projectGroup, defaultEnabled);

		this.useDefaultsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				boolean useDefaults = ProjectContentsLocationArea.this.useDefaultsButton
						.getSelection();

				if (useDefaults) {
					ProjectContentsLocationArea.this.userPath = ProjectContentsLocationArea.this.locationPathField
							.getText();
					ProjectContentsLocationArea.this.locationPathField.setText(TextProcessor
							.process(getDefaultPathDisplayString()));
				} else {
					ProjectContentsLocationArea.this.locationPathField.setText(TextProcessor
							.process(ProjectContentsLocationArea.this.userPath));
				}
				String error = checkValidLocation();
				ProjectContentsLocationArea.this.errorReporter.reportError(error, error != null
						&& error.equals(PROJECT_LOCATION_EMPTY));
				setUserAreaEnabled(!useDefaults);
			}
		});
		setUserAreaEnabled(!defaultEnabled);
	}

	/**
	 * Return whether or not we are currently showing the default location for
	 * the project.
	 * 
	 * @return boolean
	 */
	public boolean isDefault() {
		return this.useDefaultsButton.getSelection();
	}

	/**
	 * Create the area for user entry.
	 * 
	 * @param composite
	 * @param defaultEnabled
	 */
	private void createUserEntryArea(final Composite composite, final boolean defaultEnabled) {
		// location label
		this.locationLabel = new Label(composite, SWT.NONE);
		this.locationLabel.setText("Location");

		// project location entry field
		this.locationPathField = new Text(composite, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		data.horizontalSpan = 2;
		this.locationPathField.setLayoutData(data);

		// browse button
		this.browseButton = new Button(composite, SWT.PUSH);
		this.browseButton.setText(BROWSE_LABEL);
		this.browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				handleLocationBrowseButtonPressed();
			}
		});

		if (defaultEnabled) {
			this.locationPathField.setText(TextProcessor.process(getDefaultPathDisplayString()));
		} else {
			if (this.existingProject == null) {
				this.locationPathField.setText("");
			} else {
				this.locationPathField.setText(TextProcessor.process(this.existingProject
						.getLocation().toOSString()));
			}
		}

		this.locationPathField.addModifyListener(new ModifyListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.
			 * swt.events.ModifyEvent)
			 */
			public void modifyText(final ModifyEvent e) {
				ProjectContentsLocationArea.this.errorReporter.reportError(checkValidLocation(),
						false);
			}
		});
	}

	/**
	 * Return the path we are going to display. If it is a file URI then remove
	 * the file prefix.
	 * 
	 * @return String
	 */
	private String getDefaultPathDisplayString() {

		URI defaultURI = null;
		if (this.existingProject != null) {
			defaultURI = this.existingProject.getLocationURI();
		}

		// Handle files specially. Assume a file if there is no project to query
		if (defaultURI == null || defaultURI.getScheme().equals(FILE_SCHEME)) {
			return Platform.getLocation().append(this.projectName).toOSString();
		}
		return defaultURI.toString();

	}

	/**
	 * Set the enablement state of the receiver.
	 * 
	 * @param enabled
	 */
	private void setUserAreaEnabled(final boolean enabled) {

		this.locationLabel.setEnabled(enabled);
		this.locationPathField.setEnabled(enabled);
		this.browseButton.setEnabled(enabled);

	}

	/**
	 * Return the browse button. Usually referenced in order to set the layout
	 * data for a dialog.
	 * 
	 * @return Button
	 */
	public Button getBrowseButton() {
		return this.browseButton;
	}

	private IDialogSettings getDialogSettings() {
		IDialogSettings ideDialogSettings = UIPlugin.getDefault().getDialogSettings();
		IDialogSettings result = ideDialogSettings.getSection(getClass().getName());
		if (result == null) {
			result = ideDialogSettings.addNewSection(getClass().getName());
		}
		return result;
	}

	/**
	 * Open an appropriate directory browser
	 */
	private void handleLocationBrowseButtonPressed() {

		String selectedDirectory = null;
		String dirName = getPathFromLocationField();

		if (!dirName.equals("")) {
			if (ResourceUtil.fileExists(dirName)) {
				dirName = "";
			}
		} else {
			String value = getDialogSettings().get(SAVED_LOCATION_ATTR);
			if (value != null) {
				dirName = value;
			}
		}

		DirectoryDialog dialog = new DirectoryDialog(this.locationPathField.getShell(), SWT.SHEET);
		dialog.setMessage("Select directory");

		dialog.setFilterPath(dirName);

		selectedDirectory = dialog.open();

		if (selectedDirectory != null) {
			updateLocationField(selectedDirectory);
			getDialogSettings().put(SAVED_LOCATION_ATTR, selectedDirectory);
		}
	}

	/**
	 * Update the location field based on the selected path.
	 * 
	 * @param selectedPath
	 */
	private void updateLocationField(final String selectedPath) {
		this.locationPathField.setText(TextProcessor.process(selectedPath));
	}

	/**
	 * Return the path on the location field.
	 * 
	 * @return String
	 */
	private String getPathFromLocationField() {
		URI fieldURI;
		try {
			fieldURI = new URI(this.locationPathField.getText());
		} catch (URISyntaxException e) {
			return this.locationPathField.getText();
		}
		return fieldURI.getPath();
	}

	/**
	 * Check if the entry in the widget location is valid. If it is valid return
	 * null. Otherwise return a string that indicates the problem.
	 * 
	 * @return String
	 */
	public String checkValidLocation() {

		String locationFieldContents = this.locationPathField.getText();
		if (locationFieldContents.length() == 0) {
			return "Location cannot be empty";
		}

		URI newPath = getProjectLocationURI();
		if (newPath == null) {
			return "Invalid location";
		}

		if (this.existingProject != null) {
			URI projectPath = this.existingProject.getLocationURI();
			if (projectPath != null && ResourceUtil.isProjectPathEqual(projectPath, newPath)) {
				return "Location already exists";
			}
		}

		if (!isDefault()) {
			IStatus locationStatus = ResourcesPlugin.getWorkspace().validateProjectLocationURI(
					this.existingProject, newPath);

			if (!locationStatus.isOK()) {
				return locationStatus.getMessage();
			}
		}

		return null;
	}

	/**
	 * Get the URI for the location field if possible.
	 * 
	 * @return URI or <code>null</code> if it is not valid.
	 */
	public URI getProjectLocationURI() {
		return ResourceUtil.getFileSystemURI(this.locationPathField.getText());

	}

	/**
	 * Set the text to the default or clear it if not using the defaults.
	 * 
	 * @param newName
	 *            the name of the project to use. If <code>null</code> use the
	 *            existing project name.
	 */
	public void updateProjectName(final String newName) {
		this.projectName = newName;
		if (isDefault()) {
			this.locationPathField.setText(TextProcessor.process(getDefaultPathDisplayString()));
		}

	}

	/**
	 * Return the location for the project. If we are using defaults then return
	 * the workspace root so that core creates it with default values.
	 * 
	 * @return String
	 */
	public String getProjectLocation() {
		if (isDefault()) {
			return Platform.getLocation().toOSString();
		}
		return this.locationPathField.getText();
	}
}
