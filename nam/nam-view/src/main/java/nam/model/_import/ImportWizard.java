package nam.model._import;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Import;
import nam.model.Project;
import nam.model.util.ImportUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("importWizard")
@SuppressWarnings("serial")
public class ImportWizard extends AbstractDomainElementWizard<Import> implements Serializable {
	
	@Inject
	private ImportDataManager importDataManager;
	
	@Inject
	private ImportPageManager importPageManager;
	
	@Inject
	private ImportEventManager importEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Import";
	}
	
	@Override
	public String getUrlContext() {
		return importPageManager.getImportWizardPage();
	}
	
	@Override
	public void initialize(Import _import) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(importPageManager.getSections());
		super.initialize(_import);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		importPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		importPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		importPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		importPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Import _import = getInstance();
		importDataManager.saveImport(_import);
		importEventManager.fireSavedEvent(_import);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Import _import = getInstance();
		//TODO take this out soon
		if (_import == null)
			_import = new Import();
		importEventManager.fireCancelledEvent(_import);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Import _import = selectionContext.getSelection("import");
		String namespace = _import.getNamespace();
		if (StringUtils.isEmpty(namespace)) {
			display = getFromSession("display");
			display.setModule("importWizard");
			display.error("Import namespace must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(namespace);
		String nameUncapped = NameUtil.uncapName(namespace);
		return getUrl();
	}
	
}
