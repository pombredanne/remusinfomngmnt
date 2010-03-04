package org.remus.infomngmnt.ui.editors.services;

import java.util.Collection;
import java.util.List;

import org.remus.infomngmnt.ui.editors.editpage.IEditPage;
import org.remus.infomngmnt.ui.editors.editpage.ISourcePage;
import org.remus.infomngmnt.ui.editors.outline.IOutlineSection;

public interface IEditorExtensionService {

	List<IEditPage> getEditPageByType(final String type);

	List<ISourcePage> getSourcePageByType(final String type);

	List<String> getCommandIdsByTypeId(final String type);

	Collection<IOutlineSection> getOutlineSections();

	String getEditorTrimCommandIdByTypeId(final String type);

}