package org.eclipse.remus.application;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.streams.StreamUtil;
import org.eclipse.remus.common.service.ITrayService;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.extension.IInfoType;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.core.services.IInformationTypeHandler;
import org.eclipse.remus.js.extension.CheckResourceReferenceJob;
import org.eclipse.remus.resources.util.ResourceUtil;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.ui.UIPlugin;
import org.eclipse.remus.ui.desktop.panel.DesktopWindow;
import org.eclipse.remus.ui.preference.UIPreferenceInitializer;
import org.eclipse.remus.util.CategoryUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.AboutInfo;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.dialogs.WelcomeEditorInput;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.update.configurator.ConfiguratorUtils;
import org.eclipse.update.configurator.IPlatformConfiguration;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
		implements ITrayService {

	private static final String WELCOME_EDITOR_ID = "org.eclipse.ui.internal.ide.dialogs.WelcomeEditor"; //$NON-NLS-1$

	private final ApplicationWorkbenchAdvisor wbAdvisor;
	private boolean editorsAndIntrosOpened = false;
	private IEditorPart lastActiveEditor = null;
	private IPerspectiveDescriptor lastPerspective = null;

	private IWorkbenchPage lastActivePage;
	private String lastEditorTitle = ""; //$NON-NLS-1$

	private TrayItem fTrayItem;
	private boolean fTrayEnabled;
	private ShellListener fTrayShellListener;
	private boolean fBlockIconifyEvent;
	private boolean fMinimizedToTray;
	private boolean fMinimizeFromClose;
	private boolean fTrayTeasing;

	private final IPropertyListener editorPropertyListener = new IPropertyListener() {
		public void propertyChanged(final Object source, final int propId) {
			if (propId == IWorkbenchPartConstants.PROP_TITLE) {
				if (lastActiveEditor != null) {
					String newTitle = lastActiveEditor.getTitle();
					if (!lastEditorTitle.equals(newTitle)) {
						recomputeTitle();
					}
				}
			}
		}
	};

	private IAdaptable lastInput;

	private IWorkbenchAction openPerspectiveAction;

	private ApplicationActionBarAdvisor actionBarAdvisor;

	private final IPreferenceStore preferenceStore;

	private DesktopWindow window;

	// private DesktopWindow window;

	/**
	 * Crates a new IDE workbench window advisor.
	 * 
	 * @param wbAdvisor
	 *            the workbench advisor
	 * @param configurer
	 *            the window configurer
	 */
	public ApplicationWorkbenchWindowAdvisor(
			final ApplicationWorkbenchAdvisor wbAdvisor,
			final IWorkbenchWindowConfigurer configurer) {
		super(configurer);
		this.wbAdvisor = wbAdvisor;
		preferenceStore = UIPlugin.getDefault().getPreferenceStore();
		ServiceReference serviceReference = Platform
				.getBundle(ApplicationPlugin.PLUGIN_ID).getBundleContext()
				.getServiceReference(ITrayService.class.getName());
		if (serviceReference == null
				|| Platform.getBundle(ApplicationPlugin.PLUGIN_ID)
						.getBundleContext().getService(serviceReference) == null) {
			Platform.getBundle(ApplicationPlugin.PLUGIN_ID).getBundleContext()
					.registerService(ITrayService.class.getName(), this, null);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.application.WorkbenchWindowAdvisor#createActionBarAdvisor
	 * (org.eclipse.ui.application.IActionBarConfigurer)
	 */
	@Override
	public ActionBarAdvisor createActionBarAdvisor(
			final IActionBarConfigurer configurer) {
		actionBarAdvisor = new ApplicationActionBarAdvisor(configurer);
		return actionBarAdvisor;
	}

	/**
	 * Returns the workbench.
	 * 
	 * @return the workbench
	 */
	private IWorkbench getWorkbench() {
		return getWindowConfigurer().getWorkbenchConfigurer().getWorkbench();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#preWindowShellClose
	 */
	@Override
	public boolean preWindowShellClose() {
		final boolean[] res = new boolean[] { true };
		SafeRunner.run(new ISafeRunnable() {
			public void run() throws Exception {

				/* Check if Prefs tell to move to tray */
				if (ApplicationWorkbenchWindowAdvisor.this
						.equals(UIUtil.fgPrimaryApplicationWorkbenchWindowAdvisor)
						&& preferenceStore
								.getBoolean(UIPreferenceInitializer.TRAY_ON_CLOSE)) {
					fMinimizeFromClose = true;
					getWindowConfigurer().getWindow().getShell()
							.notifyListeners(SWT.Iconify, new Event());
					res[0] = false;
					fMinimizeFromClose = false;
				}

				/* shutdown the normal way */
				else {
					res[0] = onClose();
				}
			}

			public void handleException(final Throwable exception) {
				// do nothign

			}
		});
		return res[0];
	}

	public boolean onClose() {
		if (getWorkbench().getWorkbenchWindowCount() > 1) {
			return true;
		}
		// the user has asked to close the last window, while will cause the
		// workbench to close in due course - prompt the user for confirmation
		IPreferenceStore store = IDEWorkbenchPlugin.getDefault()
				.getPreferenceStore();
		boolean promptOnExit = store
				.getBoolean(IDEInternalPreferences.EXIT_PROMPT_ON_CLOSE_LAST_WINDOW);

		if (promptOnExit) {
			String message;

			String productName = null;
			IProduct product = Platform.getProduct();
			if (product != null) {
				productName = product.getName();
			}
			if (productName == null) {
				message = IDEWorkbenchMessages.PromptOnExitDialog_message0;
				;
			} else {
				message = NLS.bind(
						IDEWorkbenchMessages.PromptOnExitDialog_message1,
						productName);
			}

			MessageDialogWithToggle dlg = MessageDialogWithToggle
					.openOkCancelConfirm(getWindowConfigurer().getWindow()
							.getShell(),
							IDEWorkbenchMessages.PromptOnExitDialog_shellTitle,
							message,
							IDEWorkbenchMessages.PromptOnExitDialog_choice,
							false, null, null);
			if (dlg.getReturnCode() != IDialogConstants.OK_ID) {
				return false;
			}
			if (dlg.getToggleState()) {
				store.setValue(
						IDEInternalPreferences.EXIT_PROMPT_ON_CLOSE_LAST_WINDOW,
						false);
				IDEWorkbenchPlugin.getDefault().savePluginPreferences();
			}
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#preWindowOpen
	 */
	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();

		// show the shortcut bar and progress indicator, which are hidden by
		// default

		configurer.setInitialSize(new Point(1150, 768));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setShowMenuBar(true);
		configurer.setShowProgressIndicator(true);
		hookTitleUpdateListeners(configurer);
	}

	@Override
	public void postWindowOpen() {
		final Shell shell = getWindowConfigurer().getWindow().getShell();
		/* System Tray */
		SafeRunner.run(new ISafeRunnable() {
			public void run() throws Exception {
				boolean trayEnabled = false;

				/* Hook TrayItem if supported on OS and 1st Window */
				if (preferenceStore
						.getBoolean(UIPreferenceInitializer.TRAY_ON_MINIMIZE)
						|| preferenceStore
								.getBoolean(UIPreferenceInitializer.TRAY_ON_CLOSE)
						|| preferenceStore
								.getBoolean(UIPreferenceInitializer.TRAY_ON_START)) {
				}
				trayEnabled = enableTray();

				/* Move to Tray if set */
				if (trayEnabled
						&& preferenceStore
								.getBoolean(UIPreferenceInitializer.TRAY_ON_START)) {
					moveToTray(shell);
				}
			}

			public void handleException(final Throwable exception) {
				ApplicationPlugin.getDefault().getLog().log(null);

			}
		});

		IProject project = ResourceUtil.getProject("Inbox"); //$NON-NLS-1$
		if (project == null) {
			try {
				ResourceUtil.createNewProject(
						"Inbox", new NullProgressMonitor()); //$NON-NLS-1$
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SafeRunner.run(new ISafeRunnable() {

			public void handleException(final Throwable exception) {
				// TODO Auto-generated method stub

			}

			public void run() throws Exception {
				if (ApplicationPlugin.getDefault().getPreferenceStore()
						.getBoolean(PreferenceInitializer.FIRST_START)) {
					final RemusServiceTracker remusServiceTracker = new RemusServiceTracker(
							ApplicationPlugin.getDefault().getBundle());
					final AtomicReference<InformationUnit> result = new AtomicReference<InformationUnit>();
					ApplicationPlugin.getDefault().getPreferenceStore()
							.setValue(PreferenceInitializer.FIRST_START, false);
					final Category findCategory = CategoryUtil.findCategory(
							"Inbox", false); //$NON-NLS-1$
					Job job = new Job(
							org.eclipse.remus.application.messages.IDEWorkbenchMessages.ApplicationWorkbenchWindowAdvisor_PrepareRemus) {

						@Override
						protected IStatus run(IProgressMonitor monitor) {
							monitor.beginTask(
									org.eclipse.remus.application.messages.IDEWorkbenchMessages.ApplicationWorkbenchWindowAdvisor_InitializeMessage,
									IProgressMonitor.UNKNOWN);
							while (!CheckResourceReferenceJob.completed) {
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							IInformationTypeHandler service = remusServiceTracker
									.getService(IInformationTypeHandler.class);
							IInfoType infoTypeByType = service
									.getInfoTypeByType("HTML"); //$NON-NLS-1$
							if (infoTypeByType != null) {
								InformationStructureEdit edit = InformationStructureEdit
										.newSession("HTML"); //$NON-NLS-1$
								InformationUnit newInformationUnit = edit
										.newInformationUnit();
								newInformationUnit
										.setLabel(org.eclipse.remus.application.messages.IDEWorkbenchMessages.ApplicationWorkbenchWindowAdvisor_WelcomeToRemus);
								InputStream resourceAsStream = getClass()
										.getResourceAsStream("welcome.txt"); //$NON-NLS-1$
								newInformationUnit.setStringValue(StreamUtil
										.convertStreamToString(resourceAsStream));
								StreamCloser.closeStreams(resourceAsStream);
								CompoundCommand create_INFOTYPE = CommandFactory
										.CREATE_INFOTYPE(newInformationUnit,
												findCategory);
								AdapterFactoryEditingDomain domain = remusServiceTracker
										.getService(IEditingHandler.class)
										.getNavigationEditingDomain();
								domain.getCommandStack().execute(
										create_INFOTYPE);
								result.set(newInformationUnit);

							}
							return Status.OK_STATUS;
						}
					};
					job.setUser(true);
					job.schedule();
					job.addJobChangeListener(new JobChangeAdapter() {
						@Override
						public void done(
								org.eclipse.core.runtime.jobs.IJobChangeEvent event) {

							UIUtil.getDisplay().asyncExec(new Runnable() {

								public void run() {
									try {
										IDE.openEditor(
												PlatformUI
														.getWorkbench()
														.getActiveWorkbenchWindow()
														.getActivePage(),
												(IFile) result
														.get()
														.getAdapter(IFile.class));
										UIUtil.selectAndReveal(
												result.get(),
												PlatformUI
														.getWorkbench()
														.getActiveWorkbenchWindow());
										UIUtil.selectAndReveal(
												result.get()
														.getAdapter(
																InformationUnitListItem.class),
												PlatformUI
														.getWorkbench()
														.getActiveWorkbenchWindow());
									} catch (PartInitException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							});

						};
					});

				}

			}

		});
	}

	/**
	 * Hooks the listeners needed on the window
	 * 
	 * @param configurer
	 */
	private void hookTitleUpdateListeners(
			final IWorkbenchWindowConfigurer configurer) {
		// hook up the listeners to update the window title
		configurer.getWindow().addPageListener(new IPageListener() {
			public void pageActivated(final IWorkbenchPage page) {
				updateTitle();
			}

			public void pageClosed(final IWorkbenchPage page) {
				updateTitle();
			}

			public void pageOpened(final IWorkbenchPage page) {
				// do nothing
			}
		});
		configurer.getWindow().addPerspectiveListener(new PerspectiveAdapter() {
			@Override
			public void perspectiveActivated(final IWorkbenchPage page,
					final IPerspectiveDescriptor perspective) {
				updateTitle();
			}

			@Override
			public void perspectiveSavedAs(final IWorkbenchPage page,
					final IPerspectiveDescriptor oldPerspective,
					final IPerspectiveDescriptor newPerspective) {
				updateTitle();
			}

			@Override
			public void perspectiveDeactivated(final IWorkbenchPage page,
					final IPerspectiveDescriptor perspective) {
				updateTitle();
			}
		});
		configurer.getWindow().getPartService()
				.addPartListener(new IPartListener2() {
					public void partActivated(final IWorkbenchPartReference ref) {
						if (ref instanceof IEditorReference) {
							updateTitle();
						}
					}

					public void partBroughtToTop(
							final IWorkbenchPartReference ref) {
						if (ref instanceof IEditorReference) {
							updateTitle();
						}
					}

					public void partClosed(final IWorkbenchPartReference ref) {
						updateTitle();
					}

					public void partDeactivated(
							final IWorkbenchPartReference ref) {
						// do nothing
					}

					public void partOpened(final IWorkbenchPartReference ref) {
						// do nothing
					}

					public void partHidden(final IWorkbenchPartReference ref) {
						// do nothing
					}

					public void partVisible(final IWorkbenchPartReference ref) {
						// do nothing
					}

					public void partInputChanged(
							final IWorkbenchPartReference ref) {
						// do nothing
					}
				});
	}

	private String computeTitle() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		IWorkbenchPage currentPage = configurer.getWindow().getActivePage();
		IEditorPart activeEditor = null;
		if (currentPage != null) {
			activeEditor = currentPage.getActiveEditor();
		}

		String title = null;
		IProduct product = Platform.getProduct();
		if (product != null) {
			title = product.getName();
		}
		if (title == null) {
			title = ""; //$NON-NLS-1$
		}

		if (currentPage != null) {
			if (activeEditor != null) {
				lastEditorTitle = activeEditor.getTitleToolTip();
				title = NLS.bind(
						IDEWorkbenchMessages.WorkbenchWindow_shellTitle,
						lastEditorTitle, title);
			}
			IPerspectiveDescriptor persp = currentPage.getPerspective();
			String label = ""; //$NON-NLS-1$
			if (persp != null) {
				label = persp.getLabel();
			}
			IAdaptable input = currentPage.getInput();
			if (input != null && !input.equals(wbAdvisor.getDefaultPageInput())) {
				label = currentPage.getLabel();
			}
			if (label != null && !label.equals("")) { //$NON-NLS-1$
				title = NLS.bind(
						IDEWorkbenchMessages.WorkbenchWindow_shellTitle, label,
						title);
			}
		}

		String workspaceLocation = wbAdvisor.getWorkspaceLocation();
		if (workspaceLocation != null) {
			title = NLS.bind(IDEWorkbenchMessages.WorkbenchWindow_shellTitle,
					title, workspaceLocation);
		}

		return title;
	}

	private void recomputeTitle() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		String oldTitle = configurer.getTitle();
		String newTitle = computeTitle();
		if (!newTitle.equals(oldTitle)) {
			configurer.setTitle(newTitle);
		}
	}

	/**
	 * Updates the window title. Format will be: [pageInput -]
	 * [currentPerspective -] [editorInput -] [workspaceLocation -] productName
	 */
	private void updateTitle() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		IWorkbenchWindow window = configurer.getWindow();
		IEditorPart activeEditor = null;
		IWorkbenchPage currentPage = window.getActivePage();
		IPerspectiveDescriptor persp = null;
		IAdaptable input = null;

		if (currentPage != null) {
			activeEditor = currentPage.getActiveEditor();
			persp = currentPage.getPerspective();
			input = currentPage.getInput();
		}

		// Nothing to do if the editor hasn't changed
		if (activeEditor == lastActiveEditor && currentPage == lastActivePage
				&& persp == lastPerspective && input == lastInput) {
			return;
		}

		if (lastActiveEditor != null) {
			lastActiveEditor.removePropertyListener(editorPropertyListener);
		}

		lastActiveEditor = activeEditor;
		lastActivePage = currentPage;
		lastPerspective = persp;
		lastInput = input;

		if (activeEditor != null) {
			activeEditor.addPropertyListener(editorPropertyListener);
		}

		recomputeTitle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#postWindowRestore
	 */
	@Override
	public void postWindowRestore() throws WorkbenchException {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		IWorkbenchWindow window = configurer.getWindow();

		int index = getWorkbench().getWorkbenchWindowCount() - 1;

		AboutInfo[] welcomePerspectiveInfos = wbAdvisor
				.getWelcomePerspectiveInfos();
		if (index >= 0 && welcomePerspectiveInfos != null
				&& index < welcomePerspectiveInfos.length) {
			// find a page that exist in the window
			IWorkbenchPage page = window.getActivePage();
			if (page == null) {
				IWorkbenchPage[] pages = window.getPages();
				if (pages != null && pages.length > 0) {
					page = pages[0];
				}
			}

			// if the window does not contain a page, create one
			String perspectiveId = welcomePerspectiveInfos[index]
					.getWelcomePerspectiveId();
			if (page == null) {
				IAdaptable root = wbAdvisor.getDefaultPageInput();
				page = window.openPage(perspectiveId, root);
			} else {
				IPerspectiveRegistry reg = getWorkbench()
						.getPerspectiveRegistry();
				IPerspectiveDescriptor desc = reg
						.findPerspectiveWithId(perspectiveId);
				if (desc != null) {
					page.setPerspective(desc);
				}
			}

			// set the active page and open the welcome editor
			window.setActivePage(page);
			page.openEditor(new WelcomeEditorInput(
					welcomePerspectiveInfos[index]), WELCOME_EDITOR_ID, true);
		}
	}

	/**
	 * Tries to open the intro, if one exists and otherwise will open the legacy
	 * Welcome pages.
	 * 
	 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#openIntro()
	 */
	@Override
	public void openIntro() {
		if (editorsAndIntrosOpened) {
			return;
		}

		editorsAndIntrosOpened = true;

		// don't try to open the welcome editors if there is an intro
		if (wbAdvisor.hasIntro()) {
			super.openIntro();
		} else {
			openWelcomeEditors(getWindowConfigurer().getWindow());
			// save any preferences changes caused by the above actions
			IDEWorkbenchPlugin.getDefault().savePluginPreferences();
		}
	}

	/*
	 * Open the welcome editor for the primary feature and for any newly
	 * installed features.
	 */
	private void openWelcomeEditors(final IWorkbenchWindow window) {
		if (IDEWorkbenchPlugin.getDefault().getPreferenceStore()
				.getBoolean(IDEInternalPreferences.WELCOME_DIALOG)) {
			// show the welcome page for the product the first time the
			// workbench opens
			IProduct product = Platform.getProduct();
			if (product == null) {
				return;
			}

			AboutInfo productInfo = new AboutInfo(product);
			URL url = productInfo.getWelcomePageURL();
			if (url == null) {
				return;
			}

			IDEWorkbenchPlugin.getDefault().getPreferenceStore()
					.setValue(IDEInternalPreferences.WELCOME_DIALOG, false);
			openWelcomeEditor(window, new WelcomeEditorInput(productInfo), null);
		} else {
			// Show the welcome page for any newly installed features
			List welcomeFeatures = new ArrayList();
			for (Iterator it = wbAdvisor.getNewlyAddedBundleGroups().entrySet()
					.iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				String versionedId = (String) entry.getKey();
				String featureId = versionedId.substring(0,
						versionedId.indexOf(':'));
				AboutInfo info = (AboutInfo) entry.getValue();

				if (info != null && info.getWelcomePageURL() != null) {
					welcomeFeatures.add(info);
					// activate the feature plug-in so it can run some install
					// code
					IPlatformConfiguration platformConfiguration = ConfiguratorUtils
							.getCurrentPlatformConfiguration();
					IPlatformConfiguration.IFeatureEntry feature = platformConfiguration
							.findConfiguredFeatureEntry(featureId);
					if (feature != null) {
						String pi = feature.getFeaturePluginIdentifier();
						if (pi != null) {
							// Start the bundle if there is one
							Bundle bundle = Platform.getBundle(pi);
							if (bundle != null) {
								try {
									bundle.start(Bundle.START_TRANSIENT);
								} catch (BundleException exception) {
									StatusManager
											.getManager()
											.handle(new Status(
													IStatus.ERROR,
													ApplicationPlugin.PLUGIN_ID,
													"Failed to load feature", exception));//$NON-NLS-1$
								}
							}
						}
					}
				}
			}

			int wCount = getWorkbench().getWorkbenchWindowCount();
			for (int i = 0; i < welcomeFeatures.size(); i++) {
				AboutInfo newInfo = (AboutInfo) welcomeFeatures.get(i);
				String id = newInfo.getWelcomePerspectiveId();
				// Other editors were already opened in postWindowRestore(..)
				if (id == null || i >= wCount) {
					openWelcomeEditor(window, new WelcomeEditorInput(newInfo),
							id);
				}
			}
		}
	}

	/* Enable System-Tray Support */
	private boolean enableTray() {

		/* Avoid that this is being called redundantly */
		if (fTrayEnabled) {
			return true;
		}

		/* Only enable for Primary Window */
		IWorkbenchWindow primaryWindow = UIUtil.getPrimaryWindow();
		if (primaryWindow == null
				|| !primaryWindow.equals(getWindowConfigurer().getWindow())) {
			return false;
		}

		final Shell shell = primaryWindow.getShell();
		final Tray tray = shell.getDisplay().getSystemTray();

		/* Tray not support on the OS */
		if (tray == null) {
			return false;
		}

		/* Create Item in Tray */
		fTrayItem = new TrayItem(tray, SWT.NONE);
		fTrayItem.setToolTipText(Platform.getProduct().getName());
		fTrayEnabled = true;

		if (Application.IS_WINDOWS) {
			fTrayItem.setVisible(true);
		}

		/* Apply Image */
		// TODO set correct image
		fTrayItem.setImage(ResourceManager.getPluginImage(
				ApplicationPlugin.getDefault(),
				"icons/iconexperience/windowimages/cabinet_open_16.png")); //$NON-NLS-1$

		/* Minimize to Tray on Shell Iconify if set */
		fTrayShellListener = new ShellAdapter() {

			@Override
			public void shellIconified(final ShellEvent e) {
				if (!fBlockIconifyEvent
						&& (fMinimizeFromClose || preferenceStore
								.getBoolean(UIPreferenceInitializer.TRAY_ON_MINIMIZE))) {
					moveToTray(shell);
				}
				if (window == null || window.getShell() == null
						|| window.getShell().isDisposed()) {
					openDesktopWindow(shell);
				}
			}

			@Override
			public void shellDeiconified(ShellEvent e) {
				closeDesktopWindow();
			};
		};
		shell.addShellListener(fTrayShellListener);

		/* Show Menu on Selection */
		fTrayItem.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(final Event event) {
				MenuManager trayMenu = new MenuManager();

				/* Restore */
				trayMenu.add(new ContributionItem() {
					@Override
					public void fill(final Menu menu, final int index) {
						MenuItem restoreItem = new MenuItem(menu, SWT.PUSH);
						restoreItem
								.setText(org.eclipse.remus.application.messages.IDEWorkbenchMessages.ApplicationWorkbenchWindowAdvisor_Restore);
						restoreItem
								.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(
											final SelectionEvent e) {
										restoreFromTray(shell);
									}
								});
						menu.setDefaultItem(restoreItem);
						MenuItem restoreTray = new MenuItem(menu, SWT.PUSH);
						restoreTray
								.setText(org.eclipse.remus.application.messages.IDEWorkbenchMessages.ApplicationWorkbenchWindowAdvisor_RestoreDesktopPanel);
						restoreTray
								.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(
											final SelectionEvent e) {
										if (window.getShell() == null
												|| !window.getShell()
														.isVisible()) {
											openDesktopWindow(shell);
										}
										window.getShell().forceActive();
									}
								});
					}
				});

				/* Separator */
				trayMenu.add(new Separator());

				/* Other Items */
				actionBarAdvisor.fillTrayItem(trayMenu);

				Menu menu = trayMenu.createContextMenu(shell);
				menu.setVisible(true);
			}
		});
		/* Handle DefaultSelection */
		fTrayItem.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(final Event event) {

				/* Restore from Tray */
				if (!shell.isVisible()) {
					restoreFromTray(shell);
				} else if (!Application.IS_WINDOWS) {
					moveToTray(shell);
				}
			}
		});

		return true;
	}

	/* Move to System Tray */
	public void moveToTray(final Shell shell) {
		if (Application.IS_WINDOWS) {
			fTrayItem.setVisible(true);
		}

		/*
		 * Bug in SWT: For some reason, calling setVisible(false) here will
		 * result in a second Iconify Event. The fix is to disable processing of
		 * this event meanwhile.
		 */
		fBlockIconifyEvent = true;
		try {
			shell.setVisible(false);
		} finally {
			fBlockIconifyEvent = false;
		}

		fMinimizedToTray = true;

		openDesktopWindow(shell);
	}

	private void openDesktopWindow(final Shell shell) {
		window = new DesktopWindow(shell.getDisplay());
		window.setBlockOnOpen(false);
		window.open();
	}

	private void closeDesktopWindow() {
		if (window != null) {
			window.close();
		}
	}

	/**
	 * @param shell
	 */
	public void restoreFromTray(final Shell shell) {

		/* Restore Shell */
		shell.setVisible(true);
		shell.setActive();

		/* Un-Minimize if minimized */
		if (shell.getMinimized()) {
			shell.setMinimized(false);
		}

		// if (Application.IS_WINDOWS) {
		// fTrayItem.setVisible(false);
		// }

		if (fTrayTeasing) {
			// TODO Application Images
			fTrayItem.setImage(PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_ELEMENT));
		}
		closeDesktopWindow();
		fTrayTeasing = false;
		fMinimizedToTray = false;
	}

	/* Disable System-Tray Support */
	private void disableTray() {

		/* Avoid that this is being called redundantly */
		if (!fTrayEnabled) {
			return;
		}

		/* First make sure to have the Window restored */
		restoreFromTray(getWindowConfigurer().getWindow().getShell());

		fTrayEnabled = false;
		fMinimizedToTray = false;

		if (fTrayItem != null) {
			fTrayItem.dispose();
		}

		if (fTrayShellListener != null) {
			getWindowConfigurer().getWindow().getShell()
					.removeShellListener(fTrayShellListener);
		}
	}

	/**
	 * Open a welcome editor for the given input
	 */
	private void openWelcomeEditor(final IWorkbenchWindow window,
			final WelcomeEditorInput input, final String perspectiveId) {
		if (getWorkbench().getWorkbenchWindowCount() == 0) {
			// Something is wrong, there should be at least
			// one workbench window open by now.
			return;
		}

		IWorkbenchWindow win = window;
		if (perspectiveId != null) {
			try {
				win = getWorkbench().openWorkbenchWindow(perspectiveId,
						wbAdvisor.getDefaultPageInput());
				if (win == null) {
					win = window;
				}
			} catch (WorkbenchException e) {
				IDEWorkbenchPlugin
						.log("Error opening window with welcome perspective.", e.getStatus()); //$NON-NLS-1$
				return;
			}
		}

		if (win == null) {
			win = getWorkbench().getWorkbenchWindows()[0];
		}

		IWorkbenchPage page = win.getActivePage();
		String id = perspectiveId;
		if (id == null) {
			id = getWorkbench().getPerspectiveRegistry()
					.getDefaultPerspective();
		}

		if (page == null) {
			try {
				page = win.openPage(id, wbAdvisor.getDefaultPageInput());
			} catch (WorkbenchException e) {
				ErrorDialog.openError(win.getShell(), "", //$NON-NLS-1$
						e.getMessage(), e.getStatus());
			}
		}
		if (page == null) {
			return;
		}

		if (page.getPerspective() == null) {
			try {
				page = getWorkbench().showPerspective(id, win);
			} catch (WorkbenchException e) {
				ErrorDialog.openError(win.getShell(), "", //$NON-NLS-1$
						"", //$NON-NLS-1$
						e.getStatus());
				return;
			}
		}

		page.setEditorAreaVisible(true);

		// see if we already have an editor
		IEditorPart editor = page.findEditor(input);
		if (editor != null) {
			page.activate(editor);
			return;
		}

		try {
			page.openEditor(input, WELCOME_EDITOR_ID);
		} catch (PartInitException e) {
			ErrorDialog.openError(win.getShell(), "", //$NON-NLS-1$
					"", //$NON-NLS-1$
					e.getStatus());
		}
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.application.WorkbenchAdvisor#createEmptyWindowContents
	 * (org.eclipse.ui.application.IWorkbenchWindowConfigurer,
	 * org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public Control createEmptyWindowContents(final Composite parent) {
		final IWorkbenchWindow window = getWindowConfigurer().getWindow();
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		Display display = composite.getDisplay();
		Color bgCol = display
				.getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND);
		composite.setBackground(bgCol);
		Label label = new Label(composite, SWT.WRAP);
		label.setForeground(display
				.getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		label.setBackground(bgCol);
		label.setFont(JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT));
		String msg = ""; //$NON-NLS-1$
		label.setText(msg);
		ToolBarManager toolBarManager = new ToolBarManager();
		// TODO: should obtain the open perspective action from ActionFactory
		openPerspectiveAction = ActionFactory.OPEN_PERSPECTIVE_DIALOG
				.create(window);
		toolBarManager.add(openPerspectiveAction);
		ToolBar toolBar = toolBarManager.createControl(composite);
		toolBar.setBackground(bgCol);
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#dispose()
	 */
	@Override
	public void dispose() {
		if (openPerspectiveAction != null) {
			openPerspectiveAction.dispose();
			openPerspectiveAction = null;
		}
		super.dispose();
	}

	public boolean isMinimized() {
		return fMinimizedToTray;
	}

}
