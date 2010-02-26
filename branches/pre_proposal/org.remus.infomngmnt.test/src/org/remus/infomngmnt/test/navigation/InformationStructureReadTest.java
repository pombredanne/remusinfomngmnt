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

package org.remus.infomngmnt.test.navigation;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.streams.StreamUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.mail.ContentType;
import org.remus.infomngmnt.mail.MailActivator;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.test.AbstractTest;
import org.remus.infomngmnt.test.Activator;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationStructureReadTest extends AbstractTest {

	public static final String LABEL = "LABEL"; //$NON-NLS-1$

	public static final String CC1 = "CC!"; //$NON-NLS-1$
	public static final String CC2 = "CC2"; //$NON-NLS-1$
	public static String content = null;

	private InformationUnit newInformationUnit;

	@Before
	public void createInfoUnit() throws Exception {
		InformationStructureEdit edit = InformationStructureEdit
				.newSession(MailActivator.INFO_TYPE_ID);
		this.newInformationUnit = edit.newInformationUnit();
		this.newInformationUnit.setLabel(LABEL);
		InformationUnit cc1 = edit.createSubType(MailActivator.NODE_NAME_CC, CC1);
		InformationUnit cc2 = edit.createSubType(MailActivator.NODE_NAME_CC, CC2);
		edit.addDynamicNode(this.newInformationUnit, cc1, null);
		edit.addDynamicNode(this.newInformationUnit, cc2, null);
		edit.setValue(this.newInformationUnit, MailActivator.NODE_NAME_CONTENT_TYPE,
				ContentType.PLAIN.getKey());

		InputStream openStream = FileLocator.openStream(Platform.getBundle(Activator.PLUGIN_ID),
				new Path("dummyfiles/mailcontent/content.txt"), false);
		content = StreamUtil.convertStreamToString(openStream);
		edit.setValue(this.newInformationUnit, MailActivator.NODE_NAME_CONTENT, content);

		StreamCloser.closeStreams(openStream);

		CompoundCommand createINFOTYPE = CommandFactory.CREATE_INFOTYPE(this.newInformationUnit,
				CategoryUtil.findCategory("Inbox", false), new NullProgressMonitor());
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().execute(
				createINFOTYPE);
		IFile file = (IFile) this.newInformationUnit.getAdapter(IFile.class);

		this.newInformationUnit = EditingUtil.getInstance().getObjectFromFile(file,
				InfomngmntPackage.Literals.INFORMATION_UNIT);

		InformationUnit createSubType = edit.createSubType(MailActivator.NODE_NAME_EMBEDDED, null);
		IFile createTempFile = ResourceUtil.createTempFile("png");
		InputStream openStream2 = FileLocator.openStream(Platform.getBundle(Activator.PLUGIN_ID),
				new Path("dummyfiles/mailembedded/rim_scaled_perspective.png"), false);
		createTempFile.setContents(openStream2, true, false, new NullProgressMonitor());
		edit.addDynamicNode(this.newInformationUnit, createSubType, null, Collections.singletonMap(
				MailActivator.NODE_NAME_EMBEDDED, createTempFile));
		EditingUtil.getInstance().saveObjectToResource(file, this.newInformationUnit);

	}

	@Test
	public void readSimpleValue() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		Object valueByNodeId = read.getValueByNodeId(MailActivator.NODE_NAME_CONTENT);
		assertEquals(valueByNodeId, content);
	}

	@Test
	public void readLabel() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		Object valueByNodeId = read.getValueByNodeId("@label");
		assertEquals(valueByNodeId, LABEL);
	}

	@Test
	public void readDynmaic() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		EList<InformationUnit> dynamicList = read.getDynamicList(MailActivator.NODE_NAME_CCS);
		assertEquals(2, dynamicList.size());
	}

	@Test
	public void readDynmaicWithInnerScope() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		InformationUnit childByNodeId = read.getChildByNodeId(MailActivator.NODE_NAME_CCS);
		InformationStructureRead innerScope = InformationStructureRead.newSession(childByNodeId,
				MailActivator.INFO_TYPE_ID);
		EList<InformationUnit> dynamicList = innerScope.getDynamicList(MailActivator.NODE_NAME_CCS);
		assertEquals(2, dynamicList.size());
	}

	@Test
	public void readDynamicSimpleValue() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		EList<InformationUnit> dynamicList = read.getDynamicList(MailActivator.NODE_NAME_CCS);
		InformationStructureRead firstElementRead = InformationStructureRead.newSession(dynamicList
				.get(0), MailActivator.INFO_TYPE_ID);
		Object valueByNodeId = firstElementRead.getValueByNodeId(MailActivator.NODE_NAME_CC);
		assertEquals(CC1, valueByNodeId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void readDynamicItemOnGlobalSession() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		read.getValueByNodeId(MailActivator.NODE_NAME_CC);
	}

	@Test
	public void readAllPossibleDynamicReferences() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		List<String> nodeIdsWithBinaryReferences = read.getNodeIdsWithBinaryReferences(null);
		assertEquals(2, nodeIdsWithBinaryReferences.size());
	}

	@Test
	public void readAllPossibleDynamicReferencesScoped() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		List<String> nodeIdsWithBinaryReferences = read
				.getNodeIdsWithBinaryReferences(MailActivator.NODE_NAME_EMBEDDEDS);
		assertEquals(1, nodeIdsWithBinaryReferences.size());
	}

	@Test
	public void readAllPossibleDynamicReferencesScoped2() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		List<String> nodeIdsWithBinaryReferences = read
				.getNodeIdsWithBinaryReferences(MailActivator.NODE_NAME_CCS);
		assertEquals(0, nodeIdsWithBinaryReferences.size());
	}

	@Test
	public void readBinaryReference() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		List<BinaryReference> binaryReferences = read.getBinaryReferences(
				MailActivator.NODE_NAME_EMBEDDEDS, true);
		assertEquals(1, binaryReferences.size());
	}

	@Test
	public void readBinaryReferenceValue() {
		InformationStructureRead read = InformationStructureRead
				.newSession(this.newInformationUnit);
		List<BinaryReference> binaryReferences = read.getBinaryReferences(
				MailActivator.NODE_NAME_EMBEDDEDS, true);
		BinaryReference binaryReference = binaryReferences.get(0);
		assertNotNull(binaryReference);
		assertNotNull(binaryReference.getId());
		assertNotNull(binaryReference.getProjectRelativePath());
		assertTrue(binaryReference.getProjectRelativePath().endsWith(".png"));
	}

	@After
	public void deleteInfoUnit() {
		InformationUnitListItem adapter = (InformationUnitListItem) this.newInformationUnit
				.getAdapter(InformationUnitListItem.class);

		AdapterFactoryEditingDomain domain = EditingUtil.getInstance().getNavigationEditingDomain();
		Command command = CommandFactory
				.DELETE_INFOUNIT(Collections.singletonList(adapter), domain);
		domain.getCommandStack().execute(command);

	}

}
