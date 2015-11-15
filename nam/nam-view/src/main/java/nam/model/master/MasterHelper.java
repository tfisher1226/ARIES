package nam.model.master;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Master;
import nam.model.util.MasterUtil;


@SessionScoped
@Named("masterHelper")
public class MasterHelper extends AbstractElementHelper<Master> implements Serializable {
	
	@Override
	public boolean isEmpty(Master master) {
		return MasterUtil.isEmpty(master);
	}
	
	@Override
	public String toString(Master master) {
		return MasterUtil.toString(master);
	}
	
	@Override
	public String toString(Collection<Master> masterList) {
		return MasterUtil.toString(masterList);
	}
	
	@Override
	public boolean validate(Master master) {
		return MasterUtil.validate(master);
	}
	
	@Override
	public boolean validate(Collection<Master> masterList) {
		return MasterUtil.validate(masterList);
	}
	
}
