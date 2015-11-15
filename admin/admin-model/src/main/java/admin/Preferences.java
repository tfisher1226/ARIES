package admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.MapAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Preferences", namespace = "http://admin", propOrder = {
	"id",
	"themeId",
	"workState",
	"openNodes",
	"selectedNode",
	"enableTooltips"
})
@XmlRootElement(name = "preferences", namespace = "http://admin")
public class Preferences implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://admin")
	private Long id;
	
	@XmlTransient
	private User user;
	
	@XmlElement(name = "themeId", namespace = "http://admin")
	private String themeId;
	
	@XmlElement(name = "workState", namespace = "http://admin")
	@XmlJavaTypeAdapter(MapAdapter.class)
	private Map<String, String> workState;
	
	@XmlElement(name = "openNodes", namespace = "http://admin", type = String.class)
	@XmlJavaTypeAdapter(MapAdapter.class)
	private Map<String, Boolean> openNodes;
	
	@XmlElement(name = "selectedNode", namespace = "http://admin")
	private Long selectedNode;
	
	@XmlElement(name = "enableTooltips", namespace = "http://admin", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enableTooltips;
	
	
	public Preferences() {
		workState = new HashMap<String, String>();
		openNodes = new HashMap<String, Boolean>();
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getThemeId() {
		return themeId;
	}
	
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	
	public Map<String, String> getWorkState() {
		synchronized (workState) {
			return workState;
		}
	}
	
	public void setWorkState(Map<String, String> workState) {
		if (workState == null) {
			this.workState = null;
		} else {
		synchronized (this.workState) {
				this.workState = new HashMap<String, String>();
				addToWorkState(workState);
			}
		}
	}

	public void addToWorkState(String workStateKey, String workStateValue) {
		if (workStateKey != null) {
			synchronized (this.workState) {
				this.workState.put(workStateKey, workStateValue);
			}
		}
	}

	public void addToWorkState(Map<String, String> workStateMap) {
		if (workStateMap != null && !workStateMap.isEmpty()) {
			synchronized (this.workState) {
				this.workState.putAll(workStateMap);
			}
		}
	}

	public void removeFromWorkState(String workStateKey) {
		if (workState != null ) {
			synchronized (this.workState) {
				this.workState.remove(workStateKey);
			}
		}
	}

	public void removeFromWorkState(Map<String, String> workStateCollection) {
		if (workStateCollection != null && !workStateCollection.isEmpty()) {
			synchronized (this.workState) {
				Set<String> keySet = workStateCollection.keySet();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					this.workState.remove(key);
				}
			}
		}
	}

	public void clearWorkState() {
		synchronized (workState) {
			workState.clear();
		}
	}
	
	public Map<String, Boolean> getOpenNodes() {
		synchronized (openNodes) {
			return openNodes;
		}
	}
	
	public void setOpenNodes(Map<String, Boolean> openNodes) {
		if (openNodes == null) {
			this.openNodes = null;
		} else {
		synchronized (this.openNodes) {
			this.openNodes = new HashMap<String, Boolean>();
			addToOpenNodes(openNodes);
		}
	}
	}

	public void addToOpenNodes(String openNodesKey, Boolean openNodesValue) {
		if (openNodesKey != null) {
			synchronized (this.openNodes) {
				this.openNodes.put(openNodesKey, openNodesValue);
			}
		}
	}

	public void addToOpenNodes(Map<String, Boolean> openNodesMap) {
		if (openNodesMap != null && !openNodesMap.isEmpty()) {
			synchronized (this.openNodes) {
				this.openNodes.putAll(openNodesMap);
			}
		}
	}

	public void removeFromOpenNodes(String openNodesKey) {
		if (openNodes != null ) {
			synchronized (this.openNodes) {
				this.openNodes.remove(openNodesKey);
			}
		}
	}

	public void removeFromOpenNodes(Map<String, Boolean> openNodesCollection) {
		if (openNodesCollection != null && !openNodesCollection.isEmpty()) {
			synchronized (this.openNodes) {
				Set<String> keySet = openNodesCollection.keySet();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					this.openNodes.remove(key);
				}
			}
		}
	}

	public void clearOpenNodes() {
		synchronized (openNodes) {
			openNodes.clear();
		}
	}
	
	public Long getSelectedNode() {
		return selectedNode;
	}
	
	public void setSelectedNode(Long selectedNode) {
		this.selectedNode = selectedNode;
	}
	
	public Boolean isEnableTooltips() {
		return enableTooltips != null && enableTooltips;
	}
	
	public Boolean getEnableTooltips() {
		return enableTooltips != null && enableTooltips;
	}
	
	public void setEnableTooltips(Boolean enableTooltips) {
		this.enableTooltips = enableTooltips;
	}
	
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		if (parent instanceof User) {
			this.user = (User) parent;
		}
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Preferences other = (Preferences) object;
			int status = compareObject(user, other.user);
			if (status != 0)
				return status;
		}
		return 0;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	protected <T extends Comparable<Object>> int compareObject(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
			return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Preferences other = (Preferences) object;
		if (id != null)
			return id.equals(other.id);
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		int hashCode = 0;
		if (user != null)
			hashCode += user.hashCode();
		if (hashCode == 0)
		return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Preferences: user="+user;
	}
	
}
