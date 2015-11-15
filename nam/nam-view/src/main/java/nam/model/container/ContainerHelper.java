package nam.model.container;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Container;
import nam.model.util.ContainerUtil;


@SessionScoped
@Named("containerHelper")
public class ContainerHelper extends AbstractElementHelper<Container> implements Serializable {
	
	@Override
	public boolean isEmpty(Container container) {
		return ContainerUtil.isEmpty(container);
	}
	
	@Override
	public String toString(Container container) {
		return ContainerUtil.toString(container);
	}
	
	@Override
	public String toString(Collection<Container> containerList) {
		return ContainerUtil.toString(containerList);
	}
	
	@Override
	public boolean validate(Container container) {
		return ContainerUtil.validate(container);
	}
	
	@Override
	public boolean validate(Collection<Container> containerList) {
		return ContainerUtil.validate(containerList);
	}
	
}
