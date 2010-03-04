package org.remus.infomngmnt.ui.desktop.services;

import java.io.IOException;

import org.remus.uimodel.DesktopToolItemCollection;

public interface ILocalTrayActionService {

	DesktopToolItemCollection getItemCollection();

	void save() throws IOException;

}