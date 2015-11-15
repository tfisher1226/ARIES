package nam.model._import;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Import;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("importEventManager")
public class ImportEventManager extends AbstractEventManager<Import> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Import getInstance() {
		return selectionContext.getSelection("import");
	}
	
	public void removeImport() {
		Import _import = getInstance();
		fireRemoveEvent(_import);
	}
	
}
