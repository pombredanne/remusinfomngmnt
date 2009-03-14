/*******************************************************************************
 * Copyright (c) 2007 Tom Seidel, Spirit Link GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.richtext.actions;

import java.util.Properties;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.spiritlink.richhtml4eclipse.widgets.AllActionConstants;
import de.spiritlink.richhtml4eclipse.widgets.ComposerStatus;
import de.spiritlink.richhtml4eclipse.widgets.EventConstants;
import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;
import de.spiritlink.richhtml4eclipse.widgets.JavaScriptCommands;
import de.spiritlink.richhtml4eclipse.widgets.PropertyConstants;

/**
 * 
 * @author Tom Seidel <tom.seidel@spiritlink.de>
 * 
 */
public class InsertRowBeforeAction extends Action implements Listener{
    private HtmlComposer composer = null;

    public InsertRowBeforeAction(HtmlComposer composer) {
        super("", IAction.AS_PUSH_BUTTON);
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("de.spiritlink.richhtml4eclipse", "tiny_mce/jscripts/tiny_mce/plugins/table/images/table_insert_row_before.gif"));
        this.composer = composer;
        this.composer.addListener(EventConstants.INSERT_ROW_BEFORE, this);
    }



    /* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        this.composer.execute(JavaScriptCommands.INSERT_ROW_BEFORE);
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {
        Properties props = (Properties) event.data;
        if (ComposerStatus.NORMAL.equals(props.getProperty(PropertyConstants.STATUS))) {
            setEnabled(true);
        } else if (ComposerStatus.DISABLED.equals(props.getProperty(PropertyConstants.STATUS))) {
            setEnabled(false);
        } else if (event.type == EventConstants.ALL && AllActionConstants.RESET_ALL.equals(props.getProperty(PropertyConstants.COMMAND))) {
            setEnabled(false);
        }
    }

}
