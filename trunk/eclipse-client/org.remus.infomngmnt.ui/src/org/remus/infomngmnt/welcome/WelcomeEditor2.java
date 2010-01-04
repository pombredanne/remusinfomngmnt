package org.remus.infomngmnt.welcome;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.AbstractHyperlink;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;

import org.remus.infomngmnt.common.ui.html.OfflineBrowser;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.jslib.TemplateLocation;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.preference.UIPreferenceInitializer;

public class WelcomeEditor2 extends EditorPart {

	public static final String ID = "org.remus.infomngmnt.welcome.WelcomeEditor"; //$NON-NLS-1$

	/**
	 * Create contents of the editor part
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(final Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		Image image = ResourceManager.getImage(WelcomeEditor2.class, "rim_banner_left.png");
		Form form = toolkit.createForm(parent);
		Composite body = form.getBody();
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		body.setLayout(layout);
		toolkit.paintBordersFor(body);

		final Label label_1 = new Label(body, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		label_1.setImage(image);
		label_1.setBackgroundImage(ResourceManager.getImage(WelcomeEditor2.class,
				"rim_banner_right.png"));
		toolkit.adapt(label_1, true, true);

		Composite createComposite = toolkit.createComposite(body);
		createComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createComposite.setLayout(new GridLayout());
		final Section section = toolkit.createSection(createComposite, Section.DESCRIPTION);
		section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section
				.setDescription("Welcome to Remus Information Management. This application allows you to store all your incoming information in an easy and comfortable way. If you are new to Remus check out the 5-minutes video to see what Remus can do for you. Have fun!");

		final Composite composite_1 = toolkit.createComposite(section, SWT.NONE);
		composite_1.setLayout(new TableWrapLayout());
		toolkit.paintBordersFor(composite_1);
		section.setClient(composite_1);

		final ImageHyperlink learnRimInImageHyperlink = toolkit.createImageHyperlink(
				createComposite, SWT.NONE);
		learnRimInImageHyperlink.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		learnRimInImageHyperlink.setText("Learn Remus in 5 Minutes (Video)");
		learnRimInImageHyperlink.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(),
				"icons/iconexperience/16/film.png"));
		learnRimInImageHyperlink.setHref(UIPlugin.getDefault().getPreferenceStore().getString(
				UIPreferenceInitializer.VIDEO_LINK));

		final Section linksSection = toolkit.createSection(createComposite, SWT.NONE);
		linksSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		linksSection.setText("Links");

		final Composite composite_2 = toolkit.createComposite(linksSection, SWT.NONE);
		final TableWrapLayout tableWrapLayout = new TableWrapLayout();
		composite_2.setLayout(tableWrapLayout);
		toolkit.paintBordersFor(composite_2);
		linksSection.setClient(composite_2);

		final ImageHyperlink imageHyperlink_1 = toolkit.createImageHyperlink(composite_2, SWT.NONE);
		imageHyperlink_1.setLayoutData(new TableWrapData(TableWrapData.FILL, TableWrapData.TOP));
		imageHyperlink_1.setText("Frequently Asked Questions");
		imageHyperlink_1.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(),
				"icons/iconexperience/16/help.png"));
		imageHyperlink_1.setHref(UIPlugin.getDefault().getPreferenceStore().getString(
				UIPreferenceInitializer.FAQ_LINK));

		final ImageHyperlink imageHyperlink = toolkit.createImageHyperlink(composite_2, SWT.NONE);
		imageHyperlink.setLayoutData(new TableWrapData(TableWrapData.FILL, TableWrapData.TOP));
		imageHyperlink.setText("User Documentation");
		imageHyperlink.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(),
				"icons/iconexperience/16/help.png"));
		imageHyperlink.setHref(UIPlugin.getDefault().getPreferenceStore().getString(
				UIPreferenceInitializer.USER_DOCUMENTATION_LINK));
		addHyperlinks(learnRimInImageHyperlink, imageHyperlink, imageHyperlink_1);

		final Label label = new Label(createComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		toolkit.adapt(label, true, true);

		final Section latestNewsSection = toolkit.createSection(createComposite, SWT.NONE);
		latestNewsSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		latestNewsSection.setText("Latest News");

		final Composite composite = toolkit.createComposite(latestNewsSection, SWT.NONE);
		composite.setLayout(new GridLayout());
		toolkit.paintBordersFor(composite);
		latestNewsSection.setClient(composite);

		final Browser browser = new Browser(composite, SWT.NONE);
		browser.setMenu(new Menu(browser));
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		toolkit.adapt(browser, true, true);

		new OfflineBrowser(browser).init(TemplateLocation.getLoadingUrl(),
				new DownloadLatestNewsJob());

		final Label label2 = new Label(createComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		toolkit.adapt(label2, true, true);

		final Button button = toolkit.createButton(createComposite, "Show this page on startup",
				SWT.CHECK);
		button.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		button.setSelection(UIPlugin.getDefault().getPreferenceStore().getBoolean(
				UIPreferenceInitializer.SHOW_WELCOME));
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				UIPlugin.getDefault().getPreferenceStore().setValue(
						UIPreferenceInitializer.SHOW_WELCOME,
						((Button) event.widget).getSelection());
			}
		});
	}

	private void addHyperlinks(final ImageHyperlink... hyperlinks) {
		for (ImageHyperlink imageHyperlink : hyperlinks) {
			imageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(final HyperlinkEvent e) {
					final String href = ((AbstractHyperlink) e.widget).getHref().toString();
					getSite().getShell().getDisplay().asyncExec(new Runnable() {
						public void run() {
							Program.launch(href);
						}
					});
				}
			});
		}

	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// Do the Save operation
	}

	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}
