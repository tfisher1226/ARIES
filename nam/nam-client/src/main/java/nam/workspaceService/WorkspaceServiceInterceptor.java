package nam.workspaceService;

import java.util.List;

import nam.model.Workspace;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;


@SuppressWarnings("serial")
public class WorkspaceServiceInterceptor extends MessageInterceptor<WorkspaceService> implements WorkspaceService {
	
	@Override
	public List<Workspace> getWorkspaceList() {
		try {
			log.info("#### [nam]: getWorkspaceList() sending...");
			Message request = createMessage("getWorkspaceList");
			Message response = getProxy().invoke(request);
			List<Workspace> workspaceList = response.getPart("workspaceList");
			return workspaceList;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

	@Override
	public Workspace getWorkspaceById(Long id) {
		try {
			log.info("#### [nam]: getWorkspaceById() sending...");
			Message request = createMessage("getWorkspaceById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
			Workspace workspace = response.getPart("workspace");
			return workspace;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Workspace getWorkspaceByUserName(String userName) {
		try {
			log.info("#### [nam]: getWorkspaceByUserName() sending...");
			Message request = createMessage("getWorkspaceByUserName");
			request.addPart("userName", userName);
			Message response = getProxy().invoke(request);
			Workspace workspace = response.getPart("workspace");
			return workspace;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addWorkspace(Workspace workspace) {
		try {
			log.info("#### [nam]: addWorkspace() sending...");
			Message request = createMessage("addWorkspace");
			request.addPart("workspace", workspace);
			Message response = getProxy().invoke(request);
			Long id = response.getPart("id");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveWorkspace(Workspace workspace) {
		try {
			log.info("#### [nam]: saveWorkspace() sending...");
			Message request = createMessage("saveWorkspace");
			request.addPart("workspace", workspace);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteWorkspace(Workspace workspace) {
		try {
			log.info("#### [nam]: deleteWorkspace() sending...");
			Message request = createMessage("deleteWorkspace");
			request.addPart("workspace", workspace);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
