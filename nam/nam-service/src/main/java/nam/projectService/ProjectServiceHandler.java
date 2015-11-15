package nam.projectService;

import java.util.List;

import nam.model.Project;

import org.aries.tx.Transactional;


public interface ProjectServiceHandler extends Transactional {
	
	public List<Project> getProjectList();
	
	public Project getProjectById(Long id);
	
	public Project getProjectByName(String name);
	
	public List<Project> getProjectListByUser(String user);
	
	public Long addProject(Project project);
	
	public void saveProject(Project project);
	
	public void deleteProject(Project project);
	
}
