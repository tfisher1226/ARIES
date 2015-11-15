package nam.model.field;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Field;
import nam.model.util.FieldUtil;

import org.aries.ui.manager.AbstractElementHelper;


@SessionScoped
@Named("fieldHelper")
public class FieldHelper extends AbstractElementHelper<Field> implements Serializable {
	
	@Override
	public boolean isEmpty(Field field) {
		return FieldUtil.isEmpty(field);
	}
	
	@Override
	public String toString(Field field) {
		return FieldUtil.toString(field);
	}
	
	@Override
	public String toString(Collection<Field> fieldList) {
		return FieldUtil.toString(fieldList);
	}
	
	@Override
	public boolean validate(Field field) {
		return FieldUtil.validate(field);
	}
	
	@Override
	public boolean validate(Collection<Field> fieldList) {
		return FieldUtil.validate(fieldList);
	}
	
}
