package org.remus.infomngmnt.connector.twitter.ui;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.util.InformationUtil;

public class MessageComposite extends Composite {

	private final FormToolkit toolkit;
	private final FormText metaFormText;
	private Label photoLabel;
	private final FormText messageText;

	public static final String REGEXP_USER = "@\\w+"; //$NON-NLS-1$
	public static final String KEYWORD_REGEXP = "#\\w+"; //$NON-NLS-1$
	public static final String URL_REGEXP = "((mailto\\:|(news|(ht|f)tp(s?))\\://){1}\\S+)"; //$NON-NLS-1$
	private ToolBarManager toolBarManager;

	public final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$
	private IHyperlinkListener messageListener;
	private IHyperlinkListener metaListener;
	private final boolean renderPhoto;
	private final boolean renderToolbar;
	private final boolean renderSource;

	public MessageComposite(final Composite parent, final int style, final FormToolkit toolkit) {
		this(parent, style, toolkit, true, true, true);
	}

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public MessageComposite(final Composite parent, final int style, final FormToolkit toolkit,
			final boolean renderPhoto, final boolean renderToolbar, final boolean renderSource) {
		super(parent, style);
		this.toolkit = toolkit;
		this.renderPhoto = renderPhoto;
		this.renderToolbar = renderToolbar;
		this.renderSource = renderSource;
		final TableWrapLayout tableWrapLayout = new TableWrapLayout();

		tableWrapLayout.numColumns = 2;

		setLayout(tableWrapLayout);
		this.toolkit.adapt(this);
		this.toolkit.paintBordersFor(this);

		this.metaFormText = this.toolkit.createFormText(this, false);
		final TableWrapData twd_metaFormText = new TableWrapData(TableWrapData.FILL,
				TableWrapData.TOP);
		twd_metaFormText.grabHorizontal = true;
		twd_metaFormText.colspan = 2;
		this.metaFormText.setLayoutData(twd_metaFormText);
		this.metaFormText.setFont(ResourceManager.getFont("Arial", 8, SWT.ITALIC));

		if (renderPhoto) {
			this.photoLabel = this.toolkit.createLabel(this, "n.a.", SWT.NONE);
			final TableWrapData twd_photo = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
			twd_photo.rowspan = 2;
			twd_photo.maxWidth = 48;
			twd_photo.maxHeight = 48;
			twd_photo.heightHint = 48;

			this.photoLabel.setLayoutData(twd_photo);
		}

		this.messageText = this.toolkit.createFormText(this, false);
		final TableWrapData twd_formText = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		twd_formText.grabHorizontal = true;
		if (!renderPhoto) {
			twd_formText.colspan = 2;
		}
		this.messageText.setLayoutData(twd_formText);
		this.messageText.setText("FormText", false, false);

		if (renderToolbar) {
			final ToolBar toolBar = new ToolBar(this, SWT.NONE);
			this.toolkit.adapt(toolBar, true, true);
			final TableWrapData twd_toolBar = new TableWrapData(TableWrapData.RIGHT,
					TableWrapData.TOP);

			toolBar.setLayoutData(twd_toolBar);
			this.toolBarManager = new ToolBarManager(toolBar);
		}

		final Label label = this.toolkit.createSeparator(this, SWT.HORIZONTAL);
		final TableWrapData twd_label = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		twd_label.colspan = 2;
		label.setLayoutData(twd_label);
		//
	}

	public void setMetaFormHyperlinkListener(final IHyperlinkListener listener) {
		this.metaListener = listener;
		this.metaFormText.addHyperlinkListener(listener);
	}

	public void removeMetaFormHyperlinkListener() {
		this.metaFormText.removeHyperlinkListener(this.metaListener);
	}

	public void setMessageFormHyperlinkListener(final IHyperlinkListener listener) {
		this.messageListener = listener;
		this.messageText.addHyperlinkListener(listener);
	}

	public void removeMessageFormHyperlinkListener() {
		this.messageText.removeHyperlinkListener(this.messageListener);
	}

	public void addAction(final IAction action) {
		if (this.renderToolbar) {
			this.toolBarManager.add(action);
			this.toolBarManager.update(true);
		}
	}

	public void setValues(final InformationUnit message) {
		InformationStructureRead read = InformationStructureRead.newSession(message,
				TwitterActivator.INFOTYPE_ID);
		Date dateValue = (Date) read.getValueByNodeId(TwitterActivator.MESSAGE_DATE_TYPE);
		// user-id
		String userId = (String) read.getValueByNodeId(TwitterActivator.MESSAGE_USER_ID_TYPE);
		// user-name
		String userName = (String) read.getValueByNodeId(TwitterActivator.MESSAGE_USER_TYPE);
		// direct messages have no source type
		InformationUnit childByType = InformationUtil.getChildByType(message,
				TwitterActivator.MESSAGE_SRC_TYPE);
		String sourceLink = childByType != null ? childByType.getStringValue() : "unknown";

		// reply to - user
		InformationUnit reply = InformationUtil.getChildByType(message, TwitterActivator.REPLY_ID);
		String replyId;
		if (reply == null || reply.getStringValue() == null
				|| reply.getStringValue().trim().length() == 0) {
			replyId = null;
		} else {
			replyId = reply.getStringValue();
		}
		Long replyStatusId = (Long) read.getValueByNodeId(TwitterActivator.REPLY_STATUS_ID);

		// twitter-client
		// internal twitter-id
		Long internalId = InformationUtil.getChildByType(message,
				TwitterActivator.MESSAGE_INTERNAL_ID).getLongValue();
		buildMeta(dateValue, userId, userName, sourceLink, internalId, replyId, replyStatusId);

		String content = InformationUtil.getChildByType(message,
				TwitterActivator.MESSAGE_CONTENT_TYPE).getStringValue();
		String newContent = TwitterUtil.parseContent(content);
		try {
			this.messageText.setText(newContent, true, false);
		} catch (Exception e) {
			this.messageText.setText(content, false, false);
		}
		if (this.renderPhoto) {
			InformationUnitListItem listItem = (InformationUnitListItem) message
					.getAdapter(InformationUnitListItem.class);
			setImage(userId, listItem.getSynchronizationMetaData().getRepositoryId());
		}

	}

	private void setImage(final String userId, final String repositoryId) {
		TwitterActivator.getDefault().getImageCache().getImageByUser(userId, repositoryId,
				new ImageCacheCallBack() {
					public void callBack(final String path2File) {
						Image image = ResourceManager.getImage(path2File);
						((TableWrapData) MessageComposite.this.photoLabel.getLayoutData()).heightHint = image
								.getImageData().height;
						;
						MessageComposite.this.photoLabel.setText("");
						MessageComposite.this.photoLabel.setImage(image);

					}
				});

	}

	private void buildMeta(final Date dateValue, final String userId, final String userName,
			final String sourceLink, final Long internalId, final String replyId,
			final Long replyStatusId) {
		StringWriter sw = new StringWriter();
		sw.append("<form><p vspace=\"false\">");
		sw.append("<a href=\"user." + userId + "\">").append(userName).append("</a>, ");
		sw.append(SDF.format(dateValue));
		if (this.renderSource) {
			sw.append(" via ");
			sw.append(sourceLink);
		}
		if (replyId != null) {
			sw.append(" in reply to ").append(
					"<a href=\"reply." + replyStatusId + "." + replyId + "\">").append(replyId)
					.append("</a>");
		}
		sw.append("</p></form>");
		this.metaFormText.setText(sw.toString(), true, false);
		this.metaFormText.setData(TwitterActivator.MESSAGE_INTERNAL_ID, internalId);
	}

	/**
	 * @return the metaFormText
	 */
	public FormText getMetaFormText() {
		return this.metaFormText;
	}

	/**
	 * @return the messageText
	 */
	public FormText getMessageText() {
		return this.messageText;
	}
}
