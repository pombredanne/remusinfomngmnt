package org.remus.infomngmnt.ui.editors;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.ui.editor.ProblemEditorPart;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.util.EditUIMarkerHelper;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.editor.SharedHeaderFormEditor;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.common.ui.swt.ModelDataTransfer;
import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.progress.DelayedRunnable;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.extension.AbstractDelegationEditorPart;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;
import org.remus.infomngmnt.ui.extension.IEditPage;
import org.remus.infomngmnt.ui.extension.ISourcePage;
import org.remus.infomngmnt.ui.extension.UIExtensionManager;
import org.remus.infomngmnt.ui.views.LinkOutline;
import org.remus.infomngmnt.util.EditingUtil;

public class InformationEditor extends SharedHeaderFormEditor implements IEditingDomainProvider {

	public static final String ID = "org.remus.infomngmnt.ui.editors.InformationEditor"; //$NON-NLS-1$

	/**
	 * Map to store the diagnostic associated with a resource. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Map<Resource, Diagnostic> resourceToDiagnosticMap = new LinkedHashMap<Resource, Diagnostic>();

	/**
	 * The MarkerHelper is responsible for creating workspace resource markers
	 * presented in Eclipse's Problems View. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected MarkerHelper markerHelper = new EditUIMarkerHelper();

	/**
	 * Controls whether the problem indication should be updated. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected boolean updateProblemIndication = true;

	/**
	 * Resources that have been removed since last activation. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Collection<Resource> removedResources = new ArrayList<Resource>();

	/**
	 * Resources that have been changed since last activation. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Collection<Resource> changedResources = new ArrayList<Resource>();

	/**
	 * Resources that have been saved. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	protected Collection<Resource> savedResources = new ArrayList<Resource>();

	/**
	 * This listens for workspace changes. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected IResourceChangeListener resourceChangeListener = new IResourceChangeListener() {
		public void resourceChanged(final IResourceChangeEvent event) {
			final IResourceDelta delta = event.getDelta();
			try {
				class ResourceDeltaVisitor implements IResourceDeltaVisitor {
					protected ResourceSet resourceSet = InformationEditor.this.editingDomain
							.getResourceSet();
					protected Collection<Resource> changedResources = new ArrayList<Resource>();
					protected Collection<Resource> removedResources = new ArrayList<Resource>();

					public boolean visit(final IResourceDelta delta) {
						if (delta.getResource().getType() == IResource.FILE) {
							if (delta.getKind() == IResourceDelta.REMOVED
									|| delta.getKind() == IResourceDelta.CHANGED
									&& delta.getFlags() != IResourceDelta.MARKERS) {
								final Resource resource = this.resourceSet.getResource(URI
										.createURI(delta.getFullPath().toString()), false);
								if (resource != null) {
									if (delta.getKind() == IResourceDelta.REMOVED) {
										this.removedResources.add(resource);
									} else if (!InformationEditor.this.savedResources
											.remove(resource)) {
										this.changedResources.add(resource);
									}
								}
							}
						}

						return true;
					}

					public Collection<Resource> getChangedResources() {
						return this.changedResources;
					}

					public Collection<Resource> getRemovedResources() {
						return this.removedResources;
					}
				}

				final ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
				delta.accept(visitor);

				if (!visitor.getRemovedResources().isEmpty()) {
					InformationEditor.this.removedResources.addAll(visitor.getRemovedResources());
					if (!isDirty()) {
						getSite().getShell().getDisplay().asyncExec(new Runnable() {
							public void run() {
								// InformationEditor.this.dispose();
								getSite().getPage().closeEditor(InformationEditor.this, false);
							}
						});
					}
				}
				if (!visitor.getChangedResources().isEmpty()) {
					InformationEditor.this.changedResources.addAll(visitor.getChangedResources());
					if (getSite().getPage().getActiveEditor() == InformationEditor.this) {
						getSite().getShell().getDisplay().asyncExec(new Runnable() {
							public void run() {
								handleActivate();
							}
						});
					}
				}
			} catch (final CoreException exception) {
				InfomngmntEditPlugin.INSTANCE.log(exception);
			}
		}
	};

	private Clipboard clipboard;

	protected Adapter dirtyAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (notification.getEventType() != Notification.RESOLVE
					&& notification.getEventType() != Notification.REMOVING_ADAPTER) {
				setDirty(true);
			}
		}
	};

	private DelayedRunnable refreshRunnable;

	private final Adapter labelChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification msg) {
			if (msg.getFeature() == InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL) {
				InformationEditor.this.headerForm2.getForm().setText(getPrimaryModel().getLabel());
				setPartName(getPrimaryModel().getLabel());
			}
		};
	};

	/**
	 * This listens for when the outline becomes active <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(final IWorkbenchPart p) {
			if (p instanceof ContentOutline) {
				// if (((ContentOutline)p).getCurrentPage() ==
				// contentOutlinePage) {
				// getActionBarContributor().setActiveEditor(InformationEditor.this);
				//
				// setCurrentViewer(contentOutlineViewer);
				// }
			} else if (p == InformationEditor.this) {
				handleActivate();
			}
		}

		public void partBroughtToTop(final IWorkbenchPart p) {
			// Ignore.
		}

		public void partClosed(final IWorkbenchPart p) {
			// Ignore.
		}

		public void partDeactivated(final IWorkbenchPart p) {
			// Ignore.
		}

		public void partOpened(final IWorkbenchPart p) {
			// Ignore.
		}
	};

	private ViewPage page1;
	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryEditingDomain editingDomain;

	public InformationEditor() {
		super();
		initializeEditingDomain();
	}

	/**
	 * This is called during startup. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@Override
	public void init(final IEditorSite site, final IEditorInput editorInput) {
		setSite(site);
		setInputWithNotify(editorInput);

		// site.setSelectionProvider(this);
		site.getPage().addPartListener(this.partListener);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this.resourceChangeListener,
				IResourceChangeEvent.POST_CHANGE);
		createModel();
		setPartName(getPrimaryModel().getLabel());
		setTitleImage(InformationExtensionManager.getInstance().getInfoTypeByType(
				getPrimaryModel().getType()).getImage());

	}

	@Override
	protected void addPages() {
		try {
			IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
					getPrimaryModel().getType());
			int offset = 0;
			if (infoTypeByType.isBuildHtml()) {
				addPage(this.page1 = new ViewPage(this, getPrimaryModel()));
				setPageImage(0, ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/iconexperience/16/eyeglasses.png"));
				offset = 1;
			}
			List<IEditPage> editPageByType = UIExtensionManager.getInstance().getEditPageByType(
					getPrimaryModel().getType());
			for (int i = 0, n = editPageByType.size(); i < n; i++) {
				AbstractInformationFormPage editPage = editPageByType.get(i).getEditPage();
				editPage.initialize(this);
				editPage.setModelObject(getPrimaryModel());
				editPage.setEditingDomain(this.editingDomain);
				editPage.setBindingContext(this.ctx);
				addPage(editPage);
				if (editPageByType.get(i).getImage() != null) {
					setPageImage(i + offset, editPageByType.get(i).getImage().createImage());
				}
				setPageText(i + offset, editPageByType.get(i).getLabel());
			}
			List<ISourcePage> sourcePage = UIExtensionManager.getInstance().getSourcePageByType(
					getPrimaryModel().getType());
			offset = offset + editPageByType.size();
			for (int i = 0, n = sourcePage.size(); i < n; i++) {
				AbstractDelegationEditorPart currentSourcePage = sourcePage.get(i).getSourcePage();
				try {
					IEditorPart createEditor = currentSourcePage.createEditor();
					IEditorInput createEditorInput = currentSourcePage
							.createEditorInput(getPrimaryModel());
					if (createEditor != null && createEditorInput != null) {
						createEditor.addPropertyListener(new IPropertyListener() {
							public void propertyChanged(final Object source, final int propId) {
								firePropertyChange(propId);
								if (propId == IEditorPart.PROP_DIRTY) {
									setDirty(true);
								}
							}
						});
						addPage(createEditor, createEditorInput);
						if (sourcePage.get(i).getImage() != null) {
							setPageImage(i + offset, sourcePage.get(i).getImage().createImage());
						}
						setPageText(i + offset, sourcePage.get(i).getLabel());
					}
				} catch (Exception e) {
					// do nothing..
				}
			}

		} catch (final PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void createHeaderContents(final IManagedForm headerForm) {
		this.headerForm2 = headerForm;
		this.headerForm2.getForm().setText(getPrimaryModel().getLabel());
		this.headerForm2.getForm().setImage(
				InformationExtensionManager.getInstance().getInfoTypeByType(
						getPrimaryModel().getType()).getImageDescriptor().createImage());
		getToolkit().decorateFormHeading(this.headerForm2.getForm().getForm());
		List<String> commandIdsByTypeId = UIExtensionManager.getInstance().getCommandIdsByTypeId(
				getPrimaryModel().getType());
		if (commandIdsByTypeId != null) {
			for (String string : commandIdsByTypeId) {
				IContributionItem commandContribution = UIUtil.getCommandContribution(string, null,
						null, null, null, null);
				if (commandContribution != null) {
					headerForm.getForm().getToolBarManager().add(commandContribution);
				}
			}
			headerForm.getForm().getToolBarManager().update(true);
		}
	}

	/**
	 * Shows a dialog that asks if conflicting changes should be discarded. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected boolean handleDirtyConflict() {
		return MessageDialog.openQuestion(getSite().getShell(),
				getString("_UI_FileConflict_label"), getString("_WARN_FileConflict"));
	}

	/**
	 * Handles activation of the editor or it's associated views. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void handleActivate() {
		// Recompute the read only state.
		//
		if (this.editingDomain.getResourceToReadOnlyMap() != null) {
			this.editingDomain.getResourceToReadOnlyMap().clear();

			// Refresh any actions that may become enabled or disabled.
			//
			// setSelection(getSelection());
		}

		if (!this.removedResources.isEmpty()) {
			if (handleDirtyConflict()) {
				getSite().getPage().closeEditor(InformationEditor.this, false);
				InformationEditor.this.dispose();
			} else {
				this.removedResources.clear();
				this.changedResources.clear();
				this.savedResources.clear();
			}
		} else if (!this.changedResources.isEmpty()) {
			if (this.refreshRunnable != null && !this.refreshRunnable.isRunning()) {
				this.refreshRunnable.cancel();
				this.refreshRunnable = null;
			}
			this.refreshRunnable = new DelayedRunnable() {
				@Override
				protected void runDelayed() {
					InformationEditor.this.changedResources
							.removeAll(InformationEditor.this.savedResources);
					handleChangedResources();
					InformationEditor.this.changedResources.clear();
					InformationEditor.this.savedResources.clear();
				}
			};
			getSite().getShell().getDisplay().timerExec(2000, this.refreshRunnable);

		}
		setRead();
	}

	private void setRead() {
		getSite().getShell().getDisplay().timerExec(1000, new Runnable() {
			public void run() {
				InformationUnitListItem adapter = (InformationUnitListItem) getPrimaryModel()
						.getAdapter(InformationUnitListItem.class);
				if (adapter != null && adapter.isUnread()) {
					AdapterFactoryEditingDomain navigationEditingDomain = EditingUtil.getInstance()
							.getNavigationEditingDomain();
					Command create = SetCommand.create(navigationEditingDomain, adapter,
							InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM__UNREAD, false);
					CompoundCommand cc = new CompoundCommand();
					cc.append(create);
					cc.setLabel("Mark as read");
					navigationEditingDomain.getCommandStack().execute(cc);
				}

			}
		});

	}

	/**
	 * Adapter used to update the problem indication when resources are demanded
	 * loaded. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected EContentAdapter problemIndicationAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			if (notification.getNotifier() instanceof Resource) {
				switch (notification.getFeatureID(Resource.class)) {
				case Resource.RESOURCE__IS_LOADED:
				case Resource.RESOURCE__ERRORS:
				case Resource.RESOURCE__WARNINGS: {
					final Resource resource = (Resource) notification.getNotifier();
					final Diagnostic diagnostic = analyzeResourceProblems(resource, null);
					if (diagnostic.getSeverity() != Diagnostic.OK) {
						InformationEditor.this.resourceToDiagnosticMap.put(resource, diagnostic);
					} else {
						InformationEditor.this.resourceToDiagnosticMap.remove(resource);
					}

					if (InformationEditor.this.updateProblemIndication) {
						getSite().getShell().getDisplay().asyncExec(new Runnable() {
							public void run() {
								updateProblemIndication();
							}
						});
					}
					break;
				}
				}
			} else {
				super.notifyChanged(notification);
			}
		}

		@Override
		protected void setTarget(final Resource target) {
			basicSetTarget(target);
		}

		@Override
		protected void unsetTarget(final Resource target) {
			basicUnsetTarget(target);
		}
	};

	private InformationUnit primaryModel;

	private boolean dirty;

	private LinkOutline contentOutlinePage;

	private EMFDataBindingContext ctx;

	private IManagedForm headerForm2;

	private int tabHeight;

	@Override
	protected void createPages() {
		super.createPages();
		if (getContainer() instanceof CTabFolder) {
			this.tabHeight = ((CTabFolder) getContainer()).getTabHeight();
			if (getPageCount() == 1) {
				((CTabFolder) getContainer()).setTabHeight(0);
			}
		}
		getPrimaryModel().eAdapters().add(this.dirtyAdapter);
		getPrimaryModel().eAdapters().add(this.labelChangeAdapter);
	}

	public void setDirty(final boolean dirty) {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (dirty != InformationEditor.this.dirty) {
					InformationEditor.this.dirty = dirty;
					firePropertyChange(PROP_DIRTY);
				}
			}
		});

	}

	@Override
	public boolean isDirty() {
		return this.dirty;
	}

	/**
	 * This is for implementing {@link IEditorPart} and simply saves the model
	 * file. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void doSave(final IProgressMonitor progressMonitor) {
		for (int i = 0; i < this.pages.size(); i++) {
			if (this.pages.get(i) != null) {
				((ISaveablePart) this.pages.get(i)).doSave(progressMonitor);
				progressMonitor.internalWorked(i);
			}
		}
		// Save only resources that have actually changed.
		//
		final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
				Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);

		// Do the work within an operation because this is a long running
		// activity that modifies the workbench.
		//
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			// This is the method that gets invoked when the operation runs.
			//
			@Override
			public void execute(final IProgressMonitor monitor) {
				// Save the resources to the file system.
				//
				boolean first = true;
				for (final Resource resource : InformationEditor.this.editingDomain
						.getResourceSet().getResources()) {
					if ((first || !resource.getContents().isEmpty() || isPersisted(resource))
							&& !InformationEditor.this.editingDomain.isReadOnly(resource)) {
						try {
							EditingUtil.getInstance().saveObjectToResource(
									InformationEditor.this.primaryModel);
							InformationEditor.this.savedResources.add(resource);
						} catch (final Exception exception) {
							InformationEditor.this.resourceToDiagnosticMap.put(resource,
									analyzeResourceProblems(resource, exception));
						}
						first = false;
					}
				}
			}
		};

		this.updateProblemIndication = false;
		try {
			// This runs the options, and shows progress.
			//
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);

			// Refresh the necessary state.
			//
			((BasicCommandStack) this.editingDomain.getCommandStack()).saveIsDone();
			// firePropertyChange(IEditorPart.PROP_DIRTY);
		} catch (final Exception exception) {
			// Something went wrong that shouldn't.
			//
			InfomngmntEditPlugin.INSTANCE.log(exception);
		}
		this.updateProblemIndication = true;
		updateProblemIndication();
		setDirty(false);
	}

	/**
	 * This returns whether something has been persisted to the URI of the
	 * specified resource. The implementation uses the URI converter from the
	 * editor's resource set to try to open an input stream. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected boolean isPersisted(final Resource resource) {
		boolean result = false;
		try {
			final InputStream stream = this.editingDomain.getResourceSet().getURIConverter()
					.createInputStream(resource.getURI());
			if (stream != null) {
				result = true;
				stream.close();
			}
		} catch (final IOException e) {
			// Ignore
		}
		return result;
	}

	/**
	 * Handles what to do with changed resources on activation. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void handleChangedResources() {
		if (!this.changedResources.isEmpty() && (!isDirty() || handleDirtyConflict())) {
			if (isDirty()) {
				this.changedResources.addAll(this.editingDomain.getResourceSet().getResources());
			}
			this.editingDomain.getCommandStack().flush();

			this.updateProblemIndication = false;
			try {
				for (final Resource resource : this.changedResources) {
					if (resource.isLoaded()) {
						resource.unload();
						try {
							resource.load(Collections.EMPTY_MAP);
						} catch (final IOException exception) {
							if (!this.resourceToDiagnosticMap.containsKey(resource)) {
								this.resourceToDiagnosticMap.put(resource, analyzeResourceProblems(
										resource, exception));
							}
						}
					}
				}
			} catch (Exception e) {
				// do nothing
			}
			for (int i = 0, n = this.pages.size(); i < n; i++) {
				Object object = this.pages.get(i);
				if (object instanceof AbstractInformationFormPage) {
					if (((AbstractInformationFormPage) object).isRendered()) {
						((AbstractInformationFormPage) object).disposeBinding();
					}
				}
			}
			disposeModel();
			initializeEditingDomain();
			init(getEditorSite(), getEditorInput());
			this.headerForm2.getForm().setText(getPrimaryModel().getLabel());
			for (int i = 0, n = this.pages.size(); i < n; i++) {
				Object object = this.pages.get(i);
				if (object instanceof AbstractInformationFormPage) {
					((AbstractInformationFormPage) object).setModelObject(getPrimaryModel());
					((AbstractInformationFormPage) object).setEditingDomain(this.editingDomain);
					((AbstractInformationFormPage) object).setBindingContext(this.ctx);
					if (((AbstractInformationFormPage) object).isRendered()) {
						((AbstractInformationFormPage) object).bindValuesToUi();
					}
				}
			}
			if (this.contentOutlinePage != null) {
				this.contentOutlinePage.setInfo(getPrimaryModel());
				this.contentOutlinePage.setAdapterFactoryEditingDomain(this.editingDomain);
				this.contentOutlinePage.bindValuesToUi();
			}

			// if (AdapterFactoryEditingDomain.isStale(editorSelection)) {
			// setSelection(StructuredSelection.EMPTY);
			// }

			this.updateProblemIndication = true;
			updateProblemIndication();
			getPrimaryModel().eAdapters().add(this.dirtyAdapter);
			getPrimaryModel().eAdapters().add(this.labelChangeAdapter);

			getActionBarContributor().activate();

		}
	}

	/**
	 * This sets up the editing domain for the model editor. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void initializeEditingDomain() {
		// Create an adapter factory that yields item providers.
		//
		this.adapterFactory = EditingUtil.getInstance().getAdapterFactory();
		this.ctx = new EMFDataBindingContext();

		// Create the command stack that will notify this editor as commands are
		// executed.
		//
		final BasicCommandStack commandStack = new BasicCommandStack();

		// Add a listener to set the most recent command's affected objects to
		// be the selection of the viewer with focus.
		//
		commandStack.addCommandStackListener(new CommandStackListener() {
			public void commandStackChanged(final EventObject event) {
				getContainer().getDisplay().asyncExec(new Runnable() {
					public void run() {
						firePropertyChange(IEditorPart.PROP_DIRTY);

						// Try to select the affected objects.
						//
						final Command mostRecentCommand = ((CommandStack) event.getSource())
								.getMostRecentCommand();
						// if (mostRecentCommand != null) {
						// setSelectionToViewer(mostRecentCommand.getAffectedObjects());
						// }
						// if (propertySheetPage != null &&
						// !propertySheetPage.getControl().isDisposed()) {
						// propertySheetPage.refresh();
						// }
					}
				});
			}
		});

		// Create the editing domain with a special command stack.
		//
		this.editingDomain = new AdapterFactoryEditingDomain(this.adapterFactory, commandStack,
				new HashMap<Resource, Boolean>());
	}

	@Override
	public void doSaveAs() {
	}

	/**
	 * This returns the editing domain as required by the
	 * {@link IEditingDomainProvider} interface. This is important for
	 * implementing the static methods of {@link AdapterFactoryEditingDomain}
	 * and for supporting {@link org.eclipse.emf.edit.ui.action.CommandAction}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	/**
	 * This is the method called to load a resource into the editing domain's
	 * resource set based on the editor's input. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public void createModel() {
		final URI resourceURI = EditUIUtil.getURI(getEditorInput());
		Exception exception = null;
		Resource resource = null;
		try {
			// Load the resource through the editing domain.
			//
			resource = this.editingDomain.getResourceSet().getResource(resourceURI, true);
		} catch (final Exception e) {
			exception = e;
			resource = this.editingDomain.getResourceSet().getResource(resourceURI, false);
		}
		this.primaryModel = (InformationUnit) resource.getContents().get(0);

		final Diagnostic diagnostic = analyzeResourceProblems(resource, exception);
		if (diagnostic.getSeverity() != Diagnostic.OK) {
			this.resourceToDiagnosticMap
					.put(resource, analyzeResourceProblems(resource, exception));
		}
		this.editingDomain.getResourceSet().eAdapters().add(this.problemIndicationAdapter);
	}

	/**
	 * Updates the problems indication with the information described in the
	 * specified diagnostic. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void updateProblemIndication() {
		if (this.updateProblemIndication) {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.OK,
					"net.remus.infobroker.core.editor", 0, null, new Object[] { this.editingDomain
							.getResourceSet() });
			for (final Diagnostic childDiagnostic : this.resourceToDiagnosticMap.values()) {
				if (childDiagnostic.getSeverity() != Diagnostic.OK) {
					diagnostic.add(childDiagnostic);
				}
			}

			int lastEditorPage = getPageCount() - 1;
			if (lastEditorPage >= 0 && getEditor(lastEditorPage) instanceof ProblemEditorPart) {
				((ProblemEditorPart) getEditor(lastEditorPage)).setDiagnostic(diagnostic);
				if (diagnostic.getSeverity() != Diagnostic.OK) {
					setActivePage(lastEditorPage);
				}
			} else if (diagnostic.getSeverity() != Diagnostic.OK) {
				final ProblemEditorPart problemEditorPart = new ProblemEditorPart();
				problemEditorPart.setDiagnostic(diagnostic);
				problemEditorPart.setMarkerHelper(this.markerHelper);
				try {
					addPage(++lastEditorPage, problemEditorPart, getEditorInput());
					setPageText(lastEditorPage, problemEditorPart.getPartName());
					setActivePage(lastEditorPage);
					showTabs();
				} catch (final PartInitException exception) {
					InfomngmntEditPlugin.INSTANCE.log(exception);
				}
			}

			if (this.markerHelper.hasMarkers(this.editingDomain.getResourceSet())) {
				this.markerHelper.deleteMarkers(this.editingDomain.getResourceSet());
				if (diagnostic.getSeverity() != Diagnostic.OK) {
					try {
						this.markerHelper.createMarkers(diagnostic);
					} catch (final CoreException exception) {
						InfomngmntEditPlugin.INSTANCE.log(exception);
					}
				}
			}
		}
	}

	/**
	 * If there is more than one page in the multi-page editor part, this shows
	 * the tabs at the bottom. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void showTabs() {
		if (getPageCount() > 1) {
			// setPageText(0, getString("_UI_SelectionPage_label"));
			// if (getContainer() instanceof CTabFolder) {
			// ((CTabFolder) getContainer()).setTabHeight(SWT.DEFAULT);
			// final Point point = getContainer().getSize();
			// getContainer().setSize(point.x, point.y - 6);
			// }
			if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
				((CTabFolder) getContainer()).setTabHeight(this.tabHeight);
			}
		}
	}

	/**
	 * Returns a diagnostic describing the errors and warnings listed in the
	 * resource and the specified exception (if any). <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Diagnostic analyzeResourceProblems(final Resource resource, final Exception exception) {
		if (!resource.getErrors().isEmpty() || !resource.getWarnings().isEmpty()) {
			final BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
					CorePlugin.PLUGIN_ID, 0, getString("_UI_CreateModelError_message", resource
							.getURI()), new Object[] { exception == null ? (Object) resource
							: exception });
			basicDiagnostic.merge(EcoreUtil.computeDiagnostic(resource, true));
			return basicDiagnostic;
		} else if (exception != null) {
			return new BasicDiagnostic(Diagnostic.ERROR, CorePlugin.PLUGIN_ID, 0, getString(
					"_UI_CreateModelError_message", resource.getURI()), new Object[] { exception });
		} else {
			return Diagnostic.OK_INSTANCE;
		}
	}

	/**
	 * This looks up a string in the plugin's plugin.properties file. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static String getString(final String key) {
		return InfomngmntEditPlugin.INSTANCE.getString(key);
	}

	/**
	 * This looks up a string in plugin.properties, making a substitution. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static String getString(final String key, final Object s1) {
		return InfomngmntEditPlugin.INSTANCE.getString(key, new Object[] { s1 });
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InfomngmntActionBarContributor getActionBarContributor() {
		return (InfomngmntActionBarContributor) getEditorSite().getActionBarContributor();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IActionBars getActionBars() {
		return getActionBarContributor().getActionBars();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public InformationUnit getPrimaryModel() {
		return this.primaryModel;
	}

	public void disposeModel() {
		this.updateProblemIndication = false;

		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this.resourceChangeListener);

		getSite().getPage().removePartListener(this.partListener);

		this.adapterFactory.dispose();

		getPrimaryModel().eAdapters().remove(this.dirtyAdapter);
		getPrimaryModel().eAdapters().remove(this.labelChangeAdapter);
		this.ctx.dispose();
		if (this.contentOutlinePage != null) {
			this.contentOutlinePage.disposeModel();
		}

		if (getActionBarContributor().getActiveEditor() == this) {
			getActionBarContributor().deactivate();
		}

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void dispose() {
		disposeModel();
		super.dispose();
	}

	@Override
	public Object getAdapter(final Class adapter) {
		if (adapter.equals(IContentOutlinePage.class)) {
			if (this.pages.get(getCurrentPage()) instanceof ViewPage
					|| this.pages.get(getCurrentPage()) instanceof AbstractInformationFormPage) {
				return getContentOutline();
			}
		}
		return super.getAdapter(adapter);
	}

	public ContentOutlinePage getContentOutline() {
		if (this.contentOutlinePage == null) {
			this.contentOutlinePage = new LinkOutline(getPrimaryModel(), this.editingDomain) {
				@Override
				public void setActionBars(final IActionBars actionBars) {
					super.setActionBars(actionBars);
					getActionBarContributor().shareGlobalActions(this, actionBars);
				}
			};
		}
		return this.contentOutlinePage;
	}

	protected void performGlobalAction(final String id) {
		// preserve selection
		ISelection selection = getSelection();
		boolean handled = ((InformationFormPage) getActivePageInstance()).performGlobalAction(id);
		if (!handled) {
			IFormPage page = getActivePageInstance();
			if (page instanceof InformationFormPage) {
				if (id.equals(ActionFactory.CUT.getId()) || id.equals(ActionFactory.COPY.getId())) {
					copyToClipboard(selection);
					return;
				}
			}
		}
	}

	public ISelection getSelection() {
		return getSite().getSelectionProvider() != null ? getSite().getSelectionProvider()
				.getSelection() : null;
	}

	/**
	 * @param selection
	 */
	private void copyToClipboard(final ISelection selection) {
		Object[] objects = null;
		String textVersion = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel == null || ssel.size() == 0) {
				return;
			}
			objects = ssel.toArray();
			StringWriter writer = new StringWriter();
			PrintWriter pwriter = new PrintWriter(writer);
			Class objClass = null;
			for (int i = 0; i < objects.length; i++) {
				Object obj = objects[i];
				if (objClass == null) {
					objClass = obj.getClass();
				} else if (objClass.equals(obj.getClass()) == false) {
					return;
				} else if (obj instanceof String) {
					// Delimiter is always a newline
					pwriter.println((String) obj);
				}
			}
			pwriter.flush();
			textVersion = writer.toString();
			try {
				pwriter.close();
				writer.close();
			} catch (IOException e) {
			}
		} else if (selection instanceof ITextSelection) {
			textVersion = ((ITextSelection) selection).getText();
		}
		if ((textVersion == null || textVersion.length() == 0) && objects == null) {
			return;
		}
		// set the clipboard contents
		Object[] o = null;
		Transfer[] t = null;
		if (objects == null) {
			o = new Object[] { textVersion };
			t = new Transfer[] { TextTransfer.getInstance() };
		} else if (textVersion == null || textVersion.length() == 0) {
			o = new Object[] { objects };
			t = new Transfer[] { ModelDataTransfer.getInstance() };
		} else {
			o = new Object[] { objects, textVersion };
			t = new Transfer[] { ModelDataTransfer.getInstance(), TextTransfer.getInstance() };
		}
		this.clipboard.setContents(o, t);
	}

	public boolean canPasteFromClipboard() {
		IFormPage page = getActivePageInstance();
		if (page instanceof InformationFormPage) {
			return ((InformationFormPage) page).canPaste(getClipboard());
		}
		return false;
	}

	public boolean canCopy(final ISelection selection) {
		if (selection == null) {
			return false;
		}
		if (selection instanceof IStructuredSelection) {
			return !selection.isEmpty();
		}
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;
			return textSelection.getLength() > 0;
		}
		return false;
	}

	public boolean canCut(final ISelection selection) {
		return canCopy(selection);
	}

	public Clipboard getClipboard() {
		return this.clipboard;
	}

}
