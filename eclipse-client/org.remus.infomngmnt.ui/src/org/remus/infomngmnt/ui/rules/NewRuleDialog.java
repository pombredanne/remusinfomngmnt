package org.remus.infomngmnt.ui.rules;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.AvailableRuleDefinitions;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.RemusTransferType;
import org.remus.infomngmnt.util.StatusCreator;

public class NewRuleDialog extends StatusDialog {

	private Combo combo;
	private Text text;
	private final AvailableRuleDefinitions model;
	private final NewElementRules newElement;
	private EMFDataBindingContext ctx;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public NewRuleDialog(Shell parentShell, AvailableRuleDefinitions model) {
		super(parentShell);
		this.model = model;
		this.newElement = InfomngmntFactory.eINSTANCE.createNewElementRules();
		this.newElement.setDeletable(true);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		final Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("Name");

		this.text = new Text(container, SWT.BORDER);
		final GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_text.minimumWidth = 200;
		this.text.setLayoutData(gd_text);

		final Label baseRulesetLabel = new Label(container, SWT.NONE);
		baseRulesetLabel.setText("Base Ruleset:");

		this.combo = new Combo(container, SWT.READ_ONLY);
		final GridData gd_combo = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_combo.minimumWidth = 200;
		this.combo.setLayoutData(gd_combo);
		EList<NewElementRules> newElementRules = this.model.getNewElementRules();
		for (NewElementRules newElementRules2 : newElementRules) {
			this.combo.add(newElementRules2.getName());
		}
		this.combo.select(0);
		//
		bindValuesToUi();
		return container;
	}

	private void bindValuesToUi() {
		this.ctx = new EMFDataBindingContext();
		ISWTObservableValue swtName = SWTObservables.observeText(this.text, SWT.Modify);
		IObservableValue emfName = EMFObservables.observeValue(this.newElement, InfomngmntPackage.Literals.NEW_ELEMENT_RULES__NAME);
		this.ctx.bindValue(swtName, emfName, new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				checkName(value);
				return super.convert(value);
			}
		}, null);

	}

	protected void checkName(Object value) {
		EList<NewElementRules> newElementRules = this.model.getNewElementRules();
		for (NewElementRules newElementRules2 : newElementRules) {
			if (newElementRules2.getName().equals(value)) {
				updateStatus(StatusCreator.newStatus("Rule already exists"));
				return;
			}
		}
		updateStatus(Status.OK_STATUS);

	}
	@Override
	protected void okPressed() {
		copyTransfers();
		super.okPressed();
	}

	private void copyTransfers() {
		EList<NewElementRules> newElementRules = this.model.getNewElementRules();
		for (NewElementRules newElementRules2 : newElementRules) {
			if (newElementRules2.getName().equals(this.combo.getText())) {
				EList<RemusTransferType> transferTypes = newElementRules2.getTransferTypes();
				for (RemusTransferType remusTransferType : transferTypes) {
					this.newElement.getTransferTypes().add((RemusTransferType) EcoreUtil.copy(remusTransferType));
				}
			}
		}

	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Ruleset");
	}

	public NewElementRules getNewElement() {
		return this.newElement;
	}



}
