/*******************************************************************************
 * Copyright (c) 2004, 2008 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Benjamin Pasero - intial API and implementation
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.popup;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.common.ui.image.CommonImageRegistry;
import org.remus.infomngmnt.ui.popup.SwtFadeUtil.FadeJob;
import org.remus.infomngmnt.ui.popup.SwtFadeUtil.IFadeListener;

/**
 * @author Benjamin Pasero
 * @author Mik Kersten
 * @author Steffen Pingel
 */
public abstract class AbstractNotificationPopup extends Window {

	private static final int TITLE_HEIGHT = 24;

	private static final String LABEL_NOTIFICATION = "Notification";

	private static final String LABEL_JOB_CLOSE = "Close Notification Job";

	private static final int MAX_WIDTH = 400;

	private static final int MIN_HEIGHT = 100;

	private static final long DEFAULT_DELAY_CLOSE = 8 * 1000;

	private static final int PADDING_EDGE = 5;

	private long delayClose = DEFAULT_DELAY_CLOSE;

	protected LocalResourceManager resources;

	private NotificationPopupColors color;

	private final Display display;

	private Shell shell;

	private Region lastUsedRegion;

	private Image lastUsedBgImage;

	private final Job closeJob = new Job(LABEL_JOB_CLOSE) {

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			if (!AbstractNotificationPopup.this.display.isDisposed()) {
				AbstractNotificationPopup.this.display.asyncExec(new Runnable() {
					public void run() {
						Shell shell = AbstractNotificationPopup.this.getShell();
						if (shell == null || shell.isDisposed()) {
							return;
						}

						if (isMouseOver(shell)) {
							scheduleAutoClose();
							return;
						}

						AbstractNotificationPopup.this.closeFade();
					}

				});
			}
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			return Status.OK_STATUS;
		}
	};

	private final boolean respectDisplayBounds = true;

	private final boolean respectMonitorBounds = true;

	private FadeJob fadeJob;

	private boolean supportsFading;

	private boolean fadingEnabled;

	public AbstractNotificationPopup(final Display display) {
		this(display, SWT.NO_TRIM | SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
	}

	public AbstractNotificationPopup(final Display display, final int style) {
		super(new Shell(display));
		setShellStyle(style);

		this.display = display;
		this.resources = new LocalResourceManager(JFaceResources.getResources());
		initResources();

		this.closeJob.setSystem(true);
	}

	public boolean isFadingEnabled() {
		return this.fadingEnabled;
	}

	public void setFadingEnabled(final boolean fadingEnabled) {
		this.fadingEnabled = fadingEnabled;
	}

	/**
	 * Override to return a customized name. Default is to return the name of
	 * the product, specified by the -name (e.g. "Eclipse SDK") command line
	 * parameter that's associated with the product ID (e.g.
	 * "org.eclipse.sdk.ide"). Strips the trailing "SDK" for any name, since
	 * this part of the label is considered visual noise.
	 * 
	 * @return the name to be used in the title of the popup.
	 */
	protected String getPopupShellTitle() {
		IProduct product = Platform.getProduct();
		if (product != null) {
			String productName = product.getName();
			String LABEL_SDK = "SDK"; //$NON-NLS-1$
			if (productName.endsWith(LABEL_SDK)) {
				productName = productName.substring(0, productName.length() - LABEL_SDK.length());
			}
			return productName + " " + LABEL_NOTIFICATION; //$NON-NLS-1$
		} else {
			return LABEL_NOTIFICATION;
		}
	}

	protected Image getPopupShellImage(final int maximumHeight) {
		// always use the launching workbench window
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		if (windows != null && windows.length > 0) {
			IWorkbenchWindow workbenchWindow = windows[0];
			if (workbenchWindow != null && !workbenchWindow.getShell().isDisposed()) {
				Image image = getShell().getImage();
				int diff = Integer.MAX_VALUE;
				if (image != null && image.getBounds().height <= maximumHeight) {
					diff = maximumHeight - image.getBounds().height;
				} else {
					image = null;
				}

				Image[] images = getShell().getImages();
				if (images != null && images.length > 0) {
					// find the icon that is closest in size, but not larger
					// than maximumHeight
					for (Image image2 : images) {
						int newDiff = maximumHeight - image2.getBounds().height;
						if (newDiff >= 0 && newDiff <= diff) {
							diff = newDiff;
							image = image2;
						}
					}
				}

				return image;
			}
		}
		return null;
	}

	/**
	 * Override to populate with notifications.
	 * 
	 * @param parent
	 */
	protected void createContentArea(final Composite parent) {
		// empty by default
	}

	/**
	 * Override to customize the title bar
	 */
	protected void createTitleArea(final Composite parent) {
		((GridData) parent.getLayoutData()).heightHint = TITLE_HEIGHT;

		Label titleImageLabel = new Label(parent, SWT.NONE);
		titleImageLabel.setImage(getPopupShellImage(TITLE_HEIGHT));

		Label titleTextLabel = new Label(parent, SWT.NONE);
		titleTextLabel.setText(getPopupShellTitle());
		titleTextLabel.setFont(CommonFonts.BOLD);
		titleTextLabel.setForeground(this.color.getTitleText());
		titleTextLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		titleTextLabel.setCursor(parent.getDisplay().getSystemCursor(SWT.CURSOR_HAND));

		final Label button = new Label(parent, SWT.NONE);
		button.setImage(CommonImageRegistry.getInstance().get(
				CommonImageRegistry.NOTIFICATION_CLOSE));
		button.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(final MouseEvent e) {
				button.setImage(CommonImageRegistry.getInstance().get(
						CommonImageRegistry.NOTIFICATION_CLOSE_HOVER));
			}

			@Override
			public void mouseExit(final MouseEvent e) {
				button.setImage(CommonImageRegistry.getInstance().get(
						CommonImageRegistry.NOTIFICATION_CLOSE));
			}
		});
		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(final MouseEvent e) {
				close();
				setReturnCode(CANCEL);
			}

		});
	}

	private void initResources() {
		this.color = new NotificationPopupColors(this.display, this.resources);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);

		this.shell = newShell;
		newShell.setBackground(this.color.getBorder());
	}

	@Override
	public void create() {
		super.create();
		addRegion(this.shell);
	}

	private void addRegion(final Shell shell) {
		Region region = new Region();
		Point s = shell.getSize();

		/* Add entire Shell */
		region.add(0, 0, s.x, s.y);

		/* Subtract Top-Left Corner */
		region.subtract(0, 0, 5, 1);
		region.subtract(0, 1, 3, 1);
		region.subtract(0, 2, 2, 1);
		region.subtract(0, 3, 1, 1);
		region.subtract(0, 4, 1, 1);

		/* Subtract Top-Right Corner */
		region.subtract(s.x - 5, 0, 5, 1);
		region.subtract(s.x - 3, 1, 3, 1);
		region.subtract(s.x - 2, 2, 2, 1);
		region.subtract(s.x - 1, 3, 1, 1);
		region.subtract(s.x - 1, 4, 1, 1);

		/* Subtract Bottom-Left Corner */
		region.subtract(0, s.y, 5, 1);
		region.subtract(0, s.y - 1, 3, 1);
		region.subtract(0, s.y - 2, 2, 1);
		region.subtract(0, s.y - 3, 1, 1);
		region.subtract(0, s.y - 4, 1, 1);

		/* Subtract Bottom-Right Corner */
		region.subtract(s.x - 5, s.y - 0, 5, 1);
		region.subtract(s.x - 3, s.y - 1, 3, 1);
		region.subtract(s.x - 2, s.y - 2, 2, 1);
		region.subtract(s.x - 1, s.y - 3, 1, 1);
		region.subtract(s.x - 1, s.y - 4, 1, 1);

		/* Dispose old first */
		if (shell.getRegion() != null) {
			shell.getRegion().dispose();
		}

		/* Apply Region */
		shell.setRegion(region);

		/* Remember to dispose later */
		this.lastUsedRegion = region;
	}

	private boolean isMouseOver(final Shell shell) {
		if (this.display.isDisposed()) {
			return false;
		}
		return shell.getBounds().contains(this.display.getCursorLocation());
	}

	@Override
	public int open() {
		if (this.shell == null || this.shell.isDisposed()) {
			this.shell = null;
			create();
		}

		constrainShellSize();
		this.shell.setLocation(fixupDisplayBounds(this.shell.getSize(), this.shell.getLocation()));

		if (isFadingEnabled()) {
			this.supportsFading = SwtFadeUtil.setAlpha(this.shell, 0);
		} else {
			this.supportsFading = false;
		}
		this.shell.setVisible(true);
		if (this.supportsFading) {
			this.fadeJob = SwtFadeUtil.fadeIn(this.shell, new IFadeListener() {
				public void faded(final Shell shell, final int alpha) {
					if (shell.isDisposed()) {
						return;
					}

					if (alpha == 255) {
						scheduleAutoClose();
					}
				}
			});
		} else {
			scheduleAutoClose();
		}

		return Window.OK;
	}

	protected void scheduleAutoClose() {
		if (this.delayClose > 0) {
			this.closeJob.schedule(this.delayClose);
		}
	}

	@Override
	protected Control createContents(final Composite parent) {
		((GridLayout) parent.getLayout()).marginWidth = 1;
		((GridLayout) parent.getLayout()).marginHeight = 1;

		/* Outer Composite holding the controls */
		final Composite outerCircle = new Composite(parent, SWT.NO_FOCUS);
		outerCircle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		outerCircle.setBackgroundMode(SWT.INHERIT_FORCE);

		outerCircle.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(final ControlEvent e) {
				Rectangle clArea = outerCircle.getClientArea();
				AbstractNotificationPopup.this.lastUsedBgImage = new Image(
						outerCircle.getDisplay(), clArea.width, clArea.height);
				GC gc = new GC(AbstractNotificationPopup.this.lastUsedBgImage);

				/* Gradient */
				drawGradient(gc, clArea);

				/* Fix Region Shape */
				fixRegion(gc, clArea);

				gc.dispose();

				Image oldBGImage = outerCircle.getBackgroundImage();
				outerCircle.setBackgroundImage(AbstractNotificationPopup.this.lastUsedBgImage);

				if (oldBGImage != null) {
					oldBGImage.dispose();
				}
			}

			private void drawGradient(final GC gc, final Rectangle clArea) {
				gc.setForeground(AbstractNotificationPopup.this.color.getGradientBegin());
				gc.setBackground(AbstractNotificationPopup.this.color.getGradientEnd());
				gc.fillGradientRectangle(clArea.x, clArea.y, clArea.width, clArea.height, true);
			}

			private void fixRegion(final GC gc, final Rectangle clArea) {
				gc.setForeground(AbstractNotificationPopup.this.color.getBorder());

				/* Fill Top Left */
				gc.drawPoint(2, 0);
				gc.drawPoint(3, 0);
				gc.drawPoint(1, 1);
				gc.drawPoint(0, 2);
				gc.drawPoint(0, 3);

				/* Fill Top Right */
				gc.drawPoint(clArea.width - 4, 0);
				gc.drawPoint(clArea.width - 3, 0);
				gc.drawPoint(clArea.width - 2, 1);
				gc.drawPoint(clArea.width - 1, 2);
				gc.drawPoint(clArea.width - 1, 3);

				/* Fill Bottom Left */
				gc.drawPoint(2, clArea.height - 0);
				gc.drawPoint(3, clArea.height - 0);
				gc.drawPoint(1, clArea.height - 1);
				gc.drawPoint(0, clArea.height - 2);
				gc.drawPoint(0, clArea.height - 3);

				/* Fill Bottom Right */
				gc.drawPoint(clArea.width - 4, clArea.height - 0);
				gc.drawPoint(clArea.width - 3, clArea.height - 0);
				gc.drawPoint(clArea.width - 2, clArea.height - 1);
				gc.drawPoint(clArea.width - 1, clArea.height - 2);
				gc.drawPoint(clArea.width - 1, clArea.height - 3);
			}
		});

		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;

		outerCircle.setLayout(layout);

		/* Title area containing label and close button */
		final Composite titleCircle = new Composite(outerCircle, SWT.NO_FOCUS);
		titleCircle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		titleCircle.setBackgroundMode(SWT.INHERIT_FORCE);

		layout = new GridLayout(4, false);
		layout.marginWidth = 3;
		layout.marginHeight = 0;
		layout.verticalSpacing = 5;
		layout.horizontalSpacing = 3;

		titleCircle.setLayout(layout);

		/* Create Title Area */
		createTitleArea(titleCircle);

		/* Outer composite to hold content controlls */
		Composite outerContentCircle = new Composite(outerCircle, SWT.NONE);
		outerContentCircle.setBackgroundMode(SWT.INHERIT_FORCE);

		layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		outerContentCircle.setLayout(layout);
		outerContentCircle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		outerContentCircle.setBackground(outerCircle.getBackground());

		/* Middle composite to show a 1px black line around the content controls */
		Composite middleContentCircle = new Composite(outerContentCircle, SWT.NO_FOCUS);
		middleContentCircle.setBackgroundMode(SWT.INHERIT_FORCE);

		layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.marginTop = 1;

		middleContentCircle.setLayout(layout);
		middleContentCircle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		middleContentCircle.setBackground(this.color.getBorder());

		/* Inner composite containing the content controls */
		Composite innerContent = new Composite(middleContentCircle, SWT.NO_FOCUS);
		innerContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		innerContent.setBackgroundMode(SWT.INHERIT_FORCE);

		layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		innerContent.setLayout(layout);

		innerContent.setBackground(this.shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));

		/* Content Area */
		createContentArea(innerContent);

		setNullBackground(outerCircle);

		return outerCircle;
	}

	private void setNullBackground(final Composite outerCircle) {
		for (Control c : outerCircle.getChildren()) {
			c.setBackground(null);
			if (c instanceof Composite) {
				setNullBackground((Composite) c);
			}
		}
	}

	@Override
	protected void initializeBounds() {
		Rectangle clArea = getPrimaryClientArea();
		Point initialSize = this.shell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		int height = Math.max(initialSize.y, MIN_HEIGHT);
		int width = Math.min(initialSize.x, MAX_WIDTH);

		Point size = new Point(width, height);
		this.shell.setLocation(clArea.width + clArea.x - size.x - PADDING_EDGE, clArea.height
				+ clArea.y - size.y - PADDING_EDGE);
		this.shell.setSize(size);
	}

	private Rectangle getPrimaryClientArea() {
		Monitor primaryMonitor = this.shell.getDisplay().getPrimaryMonitor();
		return (primaryMonitor != null) ? primaryMonitor.getClientArea() : this.shell.getDisplay()
				.getClientArea();
	}

	public void closeFade() {
		if (this.fadeJob != null) {
			this.fadeJob.cancelAndWait(false);
		}
		if (this.supportsFading) {
			this.fadeJob = SwtFadeUtil.fadeOut(getShell(), new IFadeListener() {
				public void faded(final Shell shell, final int alpha) {
					if (!shell.isDisposed()) {
						if (alpha == 0) {
							shell.close();
						} else if (isMouseOver(shell)) {
							if (AbstractNotificationPopup.this.fadeJob != null) {
								AbstractNotificationPopup.this.fadeJob.cancelAndWait(false);
							}
							AbstractNotificationPopup.this.fadeJob = SwtFadeUtil.fastFadeIn(shell,
									new IFadeListener() {
										public void faded(final Shell shell, final int alpha) {
											if (shell.isDisposed()) {
												return;
											}

											if (alpha == 255) {
												scheduleAutoClose();
											}
										}
									});
						}
					}
				}
			});
		} else {
			this.shell.close();
		}
	}

	@Override
	public boolean close() {
		this.resources.dispose();
		if (this.lastUsedRegion != null) {
			this.lastUsedRegion.dispose();
		}
		if (this.lastUsedBgImage != null && !this.lastUsedBgImage.isDisposed()) {
			this.lastUsedBgImage.dispose();
		}
		return super.close();
	}

	public long getDelayClose() {
		return this.delayClose;
	}

	public void setDelayClose(final long delayClose) {
		this.delayClose = delayClose;
	}

	private Point fixupDisplayBounds(final Point tipSize, final Point location) {
		if (this.respectDisplayBounds) {
			Rectangle bounds;
			Point rightBounds = new Point(tipSize.x + location.x, tipSize.y + location.y);

			if (this.respectMonitorBounds) {
				bounds = this.shell.getDisplay().getPrimaryMonitor().getBounds();
			} else {
				bounds = getPrimaryClientArea();
			}

			if (!(bounds.contains(location) && bounds.contains(rightBounds))) {
				if (rightBounds.x > bounds.x + bounds.width) {
					location.x -= rightBounds.x - (bounds.x + bounds.width);
				}

				if (rightBounds.y > bounds.y + bounds.height) {
					location.y -= rightBounds.y - (bounds.y + bounds.height);
				}

				if (location.x < bounds.x) {
					location.x = bounds.x;
				}

				if (location.y < bounds.y) {
					location.y = bounds.y;
				}
			}
		}

		return location;
	}

}