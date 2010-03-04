package org.remus.infomngmnt.ui.rules.extension;

import org.eclipse.core.runtime.CoreException;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IGroovyScript {

	String getId();

	String getScript() throws CoreException;

	IGroovyBinding getBindingClass() throws CoreException;

}
