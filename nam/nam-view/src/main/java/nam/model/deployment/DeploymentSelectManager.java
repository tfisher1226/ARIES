package nam.model.deployment;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Deployment;
import nam.model.util.DeploymentUtil;


@SessionScoped
@Named("deploymentSelectManager")
public class DeploymentSelectManager extends AbstractSelectManager<Deployment, DeploymentListObject> implements Serializable {
	
	@Inject
	private DeploymentDataManager deploymentDataManager;
	
	@Inject
	private DeploymentHelper deploymentHelper;
	
	
	@Override
	public String getClientId() {
		return "deploymentSelect";
	}
	
	@Override
	public String getTitle() {
		return "Deployment Selection";
	}
	
	@Override
	protected Class<Deployment> getRecordClass() {
		return Deployment.class;
	}
	
	@Override
	public boolean isEmpty(Deployment deployment) {
		return deploymentHelper.isEmpty(deployment);
	}
	
	@Override
	public String toString(Deployment deployment) {
		return deploymentHelper.toString(deployment);
	}
	
	protected DeploymentHelper getDeploymentHelper() {
		return BeanContext.getFromSession("deploymentHelper");
	}
	
	protected DeploymentListManager getDeploymentListManager() {
		return BeanContext.getFromSession("deploymentListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshDeploymentList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Deployment> recordList) {
		DeploymentListManager deploymentListManager = getDeploymentListManager();
		DataModel<DeploymentListObject> dataModel = deploymentListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshDeploymentList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Deployment> refreshRecords() {
		try {
			Collection<Deployment> records = deploymentDataManager.getDeploymentList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Deployment> deploymentList) {
		Collections.sort(deploymentList, new Comparator<Deployment>() {
			public int compare(Deployment deployment1, Deployment deployment2) {
				String text1 = DeploymentUtil.toString(deployment1);
				String text2 = DeploymentUtil.toString(deployment2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
