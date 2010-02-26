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

package org.remus.infomngmnt.image.comments;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ShapableInfoDelegate extends AdapterImpl {

	public static final String TYPE_DIMENSIONWIDTH = "dim_width"; //$NON-NLS-1$
	public static final String TYPE_DIMENSIONHEIGHT = "dim_height"; //$NON-NLS-1$
	public static final String LOCATION_X = "loc_x"; //$NON-NLS-1$
	public static final String LOCATION_Y = "loc_y"; //$NON-NLS-1$
	public static final String TEXT = "text"; //$NON-NLS-1$
	private final List<Adapter> adapter;

	InformationUnit origInfoObject;
	private final Dimension imageDimensions;
	private final EditingDomain domain;

	public InformationUnit getOrigInfoObject() {
		return this.origInfoObject;
	}

	public ShapableInfoDelegate(final InformationUnit unit, final Dimension imageDimensions,
			final EditingDomain domain) {
		this.origInfoObject = unit;
		this.imageDimensions = imageDimensions;
		this.domain = domain;
		this.adapter = new ArrayList<Adapter>();
		createSubNodeOnDemand(TYPE_DIMENSIONHEIGHT).eAdapters().add(this);
		createSubNodeOnDemand(TYPE_DIMENSIONHEIGHT).eAdapters().add(this);
		createSubNodeOnDemand(LOCATION_X).eAdapters().add(this);
		createSubNodeOnDemand(LOCATION_Y).eAdapters().add(this);
		createSubNodeOnDemand(TEXT).eAdapters().add(this);
	}

	public Dimension getSize() {
		InformationUnit diwWidth = createSubNodeOnDemand(TYPE_DIMENSIONWIDTH);
		InformationUnit diwHeight = createSubNodeOnDemand(TYPE_DIMENSIONHEIGHT);
		Math.round((double) this.imageDimensions.width * (int) diwWidth.getLongValue() / 1000);
		return new Dimension((int) Math.round((double) this.imageDimensions.width
				* (int) diwWidth.getLongValue() / 1000),
				(int) Math.round((double) this.imageDimensions.height
						* (int) diwHeight.getLongValue() / 1000));
	}

	public String getText() {
		InformationUnit text = createSubNodeOnDemand(TEXT);
		return text.getStringValue();
	}

	public void setText(final String text) {
		InformationUnit textUnit = createSubNodeOnDemand(TEXT);
		SetCommand setCommand = (SetCommand) SetCommand.create(this.domain, textUnit,
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE, text);
		setCommand.setDescription("Setting comment");
		this.domain.getCommandStack().execute(setCommand);
	}

	public Point getLocation() {
		InformationUnit locX = createSubNodeOnDemand(LOCATION_X);
		InformationUnit locY = createSubNodeOnDemand(LOCATION_Y);
		return new Point((int) Math.round((double) this.imageDimensions.width
				* (int) locX.getLongValue() / 1000), (int) Math
				.round((double) this.imageDimensions.height * (int) locY.getLongValue() / 1000));
	}

	public void setSize(final Dimension size) {
		InformationUnit diwWidth = createSubNodeOnDemand(TYPE_DIMENSIONWIDTH);
		InformationUnit diwHeight = createSubNodeOnDemand(TYPE_DIMENSIONHEIGHT);
		CompoundCommand cc = new CompoundCommand();
		cc.append(SetCommand.create(this.domain, diwWidth,
				InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE, Math
						.round((double) size.width / this.imageDimensions.width * 1000)));
		cc.append(SetCommand.create(this.domain, diwHeight,
				InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE, Math
						.round((double) size.height / this.imageDimensions.height * 1000)));
		cc.setDescription("Set size");
		this.domain.getCommandStack().execute(cc);
	}

	public void setLocation(final Point location) {
		InformationUnit locX = createSubNodeOnDemand(LOCATION_X);
		InformationUnit locY = createSubNodeOnDemand(LOCATION_Y);
		CompoundCommand cc = new CompoundCommand();
		cc.append(SetCommand.create(this.domain, locX,
				InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE, Math
						.round((double) location.x / this.imageDimensions.width * 1000)));
		cc.append(SetCommand.create(this.domain, locY,
				InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE, Math
						.round((double) location.y / this.imageDimensions.height * 1000)));
		cc.setDescription("Set location");
		this.domain.getCommandStack().execute(cc);
	}

	private InformationUnit createSubNodeOnDemand(final String type) {
		InformationUnit unit = InformationUtil.getChildByType(this.origInfoObject, type);
		if (unit == null) {
			unit = InfomngmntFactory.eINSTANCE.createInformationUnit();
			unit.setType(type);
			this.origInfoObject.getChildValues().add(unit);
		}
		return unit;
	}

	public void addAdapter(final Adapter newadapter) {
		this.adapter.add(newadapter);
	}

	public void removeAdapter(final Adapter adapter) {
		this.adapter.remove(adapter);
	}

	@Override
	public void notifyChanged(final Notification msg) {
		for (Adapter currentAdapter : this.adapter) {
			currentAdapter.notifyChanged(msg);
		}
	}

	public void dispose() {
		createSubNodeOnDemand(TYPE_DIMENSIONHEIGHT).eAdapters().remove(this);
		createSubNodeOnDemand(TYPE_DIMENSIONHEIGHT).eAdapters().remove(this);
		createSubNodeOnDemand(LOCATION_X).eAdapters().remove(this);
		createSubNodeOnDemand(LOCATION_Y).eAdapters().remove(this);
		createSubNodeOnDemand(TEXT).eAdapters().remove(this);
	}

}
