package org.remus.infomngmnt.ui.editors.outline;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import org.remus.infomngmnt.InformationUnit;

public interface IOutlineSection {

	void createControl(ScrolledForm form, FormToolkit toolkit);

	void bindValuesToUi(EditingDomain domain, InformationUnit infoUnit);

	void dispose();

	void disposeModel();

	int getSortRanking();

}
