package org.remus.infomngmnt.image.ui;

/*
 * Create a ScrolledComposite with wrapping content.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Snippet166 {

public static void main(final String[] args) {
	Display display = new Display();
	Image image1 = display.getSystemImage(SWT.ICON_WORKING);
	Image image2 = display.getSystemImage(SWT.ICON_QUESTION);
	Image image3 = display.getSystemImage(SWT.ICON_ERROR);
	
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	
	final ScrolledComposite scrollComposite = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.BORDER);

	final Composite parent = new Composite(scrollComposite, SWT.NONE);
	for(int i = 0; i <= 50; i++) {
		Label label = new Label(parent, SWT.NONE);
		if (i % 3 == 0) {
			label.setImage(image1);
		}
		if (i % 3 == 1) {
			label.setImage(image2);
		}
		if (i % 3 == 2) {
			label.setImage(image3);
		}
	}
	RowLayout layout = new RowLayout(SWT.HORIZONTAL);
	layout.wrap = true;
	parent.setLayout(layout);
	
	scrollComposite.setContent(parent);
	scrollComposite.setExpandVertical(true);
	scrollComposite.setExpandHorizontal(true);
	scrollComposite.addControlListener(new ControlAdapter() {
		@Override
		public void controlResized(final ControlEvent e) {
			Rectangle r = scrollComposite.getClientArea();
			scrollComposite.setMinSize(parent.computeSize(r.width, SWT.DEFAULT));
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) {
			display.sleep();
		}
	}
	display.dispose();
}
}
