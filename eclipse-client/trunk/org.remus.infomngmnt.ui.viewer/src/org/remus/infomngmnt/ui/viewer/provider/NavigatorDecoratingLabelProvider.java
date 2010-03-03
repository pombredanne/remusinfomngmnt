package org.remus.infomngmnt.ui.viewer.provider;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.ui.viewer.internal.ResourceManager;
import org.remus.infomngmnt.util.CategoryUtil;

/**
 * Decorating label provider with styled label support for the navigator.
 * <p>
 * If the wrapped label provider is a
 * {@link DelegatingStyledCellLabelProvider.IStyledLabelProvider} it will use
 * the styled label it provides.
 * </p>
 * <p>
 * The label provider listens to the built-in decoration colors (
 * {@link JFacePreferences#QUALIFIER_COLOR},
 * {@link JFacePreferences#COUNTER_COLOR} and
 * {@link JFacePreferences#DECORATIONS_COLOR}. If other colors are used, it is
 * the responsibility of the wrapped label provider to fire the refresh.
 * </p>
 */
public class NavigatorDecoratingLabelProvider extends DecoratingStyledCellLabelProvider implements
		IPropertyChangeListener, ILabelProvider {

	/**
	 * Creates a {@link NavigatorDecoratingLabelProvider}
	 * 
	 * @param commonLabelProvider
	 *            the label provider to use
	 */
	public NavigatorDecoratingLabelProvider(final ILabelProvider commonLabelProvider) {
		super(new StyledLabelProviderAdapter(commonLabelProvider), PlatformUI.getWorkbench()
				.getDecoratorManager().getLabelDecorator(), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.StyledCellLabelProvider#initialize(org.eclipse
	 * .jface.viewers.ColumnViewer, org.eclipse.jface.viewers.ViewerColumn)
	 */
	@Override
	public void initialize(final ColumnViewer viewer, final ViewerColumn column) {
		PlatformUI.getPreferenceStore().addPropertyChangeListener(this);
		JFaceResources.getColorRegistry().addListener(this);

		setOwnerDrawEnabled(showColoredLabels());

		super.initialize(viewer, column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		PlatformUI.getPreferenceStore().removePropertyChangeListener(this);
		JFaceResources.getColorRegistry().removeListener(this);
	}

	private void refresh() {
		ColumnViewer viewer = getViewer();
		if (viewer == null) {
			return;
		}
		boolean showColoredLabels = showColoredLabels();
		if (showColoredLabels != isOwnerDrawEnabled()) {
			setOwnerDrawEnabled(showColoredLabels);
			viewer.refresh();
		} else if (showColoredLabels) {
			viewer.refresh();
		}
	}

	private static boolean showColoredLabels() {
		return PlatformUI.getPreferenceStore().getBoolean(
				IWorkbenchPreferenceConstants.USE_COLORED_LABELS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse
	 * .jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(final PropertyChangeEvent event) {
		String property = event.getProperty();
		if (property.equals(JFacePreferences.QUALIFIER_COLOR)
				|| property.equals(JFacePreferences.COUNTER_COLOR)
				|| property.equals(JFacePreferences.DECORATIONS_COLOR)
				|| property.equals(IWorkbenchPreferenceConstants.USE_COLORED_LABELS)) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					refresh();
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(final Object element) {
		return getStyledText(element).getString();
	}

	@Override
	public Font getFont(final Object element) {
		if (element instanceof InformationUnitListItem
				&& ((InformationUnitListItem) element).isUnread()) {
			return ResourceManager.getBoldFont(Display.getCurrent().getSystemFont());
		} else if (element instanceof Category) {
			InformationUnitListItem[] allInfoUnitItems = CategoryUtil
					.getAllInfoUnitItems((Category) element);
			for (InformationUnitListItem informationUnitListItem : allInfoUnitItems) {
				if (informationUnitListItem.isUnread()) {
					return ResourceManager.getBoldFont(Display.getCurrent().getSystemFont());
				}
			}
		}
		return super.getFont(element);
	}
}
