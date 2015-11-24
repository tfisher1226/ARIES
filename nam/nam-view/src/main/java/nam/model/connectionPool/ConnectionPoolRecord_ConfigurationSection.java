package nam.model.connectionPool;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.ConnectionPool;
import nam.model.util.ConnectionPoolUtil;


@SessionScoped
@Named("connectionPoolConfigurationSection")
public class ConnectionPoolRecord_ConfigurationSection extends AbstractWizardPage<ConnectionPool> implements Serializable {
	
	private ConnectionPool connectionPool;
	
	
	public ConnectionPoolRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public ConnectionPool getConnectionPool() {
		return connectionPool;
	}
	
	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	@Override
	public void initialize(ConnectionPool connectionPool) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setConnectionPool(connectionPool);
	}
	
	@Override
	public void validate() {
		if (connectionPool == null) {
			validator.missing("ConnectionPool");
		} else {
		}
	}
	
}