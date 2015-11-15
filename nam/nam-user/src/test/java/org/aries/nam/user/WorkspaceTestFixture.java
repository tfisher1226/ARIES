package org.aries.nam.user;

import nam.model.Project;
import nam.model.User;
import nam.model.Workspace;
import nam.model.util.WorkspaceUtil;

import org.aries.nam.ModelTestFixture;


public class WorkspaceTestFixture {

	public static String getWorkspaceNameForTest() {
		return "Workspace1";
	}
	
	public static Workspace createWorkspaceForTest(String userName) {
		Workspace workspace = new Workspace();
		workspace.setName(getWorkspaceNameForTest());
		User user = UserTestFixture.createUserForTest(userName);
		Project project = ModelTestFixture.createProjectForTest(userName);
		WorkspaceUtil.getProjects(workspace).add(project);
		workspace.setUser(user.getUserName());
		return workspace;
	}

}
