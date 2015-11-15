package nam.model.property;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Property;
import nam.model.util.PropertyUtil;


@SessionScoped
@Named("propertyHelper")
public class PropertyHelper extends AbstractElementHelper<Property> implements Serializable {
	
	@Override
	public boolean isEmpty(Property property) {
		return PropertyUtil.isEmpty(property);
	}
	
	@Override
	public String toString(Property property) {
		return PropertyUtil.toString(property);
	}
	
	@Override
	public String toString(Collection<Property> propertyList) {
		return PropertyUtil.toString(propertyList);
	}
	
	@Override
	public boolean validate(Property property) {
		return PropertyUtil.validate(property);
	}
	
	@Override
	public boolean validate(Collection<Property> propertyList) {
		return PropertyUtil.validate(propertyList);
	}
	
}
