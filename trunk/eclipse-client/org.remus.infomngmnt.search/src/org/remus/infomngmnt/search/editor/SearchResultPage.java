package org.remus.infomngmnt.search.editor;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.SWT;
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

import org.remus.infomngmnt.common.ui.editor.EditorUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchResult;
import org.remus.infomngmnt.search.provider.SearchPlugin;

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

		IHyperlinkListener hyperLinkListener = new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				String[] split = ((String) e.getHref()).split("\\|"); //$NON-NLS-1$
				SearchPlugin.getPlugin().getSearchContext().activate(
						SearchResultPage.this.model.getResult(), Integer.valueOf(split[0]));
				EditorUtil.openEditor(new Path(split[1]));
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
				final FormText formText = toolkit.createFormText(body, true);
				formText
						.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
				formText.setText(buildSearchResultSting(result.get(i), i, n), true, false);
				formText.setImage(result.get(i).getInfoType(), InformationExtensionManager
						.getInstance().getInfoTypeByType(result.get(i).getInfoType()).getImage());
				formText.addHyperlinkListener(hyperLinkListener);
				final Label label = toolkit.createSeparator(body, SWT.HORIZONTAL);
				label.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
			}

		}
		form.reflow(true);

	}

	private String buildSearchResultSting(final SearchResult result, final int index,
			final int count) {
		StringBuilder sb = new StringBuilder();
		sb.append("<form><p>").append(index + 1).append(" of ").append(count).append(" \u00AD ");
		if (result.getDate() != null) {
			sb.append(SearchResultEditor.SDF.format(result.getDate()));
		} else {
			sb.append("n.a.");
		}
		sb.append("</p><p>");
		sb.append("<img href=\"").append(result.getInfoType()).append("\"/>").append(" <a href=\"")
				.append(index).append("|").append(result.getPath().toString()).append("\">")
				.append(result.getTitle()).append("</a>").append("</p><p>").append(
						StringEscapeUtils.escapeXml(result.getText()).replaceAll(
								"\\{highlight-start\\}", "<b>").replaceAll("\\{highlight-end\\}",
								"</b>")).append("</p></form>");
		return sb.toString();
	}

}
