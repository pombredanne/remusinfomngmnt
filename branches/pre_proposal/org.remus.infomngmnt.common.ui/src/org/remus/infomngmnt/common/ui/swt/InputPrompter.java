package org.remus.infomngmnt.common.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Text;

public class InputPrompter {
	
	
	public static void addPrompt(final Text text, final String defaultText) {
		final Font defaultFont = text.getFont();
		FontData[] fontData = text.getFont().getFontData();
		for (FontData fontData2 : fontData) {
			fontData2.setStyle(SWT.ITALIC);
		}
		final Font italicDefault = new Font(text.getDisplay(), fontData);
		if (text.getText().length() == 0) {
			text.setText(defaultText);
			text.setForeground(text.getDisplay().getSystemColor(SWT.COLOR_GRAY));   
			text.setFont(italicDefault);
		}
	    text.addFocusListener(new FocusListener() {
	
	        public void focusGained(final FocusEvent e) {
	            if(text.getText().equals(defaultText)) {
	                text.setText("");
	                text.setForeground(null);
	                text.setFont(defaultFont);
	            }
	        }
	
	        public void focusLost(final FocusEvent e) {
	            if(text.getText().equals("")) {
	                text.setText(defaultText);
	                text.setForeground(text.getDisplay().getSystemColor(SWT.COLOR_GRAY));   
	                text.setFont(italicDefault);
	            }
	        }
	    });
	}
	
}

