package nam.model.pod;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Container;
import nam.model.Pod;
import nam.model.Volume;
import nam.model.container.ContainerListManager;
import nam.model.container.ContainerListObject;
import nam.model.util.PodUtil;
import nam.model.volume.VolumeListManager;
import nam.model.volume.VolumeListObject;


@SessionScoped
@Named("podHelper")
public class PodHelper extends AbstractElementHelper<Pod> implements Serializable {
	
	@Override
	public boolean isEmpty(Pod pod) {
		return PodUtil.isEmpty(pod);
	}
	
	@Override
	public String toString(Pod pod) {
		return PodUtil.toString(pod);
	}
	
	@Override
	public String toString(Collection<Pod> podList) {
		return PodUtil.toString(podList);
	}
	
	@Override
	public boolean validate(Pod pod) {
		return PodUtil.validate(pod);
	}
	
	@Override
	public boolean validate(Collection<Pod> podList) {
		return PodUtil.validate(podList);
	}
	
	public DataModel<ContainerListObject> getContainers(Pod pod) {
		if (pod == null)
			return null;
		return getContainers(pod.getContainers());
	}
	
	public DataModel<ContainerListObject> getContainers(Collection<Container> containersList) {
		ContainerListManager containerListManager = BeanContext.getFromSession("containerListManager");
		DataModel<ContainerListObject> dataModel = containerListManager.getDataModel(containersList);
		return dataModel;
	}
	
	public DataModel<VolumeListObject> getVolumes(Pod pod) {
		if (pod == null)
			return null;
		return getVolumes(pod.getVolumes());
	}
	
	public DataModel<VolumeListObject> getVolumes(Collection<Volume> volumesList) {
		VolumeListManager volumeListManager = BeanContext.getFromSession("volumeListManager");
		DataModel<VolumeListObject> dataModel = volumeListManager.getDataModel(volumesList);
		return dataModel;
	}
	
}
