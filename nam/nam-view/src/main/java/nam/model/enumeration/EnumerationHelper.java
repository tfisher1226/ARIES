package nam.model.enumeration;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Enumeration;
import nam.model.Literal;
import nam.model.literal.LiteralListManager;
import nam.model.literal.LiteralListObject;
import nam.model.util.EnumerationUtil;


@SessionScoped
@Named("enumerationHelper")
public class EnumerationHelper extends AbstractElementHelper<Enumeration> implements Serializable {
	
	@Override
	public boolean isEmpty(Enumeration enumeration) {
		return EnumerationUtil.isEmpty(enumeration);
	}
	
	@Override
	public String toString(Enumeration enumeration) {
		return EnumerationUtil.toString(enumeration);
	}
	
	@Override
	public String toString(Collection<Enumeration> enumerationList) {
		return EnumerationUtil.toString(enumerationList);
	}
	
	@Override
	public boolean validate(Enumeration enumeration) {
		return EnumerationUtil.validate(enumeration);
	}
	
	@Override
	public boolean validate(Collection<Enumeration> enumerationList) {
		return EnumerationUtil.validate(enumerationList);
	}
	
	public DataModel<LiteralListObject> getLiterals(Enumeration enumeration) {
		if (enumeration == null)
			return null;
		return getLiterals(enumeration.getLiterals());
	}
	
	public DataModel<LiteralListObject> getLiterals(Collection<Literal> literalsList) {
		LiteralListManager literalListManager = BeanContext.getFromSession("literalListManager");
		DataModel<LiteralListObject> dataModel = literalListManager.getDataModel(literalsList);
		return dataModel;
	}
	
}
