package nam.model.dependency;

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

import nam.model.Dependency;
import nam.model.Project;
import nam.model.util.DependencyUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("dependencyInfoManager")
public class DependencyInfoManager extends AbstractNamRecordManager<Dependency> implements Serializable {
	
	@Inject
	private DependencyWizard dependencyWizard;
	
	@Inject
	private DependencyDataManager dependencyDataManager;
	
	@Inject
	private DependencyPageManager dependencyPageManager;
	
	@Inject
	private DependencyEventManager dependencyEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private DependencyHelper dependencyHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public DependencyInfoManager() {
		setInstanceName("dependency");
	}
	
	
	public Dependency getDependency() {
		return getRecord();
	}
	
	public Dependency getSelectedDependency() {
		return selectionContext.getSelection("dependency");
	}
	
	@Override
	public Class<Dependency> getRecordClass() {
		return Dependency.class;
	}
	
	@Override
	public boolean isEmpty(Dependency dependency) {
		return dependencyHelper.isEmpty(dependency);
	}
	
	@Override
	public String toString(Dependency dependency) {
		return dependencyHelper.toString(dependency);
	}
	
	@Override
	public void initialize() {
		Dependency dependency = selectionContext.getSelection("dependency");
		if (dependency != null)
			initialize(dependency);
	}
	
	protected void initialize(Dependency dependency) {
		DependencyUtil.initialize(dependency);
		dependencyWizard.initialize(dependency);
		setContext("dependency", dependency);
	}
	
	public void handleDependencySelected(@Observes @Selected Dependency dependency) {
		selectionContext.setSelection("dependency",  dependency);
		dependencyPageManager.updateState(dependency);
		dependencyPageManager.refreshMembers();
		setRecord(dependency);
	}
	
	@Override
	public String newRecord() {
		return newDependency();
	}
	
	public String newDependency() {
		try {
			Dependency dependency = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("dependency",  dependency);
			String url = dependencyPageManager.initializeDependencyCreationPage(dependency);
			dependencyPageManager.pushContext(dependencyWizard);
			initialize(dependency);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Dependency create() {
		Dependency dependency = DependencyUtil.create();
		return dependency;
	}
	
	@Override
	public Dependency clone(Dependency dependency) {
		dependency = DependencyUtil.clone(dependency);
		return dependency;
	}
	
	@Override
	public String viewRecord() {
		return viewDependency();
	}
	
	public String viewDependency() {
		Dependency dependency = selectionContext.getSelection("dependency");
		String url = viewDependency(dependency);
		return url;
	}
	
	public String viewDependency(Dependency dependency) {
		try {
			String url = dependencyPageManager.initializeDependencySummaryView(dependency);
			dependencyPageManager.pushContext(dependencyWizard);
			initialize(dependency);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editDependency();
	}
	
	public String editDependency() {
		Dependency dependency = selectionContext.getSelection("dependency");
		String url = editDependency(dependency);
		return url;
	}
	
	public String editDependency(Dependency dependency) {
		try {
			//dependency = clone(dependency);
			selectionContext.resetOrigin();
			selectionContext.setSelection("dependency",  dependency);
			String url = dependencyPageManager.initializeDependencyUpdatePage(dependency);
			dependencyPageManager.pushContext(dependencyWizard);
			initialize(dependency);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveDependency() {
		Dependency dependency = getDependency();
		if (validateDependency(dependency)) {
			if (isImmediate())
				persistDependency(dependency);
			outject("dependency", dependency);
		}
	}
	
	public void persistDependency(Dependency dependency) {
		saveDependency(dependency);
	}
	
	public void saveDependency(Dependency dependency) {
		try {
			saveDependencyToSystem(dependency);
			dependencyEventManager.fireAddedEvent(dependency);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveDependencyToSystem(Dependency dependency) {
		dependencyDataManager.saveDependency(dependency);
	}
	
	public void handleSaveDependency(@Observes @Add Dependency dependency) {
		saveDependency(dependency);
	}
	
	public void addDependency(Dependency dependency) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichDependency(Dependency dependency) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Dependency dependency) {
		return validateDependency(dependency);
	}
	
	public boolean validateDependency(Dependency dependency) {
		Validator validator = getValidator();
		boolean isValid = DependencyUtil.validate(dependency);
		Display display = getFromSession("display");
		display.setModule("dependencyInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveDependency() {
		display = getFromSession("display");
		display.setModule("dependencyInfo");
		Dependency dependency = selectionContext.getSelection("dependency");
		if (dependency == null) {
			display.error("Dependency record must be selected.");
		}
	}
	
	public String handleRemoveDependency(@Observes @Remove Dependency dependency) {
		display = getFromSession("display");
		display.setModule("dependencyInfo");
		try {
			display.info("Removing Dependency "+DependencyUtil.getLabel(dependency)+" from the system.");
			removeDependencyFromSystem(dependency);
			selectionContext.clearSelection("dependency");
			dependencyEventManager.fireClearSelectionEvent();
			dependencyEventManager.fireRemovedEvent(dependency);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeDependencyFromSystem(Dependency dependency) {
		if (dependencyDataManager.removeDependency(dependency))
			setRecord(null);
	}
	
	public void cancelDependency() {
		BeanContext.removeFromSession("dependency");
		dependencyPageManager.removeContext(dependencyWizard);
	}
	
}
