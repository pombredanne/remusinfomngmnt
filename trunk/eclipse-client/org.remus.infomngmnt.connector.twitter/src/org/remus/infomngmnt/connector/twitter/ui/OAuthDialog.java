package org.remus.infomngmnt.connector.twitter.ui;

import java.io.StringReader;
import java.util.concurrent.atomic.AtomicReference;

import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.remus.infomngmnt.connector.twitter.Messages;
import org.remus.infomngmnt.connector.twitter.TwitterRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

public class OAuthDialog extends Dialog {

	private Twitter twitter;
	private RequestToken oAuthRequestToken;
	protected String pin;
	private String token;
	private String tokenSecret;
	private Text text;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @param twitter
	 */
	public OAuthDialog(final Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.OAuthDialog_GrantAccess);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		twitter = new Twitter();
		twitter.setOAuthConsumer(TwitterRepository.CONSUMER_KEY,
				TwitterRepository.CONSUMER_SECRET);
		final AtomicReference<String> authorizationURL = new AtomicReference<String>();
		try {
			oAuthRequestToken = twitter.getOAuthRequestToken();
			authorizationURL.set(oAuthRequestToken.getAuthorizationURL());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		final Browser browser = new Browser(container, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		browser.setUrl(authorizationURL.get());

		final Label lblEnterThePin = new Label(container, SWT.NONE);
		lblEnterThePin.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEnterThePin.setText(Messages.OAuthDialog_lblEnterThePin_text);
		lblEnterThePin.setVisible(false);

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.setVisible(false);
		((GridData) text.getLayoutData()).exclude = true;
		((GridData) lblEnterThePin.getLayoutData()).exclude = true;

		browser.addProgressListener(new ProgressListener() {

			public void completed(final ProgressEvent event) {
				if (!browser.getUrl().equals(authorizationURL.get())) {
					text.setVisible(true);
					lblEnterThePin.setVisible(true);
					((GridData) text.getLayoutData()).exclude = false;
					((GridData) lblEnterThePin.getLayoutData()).exclude = false;
					container.layout(true);
				}
			}

			public void changed(final ProgressEvent event) {
				// TODO Auto-generated method stub

			}
		});

		return container;
	}

	@Override
	protected void okPressed() {
		try {
			AccessToken accessToken = twitter.getOAuthAccessToken(
					oAuthRequestToken, text.getText());
			token = accessToken.getToken();
			tokenSecret = accessToken.getTokenSecret();
			super.okPressed();
		} catch (Exception e) {
			ErrorDialog.openError(getShell(),
					Messages.OAuthDialog_ErrorAccessingTwitter,
					Messages.OAuthDialog_ErrorConnecting, StatusCreator
							.newStatus(Messages.OAuthDialog_ErrorGettingToken,
									e));
		}
	}

	public String getToken() {
		return token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	private String getPin(final String html) {
		final DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new StringReader(html)));
			final Document document = parser.getDocument();

			Element elementsByTagName = document.getElementById("oauth_pin"); //$NON-NLS-1$
			if (elementsByTagName != null) {
				return org.apache.commons.lang.StringUtils
						.trim(elementsByTagName.getTextContent());
			}
		} catch (Exception e) {
			// do nothing
		}
		return null;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(830, 500);
	}
}
