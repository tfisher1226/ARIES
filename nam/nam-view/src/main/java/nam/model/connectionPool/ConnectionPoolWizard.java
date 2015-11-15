package nam.model.connectionPool;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.ConnectionPool;
import nam.model.Project;
import nam.model.util.ConnectionPoolUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("connectionPoolWizard")
@SuppressWarnings("serial")
public class ConnectionPoolWizard extends AbstractDomainElementWizard<ConnectionPool> implements Serializable {
	
	@Inject
	private ConnectionPoolDataManager connectionPoolDataManager;
	
	@Inject
	private ConnectionPoolPageManager connectionPoolPageManager;
	
	@Inject
	private ConnectionPoolEventManager connectionPoolEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "ConnectionPool";
	}
	
	@Override
	public String getUrlContext() {
		return connectionPoolPageManager.getConnectionPoolWizardPage();
	}
	
	@Override
	public void initialize(ConnectionPool connectionPool) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(connectionPoolPageManager.getSections());
		super.initialize(connectionPool);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		connectionPoolPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		connectionPoolPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		connectionPoolPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		connectionPoolPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		ConnectionPool connectionPool = getInstance();
		connectionPoolDataManager.saveConnectionPool(connectionPool);
		connectionPoolEventManager.fireSavedEvent(connectionPool);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		ConnectionPool connectionPool = getInstance();
		//TODO take this out soon
		if (connectionPool == null)
			connectionPool = new ConnectionPool();
		connectionPoolEventManager.fireCancelledEvent(connectionPool);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		ConnectionPool connectionPool = selectionContext.getSelection("connectionPool");
		String name = connectionPool.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("connectionPoolWizard");
			display.error("ConnectionPool name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
