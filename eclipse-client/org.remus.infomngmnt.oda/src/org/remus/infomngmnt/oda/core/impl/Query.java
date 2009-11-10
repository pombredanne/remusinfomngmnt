/*
 *************************************************************************
 * Copyright (c) 2009 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package org.remus.infomngmnt.oda.core.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.eclipse.datatools.connectivity.oda.IParameterMetaData;
import org.eclipse.datatools.connectivity.oda.IQuery;
import org.eclipse.datatools.connectivity.oda.IResultSet;
import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.datatools.connectivity.oda.SortSpec;
import org.eclipse.datatools.connectivity.oda.spec.QuerySpecification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.util.CollectionFilter;
import org.remus.infomngmnt.common.core.util.CollectionUtils;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.oda.core.QuerySerializer;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.oda.Column;
import org.remus.oda.Dataset;
import org.remus.oda.OdaFactory;
import org.remus.oda.Row;

/**
 * Implementation class of IQuery for an ODA runtime driver. <br>
 * For demo purpose, the auto-generated method stubs have hard-coded
 * implementation that returns a pre-defined set of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place.
 */
public class Query implements IQuery {
	private int m_maxRows;
	private String m_preparedText;

	private Dataset dataset;
	private final Properties connProperties;

	public Query(final Properties connProperties) {
		this.connProperties = connProperties;
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#prepare(java.lang.String)
	 */
	public void prepare(final String queryText) throws OdaException {
		if (queryText != null) {
			this.dataset = QuerySerializer.load(queryText, this.connProperties);
		} else {
			throw new OdaException("Query text must not be null");
		}
		// TODO Auto-generated method stub
		this.m_preparedText = queryText;
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setAppContext(java.lang
	 * .Object)
	 */
	public void setAppContext(final Object context) throws OdaException {
		// do nothing; assumes no support for pass-through context
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#close()
	 */
	public void close() throws OdaException {
		// TODO Auto-generated method stub
		this.m_preparedText = null;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#getMetaData()
	 */
	public IResultSetMetaData getMetaData() throws OdaException {
		return new ResultSetMetaData(this.dataset);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#executeQuery()
	 */
	public IResultSet executeQuery() throws OdaException {
		org.remus.oda.ResultSet buildResult = buildResult();
		IResultSet resultSet = new ResultSet(this.dataset, buildResult);
		resultSet.setMaxRows(getMaxRows());
		return resultSet;
	}

	private org.remus.oda.ResultSet buildResult() throws OdaException {
		org.remus.oda.ResultSet result = OdaFactory.eINSTANCE.createResultSet();
		final IInfoType infoTypeByType = InformationExtensionManager.getInstance()
				.getInfoTypeByType(this.dataset.getSelection().getInfoTypeId());
		if (infoTypeByType == null) {
			throw new OdaException(NLS.bind("Infotype\'\'{0}\'\' not installed", this.dataset
					.getSelection().getInfoTypeId()));
		}
		String path = this.dataset.getSelection().getPath();
		Category findCategory = CategoryUtil.findCategory(path, false);
		List<InformationUnitListItem> filter;
		if (findCategory == null) {
			InformationUnitListItem findItemByPath = InformationUtil.findItemByPath(path);
			if (findItemByPath == null) {
				throw new OdaException("Category or info-unit not found");
			}
			filter = Collections.singletonList(findItemByPath);
		} else {
			InformationUnitListItem[] allInfoUnitItems = CategoryUtil
					.getAllInfoUnitItems(findCategory);
			filter = CollectionUtils.filter(Arrays.asList(allInfoUnitItems),
					new CollectionFilter<InformationUnitListItem>() {

						public boolean select(final InformationUnitListItem item) {
							return item.getType().equals(infoTypeByType.getType());
						}
					});
		}
		for (InformationUnitListItem informationUnitListItem : filter) {
			InformationUnit unit = (InformationUnit) informationUnitListItem
					.getAdapter(InformationUnit.class);
			InformationStructureRead structureRead = InformationStructureRead.newSession(unit);
			if (this.dataset.getSelection().getElementSelector() != null
					&& this.dataset.getSelection().getElementSelector().trim().length() > 0) {
				EList<InformationUnit> dynamicList = structureRead.getDynamicList(this.dataset
						.getSelection().getElementSelector());
				for (InformationUnit informationUnit : dynamicList) {
					Row row = OdaFactory.eINSTANCE.createRow();
					InformationStructureRead listRead = InformationStructureRead.newSession(
							informationUnit, unit.getType());
					EList<Column> columns = this.dataset.getColumns();
					for (Column column : columns) {
						String name = column.getName();
						if (this.dataset.getSelection().getElementSelector().equals(
								listRead.getInContainedDynamicNode(name))) {
							row.getValue().add(listRead.getValueByNodeId(name));
						} else {
							row.getValue().add(structureRead.getValueByNodeId(name));
						}
					}
					result.getRows().add(row);
				}
			} else {
				Row row = OdaFactory.eINSTANCE.createRow();
				EList<Column> columns = this.dataset.getColumns();
				for (Column column : columns) {
					String name = column.getName();
					row.getValue().add(structureRead.getValueByNodeId(name));
				}
			}
		}
		return result;

	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setProperty(java.lang.String
	 * , java.lang.String)
	 */
	public void setProperty(final String name, final String value) throws OdaException {
		// do nothing; assumes no data set query property
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setMaxRows(int)
	 */
	public void setMaxRows(final int max) throws OdaException {
		this.m_maxRows = max;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#getMaxRows()
	 */
	public int getMaxRows() throws OdaException {
		return this.m_maxRows;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#clearInParameters()
	 */
	public void clearInParameters() throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setInt(java.lang.String,
	 * int)
	 */
	public void setInt(final String parameterName, final int value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setInt(int, int)
	 */
	public void setInt(final int parameterId, final int value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setDouble(java.lang.String,
	 * double)
	 */
	public void setDouble(final String parameterName, final double value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setDouble(int, double)
	 */
	public void setDouble(final int parameterId, final double value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setBigDecimal(java.lang
	 * .String, java.math.BigDecimal)
	 */
	public void setBigDecimal(final String parameterName, final BigDecimal value)
			throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setBigDecimal(int,
	 * java.math.BigDecimal)
	 */
	public void setBigDecimal(final int parameterId, final BigDecimal value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setString(java.lang.String,
	 * java.lang.String)
	 */
	public void setString(final String parameterName, final String value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setString(int,
	 * java.lang.String)
	 */
	public void setString(final int parameterId, final String value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setDate(java.lang.String,
	 * java.sql.Date)
	 */
	public void setDate(final String parameterName, final Date value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setDate(int,
	 * java.sql.Date)
	 */
	public void setDate(final int parameterId, final Date value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setTime(java.lang.String,
	 * java.sql.Time)
	 */
	public void setTime(final String parameterName, final Time value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setTime(int,
	 * java.sql.Time)
	 */
	public void setTime(final int parameterId, final Time value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setTimestamp(java.lang.
	 * String, java.sql.Timestamp)
	 */
	public void setTimestamp(final String parameterName, final Timestamp value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setTimestamp(int,
	 * java.sql.Timestamp)
	 */
	public void setTimestamp(final int parameterId, final Timestamp value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setBoolean(java.lang.String
	 * , boolean)
	 */
	public void setBoolean(final String parameterName, final boolean value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setBoolean(int,
	 * boolean)
	 */
	public void setBoolean(final int parameterId, final boolean value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setObject(java.lang.String,
	 * java.lang.Object)
	 */
	public void setObject(final String parameterName, final Object value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setObject(int,
	 * java.lang.Object)
	 */
	public void setObject(final int parameterId, final Object value) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setNull(java.lang.String)
	 */
	public void setNull(final String parameterName) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setNull(int)
	 */
	public void setNull(final int parameterId) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to input parameter
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#findInParameter(java.lang
	 * .String)
	 */
	public int findInParameter(final String parameterName) throws OdaException {
		// TODO Auto-generated method stub
		// only applies to named input parameter
		return 0;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#getParameterMetaData()
	 */
	public IParameterMetaData getParameterMetaData() throws OdaException {
		/*
		 * TODO Auto-generated method stub Replace with implementation to return
		 * an instance based on this prepared query.
		 */
		return new ParameterMetaData();
	}

	/*
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setSortSpec(org.eclipse
	 * .datatools.connectivity.oda.SortSpec)
	 */
	public void setSortSpec(final SortSpec sortBy) throws OdaException {
		// only applies to sorting, assumes not supported
		throw new UnsupportedOperationException();
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#getSortSpec()
	 */
	public SortSpec getSortSpec() throws OdaException {
		// only applies to sorting
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#setSpecification(org.eclipse
	 * .datatools.connectivity.oda.spec.QuerySpecification)
	 */
	@SuppressWarnings("restriction")
	public void setSpecification(final QuerySpecification querySpec) throws OdaException,
			UnsupportedOperationException {
		// assumes no support
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#getSpecification()
	 */
	@SuppressWarnings("restriction")
	public QuerySpecification getSpecification() {
		// assumes no support
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.IQuery#getEffectiveQueryText()
	 */
	public String getEffectiveQueryText() {
		// TODO Auto-generated method stub
		return this.m_preparedText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#cancel()
	 */
	public void cancel() throws OdaException, UnsupportedOperationException {
		// assumes unable to cancel while executing a query
		throw new UnsupportedOperationException();
	}

}
