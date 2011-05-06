/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.emitter.uixml;

import java.util.Map;

import org.eclipse.core.resources.IFile;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UiXml {

	private String xmlString;

	private Map<String, IFile> binaryReferences;

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

	public Map<String, IFile> getBinaryReferences() {
		return binaryReferences;
	}

	public void setBinaryReferences(Map<String, IFile> binaryReferences) {
		this.binaryReferences = binaryReferences;
	}

}
