package nam.workspaceService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nam.model.Workspace;


@WebService(name = "workspaceService", targetNamespace = "http://nam/model")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface WorkspaceService {
	
	public String ID = "nam.workspaceService";
	
	public List<Workspace> getWorkspaceList();
	
	public Workspace getWorkspaceById(Long id);
	
	public Workspace getWorkspaceByWorkspaceName(String workspaceName);
	
	public Long addWorkspace(Workspace workspace);
	
	public void saveWorkspace(Workspace workspace);
	
	public void deleteWorkspace(Workspace workspace);
	
}
