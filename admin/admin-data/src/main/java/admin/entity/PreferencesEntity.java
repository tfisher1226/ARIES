package admin.entity;

import static javax.persistence.FetchType.EAGER;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;


@Entity(name = "Preferences")
@Table(name = "preferences")
@Cache(usage = READ_WRITE)
public class PreferencesEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "preferences")
	@Cache(usage = READ_WRITE)
	private UserEntity user;
	
	@Column(name = "theme_id")
	private String themeId;
	
	@Column(name = "work_state")
	@ElementCollection(targetClass = String.class, fetch = EAGER)
	@CollectionTable(name = "preferences_work_state", joinColumns = @JoinColumn(name = "preferences_id"))
	@Cache(usage = READ_WRITE)
	private Map<String, String> workState;
	
	
	@Column(name = "open_nodes")
	@ElementCollection(targetClass = Boolean.class, fetch = EAGER)
	@CollectionTable(name = "preferences_open_node", joinColumns = @JoinColumn(name = "preferences_id"))
	@Cache(usage = READ_WRITE)
	private Map<String, Boolean> openNodes;
	
	@Column(name = "selected_node")
	private Long selectedNode;
	
	@Column(name = "enable_tooltips")
	private Boolean enableTooltips;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public UserEntity getUser() {
		return user;
	}
	
	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	public String getThemeId() {
		return themeId;
	}
	
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	
	public Map<String, String> getWorkState() {
		if (workState == null)
			workState = new HashMap<String, String>();
		return workState;
	}
	
	public void setWorkState(Map<String, String> workState) {
		this.workState = new HashMap<String, String>();
		this.workState.putAll(workState);
	}
	
	public Map<String, Boolean> getOpenNodes() {
		if (openNodes == null)
			openNodes = new HashMap<String, Boolean>();
		return openNodes;
	}
	
	public void setOpenNodes(Map<String, Boolean> openNodes) {
		this.openNodes = new HashMap<String, Boolean>();
		this.openNodes.putAll(openNodes);
	}
	
	public Long getSelectedNode() {
		return selectedNode;
	}
	
	public void setSelectedNode(Long selectedNode) {
		this.selectedNode = selectedNode;
	}
	
	public Boolean isEnableTooltips() {
		return enableTooltips;
	}
	
	public Boolean getEnableTooltips() {
		return enableTooltips;
	}
	
	public void setEnableTooltips(Boolean enableTooltips) {
		this.enableTooltips = enableTooltips;
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"] user="+getUser();
		return "getClass().getSimpleName(): user="+getUser();
	}
	
}
