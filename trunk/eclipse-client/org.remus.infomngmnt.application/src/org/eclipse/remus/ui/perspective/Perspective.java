package org.eclipse.remus.ui.perspective;

import java.lang.reflect.Method;

import org.eclipse.remus.ui.view.MainViewPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.internal.PageLayout;

public class Perspective implements IPerspectiveFactory {

	public static final String PERSPECTIVE_ID = "org.remus.infomngmnt.ui.infoperspective"; //$NON-NLS-1$

	public void createInitialLayout(final IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		layout.setFixed(false);

		layout.addStandaloneView(MainViewPart.VIEW_ID, false, IPageLayout.LEFT,
				0.2f, editorArea);

		try {
			// HACK: This is for making the outline minimized
			@SuppressWarnings("restriction")
			Method method = PageLayout.class.getDeclaredMethod(
					"addView", //$NON-NLS-1$
					String.class, int.class, float.class, String.class,
					boolean.class, boolean.class, boolean.class);
			method.setAccessible(true);
			method.invoke(layout, IPageLayout.ID_OUTLINE, IPageLayout.RIGHT,
					0.75f, editorArea, true, false, false);
		} catch (Exception e) {
			layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, 0.75f,
					editorArea);
		}

	}

}
