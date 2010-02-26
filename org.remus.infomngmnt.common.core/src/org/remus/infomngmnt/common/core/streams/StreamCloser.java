/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.common.core.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class StreamCloser {

	public static void closeStreams(final InputStream... streams) {
		for (InputStream inputStream : streams) {
			try {
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			} catch (IOException e) {
				// do nothing.
			}
		}
	}

}
