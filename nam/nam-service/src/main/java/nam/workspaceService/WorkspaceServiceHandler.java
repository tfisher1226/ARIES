package nam.workspaceService;

import java.util.List;

import nam.model.Workspace;

import org.aries.tx.Transactional;


public interface WorkspaceServiceHandler extends Transactional {
	
	public List<Workspace> getWorkspaceList();
	
	public Workspace getWorkspaceById(Long id);
	
	public Workspace getWorkspaceByName(String name);
	
	public Long addWorkspace(Workspace workspace);
	
	public void saveWorkspace(Workspace workspace);
	
	public void deleteWorkspace(Workspace workspace);
	
}
