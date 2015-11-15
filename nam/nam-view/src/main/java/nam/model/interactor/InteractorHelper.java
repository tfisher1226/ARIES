package nam.model.interactor;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Interactor;
import nam.model.util.InteractorUtil;


@SessionScoped
@Named("interactorHelper")
public class InteractorHelper extends AbstractElementHelper<Interactor> implements Serializable {
	
	@Override
	public boolean isEmpty(Interactor interactor) {
		return InteractorUtil.isEmpty(interactor);
	}
	
	@Override
	public String toString(Interactor interactor) {
		return InteractorUtil.toString(interactor);
	}
	
	@Override
	public String toString(Collection<Interactor> interactorList) {
		return InteractorUtil.toString(interactorList);
	}
	
	@Override
	public boolean validate(Interactor interactor) {
		return InteractorUtil.validate(interactor);
	}
	
	@Override
	public boolean validate(Collection<Interactor> interactorList) {
		return InteractorUtil.validate(interactorList);
	}
	
}
