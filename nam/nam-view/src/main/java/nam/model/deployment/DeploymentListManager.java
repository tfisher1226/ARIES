package nam.model.deployment;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Deployment;
import nam.model.util.DeploymentUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("deploymentListManager")
public class DeploymentListManager extends AbstractDomainListManager<Deployment, DeploymentListObject> implements Serializable {
	
	@Inject
	private DeploymentDataManager deploymentDataManager;
	
	@Inject
	private DeploymentEventManager deploymentEventManager;
	
	@Inject
	private DeploymentInfoManager deploymentInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "deploymentList";
	}
	
	@Override
	public String getTitle() {
		return "Deployment List";
	}
	
	@Override
	public Object getRecordKey(Deployment deployment) {
		return DeploymentUtil.getKey(deployment);
	}
	
	@Override
	public String getRecordName(Deployment deployment) {
		return DeploymentUtil.getLabel(deployment);
	}
	
	@Override
	protected Class<Deployment> getRecordClass() {
		return Deployment.class;
	}
	
	@Override
	protected Deployment getRecord(DeploymentListObject rowObject) {
		return rowObject.getDeployment();
	}
	
	@Override
	public Deployment getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? DeploymentUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Deployment deployment) {
		super.setSelectedRecord(deployment);
		fireSelectedEvent(deployment);
	}
	
	protected void fireSelectedEvent(Deployment deployment) {
		deploymentEventManager.fireSelectedEvent(deployment);
	}
	
	public boolean isSelected(Deployment deployment) {
		Deployment selection = selectionContext.getSelection("deployment");
		boolean selected = selection != null && selection.equals(deployment);
		return selected;
	}
	
	@Override
	protected DeploymentListObject createRowObject(Deployment deployment) {
		DeploymentListObject listObject = new DeploymentListObject(deployment);
		listObject.setSelected(isSelected(deployment));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Deployment> createRecordList() {
		try {
			Collection<Deployment> deploymentList = deploymentDataManager.getDeploymentList();
			if (deploymentList != null)
				return deploymentList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewDeployment() {
		return viewDeployment(selectedRecordKey);
	}
	
	public String viewDeployment(Object recordKey) {
		Deployment deployment = recordByKeyMap.get(recordKey);
		return viewDeployment(deployment);
	}
	
	public String viewDeployment(Deployment deployment) {
		String url = deploymentInfoManager.viewDeployment(deployment);
		return url;
	}
	
	public String editDeployment() {
		return editDeployment(selectedRecordKey);
	}
	
	public String editDeployment(Object recordKey) {
		Deployment deployment = recordByKeyMap.get(recordKey);
		return editDeployment(deployment);
	}
	
	public String editDeployment(Deployment deployment) {
		String url = deploymentInfoManager.editDeployment(deployment);
		return url;
	}
	
	public void removeDeployment() {
		removeDeployment(selectedRecordKey);
	}
	
	public void removeDeployment(Object recordKey) {
		Deployment deployment = recordByKeyMap.get(recordKey);
		removeDeployment(deployment);
	}
	
	public void removeDeployment(Deployment deployment) {
		try {
			if (deploymentDataManager.removeDeployment(deployment))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelDeployment(@Observes @Cancelled Deployment deployment) {
		try {
			//Object key = DeploymentUtil.getKey(deployment);
			//recordByKeyMap.put(key, deployment);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("deployment");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateDeployment(Collection<Deployment> deploymentList) {
		return DeploymentUtil.validate(deploymentList);
	}
	
	public void exportDeploymentList(@Observes @Export String tableId) {
		//String tableId = "pageForm:deploymentListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
