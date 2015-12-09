package nam.model.namespace;

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

import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("namespaceInfoManager")
public class NamespaceInfoManager extends AbstractNamRecordManager<Namespace> implements Serializable {
	
	@Inject
	private NamespaceWizard namespaceWizard;
	
	@Inject
	private NamespaceDataManager namespaceDataManager;
	
	@Inject
	private NamespacePageManager namespacePageManager;
	
	@Inject
	private NamespaceEventManager namespaceEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private NamespaceHelper namespaceHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public NamespaceInfoManager() {
		setInstanceName("namespace");
	}
	
	
	public Namespace getNamespace() {
		return getRecord();
	}
	
	public Namespace getSelectedNamespace() {
		return selectionContext.getSelection("namespace");
	}
	
	@Override
	public Class<Namespace> getRecordClass() {
		return Namespace.class;
	}
	
	@Override
	public boolean isEmpty(Namespace namespace) {
		return namespaceHelper.isEmpty(namespace);
	}
	
	@Override
	public String toString(Namespace namespace) {
		return namespaceHelper.toString(namespace);
	}
	
	@Override
	public void initialize() {
		Namespace namespace = selectionContext.getSelection("namespace");
		if (namespace != null)
			initialize(namespace);
	}
	
	protected void initialize(Namespace namespace) {
		namespaceWizard.initialize(namespace);
		setContext("namespace", namespace);
	}
	
	@Override
	public String newRecord() {
		return newNamespace();
	}
	
	public String newNamespace() {
		try {
			Namespace namespace = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("namespace",  namespace);
			String url = namespacePageManager.initializeNamespaceCreationPage(namespace);
			namespacePageManager.pushContext(namespaceWizard);
			initialize(namespace);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Namespace create() {
		Namespace namespace = NamespaceUtil.create();
		return namespace;
	}
	
	@Override
	public Namespace clone(Namespace namespace) {
		namespace = NamespaceUtil.clone(namespace);
		return namespace;
	}
	
	@Override
	public String viewRecord() {
		return viewNamespace();
	}
	
	public String viewNamespace() {
		Namespace namespace = selectionContext.getSelection("namespace");
		String url = viewNamespace(namespace);
		return url;
	}
	
	public String viewNamespace(Namespace namespace) {
		try {
			String url = namespacePageManager.initializeNamespaceSummaryView(namespace);
			namespacePageManager.pushContext(namespaceWizard);
			initialize(namespace);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editNamespace();
	}
	
	public String editNamespace() {
		Namespace namespace = selectionContext.getSelection("namespace");
		String url = editNamespace(namespace);
		return url;
	}
	
	public String editNamespace(Namespace namespace) {
		try {
			//namespace = clone(namespace);
			selectionContext.resetOrigin();
			selectionContext.setSelection("namespace",  namespace);
			String url = namespacePageManager.initializeNamespaceUpdatePage(namespace);
			namespacePageManager.pushContext(namespaceWizard);
			initialize(namespace);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveNamespace() {
		Namespace namespace = getNamespace();
		if (validateNamespace(namespace)) {
			if (isImmediate())
				persistNamespace(namespace);
			outject("namespace", namespace);
		}
	}
	
	public void persistNamespace(Namespace namespace) {
		saveNamespace(namespace);
	}
	
	public void saveNamespace(Namespace namespace) {
		try {
			saveNamespaceToSystem(namespace);
			namespaceEventManager.fireAddedEvent(namespace);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveNamespaceToSystem(Namespace namespace) {
		namespaceDataManager.saveNamespace(namespace);
	}
	
	public void handleSaveNamespace(@Observes @Add Namespace namespace) {
		saveNamespace(namespace);
	}
	
	public void addNamespace(Namespace namespace) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichNamespace(Namespace namespace) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Namespace namespace) {
		return validateNamespace(namespace);
	}
	
	public boolean validateNamespace(Namespace namespace) {
		Validator validator = getValidator();
		boolean isValid = NamespaceUtil.validate(namespace);
		Display display = getFromSession("display");
		display.setModule("namespaceInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveNamespace() {
		display = getFromSession("display");
		display.setModule("namespaceInfo");
		Namespace namespace = selectionContext.getSelection("namespace");
		if (namespace == null) {
			display.error("Namespace record must be selected.");
		}
	}
	
	public String handleRemoveNamespace(@Observes @Remove Namespace namespace) {
		display = getFromSession("display");
		display.setModule("namespaceInfo");
		try {
			display.info("Removing Namespace "+NamespaceUtil.getLabel(namespace)+" from the system.");
			removeNamespaceFromSystem(namespace);
			selectionContext.clearSelection("namespace");
			namespaceEventManager.fireClearSelectionEvent();
			namespaceEventManager.fireRemovedEvent(namespace);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeNamespaceFromSystem(Namespace namespace) {
		if (namespaceDataManager.removeNamespace(namespace))
			setRecord(null);
	}
	
	public void cancelNamespace() {
		BeanContext.removeFromSession("namespace");
		namespacePageManager.removeContext(namespaceWizard);
	}
	
}
