package org.eclipse.remus.welcome;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.epp.internal.mpc.ui.CatalogRegistry;
import org.eclipse.epp.internal.mpc.ui.commands.MarketplaceWizardCommand;
import org.eclipse.epp.mpc.ui.CatalogDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.common.ui.html.OfflineBrowser;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.core.services.IInformationTypeHandler;
import org.eclipse.remus.js.TemplateLocation;
import org.eclipse.remus.ui.UIPlugin;
import org.eclipse.remus.ui.preference.UIPreferenceInitializer;
import org.eclipse.remus.ui.remote.service.IRepositoryExtensionService;
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
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;

public class WelcomeEditor2 extends EditorPart {
	public WelcomeEditor2() {
	}

	public static final String ID = "org.eclipse.remus.welcome.WelcomeEditor"; //$NON-NLS-1$

	/**
	 * Create contents of the editor part
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(final Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		Image image = ResourceManager.getImage(WelcomeEditor2.class,
				"rim_banner_left.png");
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
		label_1.setBackgroundImage(ResourceManager.getImage(
				WelcomeEditor2.class, "rim_banner_right.png"));
		toolkit.adapt(label_1, true, true);

		Composite createComposite = toolkit.createComposite(body);
		createComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		createComposite.setLayout(new GridLayout());
		final Section section = toolkit.createSection(createComposite,
				Section.DESCRIPTION);
		section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section.setDescription("Welcome to Remus Information Management. This application allows you to store all your incoming information in an easy and comfortable way. If you are new to Remus check out the 5-minutes video to see what Remus can do for you. Have fun!");

		final Composite composite_1 = toolkit
				.createComposite(section, SWT.NONE);
		toolkit.paintBordersFor(composite_1);
		section.setClient(composite_1);
		composite_1.setLayout(new GridLayout(1, false));

		Section sctnStatus = toolkit.createSection(createComposite, SWT.NONE);
		sctnStatus.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		toolkit.paintBordersFor(sctnStatus);
		sctnStatus.setText("Installation Status");

		Composite composite_3 = toolkit.createComposite(sctnStatus, SWT.NONE);
		toolkit.paintBordersFor(composite_3);
		sctnStatus.setClient(composite_3);
		composite_3.setLayout(new GridLayout(3, false));

		ImageHyperlink mghprlnkInformationTypes = toolkit.createImageHyperlink(
				composite_3, SWT.NONE);
		mghprlnkInformationTypes.setLayoutData(new GridData(SWT.CENTER,
				SWT.CENTER, false, false, 1, 1));
		toolkit.paintBordersFor(mghprlnkInformationTypes);
		IInformationTypeHandler service = UIPlugin.getDefault()
				.getServiceTracker().getService(IInformationTypeHandler.class);
		int installedInformationTypes = service.getTypes().size();
		mghprlnkInformationTypes.setText(NLS.bind("{0} Information type(s)",
				installedInformationTypes));

		ImageHyperlink mghprlnkConnectorsInstalled = toolkit
				.createImageHyperlink(composite_3, SWT.NONE);
		mghprlnkConnectorsInstalled.setLayoutData(new GridData(SWT.CENTER,
				SWT.CENTER, false, false, 1, 1));
		toolkit.paintBordersFor(mghprlnkConnectorsInstalled);
		IRepositoryExtensionService service2 = UIPlugin.getDefault()
				.getServiceTracker()
				.getService(IRepositoryExtensionService.class);
		int installedRepositoryUis = service2.getAllItems().size();
		mghprlnkConnectorsInstalled.setText(NLS.bind("{0} Connector(s)",
				installedRepositoryUis));

		ImageHyperlink mghprlnkInstallAddons = toolkit.createImageHyperlink(
				composite_3, SWT.NONE);
		mghprlnkInstallAddons.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				true, false, 1, 1));
		toolkit.paintBordersFor(mghprlnkInstallAddons);
		mghprlnkInstallAddons.setText("Install Add-Ons...");
		mghprlnkInstallAddons.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				MarketplaceWizardCommand command = new MarketplaceWizardCommand();
				List<CatalogDescriptor> catalogDescriptors = CatalogRegistry
						.getInstance().getCatalogDescriptors();
				command.setCatalogDescriptors(catalogDescriptors);
				for (CatalogDescriptor catalogDescriptor : catalogDescriptors) {
					if (catalogDescriptor.getLabel().toLowerCase()
							.contains("remus")) {
						command.setSelectedCatalogDescriptor(catalogDescriptor);
						break;
					}
				}
				try {
					command.execute(new ExecutionEvent());
				} catch (ExecutionException e1) {
					// do nothing.
				}
			}
		});

		final Section linksSection = toolkit.createSection(createComposite,
				SWT.NONE);
		linksSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		linksSection.setText("Links");

		final Composite composite_2 = toolkit.createComposite(linksSection,
				SWT.NONE);
		final TableWrapLayout tableWrapLayout = new TableWrapLayout();
		composite_2.setLayout(tableWrapLayout);
		toolkit.paintBordersFor(composite_2);
		linksSection.setClient(composite_2);

		FormText formText = toolkit.createFormText(composite_2, false);
		formText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,
				TableWrapData.TOP, 1, 1));
		toolkit.paintBordersFor(formText);
		formText.setText(
				"<form><p><img href=\"info\" /> <a>Getting started</a> - Learn how to use the different functionalities of Remus.</p></form>",
				true, false);
		formText.setImage("info", ResourceManager.getPluginImage(
				UIPlugin.getDefault(), "icons/iconexperience/16/help.png"));
		formText.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				Program.launch("http://wiki.eclipse.org/Remus/Getting_started");
			}
		});

		FormText formText_1 = toolkit.createFormText(composite_2, false);
		formText_1.setLayoutData(new TableWrapData(TableWrapData.FILL,
				TableWrapData.TOP, 1, 1));
		toolkit.paintBordersFor(formText_1);
		formText_1
				.setText(
						"<form><p><img href=\"info\" /> <a>Install add-ons, new information types or remote connectors</a> - Extend your Remus installation with new functionalities.</p></form>",
						true, false);
		formText_1.setImage("info", ResourceManager.getPluginImage(
				UIPlugin.getDefault(), "icons/iconexperience/16/help.png"));
		formText_1.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				MarketplaceWizardCommand command = new MarketplaceWizardCommand();
				List<CatalogDescriptor> catalogDescriptors = CatalogRegistry
						.getInstance().getCatalogDescriptors();
				command.setCatalogDescriptors(catalogDescriptors);
				for (CatalogDescriptor catalogDescriptor : catalogDescriptors) {
					if (catalogDescriptor.getLabel().toLowerCase()
							.contains("remus")) {
						command.setSelectedCatalogDescriptor(catalogDescriptor);
						break;
					}
				}
			}
		});

		FormText formText_2 = toolkit.createFormText(composite_2, false);
		toolkit.paintBordersFor(formText_2);
		formText_2
				.setText(
						"<form><p><img href=\"info\" /> <a>User documentation</a> - Browse through the detailed user documentation to learn Remus.</p></form>",
						true, false);
		formText_2.setImage("info", ResourceManager.getPluginImage(
				UIPlugin.getDefault(), "icons/iconexperience/16/help.png"));
		formText_2.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				Program.launch("http://wiki.eclipse.org/Remus");
			}
		});

		FormText formText_3 = toolkit.createFormText(composite_2, false);
		toolkit.paintBordersFor(formText_3);
		formText_3
				.setText(
						"<form><p><img href=\"info\" /> <a>User Forums</a> - Get help in the user forums from the Remus community.</p></form>",
						true, false);
		formText_3.setImage("info", ResourceManager.getPluginImage(
				UIPlugin.getDefault(), "icons/iconexperience/16/help.png"));
		formText_3.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				Program.launch("http://www.eclipse.org/forums/index.php?t=thread&frm_id=166");
			}
		});

		FormText formText_4 = toolkit.createFormText(composite_2, false);
		toolkit.paintBordersFor(formText_4);
		formText_4
				.setText(
						"<form><p><img href=\"info\" /> <a>Get involved</a> - Report bugs and feedback, request new features or join our development team.</p></form>",
						true, false);
		formText_4.setImage("info", ResourceManager.getPluginImage(
				UIPlugin.getDefault(), "icons/iconexperience/16/help.png"));
		formText_4.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				Program.launch("http://remus-software.org/jira");
			}
		});

		final Label label = new Label(createComposite, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		toolkit.adapt(label, true, true);

		final Section latestNewsSection = toolkit.createSection(
				createComposite, SWT.NONE);
		latestNewsSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		latestNewsSection.setText("Latest News");

		final Composite composite = toolkit.createComposite(latestNewsSection,
				SWT.NONE);
		composite.setLayout(new GridLayout());
		toolkit.paintBordersFor(composite);
		latestNewsSection.setClient(composite);

		final Browser browser = new Browser(composite, SWT.NONE);
		browser.setMenu(new Menu(browser));
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		toolkit.adapt(browser, true, true);

		new OfflineBrowser(browser).init(TemplateLocation.getLoadingUrl(),
				new DownloadLatestNewsJob());

		final Label label2 = new Label(createComposite, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		label2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		toolkit.adapt(label2, true, true);

		final Button button = toolkit.createButton(createComposite,
				"Show this page on startup", SWT.CHECK);
		button.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		button.setSelection(UIPlugin.getDefault().getPreferenceStore()
				.getBoolean(UIPreferenceInitializer.SHOW_WELCOME));
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				UIPlugin.getDefault()
						.getPreferenceStore()
						.setValue(UIPreferenceInitializer.SHOW_WELCOME,
								((Button) event.widget).getSelection());
			}
		});
	}

	private void addHyperlinks(final ImageHyperlink... hyperlinks) {
		for (ImageHyperlink imageHyperlink : hyperlinks) {
			imageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(final HyperlinkEvent e) {
					final String href = ((AbstractHyperlink) e.widget)
							.getHref().toString();
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
	public void init(final IEditorSite site, final IEditorInput input)
			throws PartInitException {
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
