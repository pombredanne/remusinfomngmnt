package org.remus.infomngmnt.common.ui.jface;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.texteditor.IUpdate;

public class TextViewerAction extends Action implements IUpdate {
	private int operationCode = -1;
	private final ITextOperationTarget operationTarget;

	public TextViewerAction(final ITextViewer viewer, final int operationCode) {
		this.operationCode = operationCode;
		this.operationTarget = viewer.getTextOperationTarget();
		update();
	}

	public void update() {
		boolean wasEnabled = isEnabled();
		boolean isEnabled = (this.operationTarget != null && this.operationTarget
				.canDoOperation(this.operationCode));
		setEnabled(isEnabled);
		if (wasEnabled != isEnabled) {
			firePropertyChange(ENABLED, wasEnabled ? Boolean.TRUE : Boolean.FALSE,
					isEnabled ? Boolean.TRUE : Boolean.FALSE);
		}
	}

	@Override
	public void run() {
		if (this.operationCode != -1 && this.operationTarget != null) {
			this.operationTarget.doOperation(this.operationCode);
		}
	}
}