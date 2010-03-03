package org.remus.infomngmnt.ui.desktop.services;

import java.util.Collection;

import org.remus.infomngmnt.ui.desktop.extension.IToolbarContribution;

public interface IToolbarContributionService {

	Collection<IToolbarContribution> getAllContributions();

	IToolbarContribution getContributionById(final String id);

}