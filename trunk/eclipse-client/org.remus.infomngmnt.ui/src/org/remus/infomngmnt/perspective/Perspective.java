package org.remus.infomngmnt.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.remus.infomngmnt.ui.views.NavigationView;

public class Perspective implements IPerspectiveFactory {

	public static final String PERSPECTIVE_ID = "org.remus.infomngmnt.ui.infoperspective"; //$NON-NLS-1$

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		layout.setFixed(false);

		layout.addStandaloneView(NavigationView.ID,  false, IPageLayout.LEFT, 1.0f, editorArea);
	}

}
