package admin.ui.role;

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
@Name("roleFieldListManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class RoleFieldListManager extends FieldListManager implements Serializable {
	
	private GenerationContext context;
	
	public RoleFieldListManager() {
		//Element elementByName = GenerationContext.INSTANCE.getElementByName("org.aries.model.Role");
		//Element element = context.getElementByType("{http://www.aries.org/admin}role");
		//setType(element);
	}
	
	public String getTitle() {
		return "Role List";
	}
	
}
