/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Tom Seidel      - enhancements for general use   
 *******************************************************************************/
package org.remus.infomngmnt.ui.internal.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

import org.eclipse.jface.action.ToolBarManager;

/**
 * A <code>CompareViewerPane</code> is a convenience class which installs a
 * <code>CLabel</code> and a <code>Toolbar</code> in a <code>ViewForm</code>.
 * <P>
 * Double clicking onto the <code>CompareViewerPane</code>'s title bar maximizes
 * the <code>CompareViewerPane</code> to the size of an enclosing <code>Splitter</code>
 * (if there is one).
 * If more <code>Splitters</code> are nested maximizing walks up and
 * maximizes to the outermost <code>Splitter</code>.
 * 
 * @since 2.0
 */
public class CompareViewerPane extends ViewForm {
	
	ToolBarManager fToolBarManager;

	/**
	 * Constructs a new instance of this class given its parent
	 * and a style value describing its behavior and appearance.
	 *
	 * @param container a widget which will be the container of the new instance (cannot be null)
	 * @param style the style of widget to construct
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
	 * </ul>
	 * @exception org.eclipse.swt.SWTException <ul>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
	 * </ul>
	 */		
	public CompareViewerPane(Composite container, int style) {
		super(container, style);
		
		this.marginWidth= 0;
		this.marginHeight= 0;
		
		CLabel label= new CLabel(this, SWT.NONE) {
			public Point computeSize(int wHint, int hHint, boolean changed) {
				return super.computeSize(wHint, Math.max(24, hHint), changed);
			}
		};
		setTopLeft(label);
		
		MouseAdapter ml= new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				Control content= getContent();
				if (content != null && content.getBounds().contains(e.x, e.y))
					return;
				Control parent= getParent();
				if (parent instanceof Splitter)
					((Splitter)parent).setMaximizedControl(CompareViewerPane.this);
			}
		};	
				
		addMouseListener(ml);
		label.addMouseListener(ml);
		
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (CompareViewerPane.this.fToolBarManager != null) {
					CompareViewerPane.this.fToolBarManager.removeAll();
					CompareViewerPane.this.fToolBarManager.dispose();
					CompareViewerPane.this.fToolBarManager= null;
				}
			}
		});
	}
	
	/**
	 * Set the pane's title text.
	 * The value <code>null</code> clears it.
	 * 
	 * @param label the text to be displayed in the pane or null
	 */
	public void setText(String label) {
		CLabel cl= (CLabel) getTopLeft();
		if (cl != null)
			cl.setText(label);		
	}
	
	/**
	 * Set the pane's title Image.
	 * The value <code>null</code> clears it.
	 * 
	 * @param image the image to be displayed in the pane or null
	 */
	public void setImage(Image image) {
		CLabel cl= (CLabel) getTopLeft();
		if (cl != null)
			cl.setImage(image);
	}
	
	/**
	 * Returns a <code>ToolBarManager</code> if the given parent is a
	 * <code>CompareViewerPane</code> or <code>null</code> otherwise.
	 * 
	 * @param parent a <code>Composite</code> or <code>null</code>
	 * @return a <code>ToolBarManager</code> if the given parent is a <code>CompareViewerPane</code> otherwise <code>null</code>
	 */
	public static ToolBarManager getToolBarManager(Composite parent) {
		if (parent instanceof CompareViewerPane) {
			CompareViewerPane pane= (CompareViewerPane) parent;
			return pane.getToolBarManager();
		}
		return null;
	}

	/**
	 * Clears tool items in the <code>CompareViewerPane</code>'s control bar.
	 * 
	 * @param parent a <code>Composite</code> or <code>null</code>
	 */
	public static void clearToolBar(Composite parent) {
		ToolBarManager tbm= getToolBarManager(parent);
		if (tbm != null) {
			tbm.removeAll();
			tbm.update(true);
		}
	}
	
	//---- private stuff
	
	private ToolBarManager getToolBarManager() {
		if (this.fToolBarManager == null) {
			ToolBar tb= new ToolBar(this, SWT.FLAT);
			setTopCenter(tb);
			this.fToolBarManager= new ToolBarManager(tb);
		}
		return this.fToolBarManager;
	}
}
