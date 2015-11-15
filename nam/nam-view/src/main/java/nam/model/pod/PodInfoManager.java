package nam.model.pod;

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

import nam.model.Pod;
import nam.model.Project;
import nam.model.util.PodUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("podInfoManager")
public class PodInfoManager extends AbstractNamRecordManager<Pod> implements Serializable {
	
	@Inject
	private PodWizard podWizard;
	
	@Inject
	private PodDataManager podDataManager;
	
	@Inject
	private PodPageManager podPageManager;
	
	@Inject
	private PodEventManager podEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private PodHelper podHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PodInfoManager() {
		setInstanceName("pod");
	}
	
	
	public Pod getPod() {
		return getRecord();
	}
	
	public Pod getSelectedPod() {
		return selectionContext.getSelection("pod");
	}
	
	@Override
	public Class<Pod> getRecordClass() {
		return Pod.class;
	}
	
	@Override
	public boolean isEmpty(Pod pod) {
		return podHelper.isEmpty(pod);
	}
	
	@Override
	public String toString(Pod pod) {
		return podHelper.toString(pod);
	}
	
	@Override
	public void initialize() {
		Pod pod = selectionContext.getSelection("pod");
		if (pod != null)
			initialize(pod);
	}
	
	protected void initialize(Pod pod) {
		PodUtil.initialize(pod);
		podWizard.initialize(pod);
		setContext("pod", pod);
	}
	
	public void handlePodSelected(@Observes @Selected Pod pod) {
		selectionContext.setSelection("pod",  pod);
		podPageManager.updateState(pod);
		podPageManager.refreshMembers();
		setRecord(pod);
	}
	
	@Override
	public String newRecord() {
		return newPod();
	}
	
	public String newPod() {
		try {
			Pod pod = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("pod",  pod);
			String url = podPageManager.initializePodCreationPage(pod);
			podPageManager.pushContext(podWizard);
			initialize(pod);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Pod create() {
		Pod pod = PodUtil.create();
		return pod;
	}
	
	@Override
	public Pod clone(Pod pod) {
		pod = PodUtil.clone(pod);
		return pod;
	}
	
	@Override
	public String viewRecord() {
		return viewPod();
	}
	
	public String viewPod() {
		Pod pod = selectionContext.getSelection("pod");
		String url = viewPod(pod);
		return url;
	}
	
	public String viewPod(Pod pod) {
		try {
			String url = podPageManager.initializePodSummaryView(pod);
			podPageManager.pushContext(podWizard);
			initialize(pod);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editPod();
	}
	
	public String editPod() {
		Pod pod = selectionContext.getSelection("pod");
		String url = editPod(pod);
		return url;
	}
	
	public String editPod(Pod pod) {
		try {
			//pod = clone(pod);
			selectionContext.resetOrigin();
			selectionContext.setSelection("pod",  pod);
			String url = podPageManager.initializePodUpdatePage(pod);
			podPageManager.pushContext(podWizard);
			initialize(pod);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void savePod() {
		Pod pod = getPod();
		if (validatePod(pod)) {
			savePod(pod);
		}
	}
	
	public void persistPod(Pod pod) {
		savePod(pod);
	}
	
	public void savePod(Pod pod) {
		try {
			savePodToSystem(pod);
			podEventManager.fireAddedEvent(pod);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void savePodToSystem(Pod pod) {
		podDataManager.savePod(pod);
	}
	
	public void handleSavePod(@Observes @Add Pod pod) {
		savePod(pod);
	}
	
	public void enrichPod(Pod pod) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Pod pod) {
		return validatePod(pod);
	}
	
	public boolean validatePod(Pod pod) {
		Validator validator = getValidator();
		boolean isValid = PodUtil.validate(pod);
		Display display = getFromSession("display");
		display.setModule("podInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemovePod() {
		display = getFromSession("display");
		display.setModule("podInfo");
		Pod pod = selectionContext.getSelection("pod");
		if (pod == null) {
			display.error("Pod record must be selected.");
		}
	}
	
	public String handleRemovePod(@Observes @Remove Pod pod) {
		display = getFromSession("display");
		display.setModule("podInfo");
		try {
			display.info("Removing Pod "+PodUtil.getLabel(pod)+" from the system.");
			removePodFromSystem(pod);
			selectionContext.clearSelection("pod");
			podEventManager.fireClearSelectionEvent();
			podEventManager.fireRemovedEvent(pod);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removePodFromSystem(Pod pod) {
		if (podDataManager.removePod(pod))
			setRecord(null);
	}
	
	public void cancelPod() {
		BeanContext.removeFromSession("pod");
		podPageManager.removeContext(podWizard);
	}
	
}
