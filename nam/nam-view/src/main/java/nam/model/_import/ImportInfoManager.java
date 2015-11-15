package nam.model._import;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Import;
import nam.model.Project;
import nam.model.util.ImportUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("importInfoManager")
public class ImportInfoManager extends AbstractNamRecordManager<Import> implements Serializable {
	
	@Inject
	private ImportWizard importWizard;
	
	@Inject
	private ImportDataManager importDataManager;
	
	@Inject
	private ImportPageManager importPageManager;
	
	@Inject
	private ImportEventManager importEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ImportHelper importHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ImportInfoManager() {
		setInstanceName("import");
	}
	
	
	public Import getImport() {
		return getRecord();
	}
	
	public Import getSelectedImport() {
		return selectionContext.getSelection("import");
	}
	
	@Override
	public Class<Import> getRecordClass() {
		return Import.class;
	}
	
	@Override
	public boolean isEmpty(Import _import) {
		return importHelper.isEmpty(_import);
	}
	
	@Override
	public String toString(Import _import) {
		return importHelper.toString(_import);
	}
	
	@Override
	public void initialize() {
		Import _import = selectionContext.getSelection("import");
		if (_import != null)
			initialize(_import);
	}
	
	protected void initialize(Import _import) {
		ImportUtil.initialize(_import);
		importWizard.initialize(_import);
		setContext("import", _import);
	}
	
	public void handleImportSelected(@Observes @Selected Import _import) {
		selectionContext.setSelection("import",  _import);
		importPageManager.updateState(_import);
		importPageManager.refreshMembers();
		setRecord(_import);
	}
	
	@Override
	public String newRecord() {
		return newImport();
	}
	
	public String newImport() {
		try {
			Import _import = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("import",  _import);
			String url = importPageManager.initializeImportCreationPage(_import);
			importPageManager.pushContext(importWizard);
			initialize(_import);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Import create() {
		Import _import = ImportUtil.create();
		return _import;
	}
	
	@Override
	public Import clone(Import _import) {
		_import = ImportUtil.clone(_import);
		return _import;
	}
	
	@Override
	public String viewRecord() {
		return viewImport();
	}
	
	public String viewImport() {
		Import _import = selectionContext.getSelection("import");
		String url = viewImport(_import);
		return url;
	}
	
	public String viewImport(Import _import) {
		try {
			String url = importPageManager.initializeImportSummaryView(_import);
			importPageManager.pushContext(importWizard);
			initialize(_import);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editImport();
	}
	
	public String editImport() {
		Import _import = selectionContext.getSelection("import");
		String url = editImport(_import);
		return url;
	}
	
	public String editImport(Import _import) {
		try {
			//import = clone(import);
			selectionContext.resetOrigin();
			selectionContext.setSelection("import",  _import);
			String url = importPageManager.initializeImportUpdatePage(_import);
			importPageManager.pushContext(importWizard);
			initialize(_import);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveImport() {
		Import _import = getImport();
		if (validateImport(_import)) {
			if (isImmediate())
				persistImport(_import);
			outject("import", _import);
		}
	}
	
	public void persistImport(Import _import) {
		saveImport(_import);
	}
	
	public void saveImport(Import _import) {
		try {
			saveImportToSystem(_import);
			importEventManager.fireAddedEvent(_import);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveImportToSystem(Import _import) {
		importDataManager.saveImport(_import);
	}
	
	public void handleSaveImport(@Observes @Add Import _import) {
		saveImport(_import);
	}
	
	public void addImport(Import _import) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichImport(Import _import) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Import _import) {
		return validateImport(_import);
	}
	
	public boolean validateImport(Import _import) {
		Validator validator = getValidator();
		boolean isValid = ImportUtil.validate(_import);
		Display display = getFromSession("display");
		display.setModule("importInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveImport() {
		display = getFromSession("display");
		display.setModule("importInfo");
		Import _import = selectionContext.getSelection("import");
		if (_import == null) {
			display.error("Import record must be selected.");
		}
	}
	
	public String handleRemoveImport(@Observes @Remove Import _import) {
		display = getFromSession("display");
		display.setModule("importInfo");
		try {
			display.info("Removing Import "+ImportUtil.getLabel(_import)+" from the system.");
			removeImportFromSystem(_import);
			selectionContext.clearSelection("import");
			importEventManager.fireClearSelectionEvent();
			importEventManager.fireRemovedEvent(_import);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeImportFromSystem(Import _import) {
		if (importDataManager.removeImport(_import))
			setRecord(null);
	}
	
	public void cancelImport() {
		BeanContext.removeFromSession("import");
		importPageManager.removeContext(importWizard);
	}
	
}
