package nam.entity;

import static javax.persistence.FetchType.EAGER;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;


@Entity(name = "Project")
@Table(name = "project")
@Cache(usage = READ_WRITE)
public class ProjectEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "domain", nullable = false)
	private String domain;
	
	@Column(name = "version", nullable = false)
	private String version;
	
	@Column(name = "owner", nullable = false)
	private String owner;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@Column(name = "shared")
	private Boolean shared;
	
	@Column(name = "files")
	@ElementCollection(targetClass = String.class, fetch = EAGER)
	@CollectionTable(name = "project_file", joinColumns = @JoinColumn(name = "project_id"))
	@Cache(usage = READ_WRITE)
	private List<FileEntity> files;
	
	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name = "last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getShared() {
		return shared;
	}

	public void setShared(Boolean shared) {
		this.shared = shared;
	}
	
	public List<FileEntity> getFiles() {
		if (files == null)
			files = new ArrayList<FileEntity>();
		return files;
	}
	
	public void setFiles(List<FileEntity> files) {
		this.files = new ArrayList<FileEntity>();
		this.files.addAll(files);
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"] name="+getName();
		return "getClass().getSimpleName(): name="+getName();
	}
	
}
