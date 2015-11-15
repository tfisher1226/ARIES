package nam.projectService;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import nam.model.Project;


@WebService(name = "projectService", targetNamespace = "http://nam/model")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ProjectService {
	
	public String ID = "nam.projectService";
	
	public List<Project> getProjectList();
	
	public Project getProjectById(Long id);
	
	public Project getProjectByName(String name);
	
	public List<Project> getProjectListByUser(String user);
	
	public Long addProject(Project project);
	
	public void saveProject(Project project);
	
	public void deleteProject(Project project);
	
}
