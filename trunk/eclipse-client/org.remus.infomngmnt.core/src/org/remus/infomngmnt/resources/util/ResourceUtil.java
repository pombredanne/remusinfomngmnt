package org.remus.infomngmnt.resources.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.internal.utils.UniversalUniqueIdentifier;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.builder.InformationBuilder;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.EditingUtil;

public class ResourceUtil {

	public static final String FILE_EXTENSION = "info";
	public static final String PRIMARY_CONTENT_FILE = "primaryContent.info"; //$NON-NLS-1$
	public static final String SETTINGS_FOLDER = ".settings"; //$NON-NLS-1$
	public static final String BIN_FOLDER = "bin"; //$NON-NLS-1$
	public static final String HTML_EXTENSION = "html";

	public static boolean isRelevantProject(final IProject project) {
		try {
			final ICommand[] buildSpec = project.getDescription()
			.getBuildSpec();
			for (final ICommand command : buildSpec) {
				if (InformationBuilder.BUILDER_ID.equals(command.getBuilderName())) {
					return true;
				}
			}
		} catch (final CoreException e) {
			// do nothing
		}System.out.println("test");
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

	/**
	 * Adds a builder to the project
	 * 
	 * @param builder
	 *      the builder id
	 * @throws CoreException
	 *      if the project-descriptiion is invalid
	 */
	public static void addBuilder(final IProject project, final String builder)
	throws CoreException {
		// Get project description and then the associated build commands
		final IProjectDescription desc = project.getDescription();
		addBuilder(desc,builder);


	}

	public static void addBuilder(final IProjectDescription desc, final String builder) {
		final ICommand[] commands = desc.getBuildSpec();

		// Determine if builder already associated
		boolean found = false;
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builder)) {
				found = true;
				break;
			}
		}

		// Add builder if not already in project
		if (!found) {
			final ICommand command = desc.newCommand();
			command.setBuilderName(builder);
			// Create map with arguments specific to builder in project here
			// command.setArguments(Map args);
			final ICommand[] newCommands = new ICommand[commands.length + 1];

			// Add it before other builders.
			System.arraycopy(commands, 0, newCommands, 1, commands.length);
			newCommands[0] = command;
			desc.setBuildSpec(newCommands);
		}

	}

	public static void removeBuilder(final IProject project,
			final String builder) throws CoreException {

		final IProjectDescription desp = project.getDescription();
		final ICommand[] commands = desp.getBuildSpec();
		boolean found = false;

		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builder)) {
				found = true;
				break;
			}
		}

		if (found) { // Remove builder from project

			final ICommand[] newCommands = new ICommand[commands.length - 1];

			// Add it before other builders.

			System.arraycopy(commands, 1, newCommands, 0, commands.length - 1);

			desp.setBuildSpec(newCommands);
			project.setDescription(desp, null);
			// Confirm Builder Remove
		}
	}


	public static IProject getProject(final String text) {
		final IProject project = org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot().getProject(text);
		if (project.exists() && project.isOpen() && isRelevantProject(project)) {
			return project;
		}
		return null;

	}

	public static String computeBinFileLocation(final IFile originalFile) {
		IPath binPath = originalFile.getProject().getLocation().append(new Path(ResourceUtil.BIN_FOLDER + File.separator + originalFile.getProjectRelativePath()));
		return Pattern.compile(ResourceUtil.FILE_EXTENSION + "$").matcher(binPath.toOSString()).replaceFirst(ResourceUtil.HTML_EXTENSION);
	}

	public static IPath computeBinFileFulllPath(final IFile originalFile) {
		String replacedProjectRelativePath = Pattern.compile(ResourceUtil.FILE_EXTENSION + "$").matcher(originalFile.getProjectRelativePath().toOSString()).replaceFirst(ResourceUtil.HTML_EXTENSION);
		IPath binPath = originalFile.getProject().getFullPath().append(new Path(ResourceUtil.BIN_FOLDER)).append(replacedProjectRelativePath);
		return binPath;
	}

	public static void postProjectCreation(final IProjectDescription description) {
		PostProjectHandleExtensionManager manager = new PostProjectHandleExtensionManager(description);
		manager.executeProxyImplementations();
	}

	/**
	 * <p>
	 * Class that looks through extension registry to find present
	 * postproject Handle Implementations.
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
			final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
			final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
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

public static void createNewProject(final IProject newProject, final IProgressMonitor monitor, final String description) throws CoreException{
	IFolder folder = newProject.getFolder(ResourceUtil.SETTINGS_FOLDER);
	if (!folder.exists()) {
		folder.create(true, true, monitor);
	}
	IFile file = folder.getFile(ResourceUtil.PRIMARY_CONTENT_FILE);
	Category rootCategory = EditingUtil.getInstance().getObjectFromFile(file, InfomngmntPackage.eINSTANCE.getCategory(),true);
	rootCategory.setLabel(newProject.getName());
	rootCategory.setId(new UniversalUniqueIdentifier().toString());
	rootCategory.setDescription(description);
	EditingUtil.getInstance().saveObjectToResource(rootCategory);

	EditingDomain editingDomain = EditingUtil.getInstance().getNavigationEditingDomain();
	Command createCommand = CommandFactory.CREATE_ROOTCATEGORY(rootCategory, editingDomain);
	editingDomain.getCommandStack().execute(createCommand);
	ApplicationModelPool.getInstance().addListenerToCategory(rootCategory);

}

}
