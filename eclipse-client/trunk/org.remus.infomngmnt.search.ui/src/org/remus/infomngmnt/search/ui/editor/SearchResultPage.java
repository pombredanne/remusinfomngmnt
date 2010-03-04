package org.remus.infomngmnt.search.ui.editor;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchResult;
import org.remus.infomngmnt.search.ui.SearchUIActivator;
import org.remus.infomngmnt.ui.editors.EditorUtil;
import org.remus.infomngmnt.ui.infotypes.service.IInformationTypeImage;

public class SearchResultPage extends FormPage {

	private final Search model;

	/**
	 * Create the form page
	 * 
	 * @param editor
	 * @param id
	 * @param title
	 */
	public SearchResultPage(final FormEditor editor, final Search model) {
		super(editor, "search_result", "Search");
		this.model = model;
	}

	/**
	 * Create contents of the form
	 * 
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new TableWrapLayout());
		toolkit.paintBordersFor(body);

		IInformationTypeImage service = SearchUIActivator.getDefault().getServiceTracker()
				.getService(IInformationTypeImage.class);

		IHyperlinkListener hyperLinkListener = new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				String[] split = ((String) e.getHref()).split("\\|"); //$NON-NLS-1$
				SearchUIActivator.getDefault().getSearchContext().activate(
						SearchResultPage.this.model.getResult(), Integer.valueOf(split[0]));
				EditorUtil.openInfoUnit(split[2]);
			}
		};

		EList<SearchResult> result = this.model.getResult();
		if (result.size() == 0) {
			// final FormText formText = toolkit.createFormText(body, true);
			final Label formText = toolkit.createLabel(body, "");
			TableWrapData data = new TableWrapData(TableWrapData.CENTER, TableWrapData.TOP);
			data.grabHorizontal = true;
			formText.setLayoutData(data);
			formText.setFont(ResourceManager.getBoldFont(formText.getFont()));
			formText.setText("Sorry, your search returned no results...");

		} else {
			for (int i = 0, n = result.size(); i < n; i++) {
				try {
					final FormText formText = toolkit.createFormText(body, true);
					formText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,
							TableWrapData.TOP));
					formText
							.setText(buildSearchResultSting(result.get(i), i, n, true), true, false);
					EList<SearchResult> secondaryResults = result.get(i).getSecondaryResults();
					for (int j = 0, m = secondaryResults.size(); j < m; j++) {
						final FormText formText2 = toolkit.createFormText(body, true);
						TableWrapData layoutData = new TableWrapData(TableWrapData.FILL_GRAB,
								TableWrapData.TOP);
						layoutData.indent = 20;
						formText2.setLayoutData(layoutData);
						formText2.setText(buildSearchResultSting(secondaryResults.get(j), j, m,
								false), true, false);
					}
					Image imageByInfoType = service.getImageByInfoType(result.get(i).getInfoType());
					if (imageByInfoType != null) {
						formText.setImage(result.get(i).getInfoType(), imageByInfoType);
					}
					formText.addHyperlinkListener(hyperLinkListener);
					final Label label = toolkit.createSeparator(body, SWT.HORIZONTAL);
					label.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,
							TableWrapData.TOP));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
		form.reflow(true);

	}

	private String buildSearchResultSting(final SearchResult result, final int index,
			final int count, final boolean showFirstLine) {
		StringBuilder sb = new StringBuilder();
		sb.append("<form>");
		if (showFirstLine) {
			sb.append("<p>").append(index + 1).append(" of ").append(count).append(" \u00AD ");
			if (result.getDate() != null) {
				sb.append(SearchResultEditor.SDF.format(result.getDate()));
			} else {
				sb.append("n.a.");
			}
			sb.append("</p>");
		}
		sb.append("<p>");
		sb.append("<img href=\"").append(result.getInfoType()).append("\"/>").append(" <a href=\"")
				.append(index).append("|").append(result.getPath().toString()).append("|").append(
						result.getInfoId()).append("\">").append(
						StringEscapeUtils.escapeXml(result.getTitle())).append("</a>").append(
						"</p>");
		if (result.getText() != null) {
			sb.append("<p>").append(
					StringEscapeUtils.escapeXml(result.getText()).replaceAll(
							"\\{highlight-start\\}", "<b>").replaceAll("\\{highlight-end\\}",
							"</b>")).append("</p>");
		}

		sb.append("</form>");
		return sb.toString();
	}

}
