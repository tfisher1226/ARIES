package nam.model.minion;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Minion;
import nam.model.Pod;
import nam.model.pod.PodListManager;
import nam.model.pod.PodListObject;
import nam.model.util.MinionUtil;


@SessionScoped
@Named("minionHelper")
public class MinionHelper extends AbstractElementHelper<Minion> implements Serializable {
	
	@Override
	public boolean isEmpty(Minion minion) {
		return MinionUtil.isEmpty(minion);
	}
	
	@Override
	public String toString(Minion minion) {
		return MinionUtil.toString(minion);
	}
	
	@Override
	public String toString(Collection<Minion> minionList) {
		return MinionUtil.toString(minionList);
	}
	
	@Override
	public boolean validate(Minion minion) {
		return MinionUtil.validate(minion);
	}
	
	@Override
	public boolean validate(Collection<Minion> minionList) {
		return MinionUtil.validate(minionList);
	}
	
	public DataModel<PodListObject> getPods(Minion minion) {
		if (minion == null)
			return null;
		return getPods(minion.getPods());
	}
	
	public DataModel<PodListObject> getPods(Collection<Pod> podsList) {
		PodListManager podListManager = BeanContext.getFromSession("podListManager");
		DataModel<PodListObject> dataModel = podListManager.getDataModel(podsList);
		return dataModel;
	}
	
}
