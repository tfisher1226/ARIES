package nam.model.unit;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import nam.model.Property;
import nam.model.Query;
import nam.model.Unit;
import nam.model.property.PropertyListManager;
import nam.model.property.PropertyListObject;
import nam.model.query.QueryListManager;
import nam.model.query.QueryListObject;
import nam.model.util.UnitUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;


@SessionScoped
@Named("unitHelper")
public class UnitHelper extends AbstractElementHelper<Unit> implements Serializable {
	
	@Override
	public boolean isEmpty(Unit unit) {
		return UnitUtil.isEmpty(unit);
	}
	
	@Override
	public String toString(Unit unit) {
		return UnitUtil.toString(unit);
	}
	
	@Override
	public String toString(Collection<Unit> unitList) {
		return UnitUtil.toString(unitList);
	}
	
	@Override
	public boolean validate(Unit unit) {
		return UnitUtil.validate(unit);
	}
	
	@Override
	public boolean validate(Collection<Unit> unitList) {
		return UnitUtil.validate(unitList);
	}
	
	public DataModel<Serializable> getIncludesAndImports(Unit unit) {
		if (unit == null)
			return null;
		return getIncludesAndImports(unit.getIncludesAndImports());
	}
	
	public DataModel<Serializable> getIncludesAndImports(Collection<Serializable> includesAndImportsList) {
		//SerializableListManager serializableListManager = BeanContext.getFromSession("serializableListManager");
		//DataModel<Serializable> dataModel = serializableListManager.getDataModel(includesAndImportsList);
		//return dataModel;
		return null;
	}
	
	public DataModel<PropertyListObject> getProperties(Unit unit) {
		if (unit == null)
			return null;
		return getProperties(UnitUtil.getProperties(unit));
	}
	
	public DataModel<PropertyListObject> getProperties(Collection<Property> propertiesList) {
		PropertyListManager propertyListManager = BeanContext.getFromSession("propertyListManager");
		DataModel<PropertyListObject> dataModel = propertyListManager.getDataModel(propertiesList);
		return dataModel;
	}
	
	public DataModel<QueryListObject> getQueries(Unit unit) {
		if (unit == null)
			return null;
		return getQueries(unit.getQueries());
	}
	
	public DataModel<QueryListObject> getQueries(Collection<Query> queriesList) {
		QueryListManager queryListManager = BeanContext.getFromSession("queryListManager");
		DataModel<QueryListObject> dataModel = queryListManager.getDataModel(queriesList);
		return dataModel;
	}
	
}
