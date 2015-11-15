package admin.ui.permission;

import java.io.Serializable;

import nam.model.Element;

import org.aries.ui.FieldListManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import aries.generation.engine.GenerationContext;


@AutoCreate
@BypassInterceptors
@Name("permissionFieldListManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class PermissionFieldListManager extends FieldListManager implements Serializable {

	private GenerationContext context;
	
	public PermissionFieldListManager() {
		//Element element = context.getElementByType("{http://www.aries.org/admin}permission");
		//setType(element);
	}
	
	public String getTitle() {
		return "Permission List";
	}
	
}
