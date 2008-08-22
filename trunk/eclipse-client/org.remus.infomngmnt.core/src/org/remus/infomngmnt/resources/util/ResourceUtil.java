package org.remus.infomngmnt.resources.util;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

public class ResourceUtil {

	public static final String BUILDER_NAME = "net.remus.infobroker.resources.remus_infoBuilder";
	public static final String FILE_EXTENSION = "infobroker";
	public static final String PRIMARY_CONTENT_FILE = "primaryContent.info"; //$NON-NLS-1$
	public static final String SETTINGS_FOLDER = ".settings"; //$NON-NLS-1$
	public static final String BIN_FOLDER = "bin"; //$NON-NLS-1$
	private static final String HTML_EXTENSION = "html";

	public static boolean isRelevantProject(final IProject project) {
		try {
			final ICommand[] buildSpec = project.getDescription()
			.getBuildSpec();
			for (final ICommand command : buildSpec) {
				if (BUILDER_NAME.equals(command.getBuilderName())) {
					return true;
				}
			}
		} catch (final CoreException e) {
			// do nothing
		}
		return false;
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

	// public static CreateFileOperation getFileOperation

}
