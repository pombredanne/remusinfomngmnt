package org.remus.infomngmnt.connector.twitter.ui;

import java.io.StringReader;

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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import org.remus.infomngmnt.connector.twitter.TwitterRepository;

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
		newShell.setText("Grant Remus Access to your twitter-account");
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		this.twitter = new Twitter();
		this.twitter.setOAuthConsumer(TwitterRepository.CONSUMER_KEY,
				TwitterRepository.CONSUMER_SECRET);
		String authorizationURL = null;
		try {
			this.oAuthRequestToken = this.twitter.getOAuthRequestToken();
			authorizationURL = this.oAuthRequestToken.getAuthorizationURL();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Browser browser = new Browser(container, SWT.NONE);
		browser.setUrl(authorizationURL);
		browser.addProgressListener(new ProgressListener() {

			public void completed(final ProgressEvent event) {
				if ((OAuthDialog.this.pin = getPin(browser.getText())) != null) {
					okPressed();
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
			AccessToken accessToken = this.twitter.getOAuthAccessToken(this.oAuthRequestToken,
					this.pin);
			this.token = accessToken.getToken();
			this.tokenSecret = accessToken.getTokenSecret();
			super.okPressed();
		} catch (Exception e) {
			ErrorDialog.openError(getShell(), "Error accessing twitter",
					"An error occurred while connecting to twitter", StatusCreator.newStatus(
							"Error getting token", e));
		}
	}

	public String getToken() {
		return this.token;
	}

	public String getTokenSecret() {
		return this.tokenSecret;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	private String getPin(final String html) {
		final DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new StringReader(html)));
			final Document document = parser.getDocument();

			Element elementsByTagName = document.getElementById("oauth_pin");
			if (elementsByTagName != null) {
				return org.apache.commons.lang.StringUtils.trim(elementsByTagName.getTextContent());
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
