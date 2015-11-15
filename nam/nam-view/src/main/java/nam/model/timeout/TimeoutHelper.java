package nam.model.timeout;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Timeout;
import nam.model.util.TimeoutUtil;


@SessionScoped
@Named("timeoutHelper")
public class TimeoutHelper extends AbstractElementHelper<Timeout> implements Serializable {
	
	@Override
	public boolean isEmpty(Timeout timeout) {
		return TimeoutUtil.isEmpty(timeout);
	}
	
	@Override
	public String toString(Timeout timeout) {
		return TimeoutUtil.toString(timeout);
	}
	
	@Override
	public String toString(Collection<Timeout> timeoutList) {
		return TimeoutUtil.toString(timeoutList);
	}
	
	@Override
	public boolean validate(Timeout timeout) {
		return TimeoutUtil.validate(timeout);
	}
	
	@Override
	public boolean validate(Collection<Timeout> timeoutList) {
		return TimeoutUtil.validate(timeoutList);
	}
	
}
