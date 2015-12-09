package nam.model.type;

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
import org.aries.util.Validator;

import nam.model.Project;
import nam.model.Type;
import nam.model.util.ProjectUtil;
import nam.model.util.TypeUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("typeInfoManager")
public class TypeInfoManager extends AbstractNamRecordManager<Type> implements Serializable {
	
	@Inject
	private TypeWizard typeWizard;
	
	@Inject
	private TypeDataManager typeDataManager;
	
	@Inject
	private TypePageManager typePageManager;
	
	@Inject
	private TypeEventManager typeEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private TypeHelper typeHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public TypeInfoManager() {
		setInstanceName("type");
	}
	
	
	public Type getType() {
		return getRecord();
	}
	
	public Type getSelectedType() {
		return selectionContext.getSelection("type");
	}
	
	@Override
	public Class<Type> getRecordClass() {
		return Type.class;
	}
	
	@Override
	public boolean isEmpty(Type type) {
		return typeHelper.isEmpty(type);
	}
	
	@Override
	public String toString(Type type) {
		return typeHelper.toString(type);
	}
	
	@Override
	public void initialize() {
		Type type = selectionContext.getSelection("type");
		if (type != null)
			initialize(type);
	}
	
	protected void initialize(Type type) {
		typeWizard.initialize(type);
		setContext("type", type);
	}
	
	@Override
	public String newRecord() {
		return newType();
	}
	
	public String newType() {
		try {
			Type type = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("type",  type);
			String url = typePageManager.initializeTypeCreationPage(type);
			typePageManager.pushContext(typeWizard);
			initialize(type);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Type create() {
		Type type = TypeUtil.create();
		return type;
	}
	
	@Override
	public Type clone(Type type) {
		type = TypeUtil.clone(type);
		return type;
	}
	
	@Override
	public String viewRecord() {
		return viewType();
	}
	
	public String viewType() {
		Type type = selectionContext.getSelection("type");
		String url = viewType(type);
		return url;
	}
	
	public String viewType(Type type) {
		try {
			String url = typePageManager.initializeTypeSummaryView(type);
			typePageManager.pushContext(typeWizard);
			initialize(type);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editType();
	}
	
	public String editType() {
		Type type = selectionContext.getSelection("type");
		String url = editType(type);
		return url;
	}
	
	public String editType(Type type) {
		try {
			//type = clone(type);
			selectionContext.resetOrigin();
			selectionContext.setSelection("type",  type);
			String url = typePageManager.initializeTypeUpdatePage(type);
			typePageManager.pushContext(typeWizard);
			initialize(type);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveType() {
		Type type = getType();
		if (validateType(type)) {
			saveType(type);
		}
	}
	
	public void persistType(Type type) {
		saveType(type);
	}
	
	public void saveType(Type type) {
		try {
			saveTypeToSystem(type);
			typeEventManager.fireAddedEvent(type);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveTypeToSystem(Type type) {
		typeDataManager.saveType(type);
	}
	
	public void handleSaveType(@Observes @Add Type type) {
		saveType(type);
	}
	
	public void enrichType(Type type) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Type type) {
		return validateType(type);
	}
	
	public boolean validateType(Type type) {
		Validator validator = getValidator();
		boolean isValid = TypeUtil.validate(type);
		Display display = getFromSession("display");
		display.setModule("typeInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveType() {
		display = getFromSession("display");
		display.setModule("typeInfo");
		Type type = selectionContext.getSelection("type");
		if (type == null) {
			display.error("Type record must be selected.");
		}
	}
	
	public String handleRemoveType(@Observes @Remove Type type) {
		display = getFromSession("display");
		display.setModule("typeInfo");
		try {
			display.info("Removing Type "+TypeUtil.getLabel(type)+" from the system.");
			removeTypeFromSystem(type);
			selectionContext.clearSelection("type");
			typeEventManager.fireClearSelectionEvent();
			typeEventManager.fireRemovedEvent(type);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeTypeFromSystem(Type type) {
		if (typeDataManager.removeType(type))
			setRecord(null);
	}
	
	public void cancelType() {
		BeanContext.removeFromSession("type");
		typePageManager.removeContext(typeWizard);
	}
	
}
