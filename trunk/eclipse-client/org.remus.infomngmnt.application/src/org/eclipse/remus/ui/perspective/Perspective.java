package org.eclipse.remus.ui.perspective;

import org.eclipse.remus.ui.view.MainViewPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


public class Perspective implements IPerspectiveFactory {

	public static final String PERSPECTIVE_ID = "org.remus.infomngmnt.ui.infoperspective"; //$NON-NLS-1$

	public void createInitialLayout(final IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		layout.setFixed(false);

		layout.addStandaloneView(MainViewPart.VIEW_ID, false, IPageLayout.LEFT, 0.2f, editorArea);
		// layout.addView(MainViewPart.VIEW_ID, IPageLayout.LEFT, 0.3f,
		// editorArea);
		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, 0.75f, editorArea);

	}

}