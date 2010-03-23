package org.remus.infomngmnt.resources.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.common.core.streams.FileUtil;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.util.IdFactory;
import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.edit.EditingUtil;
import org.remus.infomngmnt.core.internal.builder.InformationBuilder;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.util.StatusCreator;

public class ResourceUtil {

	public static final String FILE_EXTENSION = "info";

	public static final String DOT_FILE_EXTENSION = ".info";

	public static final String TMPFILE_FILE_EXTENSION = ".remus";

	public static final String PRIMARY_CONTENT_FILE = "primaryContent.info"; //$NON-NLS-1$

	public static final String SETTINGS_FOLDER = ".settings"; //$NON-NLS-1$

	public static final String BINARY_FOLDER = ".binary"; //$NON-NLS-1$

	public static final String CMDSTACK_FOLDER = ".commandstack"; //$NON-NLS-1$

	public static final String BIN_FOLDER = "bin"; //$NON-NLS-1$

	public static final String HTML_EXTENSION = "html";

	public static final String SHEMAPREFIX_ENCRYPTED_PROJECTS = "encrypted"; //$NON-NLS-1$

	public static final String PROJECT_NAME_INTERN = "__intern"; //$NON-NLS-1$

	/**
	 * Checks for presence of the {@link InformationBuilder#BUILDER_ID} builder.
	 * 
	 * @param project
	 *            the project to check
	 * @return true if the project has the builder, else <code>false</code>.
	 */
	public static boolean isRelevantProject(final IProject project) {
		try {
			final ICommand[] buildSpec = project.getDescription().getBuildSpec();
			for (final ICommand command : buildSpec) {
				if (InformationBuilder.BUILDER_ID.equals(command.getBuilderName())) {
					return true;
				}
			}
		} catch (final CoreException e) {
			// do nothing
		}
		return false;
	}

	public static IProject[] getRelevantProjects() {
		List<IProject> returnValue = new ArrayList<IProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			if (isRelevantProject(project)) {
				returnValue.add(project);
			}
		}
		return returnValue.toArray(new IProject[returnValue.size()]);
	}

	public static IProject[] getRelevantAndClosedProjects() {
		List<IProject> returnValue = new ArrayList<IProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			if (isRelevantProject(project) || !project.isOpen()) {
				returnValue.add(project);
			}
		}
		return returnValue.toArray(new IProject[returnValue.size()]);
	}

	public static IProject getProject(final String text) {
		final IProject project = org.eclipse.core.resources.ResourcesPlugin.getWorkspace()
				.getRoot().getProject(text);
		if (project.exists() && project.isOpen() && isRelevantProject(project)) {
			return project;
		}
		return null;

	}

	public static String computeBinFileLocation(final IFile originalFile) {
		IPath binPath = originalFile.getProject().getLocation().append(
				new Path(ResourceUtil.BIN_FOLDER + File.separator
						+ originalFile.getProjectRelativePath()));
		return Pattern.compile(ResourceUtil.FILE_EXTENSION + "$").matcher(binPath.toOSString())
				.replaceFirst(ResourceUtil.HTML_EXTENSION);
	}

	/**
	 * Returns the path of the build HTML file
	 * 
	 * @param originalFile
	 *            the original info unit file
	 * @return the path of the generated html file
	 */
	public static IPath computeBinFileFulllPath(final IFile originalFile) {
		String replacedProjectRelativePath = Pattern.compile(ResourceUtil.FILE_EXTENSION + "$")
				.matcher(originalFile.getProjectRelativePath().toOSString()).replaceFirst(
						ResourceUtil.HTML_EXTENSION);
		IPath binPath = originalFile.getProject().getFullPath().append(
				new Path(ResourceUtil.BIN_FOLDER)).append(replacedProjectRelativePath);
		return binPath;
	}

	/**
	 * If you're creating a project programmatically this method must be called
	 * after the project is created due to the processing of the
	 * postProjectHandle Extension Point.
	 * 
	 * @param description
	 *            the project descriptor
	 */
	public static void postProjectCreation(final IProjectDescription description) {
		PostProjectHandleExtensionManager manager = new PostProjectHandleExtensionManager(
				description);
		manager.executeProxyImplementations();
	}

	/**
	 * <p>
	 * Class that looks through extension registry to find present postproject
	 * Handle Implementations.
	 * </p>
	 * 
	 * 
	 * @author Tom Seidel <tom.seidel@remus-software.org>
	 */
	static final class PostProjectHandleExtensionManager {
		public static final String EXTENSION_POINT = CorePlugin.PLUGIN_ID + ".postProjectHandle"; //$NON-NLS-1$
		public static final String HANDLE_IMPL_NODE_NAME = "handle"; //$NON-NLS-1$
		private final IProjectDescription description;

		public PostProjectHandleExtensionManager(final IProjectDescription description) {
			this.description = description;
		}

		void executeProxyImplementations() {
			final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
					.getExtensionPoint(EXTENSION_POINT);
			final IConfigurationElement[] configurationElements = extensionPoint
					.getConfigurationElements();
			for (final IConfigurationElement configurationElement : configurationElements) {
				try {
					IPostProjectHandle extension = (IPostProjectHandle) configurationElement
							.createExecutableExtension(HANDLE_IMPL_NODE_NAME);
					extension.postProjectCreation(this.description);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * <p>
	 * Creates a new project and creates the needed XMI file for creating new
	 * unit within that project. This is wrapped in a command and is executed on
	 * the Editing domain which is returned on
	 * {@link EditingUtil#getNavigationEditingDomain()}
	 * </p>
	 * <p>
	 * The {@link IProject} is already created.
	 * </p>
	 * 
	 * @param newProject
	 *            the new project
	 * @param monitor
	 *            the progress monitor
	 * @param description
	 *            the project descriptor
	 * @throws CoreException
	 *             if folder or file creation fails.
	 */
	public static Category createNewProject(final IProject newProject,
			final IProgressMonitor monitor, final String description) throws CoreException {
		// At first we have to create the folder for the primary-content file
		IFolder folder = newProject.getFolder(ResourceUtil.SETTINGS_FOLDER);
		if (!folder.exists()) {
			folder.create(true, true, monitor);
		}
		// We're creating the folder where all binary data is located.
		IFolder binaryFolder = newProject.getFolder(ResourceUtil.BINARY_FOLDER);
		if (!binaryFolder.exists()) {
			binaryFolder.create(true, true, monitor);
		}
		// This is the folder for keeping binary references of objects that are
		// obsolete but still on some commandstack.
		IFolder cmdFolder = newProject.getFolder(ResourceUtil.CMDSTACK_FOLDER);
		if (!cmdFolder.exists()) {
			cmdFolder.create(true, true, monitor);
		}
		IFile file = folder.getFile(ResourceUtil.PRIMARY_CONTENT_FILE);
		Category rootCategory = InfomngmntEditPlugin.getPlugin().getEditService()
				.getObjectFromFile(file, InfomngmntPackage.eINSTANCE.getCategory(), true);
		rootCategory.setLabel(newProject.getName());
		rootCategory.setId(IdFactory.createNewId(null));
		rootCategory.setDescription(description);
		InfomngmntEditPlugin.getPlugin().getEditService().saveObjectToResource(rootCategory);

		EditingDomain editingDomain = InfomngmntEditPlugin.getPlugin().getEditService()
				.getNavigationEditingDomain();
		Command createCommand = CommandFactory.CREATE_ROOTCATEGORY(rootCategory, editingDomain);
		editingDomain.getCommandStack().execute(createCommand);
		IApplicationModel appService = InfomngmntEditPlugin.getPlugin().getServiceTracker()
				.getService(IApplicationModel.class);
		appService.addListenerToCategory(rootCategory);
		return rootCategory;
	}

	public static Category createNewProject(final String name, final IProgressMonitor monitor)
			throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (!project.exists()) {
			try {
				IProjectDescription newProjectDescription = ResourcesPlugin.getWorkspace()
						.newProjectDescription(project.getName());
				ResourceUtil.postProjectCreation(newProjectDescription);
				project.create(null);
				project.open(null);
				project.setDescription(newProjectDescription, null);
				return ResourceUtil.createNewProject(project, null, name);
			} catch (CoreException e) {
				throw e;
			}
		} else {
			throw new CoreException(StatusCreator.newStatus(NLS.bind("Project {0} already exists",
					name)));
		}
	}

	public static IFile getInternalFile(final String name, final boolean createIfnotexist) {
		NullProgressMonitor nullProgressMonitor = new NullProgressMonitor();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME_INTERN);
		IFile file = null;
		if (!project.exists()) {
			try {
				project.create(nullProgressMonitor);
				project.open(nullProgressMonitor);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (name != null) {
			file = project.getFile(name);
		} else {
			file = project.getFile(IdFactory.createId());
		}
		if (!file.exists() && createIfnotexist) {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);
			try {
				file.create(inputStream, true, nullProgressMonitor);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				StreamCloser.closeStreams(inputStream);
			}
		}
		return file;
	}

	public static void closeProject(final IProject object) throws CoreException {

		if (isRelevantProject(object) && object.isOpen()) {
			IApplicationModel appService = InfomngmntEditPlugin.getPlugin().getServiceTracker()
					.getService(IApplicationModel.class);
			appService.removeFromModel(object);
		}

	}

	public static boolean isPathInResourceDelta(final IResourceDelta delta, final IPath path) {
		boolean returnValue = false;
		if (delta.getResource() != null && delta.getResource().getFullPath().equals(path)) {
			return true;
		} else {
			IResourceDelta[] affectedChildren = delta.getAffectedChildren();
			for (IResourceDelta iResourceDelta : affectedChildren) {
				if (!returnValue) {
					returnValue = isPathInResourceDelta(iResourceDelta, path);
				}
			}
		}
		return returnValue;
	}

	public static void deleteProject(final IProject object, final IProgressMonitor monitor)
			throws CoreException {
		// TODO implement search for references and delete the linkings.
		IApplicationModel appService = InfomngmntEditPlugin.getPlugin().getServiceTracker()
				.getService(IApplicationModel.class);
		appService.removeFromModel(object);
		object.delete(true, true, monitor);

	}

	public static void cleanUp(final IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Clean up", IProgressMonitor.UNKNOWN);
		monitor.setTaskName("Deleting temporary files");
		ResourcesPlugin.getWorkspace().getRoot().getProject(
				org.remus.infomngmnt.common.core.util.ResourceUtil.PROJECT_NAME_TMP).delete(true,
				new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN));
		IProject[] relevantProjects = getRelevantProjects();
		for (IProject iProject : relevantProjects) {
			IFolder folder = iProject.getFolder(CMDSTACK_FOLDER);
			if (folder.exists()) {
				IResource[] members = folder.members();
				monitor.setTaskName(NLS.bind("Deleting temporary files of project\'\'{0}\'\'",
						folder.getProject().getName()));
				for (IResource iResource : members) {
					iResource.delete(true,
							new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN));
				}
			}
		}
		File file = CorePlugin.getDefault().getStateLocation().append("compare").toFile();
		try {
			monitor.setTaskName("Deleting compare folder");
			FileUtil.delete(file);
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus("Error deleting compare folder", e));
		}

	}
}
