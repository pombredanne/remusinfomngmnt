package org.remus.infomngmnt.ui.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import org.remus.infomngmnt.ui.views.NavigationView;

public class Perspective implements IPerspectiveFactory {

	public static final String PERSPECTIVE_ID = "org.remus.infomngmnt.ui.infoperspective"; //$NON-NLS-1$

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		layout.setFixed(false);

		layout.addView(NavigationView.ID,  IPageLayout.LEFT, 0.3f, editorArea);
		layout.addView(IPageLayout.ID_OUTLINE,  IPageLayout.RIGHT, 0.7f, editorArea);

		layout.getViewLayout(NavigationView.ID).setCloseable(false);
	}

}
