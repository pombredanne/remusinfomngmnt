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

package org.remus.infomngmnt.eclipsemarketplace.api.beans;

import java.util.Date;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MarketPlaceElement {

	private int id;

	private String title;

	private Type type;

	private String owner;

	private int favorited;

	private String body;

	private Date created;

	private Date changed;

	private boolean foundationMember;

	private String url;

	private String image;

	private String version;

	private String license;

	private String company;

	private String status;

	private String eclipseversion;

	private String supportUrl;

	private String updateUrl;

	/**
	 * @return the id
	 */
	public final int getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public final void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * @return the type
	 */
	public final Type getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public final void setType(final Type type) {
		this.type = type;
	}

	/**
	 * @return the owner
	 */
	public final String getOwner() {
		return this.owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public final void setOwner(final String owner) {
		this.owner = owner;
	}

	/**
	 * @return the favorited
	 */
	public final int getFavorited() {
		return this.favorited;
	}

	/**
	 * @param favorited
	 *            the favorited to set
	 */
	public final void setFavorited(final int favorited) {
		this.favorited = favorited;
	}

	/**
	 * @return the body
	 */
	public final String getBody() {
		return this.body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public final void setBody(final String body) {
		this.body = body;
	}

	/**
	 * @return the created
	 */
	public final Date getCreated() {
		return this.created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public final void setCreated(final Date created) {
		this.created = created;
	}

	/**
	 * @return the changed
	 */
	public final Date getChanged() {
		return this.changed;
	}

	/**
	 * @param changed
	 *            the changed to set
	 */
	public final void setChanged(final Date changed) {
		this.changed = changed;
	}

	/**
	 * @return the foundationMember
	 */
	public final boolean isFoundationMember() {
		return this.foundationMember;
	}

	/**
	 * @param foundationMember
	 *            the foundationMember to set
	 */
	public final void setFoundationMember(final boolean foundationMember) {
		this.foundationMember = foundationMember;
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the image
	 */
	public final String getImage() {
		return this.image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public final void setImage(final String image) {
		this.image = image;
	}

	/**
	 * @return the version
	 */
	public final String getVersion() {
		return this.version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public final void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * @return the license
	 */
	public final String getLicense() {
		return this.license;
	}

	/**
	 * @param license
	 *            the license to set
	 */
	public final void setLicense(final String license) {
		this.license = license;
	}

	/**
	 * @return the company
	 */
	public final String getCompany() {
		return this.company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public final void setCompany(final String company) {
		this.company = company;
	}

	/**
	 * @return the status
	 */
	public final String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public final void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * @return the eclipseversion
	 */
	public final String getEclipseversion() {
		return this.eclipseversion;
	}

	/**
	 * @param eclipseversion
	 *            the eclipseversion to set
	 */
	public final void setEclipseversion(final String eclipseversion) {
		this.eclipseversion = eclipseversion;
	}

	/**
	 * @return the supportUrl
	 */
	public final String getSupportUrl() {
		return this.supportUrl;
	}

	/**
	 * @param supportUrl
	 *            the supportUrl to set
	 */
	public final void setSupportUrl(final String supportUrl) {
		this.supportUrl = supportUrl;
	}

	/**
	 * @return the updateUrl
	 */
	public final String getUpdateUrl() {
		return this.updateUrl;
	}

	/**
	 * @param updateUrl
	 *            the updateUrl to set
	 */
	public final void setUpdateUrl(final String updateUrl) {
		this.updateUrl = updateUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MarketPlaceElement [body=" + this.body + ", changed=" + this.changed + ", company="
				+ this.company + ", created=" + this.created + ", eclipseversion="
				+ this.eclipseversion + ", favorited=" + this.favorited + ", foundationMember="
				+ this.foundationMember + ", id=" + this.id + ", image=" + this.image
				+ ", license=" + this.license + ", owner=" + this.owner + ", status=" + this.status
				+ ", supportUrl=" + this.supportUrl + ", title=" + this.title + ", type="
				+ this.type + ", updateUrl=" + this.updateUrl + ", url=" + this.url + ", version="
				+ this.version + "]";
	}

}
