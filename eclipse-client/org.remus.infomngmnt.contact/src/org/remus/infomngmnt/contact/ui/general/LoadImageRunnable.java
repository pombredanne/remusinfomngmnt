package org.remus.infomngmnt.contact.ui.general;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.streams.FileUtil;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.progress.CancelableRunnable;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;

import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.messages.Messages;

public class LoadImageRunnable extends CancelableRunnable {

	private String imagePath;
	private InformationUnit rawImageDataNode;
	private EditingDomain domain;
	private boolean executeOnEditingDomain = true;
	private File file;
	private InformationUnit infoUnit;
	private Point scaling;

	@Override
	protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		monitor.beginTask(Messages.LoadImageRunnable_CheckFilename, IProgressMonitor.UNKNOWN);
		this.file = new File(this.imagePath);
		IEditingHandler service = ContactActivator.getDefault().getServiceTracker().getService(
				IEditingHandler.class);
		if (this.file.exists() && this.file.isFile()) {
			monitor.beginTask(NLS.bind(Messages.LoadImageRunnable_ReadFile, this.file.getName()), (int) this.file
					.length());
			try {
				if (this.domain == null) {
					this.domain = service.createNewEditingDomain();
				}

				final CompoundCommand cc = new CompoundCommand();
				byte[] bytesFromFile = FileUtil.getBytesFromFile(this.file, monitor);
				if (this.scaling != null) {
					ImageLoader loader = new ImageLoader();
					loader.load(new ByteArrayInputStream(bytesFromFile));
					ImageData imageScaled = scaleImageToTarget(loader.data[0], this.scaling.x,
							this.scaling.y);
					loader.data[0] = imageScaled;
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					loader.save(baos, SWT.IMAGE_PNG);
					bytesFromFile = baos.toByteArray();
					baos.close();
				}

				cc.append(SetCommand.create(this.domain, this.rawImageDataNode,
						InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE, bytesFromFile));

				InformationUnit childByType = InformationUtil.getChildByType(this.infoUnit,
						ContactActivator.NODE_NAME_RAWDATA_IMAGE);

				childByType = InfomngmntFactory.eINSTANCE.createInformationUnit();
				childByType.setType(ContactActivator.NODE_NAME_RAWDATA_IMAGE);

				cc.append(new AddCommand(this.domain, this.infoUnit.getChildValues(), Collections
						.singleton(childByType)));

				InputStream is = new FileInputStream(this.file);

				cc.append(SetCommand.create(this.domain, InformationUtil.getChildByType(
						this.infoUnit, ContactActivator.ORIGINAL_FILEPATH),
						InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE, this.imagePath));

				cc.setLabel(Messages.LoadImageRunnable_SettingImage);
				UIUtil.getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (LoadImageRunnable.this.executeOnEditingDomain) {
							LoadImageRunnable.this.domain.getCommandStack().execute(cc);
						} else {
							cc.execute();
						}
						if (LoadImageRunnable.this.domain instanceof IDisposable) {
							((IDisposable) LoadImageRunnable.this.domain).dispose();
						}
					}
				});
				is.close();
				is = null;
				return Status.OK_STATUS;
			} catch (IOException e) {
				return StatusCreator.newStatus(Messages.LoadImageRunnable_FileNotAccessible);
			}
		}
		ContactActivator.getDefault().getServiceTracker().ungetService(service);
		return StatusCreator.newStatus(Messages.LoadImageRunnable_FileNotAccessible);
	}

	public void setImageNode(final InformationUnit infoUnit, final String string) {
		this.infoUnit = infoUnit;
		this.rawImageDataNode = InformationUtil.getChildByType(infoUnit, string);
	}

	public void setFile(final File file) {
		this.file = file;
	}

	public static ImageData scaleImageToTarget(final ImageData inputData, final double targetX,
			final double targetY) {

		double imageX = inputData.width;
		double imageY = inputData.height;
		ImageData imageScaled = inputData;

		double deltaX = imageX / targetX;
		double deltaY = imageY / targetY;

		if (deltaX > deltaY) {
			int scaledX = (int) (imageX / deltaX);
			int scaledY = (int) (imageY / deltaX);
			imageScaled = inputData.scaledTo(scaledX, scaledY);
		} else if (deltaX <= deltaY) {
			int scaledX = (int) (imageX / deltaY);
			int scaledY = (int) (imageY / deltaY);
			imageScaled = inputData.scaledTo(scaledX, scaledY);
		}
		return imageScaled;
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

	/**
	 * @param scaling
	 *            the scaling to set
	 */
	public void setScaling(final Point scaling) {
		this.scaling = scaling;
	}

}
