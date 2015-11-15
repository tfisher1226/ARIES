package nam.ui.control;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.ui.Control;
import nam.ui.util.ControlUtil;


@SessionScoped
@Named("controlHelper")
public class ControlHelper extends AbstractElementHelper<Control> implements Serializable {
	
	@Override
	public boolean isEmpty(Control control) {
		return ControlUtil.isEmpty(control);
	}
	
	@Override
	public String toString(Control control) {
		return ControlUtil.toString(control);
	}
	
	@Override
	public String toString(Collection<Control> controlList) {
		return ControlUtil.toString(controlList);
	}
	
	@Override
	public boolean validate(Control control) {
		return ControlUtil.validate(control);
	}
	
	@Override
	public boolean validate(Collection<Control> controlList) {
		return ControlUtil.validate(controlList);
	}
	
}
