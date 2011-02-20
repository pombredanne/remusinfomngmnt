package org.remus.infomngmnt.task.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

public class Snippet {
	public static void main(String[] args) {
		final Display d = new Display();
		Shell s = new Shell(d, SWT.SHELL_TRIM);
		s.setLayout(new GridLayout());

		final Text text = new Text(s, SWT.NONE);
		final Slider slider = new Slider(s, SWT.HORIZONTAL);
		final int thumb = 10;
		slider.setMaximum(100);
		slider.setThumb(thumb);
		slider.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText(Integer.toString(slider.getSelection()));
				System.out.println(text.getText());
				text.update();
				if (slider.getSelection() == slider.getMaximum() - thumb)
					slider.setMaximum(slider.getMaximum() + 10);
			}
		});

		s.open();
		while (!s.isDisposed()) {
			if (!d.readAndDispatch())
				d.sleep();
		}

		d.dispose();
	}

}
