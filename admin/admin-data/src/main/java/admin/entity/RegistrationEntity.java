package admin.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;


@Entity(name = "Registration")
@Table(name = "registration")
@Cache(usage = READ_WRITE)
public class RegistrationEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	@ForeignKey(name = "registration_user_fk", inverseName = "registration_user_inverse_fk")
	@Cache(usage = READ_WRITE)
	private UserEntity user;
	
	@Column(name = "login_count")
	private Long loginCount;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean isEnabled() {
		return enabled;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public UserEntity getUser() {
		return user;
	}
	
	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	public Long getLoginCount() {
		return loginCount;
	}
	
	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"] user="+getUser();
		return "getClass().getSimpleName(): user="+getUser();
	}
	
}
