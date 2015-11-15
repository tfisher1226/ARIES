package admin.permission;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.FieldListManager;

import aries.generation.engine.GenerationContext;


@SessionScoped
@Named("permissionFieldListManager")
public class PermissionFieldListManager extends FieldListManager implements Serializable {

	private GenerationContext context;
	
	public PermissionFieldListManager() {
		//Element element = context.getElementByType("{http://nam/model}permission");
		//setType(element);
	}
	
	public String getTitle() {
		return "Permission List";
	}
	
}
