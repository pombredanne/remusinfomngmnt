/*******************************************************************************
 * Copyright (c) 2006 Tom Seidel, Spirit Link GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.common.ui.richtext.actions;

import java.util.Properties;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.spiritlink.richhtml4eclipse.widgets.AllActionConstants;
import de.spiritlink.richhtml4eclipse.widgets.EventConstants;
import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;
import de.spiritlink.richhtml4eclipse.widgets.JavaScriptCommands;
import de.spiritlink.richhtml4eclipse.widgets.PropertyConstants;

/**
 * 
 * @author Tom Seidel <tom.seidel@spiritlink.de>
 * 
 */
public class GetHtmlAction extends Action implements Listener {
    
    private HtmlComposer composer = null;
    private Text target;
    
    
    public GetHtmlAction(HtmlComposer composer, Text target) {
        super("", IAction.AS_PUSH_BUTTON);
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("de.spiritlink.richhtml4eclipse", "tiny_mce/jscripts/tiny_mce/themes/advanced/images/code.gif"));
        this.composer = composer;
        this.composer.addListener(EventConstants.ALL, this);
        this.target = target;
    }
    
   
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        this.composer.execute(JavaScriptCommands.GET_HTML);
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {
        Properties evtProps = (Properties) event.data;
        if (evtProps.getProperty(PropertyConstants.COMMAND).equals(AllActionConstants.GET_HTML)) {
            this.target.setText(evtProps.getProperty(PropertyConstants.VALUE) == null ? "" : evtProps.getProperty(PropertyConstants.VALUE)); //$NON-NLS-1$
            
        }
    }

}