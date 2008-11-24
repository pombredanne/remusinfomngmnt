/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.core.rules;

import java.net.URL;

import org.eclipse.swt.graphics.Image;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RuleProcessorInput {

	private String source;

	private String ruleSet;

	private String textTransferValue;

	private String htmlTransferValue;

	private String rtfTransferValue;

	private Image imageTransferData;

	private URL urlTransferData;

	private String[] fileTransferData;

	private Object additionalFileTransferData;

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTextTransferValue() {
		return this.textTransferValue;
	}

	public void setTextTransferValue(String textTransferValue) {
		this.textTransferValue = textTransferValue;
	}

	public String getHtmlTransferValue() {
		return this.htmlTransferValue;
	}

	public void setHtmlTransferValue(String htmlTransferValue) {
		this.htmlTransferValue = htmlTransferValue;
	}

	public String getRtfTransferValue() {
		return this.rtfTransferValue;
	}

	public void setRtfTransferValue(String rtfTransferValue) {
		this.rtfTransferValue = rtfTransferValue;
	}

	public Image getImageTransferData() {
		return this.imageTransferData;
	}

	public void setImageTransferData(Image imageTransferData) {
		this.imageTransferData = imageTransferData;
	}

	public URL getUrlTransferData() {
		return this.urlTransferData;
	}

	public void setUrlTransferData(URL urlTransferData) {
		this.urlTransferData = urlTransferData;
	}

	public String[] getFileTransferData() {
		return this.fileTransferData;
	}

	public void setFileTransferData(String[] fileTransferData) {
		this.fileTransferData = fileTransferData;
	}

	public Object getAdditionalFileTransferData() {
		return this.additionalFileTransferData;
	}

	public void setAdditionalFileTransferData(Object additionalFileTransferData) {
		this.additionalFileTransferData = additionalFileTransferData;
	}

	public String getRuleSet() {
		return this.ruleSet;
	}

	public void setRuleSet(String ruleSet) {
		this.ruleSet = ruleSet;
	}

}
