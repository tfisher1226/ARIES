package nam.model.transacted;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Component;
import nam.model.Service;
import nam.model.Transacted;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;

import org.aries.Assert;


@SessionScoped
@Named("transactedDataManager")
public class TransactedDataManager implements Serializable {
	
	@Inject
	private TransactedEventManager transactedEventManager;
	
	@Inject
	private SelectionContext selectionContext;

	private Object owner;
	
	
	public Object getOwner() {
		return owner;
	}
	
	public void setOwner(Object owner) {
		this.owner = owner;
	}
	
	public Transacted getTransacted() {
		Assert.notNull(owner, "Owner not specified");
		if (owner instanceof Service)
			return ((Service) owner).getTransacted();
		else if (owner instanceof Component)
			return ((Component) owner).getTransacted();
		return null;
	}
	
//	public Collection<Transacted> getTransactedList() {
//		Assert.notNull(owner, "Owner not specified");
//		if (owner instanceof Service)
//			getTransactedList((Service) owner);
//		return null;
//	}

	public void saveTransacted(Transacted transacted) {
		Assert.notNull(owner, "Owner not specified");
		if (owner instanceof Service)
			((Service) owner).setTransacted(transacted);
		else if (owner instanceof Component)
			((Component) owner).setTransacted(transacted);
	}
		
	public boolean removeTransacted(Transacted transacted) {
		Assert.notNull(owner, "Owner not specified");
		if (owner instanceof Service)
			((Service) owner).setTransacted(null);
		else if (owner instanceof Component)
			((Component) owner).setTransacted(null);
		return true;
	}
	
}
