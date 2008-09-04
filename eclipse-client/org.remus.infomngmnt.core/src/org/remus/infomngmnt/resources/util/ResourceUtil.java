package org.remus.infomngmnt.resources.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.remus.infomngmnt.core.builder.InformationBuilder;

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

	public static String computeBinFileLocation(IFile originalFile) {
		IPath binPath = originalFile.getProject().getLocation().append(new Path(ResourceUtil.BIN_FOLDER + File.separator + originalFile.getProjectRelativePath()));
		return Pattern.compile(ResourceUtil.FILE_EXTENSION + "$").matcher(binPath.toOSString()).replaceFirst(ResourceUtil.HTML_EXTENSION);
	}

	public static IPath computeBinFileFulllPath(IFile originalFile) {
		String replacedProjectRelativePath = Pattern.compile(ResourceUtil.FILE_EXTENSION + "$").matcher(originalFile.getProjectRelativePath().toOSString()).replaceFirst(ResourceUtil.HTML_EXTENSION);
		IPath binPath = originalFile.getProject().getFullPath().append(new Path(ResourceUtil.BIN_FOLDER)).append(replacedProjectRelativePath);
		return binPath;
	}

}
