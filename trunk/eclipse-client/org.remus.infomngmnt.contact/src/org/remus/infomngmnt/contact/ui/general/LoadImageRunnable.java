package org.remus.infomngmnt.contact.ui.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.util.NLS;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.FileUtil;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.progress.CancelableRunnable;

public class LoadImageRunnable extends CancelableRunnable {

	private String imagePath;
	private InformationUnit rawImageDataNode;
	private EditingDomain domain;
	private boolean executeOnEditingDomain = true;
	private File file;
	private InformationUnit infoUnit;

	@Override
	protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		monitor.beginTask("Checking filename...", IProgressMonitor.UNKNOWN);
		this.file = new File(this.imagePath);
		if (this.file.exists() && this.file.isFile()) {
			monitor.beginTask(NLS.bind("Reading file {0}", this.file.getName()), (int) this.file.length());
			try {
				if (this.domain == null) {
					this.domain = EditingUtil.getInstance().createNewEditingDomain();
				}

				final CompoundCommand cc = new CompoundCommand();
				cc.append(new SetCommand(this.domain, this.rawImageDataNode,
						InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE, FileUtil
								.getBytesFromFile(this.file, monitor)));
				
				InformationUnit childByType = InformationUtil.getChildByType(this.infoUnit,	ContactActivator.NODE_NAME_RAWDATA);				
			
				childByType = InfomngmntFactory.eINSTANCE.createInformationUnit();
				childByType.setType(ContactActivator.NODE_NAME_RAWDATA);
				
				cc.append(new AddCommand(this.domain, this.infoUnit.getChildValues(),
						Collections.singleton(childByType)));

				InputStream is = new FileInputStream(this.file);
				
				cc.append(new SetCommand(this.domain, InformationUtil.getChildByType(this.infoUnit,
						ContactActivator.ORIGINAL_FILEPATH),
						InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE, this.imagePath));
		
				cc.setLabel("Set new image");
				UIUtil.getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (LoadImageRunnable.this.executeOnEditingDomain) {
							LoadImageRunnable.this.domain.getCommandStack().execute(cc);
						} else {
							cc.execute();
						}
					}
				});
				is.close();
				is = null;
				return Status.OK_STATUS;
			} catch (IOException e) {
				return StatusCreator.newStatus("File not exisits or is not accessible");
			}
		}
		return StatusCreator.newStatus("File not exisits or is not accessible");
	}

	public void setImageNode(final InformationUnit infoUnit) {
		this.infoUnit = infoUnit;
		this.rawImageDataNode = InformationUtil.getChildByType(infoUnit,
				ContactActivator.NODE_NAME_RAWDATA);
	}

	public void setFile(final File file) {
		this.file = file;
	}

	/**
	 * @param executeOnEditingDomain
	 *            the executeOnEditingDomain to set
	 */
	public void setExecuteOnEditingDomain(final boolean executeOnEditingDomain) {
		this.executeOnEditingDomain = executeOnEditingDomain;
	}
	public File getFile() {
		return this.file;
	}
	public void setDomain(final EditingDomain domain) {
		this.domain = domain;
	}
	public void setImagePath(final String imagePath) {
		this.imagePath = imagePath;
	}

}
