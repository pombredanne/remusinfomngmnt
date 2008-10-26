package org.remus.infomngmnt.search.editor;

import java.text.SimpleDateFormat;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.SearchResult;
import org.remus.infomngmnt.search.save.SavedSearchesHandler;

public class SearchResultPage extends FormPage {

	private Search model;

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$

	/**
	 * Create the form page
	 * @param id
	 * @param title
	 */
	public SearchResultPage(String id, String title) {
		super(id, title);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
		if (getEditorInput() instanceof URIEditorInput) {
			this.model = new SavedSearchesHandler().getObjectFromUri(((URIEditorInput)getEditorInput()).getURI(), SearchPackage.Literals.SEARCH);
		}
		setPartName(this.model.getId());
	}
	/**
	 * Create the form page
	 * @param editor
	 * @param id
	 * @param title
	 */
	public SearchResultPage(FormEditor editor, String id, String title) {
		super(editor, "search_result", title);
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

		EList<SearchResult> result = this.model.getResult();
		for (int i = 0, n = result.size(); i < n; i++) {
			final FormText formText = toolkit.createFormText(body, true);
			formText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
			formText.setText(buildSearchResultSting(result.get(i), i, n), true, false);
			formText.setImage(result.get(i).getInfoType(), InformationExtensionManager.getInstance().getInfoTypeByType(result.get(i).getInfoType()).getImage());
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
			sb.append(this.sdf.format(result.getDate()));
		} else {
			sb.append("n.a.");
		}
		sb.append("</p><p>");
		sb.append("<img href=\"")
		.append(result.getInfoType())
		.append("\"/>")
		.append(" <a href=\"")
		.append(result.getInfoId())
		.append("\">")
		.append(result.getTitle())
		.append("</a>")
		.append("</p><p>")
		.append(result.getText())
		.append("</p></form>");
		return sb.toString();
	}

}
