package nam.ui.userInterface;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.ui.UserInterface;
import nam.ui.util.UserInterfaceUtil;


@SessionScoped
@Named("userInterfaceHelper")
public class UserInterfaceHelper extends AbstractElementHelper<UserInterface> implements Serializable {
	
	@Override
	public boolean isEmpty(UserInterface userInterface) {
		return UserInterfaceUtil.isEmpty(userInterface);
	}
	
	@Override
	public String toString(UserInterface userInterface) {
		return UserInterfaceUtil.toString(userInterface);
	}
	
	@Override
	public String toString(Collection<UserInterface> userInterfaceList) {
		return UserInterfaceUtil.toString(userInterfaceList);
	}
	
	@Override
	public boolean validate(UserInterface userInterface) {
		return UserInterfaceUtil.validate(userInterface);
	}
	
	@Override
	public boolean validate(Collection<UserInterface> userInterfaceList) {
		return UserInterfaceUtil.validate(userInterfaceList);
	}
	
}
