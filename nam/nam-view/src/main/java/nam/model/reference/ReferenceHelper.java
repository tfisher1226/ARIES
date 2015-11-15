package nam.model.reference;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Reference;
import nam.model.util.ReferenceUtil;


@SessionScoped
@Named("referenceHelper")
public class ReferenceHelper extends AbstractElementHelper<Reference> implements Serializable {
	
	@Override
	public boolean isEmpty(Reference reference) {
		return ReferenceUtil.isEmpty(reference);
	}
	
	@Override
	public String toString(Reference reference) {
		return ReferenceUtil.toString(reference);
	}
	
	@Override
	public String toString(Collection<Reference> referenceList) {
		return ReferenceUtil.toString(referenceList);
	}
	
	@Override
	public boolean validate(Reference reference) {
		return ReferenceUtil.validate(reference);
	}
	
	@Override
	public boolean validate(Collection<Reference> referenceList) {
		return ReferenceUtil.validate(referenceList);
	}
	
}
