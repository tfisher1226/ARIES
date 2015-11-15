package nam.ui.userInterfaceType;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import nam.ui.UserInterfaceType;
import nam.ui.util.UserInterfaceTypeUtil;


@SessionScoped
@Named("userInterfaceTypeHelper")
public class UserInterfaceTypeHelper extends AbstractEnumerationHelper<UserInterfaceType> implements Serializable {
	
	@Produces
	public UserInterfaceType[] getUserInterfaceTypeArray() {
		return UserInterfaceType.values();
	}
	
	@Override
	public String toString(UserInterfaceType userInterfaceType) {
		return userInterfaceType.name();
	}
	
//	@Override
//	public String toString(Collection<UserInterfaceType> userInterfaceTypeList) {
//		return UserInterfaceTypeUtil.toString(userInterfaceTypeList);
//	}
	
}
