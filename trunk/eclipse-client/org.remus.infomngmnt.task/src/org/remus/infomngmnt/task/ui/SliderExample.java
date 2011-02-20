package org.remus.infomngmnt.task.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

public class SliderExample {

	Display d;

	Shell s;

	SliderExample() {
		this.d = new Display();
		this.s = new Shell(this.d);
		this.s.setSize(250, 250);

		this.s.setText("A Slider Example");
		final Slider slide = new Slider(this.s, SWT.HORIZONTAL);
		slide.setBounds(115, 50, 25, 15);
		slide.setMinimum(0);
		slide.setMaximum(100);
		slide.setIncrement(1);

		final Text t = new Text(this.s, SWT.BORDER);
		t.setBounds(115, 25, 25, 25);
		t.setText("0");

		slide.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				t.setText(new Integer(slide.getSelection()).toString());
			}
		});

		this.s.open();
		while (!this.s.isDisposed()) {
			if (!this.d.readAndDispatch())
				this.d.sleep();
		}
		this.d.dispose();
	}

	public static void main(String[] args) {
		new SliderExample();
	}

}
