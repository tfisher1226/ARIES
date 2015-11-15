package bookshop2.ui2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.aries.runtime.BeanContext;


@SessionScoped
@ManagedBean(name = "navigationManager")
public class NavigationManager implements Serializable {

//	@Inject
//	private NavigationDriver navigationDriver;
	
	@ManagedProperty(value = "#{navigationParser.groupList}")
	private List<GroupDescriptor> groups;

	private Map<String, String> itemMap;

	private String itemId;

	private String activeItem;
	
	
	public List<GroupDescriptor> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupDescriptor> groups) {
		this.groups = groups;
	}

	public String getActiveItem() {
		return activeItem;
	}

	public String getItemState(String itemId) {
		String state = this.itemMap.get(itemId);
		if (state == null) {
			state = "collapsed";
			this.itemMap.put(itemId, state);
		}
		if (!state.equals("collapsed") && !state.equals("expanded"))
			return state;
		return state;
	}

	public void setItemState(String itemId, String state) {
		this.itemMap.put(itemId, state);
	}
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public boolean isExpanded() {
		return getItemState(itemId).equals("expanded");
	}

	public boolean isCollapsed() {
		return getItemState(itemId).equals("collapsed");
	}

	public void setExpanded() {
		this.itemMap.put(itemId, "expanded");
		NavigationDriver navigationDriver = BeanContext.getFromSession("navigationDriver");
		navigationDriver.clearState();
	}

	public void setCollapsed() {
		this.itemMap.put(itemId, "collapsed");
		NavigationDriver navigationDriver = BeanContext.getFromSession("navigationDriver");
		navigationDriver.clearState();
	}

	@PostConstruct
	public void initialize() {
		itemMap = new HashMap<String, String>();
	}
	
}
