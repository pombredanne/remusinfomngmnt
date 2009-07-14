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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.comments.ShapableInfoDelegate;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * EditPart for the a ShapesDiagram instance.
 * <p>
 * This edit part server as the main diagram container, the white area where
 * everything else is in. Also responsible for the container's layout (the way
 * the container rearanges is contents) and the container's capabilities (edit
 * policies).
 * </p>
 * <p>
 * This edit part must implement the PropertyChangeListener interface, so it can
 * be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
class ImageEditPart extends AbstractGraphicalEditPart implements Adapter, IEditingDomainHolder {

	private EditingDomain editingDomain;
	private long width;
	private long height;

	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			InformationUnit unit = InformationUtil.getChildByType(getCastedModel(),
					ImagePlugin.NODE_NAME_LINKS);
			unit.eAdapters().add(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model
		// elements
		// and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ShapesXYLayoutEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		this.width = InformationUtil.getChildByType(getCastedModel(), ImagePlugin.NODE_NAME_WIDTH)
				.getLongValue();
		this.height = InformationUtil
				.getChildByType(getCastedModel(), ImagePlugin.NODE_NAME_HEIGHT).getLongValue();
		try {
			InputStream bis = ((IResource) getCastedModel().getAdapter(IFile.class)).getProject()
					.getFolder(ResourceUtil.BINARY_FOLDER).getFile(
							getCastedModel().getBinaryReferences().get(0).getProjectRelativePath())
					.getContents();
			ImageData imageData = new ImageData(bis).scaledTo((int) this.width, (int) this.height);
			final Image image = ImageDescriptor.createFromImageData(imageData).createImage();
			ImageFigure figure = new ImageFigure();
			bis.close();
			figure.setImage(image);
			figure.setAlignment(PositionConstants.NORTH_WEST);
			figure.setBorder(new MarginBorder(3));
			figure.setLayoutManager(new XYLayout());
			figure.setOpaque(true);
			return figure;
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Upon deactivation, detach from the model element as a property change
	 * listener.
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			InformationUnit unit = InformationUtil.getChildByType(getCastedModel(),
					ImagePlugin.NODE_NAME_LINKS);
			unit.eAdapters().add(this);
		}
	}

	private InformationUnit getCastedModel() {
		return (InformationUnit) getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List getModelChildren() {
		List<ShapableInfoDelegate> returnValue = new ArrayList<ShapableInfoDelegate>();
		InformationUnit unit = InformationUtil.getChildByType(getCastedModel(),
				ImagePlugin.NODE_NAME_LINKS);
		EList<InformationUnit> childValues = unit.getChildValues(); // return a
		// list of
		// shapes
		for (InformationUnit informationUnit : childValues) {
			returnValue.add(new ShapableInfoDelegate(informationUnit, new java.awt.Dimension(
					(int) this.width, (int) this.height), this.editingDomain));
		}
		return returnValue;
	}

	/**
	 * EditPolicy for the Figure used by this edit part. Children of
	 * XYLayoutEditPolicy can be used in Figures with XYLayout.
	 * 
	 * @author Elias Volanakis
	 */
	private static class ShapesXYLayoutEditPolicy extends XYLayoutEditPolicy {

		/*
		 * (non-Javadoc)
		 * 
		 * @seeConstrainedLayoutEditPolicy#createChangeConstraintCommand(
		 * ChangeBoundsRequest, EditPart, Object)
		 */
		@Override
		protected Command createChangeConstraintCommand(final ChangeBoundsRequest request,
				final EditPart child, final Object constraint) {
			if (child instanceof CommentEditPart && constraint instanceof Rectangle) {
				// return a command that can move and/or resize a Shape
				return new ShapeSetConstraintCommand((ShapableInfoDelegate) child.getModel(),
						request, (Rectangle) constraint);
			}
			return super.createChangeConstraintCommand(request, child, constraint);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * ConstrainedLayoutEditPolicy#createChangeConstraintCommand(EditPart,
		 * Object)
		 */
		@Override
		protected Command createChangeConstraintCommand(final EditPart child,
				final Object constraint) {
			// not used in this example
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see LayoutEditPolicy#getCreateCommand(CreateRequest)
		 */
		@Override
		protected Command getCreateCommand(final CreateRequest request) {
			Object childClass = request.getNewObjectType();
			// return a command that can add a Shape to a ShapesDiagram
			return new CommentCreateCommand((ShapableInfoDelegate) request.getNewObject(),
					(InformationUnit) getHost().getModel(), (Rectangle) getConstraintFor(request));

		}

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
		refreshChildren();

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