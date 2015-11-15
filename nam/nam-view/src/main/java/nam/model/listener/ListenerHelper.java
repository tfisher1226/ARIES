package nam.model.listener;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Listener;
import nam.model.util.ListenerUtil;


@SessionScoped
@Named("listenerHelper")
public class ListenerHelper extends AbstractElementHelper<Listener> implements Serializable {
	
	@Override
	public boolean isEmpty(Listener listener) {
		return ListenerUtil.isEmpty(listener);
	}
	
	@Override
	public String toString(Listener listener) {
		return ListenerUtil.toString(listener);
	}
	
	@Override
	public String toString(Collection<Listener> listenerList) {
		return ListenerUtil.toString(listenerList);
	}
	
	@Override
	public boolean validate(Listener listener) {
		return ListenerUtil.validate(listener);
	}
	
	@Override
	public boolean validate(Collection<Listener> listenerList) {
		return ListenerUtil.validate(listenerList);
	}
	
}
