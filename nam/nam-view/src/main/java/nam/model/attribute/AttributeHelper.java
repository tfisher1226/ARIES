package nam.model.attribute;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Attribute;
import nam.model.util.AttributeUtil;


@SessionScoped
@Named("attributeHelper")
public class AttributeHelper extends AbstractElementHelper<Attribute> implements Serializable {
	
	@Override
	public boolean isEmpty(Attribute attribute) {
		return AttributeUtil.isEmpty(attribute);
	}
	
	@Override
	public String toString(Attribute attribute) {
		return AttributeUtil.toString(attribute);
	}
	
	@Override
	public String toString(Collection<Attribute> attributeList) {
		return AttributeUtil.toString(attributeList);
	}
	
	@Override
	public boolean validate(Attribute attribute) {
		return AttributeUtil.validate(attribute);
	}
	
	@Override
	public boolean validate(Collection<Attribute> attributeList) {
		return AttributeUtil.validate(attributeList);
	}
	
}
