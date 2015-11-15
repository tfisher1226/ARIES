package nam.model.type;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Type;
import nam.model.util.TypeUtil;


@SessionScoped
@Named("typeHelper")
public class TypeHelper extends AbstractElementHelper<Type> implements Serializable {
	
	@Override
	public boolean isEmpty(Type type) {
		return TypeUtil.isEmpty(type);
	}
	
	@Override
	public String toString(Type type) {
		return TypeUtil.toString(type);
	}
	
	@Override
	public String toString(Collection<Type> typeList) {
		return TypeUtil.toString(typeList);
	}
	
	@Override
	public boolean validate(Type type) {
		return TypeUtil.validate(type);
	}
	
	@Override
	public boolean validate(Collection<Type> typeList) {
		return TypeUtil.validate(typeList);
	}
	
}
