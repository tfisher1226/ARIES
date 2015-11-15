package nam.model.adapter;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Adapter;
import nam.model.util.AdapterUtil;


@SessionScoped
@Named("adapterHelper")
public class AdapterHelper extends AbstractElementHelper<Adapter> implements Serializable {
	
	@Override
	public boolean isEmpty(Adapter adapter) {
		return AdapterUtil.isEmpty(adapter);
	}
	
	@Override
	public String toString(Adapter adapter) {
		return AdapterUtil.toString(adapter);
	}
	
	@Override
	public String toString(Collection<Adapter> adapterList) {
		return AdapterUtil.toString(adapterList);
	}
	
	@Override
	public boolean validate(Adapter adapter) {
		return AdapterUtil.validate(adapter);
	}
	
	@Override
	public boolean validate(Collection<Adapter> adapterList) {
		return AdapterUtil.validate(adapterList);
	}
	
}
