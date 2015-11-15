package nam.model.fault;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Attribute;
import nam.model.Fault;
import nam.model.attribute.AttributeListManager;
import nam.model.attribute.AttributeListObject;
import nam.model.util.FaultUtil;


@SessionScoped
@Named("faultHelper")
public class FaultHelper extends AbstractElementHelper<Fault> implements Serializable {
	
	@Override
	public boolean isEmpty(Fault fault) {
		return FaultUtil.isEmpty(fault);
	}
	
	@Override
	public String toString(Fault fault) {
		return FaultUtil.toString(fault);
	}
	
	@Override
	public String toString(Collection<Fault> faultList) {
		return FaultUtil.toString(faultList);
	}
	
	@Override
	public boolean validate(Fault fault) {
		return FaultUtil.validate(fault);
	}
	
	@Override
	public boolean validate(Collection<Fault> faultList) {
		return FaultUtil.validate(faultList);
	}
	
	public DataModel<AttributeListObject> getAttributes(Fault fault) {
		if (fault == null)
			return null;
		return getAttributes(fault.getAttributes());
	}
	
	public DataModel<AttributeListObject> getAttributes(Collection<Attribute> attributesList) {
		AttributeListManager attributeListManager = BeanContext.getFromSession("attributeListManager");
		DataModel<AttributeListObject> dataModel = attributeListManager.getDataModel(attributesList);
		return dataModel;
	}
	
}
