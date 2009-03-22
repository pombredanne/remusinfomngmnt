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
package org.remus.infomngmnt.common.ui.richtext.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;
import de.spiritlink.richhtml4eclipse.widgets.JavaScriptCommands;

/**
 * @author Tom Seidel <tom.seidel@spiritlink.de>
 * 
 */
public class InsertNonBreakingWhitespace extends Action {

    private HtmlComposer composer = null;

    public InsertNonBreakingWhitespace(HtmlComposer composer) {
        super("", IAction.AS_PUSH_BUTTON); //$NON-NLS-1$
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("de.spiritlink.richhtml4eclipse", //$NON-NLS-1$
        "tiny_mce/jscripts/tiny_mce/plugins/nonbreaking/images/nonbreaking.gif")); //$NON-NLS-1$
        this.composer = composer;
       

    }



    /* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        this.composer.execute(JavaScriptCommands.INSERT_NBSP);
        
    }
    
   
}
