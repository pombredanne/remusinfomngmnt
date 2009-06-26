/*******************************************************************************
 * Copyright (c) 2004, 2005 Elias Volanakis and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Elias Volanakis - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.image.gef;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.remus.infomngmnt.image.comments.ShapableInfoDelegate;

/**
 * EditPart used for Shape instances (more specific for EllipticalShape and
 * RectangularShape instances).
 * <p>
 * This edit part must implement the PropertyChangeListener interface, so it can
 * be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
class CommentEditPart extends AbstractGraphicalEditPart implements Adapter, IEditingDomainHolder {

	private EditingDomain editingDomain;

	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getCastedModel().addAdapter(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new CommentComponentEditPolicy());
		// allow the creation of connections and
		// and the reconnection of connections between Shape instances
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		IFigure f = createFigureForModel();
		f.setOpaque(true); // non-transparent figure
		return f;
	}

	/**
	 * Return a IFigure depending on the instance of the current model element.
	 * This allows this EditPart to be used for both sublasses of Shape.
	 */
	private IFigure createFigureForModel() {
		RectangleFigure figure2 = new RectangleFigure();
		figure2.setFill(false);
		figure2.setForegroundColor(ColorConstants.orange);
		figure2.setBorder(new SchemeBorder(SchemeBorder.SCHEMES.ETCHED));
		figure2.setToolTip(new Label(getCastedModel().getText()));
		return figure2;
	}

	/**
	 * Upon deactivation, detach from the model element as a property change
	 * listener.
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getCastedModel().removeAdapter(this);
		}
	}

	private ShapableInfoDelegate getCastedModel() {
		return (ShapableInfoDelegate) getModel();
	}

	@Override
	protected void unregisterModel() {
		getCastedModel().dispose();
		super.unregisterModel();
	}

	@Override
	protected void refreshVisuals() {
		// notify parent container of changed position & location
		// if this line is removed, the XYLayoutManager used by the parent
		// container
		// (the Figure of the ShapesDiagramEditPart), will not know the bounds
		// of this figure
		// and will not draw it correctly.
		Rectangle bounds = new Rectangle(getCastedModel().getLocation().x, getCastedModel()
				.getLocation().y, getCastedModel().getSize().width,
				getCastedModel().getSize().height);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
	}

	public Notifier getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAdapterForType(final Object type) {
		// TODO Auto-generated method stub
		return false;
	}

	public void notifyChanged(final Notification notification) {
		refreshVisuals();

	}

	@Override
	public void performRequest(final Request req) {
		if (req.getType() == RequestConstants.REQ_OPEN) {
			InputDialog inputDialog = new InputDialog(getViewer().getControl().getShell(),
					"Set comment", "Set a new message", getCastedModel().getText(), null) {
				@Override
				protected int getInputTextStyle() {
					return SWT.MULTI | SWT.BORDER | SWT.V_SCROLL;
				}

				@Override
				protected Control createDialogArea(final Composite parent) {
					Control createDialogArea = super.createDialogArea(parent);
					GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
					gd.minimumHeight = 50;
					getText().setLayoutData(gd);
					return createDialogArea;
				}
			};

			if (inputDialog.open() == IDialogConstants.OK_ID) {
				getCastedModel().setText(inputDialog.getValue());
				getFigure().setToolTip(new Label(getCastedModel().getText()));
			}
		}
		super.performRequest(req);
	}

	public void setTarget(final Notifier newTarget) {
		// TODO Auto-generated method stub

	}

	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	public void setEditingDomain(final EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}
}