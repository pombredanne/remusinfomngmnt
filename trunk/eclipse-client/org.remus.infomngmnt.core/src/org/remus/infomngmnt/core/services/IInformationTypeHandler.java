package org.remus.infomngmnt.core.services;

import java.util.Collection;

import org.remus.infomngmnt.core.extension.IInfoType;

public interface IInformationTypeHandler {

	IInfoType getInfoTypeByType(final String type);

	Collection<IInfoType> getTypes();

}