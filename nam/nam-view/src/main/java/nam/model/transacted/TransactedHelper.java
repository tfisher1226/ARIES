package nam.model.transacted;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Transacted;
import nam.model.util.TransactedUtil;


@SessionScoped
@Named("transactedHelper")
public class TransactedHelper extends AbstractElementHelper<Transacted> implements Serializable {
	
	@Override
	public boolean isEmpty(Transacted transacted) {
		return TransactedUtil.isEmpty(transacted);
	}
	
	@Override
	public String toString(Transacted transacted) {
		return TransactedUtil.toString(transacted);
	}
	
	@Override
	public String toString(Collection<Transacted> transactedList) {
		return TransactedUtil.toString(transactedList);
	}
	
	@Override
	public boolean validate(Transacted transacted) {
		return TransactedUtil.validate(transacted);
	}
	
	@Override
	public boolean validate(Collection<Transacted> transactedList) {
		return TransactedUtil.validate(transactedList);
	}
	
}
