/*
 *************************************************************************
 * Copyright (c) 2009 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package org.remus.infomngmnt.oda.core.impl;

import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.InformationStructureItem;
import org.remus.infomngmnt.InformationStructureType;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.oda.Dataset;

/**
 * Implementation class of IResultSetMetaData for an ODA runtime driver. <br>
 * For demo purpose, the auto-generated method stubs have hard-coded
 * implementation that returns a pre-defined set of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place.
 */
public class ResultSetMetaData implements IResultSetMetaData {

	private final Dataset dataset;

	public ResultSetMetaData(final Dataset dataset) {
		this.dataset = dataset;

	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnCount
	 * ()
	 */
	public int getColumnCount() throws OdaException {
		return this.dataset.getColumns().size();
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnName
	 * (int)
	 */
	public String getColumnName(final int index) throws OdaException {
		return this.dataset.getColumns().get(index - 1).getName();
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnLabel
	 * (int)
	 */
	public String getColumnLabel(final int index) throws OdaException {
		return getColumnName(index); // default
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnType
	 * (int)
	 */
	public int getColumnType(final int index) throws OdaException {
		String infoTypeId = this.dataset.getSelection().getInfoTypeId();
		IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
				infoTypeId);
		if (infoTypeByType == null) {
			throw new OdaException(NLS.bind("Info-Type {0} not installed", infoTypeId));
		}
		InformationStructureDefinition structureDefinition = infoTypeByType
				.getStructureDefinition();
		String columnName = getColumnName(index);
		if (columnName.startsWith(InformationStructureRead.ATTRIBUTE_ACCESSOR)) {
			return InformationStructureType.STRING_VALUE;
		}
		EObject eObject = structureDefinition.eResource().getEObject(columnName);
		if (eObject instanceof InformationStructureItem) {
			return ((InformationStructureItem) eObject).getType().getValue();
		}
		return -1;
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnTypeName
	 * (int)
	 */
	public String getColumnTypeName(final int index) throws OdaException {
		int nativeTypeCode = getColumnType(index);
		return Driver.getNativeDataTypeName(nativeTypeCode);
	}

	/*
	 * @seeorg.eclipse.datatools.connectivity.oda.IResultSetMetaData#
	 * getColumnDisplayLength(int)
	 */
	public int getColumnDisplayLength(final int index) throws OdaException {
		return getColumnName(index).length();
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getPrecision
	 * (int)
	 */
	public int getPrecision(final int index) throws OdaException {
		return -1;
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getScale(int)
	 */
	public int getScale(final int index) throws OdaException {
		return -1;
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IResultSetMetaData#isNullable(int)
	 */
	public int isNullable(final int index) throws OdaException {
		return IResultSetMetaData.columnNullableUnknown;
	}

}