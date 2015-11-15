package nam.workspaceService;

import java.util.List;

import nam.model.Workspace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;


public class WorkspaceServiceClient extends AbstractDelegate implements WorkspaceService {
	
	private static final Log log = LogFactory.getLog(WorkspaceServiceClient.class);
	

	@Override
	public String getDomain() {
		return "aries.org";
	}
	
	@Override
	public String getServiceId() {
		return WorkspaceService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public WorkspaceService getProxy() throws Exception {
		return getProxy(WorkspaceService.ID);
	}
	
	@Override
	public List<Workspace> getWorkspaceList() {
		try {
			List<Workspace> workspaceList = getProxy().getWorkspaceList();
			return workspaceList;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}

	@Override
	public Workspace getWorkspaceById(Long id) {
		try {
			Workspace workspace = getProxy().getWorkspaceById(id);
			return workspace;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Workspace getWorkspaceByUserName(String userName) {
		try {
			Workspace workspace = getProxy().getWorkspaceByUserName(userName);
			return workspace;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addWorkspace(Workspace workspace) {
		try {
			Long id = getProxy().addWorkspace(workspace);
			return id;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveWorkspace(Workspace workspace) {
		try {
			getProxy().saveWorkspace(workspace);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteWorkspace(Workspace workspace) {
		try {
			getProxy().deleteWorkspace(workspace);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
