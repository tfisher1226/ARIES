package nam.ui.userInterfaceType;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.UserInterfaceType;
import nam.ui.design.SelectionContext;
import nam.ui.util.UserInterfaceTypeUtil;


@SessionScoped
@Named("userInterfaceTypeDataManager")
public class UserInterfaceTypeDataManager implements Serializable {
	
//	@Inject
//	private UserInterfaceTypeEventManager userInterfaceTypeEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
}
