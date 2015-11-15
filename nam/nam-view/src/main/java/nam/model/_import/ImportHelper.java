package nam.model._import;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Import;
import nam.model.util.ImportUtil;


@SessionScoped
@Named("importHelper")
public class ImportHelper extends AbstractElementHelper<Import> implements Serializable {
	
	@Override
	public boolean isEmpty(Import _import) {
		return ImportUtil.isEmpty(_import);
	}
	
	@Override
	public String toString(Import _import) {
		return ImportUtil.toString(_import);
	}
	
	@Override
	public String toString(Collection<Import> importList) {
		return ImportUtil.toString(importList);
	}
	
	@Override
	public boolean validate(Import _import) {
		return ImportUtil.validate(_import);
	}
	
	@Override
	public boolean validate(Collection<Import> importList) {
		return ImportUtil.validate(importList);
	}
	
	public DataModel<ImportListObject> getImports(Import _import) {
		if (_import == null)
			return null;
		return getImports(_import.getImports());
	}
	
	public DataModel<ImportListObject> getImports(Collection<Import> importsList) {
		ImportListManager importListManager = BeanContext.getFromSession("importListManager");
		DataModel<ImportListObject> dataModel = importListManager.getDataModel(importsList);
		return dataModel;
	}
	
}
