package admin.ui.role;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.FieldListManager;

import aries.generation.engine.GenerationContext;


@SessionScoped
@Named("roleFieldListManager")
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
