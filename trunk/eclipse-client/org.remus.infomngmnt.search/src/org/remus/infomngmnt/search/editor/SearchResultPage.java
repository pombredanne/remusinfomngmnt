package org.remus.infomngmnt.search.editor;

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
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchResult;

import org.apache.commons.lang.StringEscapeUtils;

public class SearchResultPage extends FormPage {


	private final Search model;

	/**
	 * Create the form page
	 * @param editor
	 * @param id
	 * @param title
	 */
	public SearchResultPage(FormEditor editor, Search model) {
		super(editor, "search_result", "Search");
		this.model = model;
	}

	/**
	 * Create contents of the form
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new TableWrapLayout());
		toolkit.paintBordersFor(body);

		IHyperlinkListener hyperLinkListener = new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				EditorUtil.openEditor(new Path((String) e.getHref()));
			}
		};



		EList<SearchResult> result = this.model.getResult();
		for (int i = 0, n = result.size(); i < n; i++) {
			final FormText formText = toolkit.createFormText(body, true);
			formText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
			formText.setText(buildSearchResultSting(result.get(i), i, n), true, false);
			formText.setImage(result.get(i).getInfoType(), InformationExtensionManager.getInstance().getInfoTypeByType(result.get(i).getInfoType()).getImage());
			formText.addHyperlinkListener(hyperLinkListener);
			final Label label = toolkit.createSeparator(body, SWT.HORIZONTAL);
			label.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
		}
		form.reflow(true);

	}


	private String buildSearchResultSting(SearchResult result, int index, int count) {
		StringBuilder sb = new StringBuilder();
		sb.append("<form><p>")
		.append(index + 1)
		.append(" of ")
		.append(count)
		.append(" \u00AD ");
		if (result.getDate() != null) {
			sb.append(SearchResultEditor.SDF.format(result.getDate()));
		} else {
			sb.append("n.a.");
		}
		sb.append("</p><p>");
		sb.append("<img href=\"")
		.append(result.getInfoType())
		.append("\"/>")
		.append(" <a href=\"")
		.append(result.getPath().toString())
		.append("\">")
		.append(result.getTitle())
		.append("</a>")
		.append("</p><p>")
		.append(StringEscapeUtils.escapeXml(result.getText()).replaceAll("\\{highlight-start\\}", "<b>").replaceAll("\\{highlight-end\\}", "</b>"))
		.append("</p></form>");
		return sb.toString();
	}

}
