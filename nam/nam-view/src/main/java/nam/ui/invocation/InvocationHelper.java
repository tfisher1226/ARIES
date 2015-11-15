package nam.ui.invocation;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.ui.Invocation;
import nam.ui.util.InvocationUtil;


@SessionScoped
@Named("invocationHelper")
public class InvocationHelper extends AbstractElementHelper<Invocation> implements Serializable {
	
	@Override
	public boolean isEmpty(Invocation invocation) {
		return InvocationUtil.isEmpty(invocation);
	}
	
	@Override
	public String toString(Invocation invocation) {
		return InvocationUtil.toString(invocation);
	}
	
	@Override
	public String toString(Collection<Invocation> invocationList) {
		return InvocationUtil.toString(invocationList);
	}
	
	@Override
	public boolean validate(Invocation invocation) {
		return InvocationUtil.validate(invocation);
	}
	
	@Override
	public boolean validate(Collection<Invocation> invocationList) {
		return InvocationUtil.validate(invocationList);
	}
	
}
