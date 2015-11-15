package admin.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.FieldListManager;

import aries.generation.engine.GenerationContext;


@SessionScoped
@Named("userFieldListManager")
public class UserFieldListManager extends FieldListManager implements Serializable {

	private GenerationContext context;
	
	public UserFieldListManager() {
		//Element element = context.getElementByType("{http://nam/model}user");
		//setType(element);
	}
	
	public String getTitle() {
		return "User List";
	}
	
}
