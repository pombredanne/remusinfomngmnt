package org.remus.infomngmnt.common.ui.quickaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.SWTKeySupport;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.progress.ProgressManagerUtil;
import org.eclipse.ui.internal.quickaccess.QuickAccessMessages;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.themes.ColorUtil;


/**
 * @since 3.3
 * 
 */
@SuppressWarnings("restriction")
public class QuickAccessDialog extends PopupDialog {
	private static final int INITIAL_COUNT_PER_PROVIDER = 5;
	private static final int MAX_COUNT_TOTAL = 20;

	private Text filterText;

	private QuickAccessProvider[] providers;
	private final IWorkbenchWindow window;

	private Table table;

	private LocalResourceManager resourceManager = new LocalResourceManager(
			JFaceResources.getResources());

	private static final String TEXT_ARRAY = "textArray"; //$NON-NLS-1$
	private static final String TEXT_ENTRIES = "textEntries"; //$NON-NLS-1$
	private static final String ORDERED_PROVIDERS = "orderedProviders"; //$NON-NLS-1$
	private static final String ORDERED_ELEMENTS = "orderedElements"; //$NON-NLS-1$
	static final int MAXIMUM_NUMBER_OF_ELEMENTS = 60;
	static final int MAXIMUM_NUMBER_OF_TEXT_ENTRIES_PER_ELEMENT = 3;

	protected String rememberedText;

	protected Map textMap = new HashMap();

	protected Map elementMap = new HashMap();

	protected Map providerMap;
	// private Font italicsFont;
	private Color grayColor;
	private TextLayout textLayout;
	private TriggerSequence[] invokingCommandKeySequences;
	private Command invokingCommand;
	private KeyAdapter keyAdapter;
	private boolean showAllMatches = false;
	protected boolean resized = false;



	public QuickAccessDialog(IWorkbenchWindow window, final Command invokingCommand, final QuickAccessProvider[] provider) {
		super(ProgressManagerUtil.getDefaultParent(), SWT.RESIZE, true, true, // persist size
				false, // but not location
				true, true, null,
				QuickAccessMessages.QuickAccess_StartTypingToFindMatches);

		this.window = window;
		BusyIndicator.showWhile(window.getShell() == null ? null : window
				.getShell().getDisplay(), new Runnable() {
			public void run() {
				QuickAccessDialog.this.providers = provider;
				QuickAccessDialog.this.providerMap = new HashMap();
				for (int i = 0; i < QuickAccessDialog.this.providers.length; i++) {
					QuickAccessDialog.this.providerMap.put(QuickAccessDialog.this.providers[i].getId(), QuickAccessDialog.this.providers[i]);
				}
				restoreDialog();
				QuickAccessDialog.this.invokingCommand = invokingCommand;
				if (QuickAccessDialog.this.invokingCommand != null
						&& !QuickAccessDialog.this.invokingCommand.isDefined()) {
					QuickAccessDialog.this.invokingCommand = null;
				} else {
					// Pre-fetch key sequence - do not change because scope will
					// change later.
					getInvokingCommandKeySequences();
				}
				// create early
				create();
			}
		});
		// Ugly hack to avoid bug 184045. If this gets fixed, replace the
		// following code with a call to refresh("").
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				final Shell shell = getShell();
				if (shell != null && !shell.isDisposed()) {
					Point size = shell.getSize();
					shell.setSize(size.x, size.y + 1);
				}
			}
		});
	}

	@Override
	protected Control createTitleControl(Composite parent) {
		this.filterText = new Text(parent, SWT.NONE);

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false)
		.applyTo(this.filterText);

		this.filterText.addKeyListener(getKeyAdapter());
		this.filterText.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 0x0D) {
					handleSelection();
					return;
				} else if (e.keyCode == SWT.ARROW_DOWN) {
					int index = QuickAccessDialog.this.table.getSelectionIndex();
					if (index != -1 && QuickAccessDialog.this.table.getItemCount() > index + 1) {
						QuickAccessDialog.this.table.setSelection(index + 1);
					}
					QuickAccessDialog.this.table.setFocus();
				} else if (e.keyCode == SWT.ARROW_UP) {
					int index = QuickAccessDialog.this.table.getSelectionIndex();
					if (index != -1 && index >= 1) {
						QuickAccessDialog.this.table.setSelection(index - 1);
						QuickAccessDialog.this.table.setFocus();
					}
				} else if (e.character == 0x1B) // ESC
					close();
			}

			public void keyReleased(KeyEvent e) {
				// do nothing
			}
		});
		this.filterText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String text = ((Text) e.widget).getText().toLowerCase();
				refresh(text);
			}
		});

		return this.filterText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.PopupDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		boolean isWin32 = "win32".equals(SWT.getPlatform()); //$NON-NLS-1$
		GridLayoutFactory.fillDefaults().extendedMargins(isWin32 ? 0 : 3, 3, 2, 2).applyTo(composite);
		Composite tableComposite = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableComposite);
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);
		this.table = new Table(tableComposite, SWT.SINGLE | SWT.FULL_SELECTION);
		this.textLayout = new TextLayout(this.table.getDisplay());
		this.textLayout.setOrientation(getDefaultOrientation());
		Font boldFont = this.resourceManager.createFont(FontDescriptor.createFrom(
				JFaceResources.getDialogFont()).setStyle(SWT.BOLD));
		this.textLayout.setFont(this.table.getFont());
		this.textLayout.setText(QuickAccessMessages.QuickAccess_AvailableCategories);
		int maxProviderWidth = (int) (this.textLayout.getBounds().width * 1.1);
		this.textLayout.setFont(boldFont);
		for (int i = 0; i < this.providers.length; i++) {
			QuickAccessProvider provider = this.providers[i];
			this.textLayout.setText(provider.getName());
			int width = (int) (this.textLayout.getBounds().width * 1.1);
			if (width > maxProviderWidth) {
				maxProviderWidth = width;
			}
		}
		tableColumnLayout.setColumnData(new TableColumn(this.table, SWT.NONE),
				new ColumnWeightData(0, maxProviderWidth));
		tableColumnLayout.setColumnData(new TableColumn(this.table, SWT.NONE),
				new ColumnWeightData(100, 100));
		this.table.getShell().addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				if (!QuickAccessDialog.this.showAllMatches) {
					if (!QuickAccessDialog.this.resized) {
						QuickAccessDialog.this.resized = true;
						e.display.timerExec(100, new Runnable() {
							public void run() {
								if (getShell() != null
										&& !getShell().isDisposed()) {
									refresh(QuickAccessDialog.this.filterText.getText().toLowerCase());
								}
								QuickAccessDialog.this.resized = false;
							}
						});
					}
				}
			}
		});

		this.table.addKeyListener(getKeyAdapter());
		this.table.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_UP && QuickAccessDialog.this.table.getSelectionIndex() == 0) {
					QuickAccessDialog.this.filterText.setFocus();
				} else if (e.character == SWT.ESC) {
					close();
				}
			}

			public void keyReleased(KeyEvent e) {
				// do nothing
			}
		});
		this.table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

				if (QuickAccessDialog.this.table.getSelectionCount() < 1)
					return;

				if (e.button != 1)
					return;

				if (QuickAccessDialog.this.table.equals(e.getSource())) {
					Object o= QuickAccessDialog.this.table.getItem(new Point(e.x, e.y));
					TableItem selection= QuickAccessDialog.this.table.getSelection()[0];
					if (selection.equals(o))
						handleSelection();
				}
			}
		});

		this.table.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				// do nothing
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				handleSelection();
			}
		});

		// italicsFont = resourceManager.createFont(FontDescriptor.createFrom(
		// table.getFont()).setStyle(SWT.ITALIC));
		this.grayColor = this.resourceManager.createColor(ColorUtil.blend(this.table
				.getBackground().getRGB(), this.table.getForeground().getRGB()));
		final TextStyle boldStyle = new TextStyle(boldFont, null, null);
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				QuickAccessEntry entry = (QuickAccessEntry) event.item
				.getData();
				if (entry != null) {
					switch (event.type) {
					case SWT.MeasureItem:
						entry.measure(event, QuickAccessDialog.this.textLayout, QuickAccessDialog.this.resourceManager,
								boldStyle);
						break;
					case SWT.PaintItem:
						entry.paint(event, QuickAccessDialog.this.textLayout, QuickAccessDialog.this.resourceManager,
								boldStyle, QuickAccessDialog.this.grayColor);
						break;
					case SWT.EraseItem:
						entry.erase(event);
						break;
					}
				}
			}
		};
		this.table.addListener(SWT.MeasureItem, listener);
		this.table.addListener(SWT.EraseItem, listener);
		this.table.addListener(SWT.PaintItem, listener);
		toggleShowAllMatches();
		return composite;
	}

	/**
	 * 
	 */
	private int computeNumberOfItems() {
		Rectangle rect = this.table.getClientArea ();
		int itemHeight = this.table.getItemHeight ();
		int headerHeight = this.table.getHeaderHeight ();
		return (rect.height - headerHeight + itemHeight - 1) / (itemHeight + this.table.getGridLineWidth());
	}

	/**
	 * 
	 */
	private void refresh(String filter) {
		int numItems = computeNumberOfItems();

		// perfect match, to be selected in the table if not null
		QuickAccessElement perfectMatch = (QuickAccessElement) this.elementMap
		.get(filter);

		List[] entries = computeMatchingEntries(filter, perfectMatch, numItems);

		int selectionIndex = refreshTable(perfectMatch, entries);

		if (this.table.getItemCount() > 0) {
			this.table.setSelection(selectionIndex);
		} else if (filter.length() == 0) {
			{
				TableItem item = new TableItem(this.table, SWT.NONE);
				item.setText(0,
						QuickAccessMessages.QuickAccess_AvailableCategories);
				item.setForeground(0, this.grayColor);
			}
			for (int i = 0; i < this.providers.length; i++) {
				QuickAccessProvider provider = this.providers[i];
				TableItem item = new TableItem(this.table, SWT.NONE);
				item.setText(1, provider.getName());
				item.setForeground(1, this.grayColor);
			}
		}

		if (filter.length() == 0) {
			setInfoText(QuickAccessMessages.QuickAccess_StartTypingToFindMatches);
		} else {
			TriggerSequence[] sequences = getInvokingCommandKeySequences();
			if (this.showAllMatches || sequences == null || sequences.length == 0) {
				setInfoText(""); //$NON-NLS-1$
			} else {
				setInfoText(NLS
						.bind(
								QuickAccessMessages.QuickAccess_PressKeyToShowAllMatches,
								sequences[0].format()));
			}
		}
	}

	final protected TriggerSequence[] getInvokingCommandKeySequences() {
		if (this.invokingCommandKeySequences == null) {
			if (this.invokingCommand != null) {
				IBindingService bindingService = (IBindingService) this.window
				.getWorkbench().getAdapter(IBindingService.class);
				this.invokingCommandKeySequences = bindingService
				.getActiveBindingsFor(this.invokingCommand.getId());
			}
		}
		return this.invokingCommandKeySequences;
	}

	private KeyAdapter getKeyAdapter() {
		if (this.keyAdapter == null) {
			this.keyAdapter = new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					int accelerator = SWTKeySupport
					.convertEventToUnmodifiedAccelerator(e);
					KeySequence keySequence = KeySequence
					.getInstance(SWTKeySupport
							.convertAcceleratorToKeyStroke(accelerator));
					TriggerSequence[] sequences = getInvokingCommandKeySequences();
					if (sequences == null)
						return;
					for (int i = 0; i < sequences.length; i++) {
						if (sequences[i].equals(keySequence)) {
							e.doit = false;
							toggleShowAllMatches();
							return;
						}
					}
				}
			};
		}
		return this.keyAdapter;
	}

	private void toggleShowAllMatches() {
		this.showAllMatches = !this.showAllMatches;
		refresh(this.filterText.getText().toLowerCase());
	}

	private int refreshTable(QuickAccessElement perfectMatch, List[] entries) {
		if (this.table.getItemCount() > entries.length
				&& this.table.getItemCount() - entries.length > 20) {
			this.table.removeAll();
		}
		TableItem[] items = this.table.getItems();
		int selectionIndex = -1;
		int index = 0;
		for (int i = 0; i < this.providers.length; i++) {
			if (entries[i] != null) {
				boolean firstEntry = true;
				for (Iterator it = entries[i].iterator(); it.hasNext();) {
					QuickAccessEntry entry = (QuickAccessEntry) it.next();
					entry.firstInCategory = firstEntry;
					firstEntry = false;
					if (!it.hasNext()) {
						entry.lastInCategory = true;
					}
					TableItem item;
					if (index < items.length) {
						item = items[index];
						this.table.clear(index);
					} else {
						item = new TableItem(this.table, SWT.NONE);
					}
					if (perfectMatch == entry.element && selectionIndex == -1) {
						selectionIndex = index;
					}
					item.setData(entry);
					item.setText(0, entry.provider.getName());
					item.setText(1, entry.element.getLabel());
					if (SWT.getPlatform().equals("wpf")) { //$NON-NLS-1$
						item.setImage(1, entry.getImage(entry.element,
								this.resourceManager));
					}
					index++;
				}
			}
		}
		if (index < items.length) {
			this.table.remove(index, items.length - 1);
		}
		if (selectionIndex == -1) {
			selectionIndex = 0;
		}
		return selectionIndex;
	}

	private List[] computeMatchingEntries(String filter,
			QuickAccessElement perfectMatch, int maxCount) {
		// collect matches in an array of lists
		List[] entries = new ArrayList[this.providers.length];
		int[] indexPerProvider = new int[this.providers.length];
		int countPerProvider = Math.min(maxCount / 4,
				INITIAL_COUNT_PER_PROVIDER);
		int countTotal = 0;
		boolean perfectMatchAdded = true;
		if (perfectMatch != null) {
			// reserve one entry for the perfect match
			maxCount--;
			perfectMatchAdded = false;
		}
		boolean done;
		do {
			// will be set to false if we find a provider with remaining
			// elements
			done = true;
			for (int i = 0; i < this.providers.length
			&& (this.showAllMatches || countTotal < maxCount); i++) {
				if (entries[i] == null) {
					entries[i] = new ArrayList();
					indexPerProvider[i] = 0;
				}
				int count = 0;
				QuickAccessProvider provider = this.providers[i];
				if (filter.length() > 0
						|| this.showAllMatches) {
					QuickAccessElement[] elements = provider
					.getElementsSorted();
					int j = indexPerProvider[i];
					while (j < elements.length
							&& (this.showAllMatches || (count < countPerProvider && countTotal < maxCount))) {
						QuickAccessElement element = elements[j];
						QuickAccessEntry entry;
						if (filter.length() == 0) {
							if (i == 0 || this.showAllMatches) {
								entry = new QuickAccessEntry(element, provider,
										new int[0][0], new int[0][0]);
							} else {
								entry = null;
							}
						} else {
							entry = element.match(filter, provider);
						}
						if (entry != null) {
							entries[i].add(entry);
							count++;
							countTotal++;
							if (i == 0 && entry.element == perfectMatch) {
								perfectMatchAdded = true;
								maxCount = MAX_COUNT_TOTAL;
							}
						}
						j++;
					}
					indexPerProvider[i] = j;
					if (j < elements.length) {
						done = false;
					}
				}
			}
			// from now on, add one element per provider
			countPerProvider = 1;
		} while ((this.showAllMatches || countTotal < maxCount) && !done);
		if (!perfectMatchAdded) {
			QuickAccessEntry entry = perfectMatch.match(filter, this.providers[0]);
			if (entry != null) {
				if (entries[0] == null) {
					entries[0] = new ArrayList();
					indexPerProvider[0] = 0;
				}
				entries[0].add(entry);
			}
		}
		return entries;
	}

	@Override
	protected Control getFocusControl() {
		return this.filterText;
	}

	@Override
	public boolean close() {
		storeDialog(getDialogSettings());
		if (this.textLayout != null && !this.textLayout.isDisposed()) {
			this.textLayout.dispose();
		}
		if (this.resourceManager != null) {
			this.resourceManager.dispose();
			this.resourceManager = null;
		}
		return super.close();
	}

	@Override
	protected Point getDefaultSize() {
		return new Point(350, 420);
	}

	@Override
	protected Point getDefaultLocation(Point initialSize) {
		Point size = new Point(400, 400);
		Rectangle parentBounds = getParentShell().getBounds();
		int x = parentBounds.x + parentBounds.width / 2 - size.x / 2;
		int y = parentBounds.y + parentBounds.height / 2 - size.y / 2;
		return new Point(x, y);
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		final IDialogSettings workbenchDialogSettings = WorkbenchPlugin
		.getDefault().getDialogSettings();
		IDialogSettings result = workbenchDialogSettings.getSection(getId());
		if (result == null) {
			result = workbenchDialogSettings.addNewSection(getId());
		}
		return result;
	}

	protected String getId() {
		return "org.eclipse.ui.internal.QuickAccess"; //$NON-NLS-1$
	}

	private void storeDialog(IDialogSettings dialogSettings) {
		ArrayList arrayList = new ArrayList();
		String[] textArray = (String[]) arrayList.toArray(new String[arrayList
		                                                             .size()]);
		dialogSettings.put(TEXT_ARRAY, textArray);
	}

	private void restoreDialog() {
		IDialogSettings dialogSettings = getDialogSettings();
		if (dialogSettings != null) {
			String[] orderedElements = dialogSettings
			.getArray(ORDERED_ELEMENTS);
			String[] orderedProviders = dialogSettings
			.getArray(ORDERED_PROVIDERS);
			String[] textEntries = dialogSettings.getArray(TEXT_ENTRIES);
			String[] textArray = dialogSettings.getArray(TEXT_ARRAY);
			this.elementMap = new HashMap();
			this.textMap = new HashMap();
			if (orderedElements != null && orderedProviders != null
					&& textEntries != null && textArray != null) {
				int arrayIndex = 0;
				for (int i = 0; i < orderedElements.length; i++) {
					QuickAccessProvider quickAccessProvider = (QuickAccessProvider) this.providerMap
					.get(orderedProviders[i]);
					int numTexts = Integer.parseInt(textEntries[i]);
					if (quickAccessProvider != null) {
						QuickAccessElement quickAccessElement = quickAccessProvider
						.getElementForId(orderedElements[i]);
						if (quickAccessElement != null) {
							ArrayList arrayList = new ArrayList();
							for (int j = arrayIndex; j < arrayIndex + numTexts; j++) {
								String text = textArray[j];
								// text length can be zero for old workspaces, see bug 190006
								if (text.length() > 0) {
									arrayList.add(text);
									this.elementMap.put(text, quickAccessElement);
								}
							}
							this.textMap.put(quickAccessElement, arrayList);
						}
					}
					arrayIndex += numTexts;
				}
			}
		}
	}

	protected void handleElementSelected(String text, Object selectedElement) {
		IWorkbenchPage activePage = this.window.getActivePage();
		if (activePage != null) {
			if (selectedElement instanceof QuickAccessElement) {
				storeDialog(getDialogSettings());
				QuickAccessElement element = (QuickAccessElement) selectedElement;
				element.execute();
			}
		}
	}



	/**
	 * 
	 */
	private void handleSelection() {
		QuickAccessElement selectedElement = null;
		String text = this.filterText.getText().toLowerCase();
		if (this.table.getSelectionCount() == 1) {
			QuickAccessEntry entry = (QuickAccessEntry) this.table
			.getSelection()[0].getData();
			selectedElement = entry == null ? null : entry.element;
		}
		close();
		if (selectedElement != null) {
			handleElementSelected(text, selectedElement);
		}
	}

	@Override
	public int open() {
		setBlockOnOpen(true);
		return super.open();
	}

	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.ON_TOP;
	}

}
