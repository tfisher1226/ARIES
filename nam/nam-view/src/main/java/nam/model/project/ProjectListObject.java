package nam.model.project;

import java.io.Serializable;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractListObject;

import nam.model.Project;
import nam.model.util.ProjectUtil;


public class ProjectListObject extends AbstractListObject<Project> implements Comparable<ProjectListObject>, Serializable {
	
	private Project project;
	
	
	public ProjectListObject(Project project) {
		this.project = project;
	}
	
	
	public Project getProject() {
		return project;
	}
	
	@Override
	public Object getKey() {
		return getKey(project);
	}
	
	public Object getKey(Project project) {
		return ProjectUtil.getKey(project);
	}
	
	@Override
	public String getLabel() {
		return getLabel(project);
	}
	
	public String getLabel(Project project) {
		return ProjectUtil.getLabel(project);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}

	@Override
	public String getIcon() {
		return "/icons/nam/Project16.gif";
	}
	
	@Override
	public String toString() {
		return toString(project);
	}
	
	@Override
	public String toString(Project project) {
		return ProjectUtil.toString(project);
	}
	
	@Override
	public int compareTo(ProjectListObject other) {
		Object thisKey = getKey(this.project);
		Object otherKey = getKey(other.project);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ProjectListObject other = (ProjectListObject) object;
		Object thisKey = ProjectUtil.getKey(this.project);
		Object otherKey = ProjectUtil.getKey(other.project);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
