/*******************************************************************************
 * Copyright (c) 2004, 2008 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Balazs Brinkus - initial API and implementation
 *     Tasktop Technologies - improvements
 *     Willian Mitsuda - improvements
 *******************************************************************************/

package org.remus.infomngmnt.image.screenshot;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

/**
 * NOTE: this class exposes a lot of implementation detial and is likely to
 * change.
 * 
 * A wizard page to create a screenshot from the display.
 * 
 * @author Balazs Brinkus (bug 160572)
 * @author Mik Kersten
 * @author Willian Mitsuda
 */
public class ScreenshotCreationPage extends WizardPage {

	private IAction captureAction;

	private IAction fitAction;

	private IAction cropAction;

	private IAction markAction;

	private IAction colorAction;

	private Image colorIcon;

	private Color markColor;

	private IAction clearAction;

	private boolean imageDirty;

	/**
	 * Original screenshot image; used for backup purposes
	 */
	private Image originalImage;

	/**
	 * Copy of {@link #originalImage original} image; all drawing operations are
	 * done here; base for the result image
	 */
	private Image workImage;

	/**
	 * Used to draw into {@link #workImage}
	 */
	private GC workImageGC;

	private Canvas canvas;

	private ScrolledComposite scrolledComposite;

	/**
	 * Stores the selection rectangle; this value is normalized to real image
	 * coordinates, no matter the zoom level (see {@link #scaleFactor})
	 */
	private Rectangle currentSelection;

	/**
	 * Stores the original selection rectangle, before a selection resize/move
	 * operation starts
	 */
	private Rectangle originalSelection;

	/**
	 * Temporary storage for selection start point, selection resizing initial
	 * reference point or previous mark point (it depends on current tool); this
	 * value is normalized to real image coordinates, no matter the zoom level
	 * (see {@link #scaleFactor})
	 */
	private Point startPoint;

	/**
	 * What sides I'm resizing when doing an selection
	 * {@link EditorAction#RESIZING_SELECTION resize}
	 */
	private Set<SelectionSide> resizableSides = EnumSet.noneOf(SelectionSide.class);

	/**
	 * Scale factor of displayed image compared to the original image
	 */
	private double scaleFactor = 1.0;

	/**
	 * Manages allocated cursors
	 */
	private final Map<Integer, Cursor> cursors = new HashMap<Integer, Cursor>();

	/**
	 * Available actions for the screenshot editor
	 */
	private static enum EditorAction {

		CROPPING, SELECTING, RESIZING_SELECTION, MOVING_SELECTION, MARKING;

	};

	public static final Color GRAY_MID = new Color(Display.getDefault(), 100, 100, 100);

	/**
	 * What am I doing now?
	 */
	private EditorAction currentAction = EditorAction.CROPPING;

	public ScreenshotCreationPage() {
		super("ScreenShotAttachment"); //$NON-NLS-1$
		setTitle("Capture screenshot");
		setDescription("Screenshot bla");
	}

	public void createControl(final Composite parent) {
		ViewForm vf = new ViewForm(parent, SWT.BORDER | SWT.FLAT);
		vf.horizontalSpacing = 0;
		vf.verticalSpacing = 0;
		setControl(vf);
		vf.setLayoutData(GridDataFactory.fillDefaults().create());

		allocateCursors();

		// TODO: need disabled versions of all toolbar icons
		ToolBarManager tbm = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL | SWT.RIGHT);
		this.captureAction = new Action("Blabla", IAction.AS_PUSH_BUTTON) {

			private boolean isFirstCapture = true;

			@Override
			public void run() {
				captureScreenshotContent();
				setErrorMessage(null);
				if (this.isFirstCapture) {
					this.isFirstCapture = false;
					ScreenshotCreationPage.this.fitAction.setEnabled(true);
					ScreenshotCreationPage.this.cropAction.setEnabled(true);
					ScreenshotCreationPage.this.cropAction.setChecked(true);
					ScreenshotCreationPage.this.markAction.setEnabled(true);
					ScreenshotCreationPage.this.clearAction.setEnabled(false);
				}
			}

		};
		this.captureAction.setToolTipText("Capture desktop");
		this.captureAction.setImageDescriptor(ImageDescriptor.createFromImage(PlatformUI
				.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT)));

		// captureDelayedButton = new Button(buttonsComposite, SWT.PUSH);
		// final String captureIn = "Capture in ";
		// final int secondsDelay = 1;
		// captureDelayedButton.setText(captureIn + secondsDelay +" seconds");
		// captureDelayedButton.setImage(TasksUiImages.getImage(TasksUiImages.IMAGE_CAPTURE));
		// captureDelayedButton.addSelectionListener(new SelectionListener() {
		//
		// public void widgetSelected(SelectionEvent e) {
		// PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		// public void run() {
		// getShell().setVisible(false);
		// for (int i = 1; i <= secondsDelay; i++) {
		// try {
		// Thread.sleep(1000);
		// // captureDelayedButton.setText("Capture in " + (secondsDelay-i) +
		// " seconds");
		// } catch (InterruptedException e1) {
		// // ignore
		// }
		// }
		// captureScreenshotContent();
		// page.setErrorMessage(null);
		// fitButton.setEnabled(true);
		// captureDelayedButton.setText(captureIn + secondsDelay +" seconds");
		// getShell().setVisible(true);
		// }
		// });
		// }
		//
		// public void widgetDefaultSelected(SelectionEvent e) {
		// //ignore
		// }
		// });

		this.fitAction = new Action("", IAction.AS_CHECK_BOX) { //$NON-NLS-1$
			@Override
			public void run() {
				refreshCanvasSize();
			}
		};
		this.fitAction.setToolTipText("Fit image");
		this.fitAction.setText("Fit image");
		this.fitAction.setImageDescriptor(ImageDescriptor.createFromImage(PlatformUI.getWorkbench()
				.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT)));
		this.fitAction.setChecked(true);
		this.fitAction.setEnabled(false);

		this.cropAction = new Action("Crop", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				ScreenshotCreationPage.this.currentAction = EditorAction.CROPPING;
				ScreenshotCreationPage.this.cropAction.setChecked(true);
				ScreenshotCreationPage.this.markAction.setChecked(false);
				ScreenshotCreationPage.this.colorAction.setEnabled(false);
				ScreenshotCreationPage.this.canvas.redraw();
			}
		};
		this.cropAction.setToolTipText("Crop");
		this.cropAction.setImageDescriptor(ImageDescriptor.createFromImage(PlatformUI
				.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT)));
		this.cropAction.setEnabled(false);

		this.markAction = new Action("Annotate", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				ScreenshotCreationPage.this.currentAction = EditorAction.MARKING;
				ScreenshotCreationPage.this.cropAction.setChecked(false);
				ScreenshotCreationPage.this.markAction.setChecked(true);
				ScreenshotCreationPage.this.colorAction.setEnabled(true);
				ScreenshotCreationPage.this.canvas.redraw();
			}
		};
		this.markAction.setToolTipText("Draw annotation");
		this.markAction.setImageDescriptor(ImageDescriptor.createFromImage(PlatformUI
				.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT)));
		// markAction.setDisabledImageDescriptor(ImageDescriptor.createFromFile(getClass(),
		// "mark_disabled.gif"));
		this.markAction.setEnabled(false);

		this.colorAction = new Action("", IAction.AS_DROP_DOWN_MENU) { //$NON-NLS-1$
			@Override
			public void runWithEvent(final Event e) {
				final ColorSelectionWindow colorWindow = new ColorSelectionWindow(getControl()
						.getShell()) {

					@Override
					protected Point getInitialLocation(final Point initialSize) {
						ToolItem toolItem = (ToolItem) e.widget;
						Rectangle itemBounds = toolItem.getBounds();
						Point location = toolItem.getParent().toDisplay(
								itemBounds.x + itemBounds.width, itemBounds.y + itemBounds.height);
						location.x -= initialSize.x;
						return location;
					}

				};
				colorWindow.setBlockOnOpen(true);
				colorWindow.open();
				RGB color = colorWindow.getSelectedRGB();
				if (color != null) {
					setMarkColor(color);
				}
			}
		};
		this.colorAction.setToolTipText("Pen color");
		this.colorIcon = new Image(getShell().getDisplay(), 16, 16);
		setMarkColor(new RGB(255, 85, 85));
		this.colorAction.setEnabled(false);

		this.clearAction = new Action("Clear annotations", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ScreenshotCreationPage.this.clearAction.setEnabled(false);
				ScreenshotCreationPage.this.workImageGC.drawImage(
						ScreenshotCreationPage.this.originalImage, 0, 0);
				ScreenshotCreationPage.this.canvas.redraw();
				setImageDirty(true);
			}
		};
		this.clearAction.setToolTipText("Clear");
		this.clearAction.setImageDescriptor(ImageDescriptor.createFromImage(PlatformUI
				.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT)));
		this.clearAction.setEnabled(false);

		tbm.add(createAndConfigureCI(this.captureAction));
		tbm.add(createAndConfigureCI(this.fitAction));
		tbm.add(new Separator());
		tbm.add(createAndConfigureCI(this.cropAction));
		tbm.add(createAndConfigureCI(this.markAction));
		tbm.add(createAndConfigureCI(this.colorAction));
		tbm.add(new Separator());
		tbm.add(createAndConfigureCI(this.clearAction));

		this.scrolledComposite = new ScrolledComposite(vf, SWT.V_SCROLL | SWT.H_SCROLL);
		this.canvas = new Canvas(this.scrolledComposite, SWT.DOUBLE_BUFFERED);
		this.scrolledComposite.setContent(this.canvas);
		this.canvas.addPaintListener(new PaintListener() {

			public void paintControl(final PaintEvent e) {
				if (ScreenshotCreationPage.this.workImage != null) {
					Rectangle imageBounds = ScreenshotCreationPage.this.workImage.getBounds();
					Rectangle canvasBounds = ScreenshotCreationPage.this.canvas.getClientArea();

					if (ScreenshotCreationPage.this.fitAction.isChecked()) {
						e.gc.drawImage(ScreenshotCreationPage.this.workImage, 0, 0,
								imageBounds.width, imageBounds.height, 0, 0, canvasBounds.width,
								canvasBounds.height);
					} else {
						e.gc.drawImage(ScreenshotCreationPage.this.workImage, 0, 0);
					}
					drawSelection(e.gc);
				} else {
					// page.setErrorMessage("Screenshot required");
					ScreenshotCreationPage.this.fitAction.setEnabled(false);
				}
			}
		});

		this.scrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				if (ScreenshotCreationPage.this.fitAction.isChecked()) {
					refreshCanvasSize();
				}
			}
		});
		this.scrolledComposite.setEnabled(false);

		vf.setTopLeft(tbm.createControl(vf));
		vf.setContent(this.scrolledComposite);
		registerMouseListeners();

		Dialog.applyDialogFont(vf);
	}

	private ActionContributionItem createAndConfigureCI(final IAction action) {
		ActionContributionItem ci = new ActionContributionItem(action);
		ci.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		return ci;
	}

	private void setMarkColor(final RGB color) {
		if (this.markColor != null) {
			this.markColor.dispose();
		}
		this.markColor = new Color(getShell().getDisplay(), color);
		if (this.workImageGC != null) {
			this.workImageGC.setForeground(this.markColor);
		}

		GC colorGC = new GC(this.colorIcon);
		colorGC.setBackground(this.markColor);
		colorGC.fillRectangle(0, 0, 16, 16);
		colorGC.drawRectangle(0, 0, 15, 15);
		colorGC.dispose();

		this.colorAction.setImageDescriptor(ImageDescriptor.createFromImage(this.colorIcon));
	}

	@Override
	public void dispose() {
		disposeImageResources();
		if (this.markColor != null) {
			this.markColor.dispose();
		}
		if (this.colorIcon != null) {
			this.colorIcon.dispose();
		}

		this.canvas.setCursor(null);
		for (Cursor cursor : this.cursors.values()) {
			cursor.dispose();
		}
		super.dispose();
	}

	private void disposeImageResources() {
		if (this.originalImage != null) {
			this.originalImage.dispose();
		}
		if (this.workImageGC != null) {
			this.workImageGC.dispose();
		}
		if (this.workImage != null) {
			this.workImage.dispose();
		}
	}

	private static final int CURSOR_MARK_TOOL = -1;

	private static final long CAPTURE_DELAY = 400;

	private void allocateCursors() {
		Display display = getShell().getDisplay();
		this.cursors.put(SWT.CURSOR_ARROW, new Cursor(display, SWT.CURSOR_ARROW));
		this.cursors.put(SWT.CURSOR_SIZEALL, new Cursor(display, SWT.CURSOR_SIZEALL));
		this.cursors.put(SWT.CURSOR_SIZENWSE, new Cursor(display, SWT.CURSOR_SIZENWSE));
		this.cursors.put(SWT.CURSOR_SIZENESW, new Cursor(display, SWT.CURSOR_SIZENESW));
		this.cursors.put(SWT.CURSOR_SIZENS, new Cursor(display, SWT.CURSOR_SIZENS));
		this.cursors.put(SWT.CURSOR_SIZEWE, new Cursor(display, SWT.CURSOR_SIZEWE));
		this.cursors.put(SWT.CURSOR_CROSS, new Cursor(display, SWT.CURSOR_CROSS));

		// TODO: allocate custom cursor for "mark" tool
		this.cursors.put(CURSOR_MARK_TOOL, new Cursor(display, SWT.CURSOR_HAND));
	}

	private Rectangle getScaledSelection() {
		if (this.currentSelection == null) {
			return null;
		}
		int x = (int) Math.round(this.currentSelection.x * this.scaleFactor);
		int y = (int) Math.round(this.currentSelection.y * this.scaleFactor);
		int right = (int) Math.round((this.currentSelection.x + this.currentSelection.width)
				* this.scaleFactor);
		int bottom = (int) Math.round((this.currentSelection.y + this.currentSelection.height)
				* this.scaleFactor);
		int width = Math.min(right, (int) Math.round((this.workImage.getBounds().width - 1)
				* this.scaleFactor))
				- x;
		int height = Math.min(bottom, (int) Math.round((this.workImage.getBounds().height - 1)
				* this.scaleFactor))
				- y;
		return new Rectangle(x, y, width, height);
	}

	@Override
	public boolean isPageComplete() {
		return this.workImage != null;
	}

	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete();
	}

	private void captureScreenshotContent() {
		final Display display = getShell().getDisplay();
		final Shell wizardShell = getWizard().getContainer().getShell();
		wizardShell.setVisible(false);

		// this code needs to run asynchronously to allow the workbench to
		// refresh before the screen is captured
		UIJob job = new UIJob("Capturing Screenshot") { //$NON-NLS-1$

			@Override
			public IStatus runInUIThread(final IProgressMonitor monitor) {
				disposeImageResources();
				Rectangle displayBounds = display.getBounds();
				ScreenshotCreationPage.this.originalImage = new Image(display, displayBounds.width,
						displayBounds.height);
				ScreenshotCreationPage.this.workImage = new Image(display, displayBounds.width,
						displayBounds.height);

				GC gc = new GC(display);
				gc.copyArea(ScreenshotCreationPage.this.originalImage, displayBounds.x,
						displayBounds.y);
				gc
						.copyArea(ScreenshotCreationPage.this.workImage, displayBounds.x,
								displayBounds.y);
				gc.dispose();

				ScreenshotCreationPage.this.workImageGC = new GC(
						ScreenshotCreationPage.this.workImage);
				ScreenshotCreationPage.this.workImageGC
						.setForeground(ScreenshotCreationPage.this.markColor);
				ScreenshotCreationPage.this.workImageGC.setLineWidth(4);
				ScreenshotCreationPage.this.workImageGC.setLineCap(SWT.CAP_ROUND);

				ScreenshotCreationPage.this.scrolledComposite.setEnabled(true);
				clearSelection();
				refreshCanvasSize();

				wizardShell.setVisible(true);
				setPageComplete(true);

				return Status.OK_STATUS;
			}
		};
		// NOTE: need a wait since the shell can take time to disappear (e.g.
		// fade on Vista)
		job.schedule(CAPTURE_DELAY);
	}

	/**
	 * Sets the selection rectangle based on the initial selection start point
	 * previously set in {@link #startPoint} and the end point passed as
	 * parameters to this method
	 * <p>
	 * The coordinates are based on the real image coordinates
	 */
	private void refreshCurrentSelection(final int x, final int y) {
		int startX = Math.min(this.startPoint.x, x);
		int startY = Math.min(this.startPoint.y, y);
		int width = Math.abs(this.startPoint.x - x);
		int height = Math.abs(this.startPoint.y - y);
		this.currentSelection = new Rectangle(startX, startY, width, height);

		// Decreases 1 pixel size from original image because
		// Rectangle.intersect() consider them as right-bottom limit
		Rectangle imageBounds = this.workImage.getBounds();
		imageBounds.width--;
		imageBounds.height--;
		this.currentSelection.intersect(imageBounds);
	}

	/**
	 * Create the grab points to resize the selection; this method should be
	 * called every time the selection or zoom level is changed
	 */
	private void setUpGrabPoints() {
		this.grabPoints.clear();
		if (this.currentSelection == null) {
			return;
		}

		Rectangle scaledSelection = getScaledSelection();
		this.grabPoints.add(GrabPoint.createGrabPoint(scaledSelection.x, scaledSelection.y,
				SWT.CURSOR_SIZENWSE, EnumSet.of(SelectionSide.LEFT, SelectionSide.TOP)));
		this.grabPoints.add(GrabPoint.createGrabPoint(
				scaledSelection.x + scaledSelection.width / 2, scaledSelection.y,
				SWT.CURSOR_SIZENS, EnumSet.of(SelectionSide.TOP)));
		this.grabPoints.add(GrabPoint.createGrabPoint(scaledSelection.x + scaledSelection.width,
				scaledSelection.y, SWT.CURSOR_SIZENESW, EnumSet.of(SelectionSide.TOP,
						SelectionSide.RIGHT)));
		this.grabPoints.add(GrabPoint.createGrabPoint(scaledSelection.x, scaledSelection.y
				+ scaledSelection.height / 2, SWT.CURSOR_SIZEWE, EnumSet.of(SelectionSide.LEFT)));
		this.grabPoints.add(GrabPoint.createGrabPoint(scaledSelection.x + scaledSelection.width,
				scaledSelection.y + scaledSelection.height / 2, SWT.CURSOR_SIZEWE, EnumSet
						.of(SelectionSide.RIGHT)));
		this.grabPoints.add(GrabPoint.createGrabPoint(scaledSelection.x, scaledSelection.y
				+ scaledSelection.height, SWT.CURSOR_SIZENESW, EnumSet.of(SelectionSide.LEFT,
				SelectionSide.BOTTOM)));
		this.grabPoints.add(GrabPoint.createGrabPoint(
				scaledSelection.x + scaledSelection.width / 2, scaledSelection.y
						+ scaledSelection.height, SWT.CURSOR_SIZENS, EnumSet
						.of(SelectionSide.BOTTOM)));
		this.grabPoints.add(GrabPoint.createGrabPoint(scaledSelection.x + scaledSelection.width,
				scaledSelection.y + scaledSelection.height, SWT.CURSOR_SIZENWSE, EnumSet.of(
						SelectionSide.BOTTOM, SelectionSide.RIGHT)));
	}

	private void refreshSelectionResize(final int x, final int y) {
		this.currentSelection = new Rectangle(this.originalSelection.x, this.originalSelection.y,
				this.originalSelection.width, this.originalSelection.height);
		int deltaX = x - this.startPoint.x;
		int deltaY = y - this.startPoint.y;
		Rectangle imageBounds = this.workImage.getBounds();

		// Check current selection limits
		if (this.resizableSides.contains(SelectionSide.LEFT)) {
			deltaX = Math.min(deltaX, this.originalSelection.width);
			if (this.originalSelection.x + deltaX < 0) {
				deltaX = -this.originalSelection.x;
			}
		}
		if (this.resizableSides.contains(SelectionSide.RIGHT)) {
			deltaX = Math.max(deltaX, -this.originalSelection.width);
			if (this.originalSelection.x + this.originalSelection.width + deltaX - 1 > imageBounds.width) {
				deltaX = imageBounds.width
						- (this.originalSelection.x + this.originalSelection.width);
			}
		}
		if (this.resizableSides.contains(SelectionSide.TOP)) {
			deltaY = Math.min(deltaY, this.originalSelection.height);
			if (this.originalSelection.y + deltaY < 0) {
				deltaY = -this.originalSelection.y;
			}
		}
		if (this.resizableSides.contains(SelectionSide.BOTTOM)) {
			deltaY = Math.max(deltaY, -this.originalSelection.height);
			if (this.originalSelection.y + this.originalSelection.height + deltaY - 1 > imageBounds.height) {
				deltaY = imageBounds.height
						- (this.originalSelection.y + this.originalSelection.height);
			}
		}

		// Adjust corresponding sides
		if (this.resizableSides.contains(SelectionSide.LEFT)) {
			this.currentSelection.x += deltaX;
			this.currentSelection.width -= deltaX;
		}
		if (this.resizableSides.contains(SelectionSide.RIGHT)) {
			this.currentSelection.width += deltaX;
		}
		if (this.resizableSides.contains(SelectionSide.TOP)) {
			this.currentSelection.y += deltaY;
			this.currentSelection.height -= deltaY;
		}
		if (this.resizableSides.contains(SelectionSide.BOTTOM)) {
			this.currentSelection.height += deltaY;
		}

		setUpGrabPoints();
	}

	private void refreshSelectionPosition(final int x, final int y) {
		int newX = this.originalSelection.x + (x - this.startPoint.x);
		int newY = this.originalSelection.y + (y - this.startPoint.y);
		if (newX < 0) {
			newX = 0;
		}
		if (newY < 0) {
			newY = 0;
		}
		Rectangle imageBounds = this.workImage.getBounds();
		if (newX + this.originalSelection.width - 1 > imageBounds.width) {
			newX = imageBounds.width - this.originalSelection.width;
		}
		if (newY + this.originalSelection.height - 1 > imageBounds.height) {
			newY = imageBounds.height - this.originalSelection.height;
		}
		this.currentSelection = new Rectangle(newX, newY, this.originalSelection.width,
				this.originalSelection.height);

		setUpGrabPoints();
	}

	private void registerMouseListeners() {
		this.canvas.addMouseMoveListener(new MouseMoveListener() {

			/**
			 * If a selection is in course, moving the mouse around refreshes
			 * the selection rectangle
			 */
			public void mouseMove(final MouseEvent e) {
				int scaledX = (int) Math.round(e.x / ScreenshotCreationPage.this.scaleFactor);
				int scaledY = (int) Math.round(e.y / ScreenshotCreationPage.this.scaleFactor);

				if (ScreenshotCreationPage.this.currentAction == EditorAction.SELECTING) {
					refreshCurrentSelection(scaledX, scaledY);
					ScreenshotCreationPage.this.canvas.redraw();
				} else if (ScreenshotCreationPage.this.currentAction == EditorAction.RESIZING_SELECTION) {
					refreshSelectionResize(scaledX, scaledY);
					ScreenshotCreationPage.this.canvas.redraw();
				} else if (ScreenshotCreationPage.this.currentAction == EditorAction.MOVING_SELECTION) {
					refreshSelectionPosition(scaledX, scaledY);
					ScreenshotCreationPage.this.canvas.redraw();
				} else if (ScreenshotCreationPage.this.currentAction == EditorAction.CROPPING
						&& ScreenshotCreationPage.this.currentSelection != null) {
					boolean cursorSet = false;

					// No selection in course, but have something selected;
					// first test if I'm hovering some grab point
					for (GrabPoint point : ScreenshotCreationPage.this.grabPoints) {
						if (point.grabArea.contains(e.x, e.y)) {
							ScreenshotCreationPage.this.canvas
									.setCursor(ScreenshotCreationPage.this.cursors
											.get(point.cursorType));
							cursorSet = true;
							break;
						}
					}

					// Test if I'm inside selection, so I can move it
					if (!cursorSet && getScaledSelection().contains(e.x, e.y)) {
						ScreenshotCreationPage.this.canvas
								.setCursor(ScreenshotCreationPage.this.cursors
										.get(SWT.CURSOR_SIZEALL));
						cursorSet = true;
					}

					// If I'm out, the default cursor for cropping mode is cross
					Cursor crossCursor = ScreenshotCreationPage.this.cursors.get(SWT.CURSOR_CROSS);
					if (!cursorSet && ScreenshotCreationPage.this.canvas.getCursor() != crossCursor) {
						ScreenshotCreationPage.this.canvas.setCursor(crossCursor);
					}
				} else if (ScreenshotCreationPage.this.currentAction == EditorAction.MARKING) {
					drawMarkLine(scaledX, scaledY);

					Cursor markCursor = ScreenshotCreationPage.this.cursors.get(CURSOR_MARK_TOOL);
					if (ScreenshotCreationPage.this.canvas.getCursor() != markCursor) {
						ScreenshotCreationPage.this.canvas.setCursor(markCursor);
					}
				}
			}

		});

		this.canvas.addMouseListener(new MouseAdapter() {

			/**
			 * Releasing the mouse button ends the selection or a drawing;
			 * compute the selection rectangle and redraw the cropped image
			 */
			@Override
			public void mouseUp(final MouseEvent e) {
				if (ScreenshotCreationPage.this.currentAction == EditorAction.SELECTING
						|| ScreenshotCreationPage.this.currentAction == EditorAction.RESIZING_SELECTION
						|| ScreenshotCreationPage.this.currentAction == EditorAction.MOVING_SELECTION) {
					int scaledX = (int) Math.round(e.x / ScreenshotCreationPage.this.scaleFactor);
					int scaledY = (int) Math.round(e.y / ScreenshotCreationPage.this.scaleFactor);
					if (ScreenshotCreationPage.this.currentAction == EditorAction.SELECTING) {
						refreshCurrentSelection(scaledX, scaledY);
					} else if (ScreenshotCreationPage.this.currentAction == EditorAction.RESIZING_SELECTION) {
						refreshSelectionResize(scaledX, scaledY);
					} else if (ScreenshotCreationPage.this.currentAction == EditorAction.MOVING_SELECTION) {
						refreshSelectionPosition(scaledX, scaledY);
					}
					if (ScreenshotCreationPage.this.currentSelection.width == 0
							&& ScreenshotCreationPage.this.currentSelection.height == 0) {
						ScreenshotCreationPage.this.currentSelection = null;
					}
					setUpGrabPoints();
					ScreenshotCreationPage.this.startPoint = null;
					ScreenshotCreationPage.this.currentAction = EditorAction.CROPPING;

					ScreenshotCreationPage.this.canvas.redraw();
					setImageDirty(true);
				} else if (ScreenshotCreationPage.this.currentAction == EditorAction.MARKING) {
					ScreenshotCreationPage.this.startPoint = null;
					setImageDirty(true);
				}
			}

			/**
			 * Pressing mouse button starts a selection or a drawing; normalizes
			 * and marks the start point
			 */
			@Override
			public void mouseDown(final MouseEvent e) {
				int scaledX = (int) (e.x / ScreenshotCreationPage.this.scaleFactor);
				int scaledY = (int) (e.y / ScreenshotCreationPage.this.scaleFactor);

				if (ScreenshotCreationPage.this.currentAction == EditorAction.MARKING) {
					ScreenshotCreationPage.this.startPoint = new Point(scaledX, scaledY);
					drawMarkLine(scaledX, scaledY);
					ScreenshotCreationPage.this.canvas
							.setCursor(ScreenshotCreationPage.this.cursors.get(CURSOR_MARK_TOOL));
					return;
				} else if (ScreenshotCreationPage.this.currentAction != EditorAction.CROPPING) {
					return;
				}

				// Check the most appropriate action to follow; first check if
				// I'm on some grab point
				if (ScreenshotCreationPage.this.currentSelection != null) {
					for (GrabPoint point : ScreenshotCreationPage.this.grabPoints) {
						if (point.grabArea.contains(e.x, e.y)) {
							ScreenshotCreationPage.this.originalSelection = ScreenshotCreationPage.this.currentSelection;
							ScreenshotCreationPage.this.currentAction = EditorAction.RESIZING_SELECTION;
							ScreenshotCreationPage.this.resizableSides = point.resizableSides;
							ScreenshotCreationPage.this.startPoint = new Point(scaledX, scaledY);
							ScreenshotCreationPage.this.canvas.redraw();
							return;
						}
					}
				}

				// Check if I could move the selection
				if (ScreenshotCreationPage.this.currentSelection != null
						&& ScreenshotCreationPage.this.currentSelection.contains(scaledX, scaledY)) {
					ScreenshotCreationPage.this.originalSelection = ScreenshotCreationPage.this.currentSelection;
					ScreenshotCreationPage.this.currentAction = EditorAction.MOVING_SELECTION;
					ScreenshotCreationPage.this.startPoint = new Point(scaledX, scaledY);
					ScreenshotCreationPage.this.canvas.redraw();
					return;
				}

				// Do a simple selection
				ScreenshotCreationPage.this.canvas.setCursor(ScreenshotCreationPage.this.cursors
						.get(SWT.CURSOR_CROSS));
				ScreenshotCreationPage.this.currentAction = EditorAction.SELECTING;
				ScreenshotCreationPage.this.currentSelection = null;
				ScreenshotCreationPage.this.startPoint = new Point(scaledX, scaledY);
				setUpGrabPoints();
				ScreenshotCreationPage.this.canvas.redraw();
			}

		});

	}

	private void clearSelection() {
		this.currentSelection = null;
		this.startPoint = null;
		setImageDirty(true);
	}

	/**
	 * Recalculates image canvas size based on "fit on canvas" setting, set up
	 * the grab points, and redraws
	 * <p>
	 * This method should be called whenever the {@link #workImage image}
	 * <strong>visible</strong> size is changed, which can happen when:
	 * <p>
	 * <ul>
	 * <li>The "Fit Image" setting is changed, so the image zoom level changes
	 * <li>The image changes (by recapturing)
	 * <li>The canvas is resized (indirectly happens by resizing the wizard
	 * page) <strong>AND</strong> "Fit Image" setting is ON
	 * </ul>
	 * <p>
	 * Calling this method under other circumstances may lead to strange
	 * behavior in the scrolled composite
	 */
	private void refreshCanvasSize() {
		if (this.fitAction.isChecked()) {
			// This little hack is necessary to get the client area without
			// scrollbars;
			// they'll be automatically restored if necessary after
			// Canvas.setBounds()
			this.scrolledComposite.getHorizontalBar().setVisible(false);
			this.scrolledComposite.getVerticalBar().setVisible(false);

			Rectangle bounds = this.scrolledComposite.getClientArea();
			if (this.workImage != null) {
				Rectangle imageBounds = this.workImage.getBounds();
				if (imageBounds.width > bounds.width || imageBounds.height > bounds.height) {
					double xRatio = (double) bounds.width / imageBounds.width;
					double yRatio = (double) bounds.height / imageBounds.height;
					this.scaleFactor = Math.min(xRatio, yRatio);
					bounds.width = (int) Math.round(imageBounds.width * this.scaleFactor);
					bounds.height = (int) Math.round(imageBounds.height * this.scaleFactor);
				}
			}
			this.canvas.setBounds(bounds);
		} else {
			this.scaleFactor = 1.0;
			Rectangle bounds = this.scrolledComposite.getClientArea();
			if (this.workImage != null) {
				Rectangle imageBounds = this.workImage.getBounds();
				bounds.width = imageBounds.width;
				bounds.height = imageBounds.height;
			}
			this.canvas.setBounds(bounds);
		}
		setUpGrabPoints();
		this.canvas.redraw();
	}

	/**
	 * Decorates the screenshot canvas with the selection rectangle, resize grab
	 * points and other adornments
	 */
	private void drawSelection(final GC gc) {
		if (this.currentSelection == null) {
			return;
		}
		Rectangle scaledSelection = getScaledSelection();

		// Draw shadow
		gc.setBackground(GRAY_MID);
		gc.setAdvanced(true);
		gc.setAlpha(120);

		Region invertedSelection = new Region();
		invertedSelection.add(this.canvas.getClientArea());
		invertedSelection.subtract(scaledSelection);
		gc.setClipping(invertedSelection);
		gc.fillRectangle(this.canvas.getClientArea());
		gc.setClipping((Region) null);
		invertedSelection.dispose();

		gc.setAdvanced(false);

		// Draw selection rectangle
		gc.setLineStyle(SWT.LINE_SOLID);
		gc.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		gc.drawRectangle(scaledSelection);

		// Draw grab points
		gc.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		gc.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_BLACK));
		for (GrabPoint point : this.grabPoints) {
			gc.fillRectangle(point.grabArea);
			gc.drawRectangle(point.grabArea);
		}
	}

	/**
	 * Connects the previous mark point to the new reference point, by drawing a
	 * new line
	 */
	private void drawMarkLine(final int x, final int y) {
		if (this.startPoint != null) {
			this.clearAction.setEnabled(true);
			this.workImageGC.drawLine(this.startPoint.x, this.startPoint.y, x, y);
			this.startPoint.x = x;
			this.startPoint.y = y;
			this.canvas.redraw();
		}
	}

	private static enum SelectionSide {

		LEFT, RIGHT, TOP, BOTTOM;

	};

	private static final int SQUARE_SIZE = 3;

	private static class GrabPoint {

		public Rectangle grabArea;

		public int cursorType;

		public Set<SelectionSide> resizableSides;

		public static GrabPoint createGrabPoint(final int x, final int y, final int cursorType,
				final Set<SelectionSide> resizableSides) {
			GrabPoint point = new GrabPoint();
			point.grabArea = new Rectangle(x - SQUARE_SIZE, y - SQUARE_SIZE, SQUARE_SIZE * 2 + 1,
					SQUARE_SIZE * 2 + 1);
			point.cursorType = cursorType;
			point.resizableSides = resizableSides;
			return point;
		}

	}

	private final List<GrabPoint> grabPoints = new ArrayList<GrabPoint>(8);

	/**
	 * Creates the final screenshot
	 * 
	 * @return The final screenshot, with all markings, and cropped according to
	 *         user settings; <strong>The caller is responsible for disposing
	 *         the returned image</strong>
	 */
	public Image createImage() {
		Image screenshot = new Image(getShell().getDisplay(),
				this.currentSelection != null ? this.currentSelection : this.workImage.getBounds());

		GC gc = new GC(screenshot);
		if (this.currentSelection != null) {
			gc.drawImage(this.workImage, this.currentSelection.x, this.currentSelection.y,
					this.currentSelection.width, this.currentSelection.height, 0, 0,
					this.currentSelection.width, this.currentSelection.height);
		} else {
			gc.drawImage(this.workImage, 0, 0);
		}
		gc.dispose();
		this.imageDirty = false;

		return screenshot;
	}

	public void setImageDirty(final boolean pageDirty) {
		this.imageDirty = pageDirty;
	}

	public boolean isImageDirty() {
		return this.imageDirty;
	}
}
