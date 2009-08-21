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

package org.remus.infomngmnt.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import org.remus.infomngmnt.test.navigation.InformationStructureReadTest;
import org.remus.infomngmnt.test.navigation.SingleItemNavigationTest;
import org.remus.infomngmnt.test.remote.connector.FolderConnectorTest;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
@RunWith(Suite.class)
@SuiteClasses( { SingleItemNavigationTest.class, InformationStructureReadTest.class,
		FolderConnectorTest.class })
// @SuiteClasses( { FolderConnectorTest.class })
public class TestSuite {

}
