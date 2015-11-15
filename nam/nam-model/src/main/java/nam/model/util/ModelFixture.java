package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.File;
import nam.model.FileType;
import nam.model.Project;
import nam.model.Workspace;

import org.aries.Assert;
import org.aries.util.BaseFixture;


public class ModelFixture extends BaseFixture {
	
	private static long workspaceCount = 0L;
	
	private static long projectCount = 0L;
	
	private static long fileCount = 0L;
	
	
	public static Workspace createEmptyWorkspace() {
		Workspace workspace = new Workspace();
		return workspace;
	}
	
	public static Workspace createWorkspace() {
		Workspace workspace = createWorkspace(workspaceCount++);
		return workspace;
	}
	
	public static Workspace createWorkspace(long index) {
		Workspace workspace = createEmptyWorkspace();
		workspace.setName("dummyName" + index);
		workspace.setUser("dummyUser" + index);
		//workspace.getProjects().addAll(createProject_List(2));
		workspace.setEnabled(true);
		workspace.setCreationDate(new Date(1000L + index));
		workspace.setLastUpdate(new Date(1000L + index));
		workspace.setId(10L * index);
		return workspace;
	}
	
	public static List<Workspace> createEmptyWorkspace_List() {
		return new ArrayList<Workspace>();
	}
	
	public static List<Workspace> createWorkspace_List() {
		return createWorkspace_List(2);
	}
	
	public static List<Workspace> createWorkspace_List(int size) {
		return createWorkspace_List(workspaceCount++, size);
	}
	
	public static List<Workspace> createWorkspace_List(long index, int size) {
		List<Workspace> workspaceList = createEmptyWorkspace_List();
		long limit = index + size;
		for (; index < limit; index++)
			workspaceList.add(createWorkspace(index));
		return workspaceList;
	}
	
	public static Set<Workspace> createEmptyWorkspace_Set() {
		return new HashSet<Workspace>();
	}
	
	public static Set<Workspace> createWorkspace_Set() {
		return createWorkspace_Set(2);
	}
	
	public static Set<Workspace> createWorkspace_Set(int size) {
		return createWorkspace_Set(workspaceCount++, size);
	}
	
	public static Set<Workspace> createWorkspace_Set(long index, int size) {
		Set<Workspace> workspaceSet = createEmptyWorkspace_Set();
		long limit = index + size;
		for (; index < limit; index++)
			workspaceSet.add(createWorkspace(index));
		return workspaceSet;
	}
	
	public static void assertWorkspaceExists(Collection<Workspace> workspaceList, Workspace workspace) {
		Assert.notNull(workspaceList, "Workspace list must be specified");
		Assert.notNull(workspace, "Workspace record must be specified");
		Iterator<Workspace> iterator = workspaceList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Workspace workspace1 = iterator.next();
			if (workspace1.equals(workspace)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Workspace should exist: "+workspace);
	}
	
	public static void assertWorkspaceCorrect(Workspace workspace) {
		long index = workspace.getId() / 10L;
		assertObjectCorrect("Name", workspace.getName(), index);
		assertObjectCorrect("User", workspace.getUser(), index);
		assertObjectCorrect("Enabled", workspace.getEnabled(), index);
		assertObjectCorrect("CreationDate", workspace.getCreationDate(), index);
		assertObjectCorrect("LastUpdate", workspace.getLastUpdate(), index);
		//assertProjectCorrect(workspace.getProjects());
	}
	
	public static void assertWorkspaceCorrect(Collection<Workspace> workspaceList) {
		Assert.isTrue(workspaceList.size() == 2, "Workspace count not correct");
		Iterator<Workspace> iterator = workspaceList.iterator();
		while (iterator.hasNext()) {
			Workspace workspace = iterator.next();
			assertWorkspaceCorrect(workspace);
		}
	}
	
	public static void assertSameWorkspace(Workspace workspace1, Workspace workspace2) {
		assertSameWorkspace(workspace1, workspace2, "");
	}
	
	public static void assertSameWorkspace(Workspace workspace1, Workspace workspace2, String message) {
		assertObjectExists("Workspace1", workspace1);
		assertObjectExists("Workspace2", workspace2);
		assertObjectEquals("Name", workspace1.getName(), workspace2.getName(), message);
		assertObjectEquals("User", workspace1.getUser(), workspace2.getUser(), message);
		assertObjectEquals("Enabled", workspace1.getEnabled(), workspace2.getEnabled(), message);
		assertObjectEquals("CreationDate", workspace1.getCreationDate(), workspace2.getCreationDate(), message);
		assertObjectEquals("LastUpdate", workspace1.getLastUpdate(), workspace2.getLastUpdate(), message);
		//assertSameProject(workspace1.getProjects(), workspace2.getProjects(), message);
	}
	
	public static void assertSameWorkspace(Collection<Workspace> workspaceList1, Collection<Workspace> workspaceList2) {
		assertSameWorkspace(workspaceList1, workspaceList2, "");
	}
	
	public static void assertSameWorkspace(Collection<Workspace> workspaceList1, Collection<Workspace> workspaceList2, String message) {
		Assert.notNull(workspaceList1, "Workspace list1 must be specified");
		Assert.notNull(workspaceList2, "Workspace list2 must be specified");
		Assert.equals(workspaceList1.size(), workspaceList2.size(), "Workspace count not equal");
		//Collection<Workspace> sortedRecords1 = WorkspaceUtil.sortRecords(workspaceList1);
		//Collection<Workspace> sortedRecords2 = WorkspaceUtil.sortRecords(workspaceList2);
		Iterator<Workspace> list1Iterator = workspaceList1.iterator();
		Iterator<Workspace> list2Iterator = workspaceList2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Workspace workspace1 = list1Iterator.next();
			Workspace workspace2 = list2Iterator.next();
			assertSameWorkspace(workspace1, workspace2, message);
		}
	}
	
	
	public static Project createEmptyProject() {
		Project project = new Project();
		return project;
	}
	
	public static Project createProject() {
		Project project = createProject(projectCount++);
		return project;
	}
	
	public static Project createProject(long index) {
		Project project = createEmptyProject();
		project.setName("dummyName" + index);
		project.setDomain("dummyDomain" + index);
		project.setVersion("dummyVersion" + index);
		project.setOwner("dummyOwner" + index);
		//project.getFiles().addAll(createFile_List(2));
		project.setEnabled(true);
		project.setShared(true);
		project.setCreationDate(new Date(1000L + index));
		project.setLastUpdate(new Date(1000L + index));
		project.setId(10L * index);
		return project;
	}
	
	public static List<Project> createEmptyProject_List() {
		return new ArrayList<Project>();
	}
	
	public static List<Project> createProject_List() {
		return createProject_List(2);
	}
	
	public static List<Project> createProject_List(int size) {
		return createProject_List(projectCount++, size);
	}
	
	public static List<Project> createProject_List(long index, int size) {
		List<Project> projectList = createEmptyProject_List();
		long limit = index + size;
		for (; index < limit; index++)
			projectList.add(createProject(index));
		return projectList;
	}
	
	public static Set<Project> createEmptyProject_Set() {
		return new HashSet<Project>();
	}
	
	public static Set<Project> createProject_Set() {
		return createProject_Set(2);
	}
	
	public static Set<Project> createProject_Set(int size) {
		return createProject_Set(projectCount++, size);
	}
	
	public static Set<Project> createProject_Set(long index, int size) {
		Set<Project> projectSet = createEmptyProject_Set();
		long limit = index + size;
		for (; index < limit; index++)
			projectSet.add(createProject(index));
		return projectSet;
	}
	
	public static void assertProjectExists(Collection<Project> projectList, Project project) {
		Assert.notNull(projectList, "Project list must be specified");
		Assert.notNull(project, "Project record must be specified");
		Iterator<Project> iterator = projectList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Project project1 = iterator.next();
			if (project1.equals(project)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Project should exist: "+project);
	}
	
	public static void assertProjectCorrect(Project project) {
		long index = project.getId() / 10L;
		assertObjectCorrect("Name", project.getName(), index);
		assertObjectCorrect("Domain", project.getDomain(), index);
		assertObjectCorrect("Version", project.getVersion(), index);
		assertObjectCorrect("Owner", project.getOwner(), index);
		assertObjectCorrect("Enabled", project.getEnabled(), index);
		assertObjectCorrect("Shared", project.getShared(), index);
		//assertFileCorrect(project.getFiles());
		assertObjectCorrect("CreationDate", project.getCreationDate(), index);
		assertObjectCorrect("LastUpdate", project.getLastUpdate(), index);
	}
	
	public static void assertProjectCorrect(Collection<Project> projectList) {
		Assert.isTrue(projectList.size() == 2, "Project count not correct");
		Iterator<Project> iterator = projectList.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			assertProjectCorrect(project);
		}
	}
	
	public static void assertSameProject(Project project1, Project project2) {
		assertSameProject(project1, project2, "");
	}
	
	public static void assertSameProject(Project project1, Project project2, String message) {
		assertObjectExists("Project1", project1);
		assertObjectExists("Project2", project2);
		assertObjectEquals("Name", project1.getName(), project2.getName(), message);
		assertObjectEquals("Domain", project1.getDomain(), project2.getDomain(), message);
		assertObjectEquals("Version", project1.getVersion(), project2.getVersion(), message);
		assertObjectEquals("Owner", project1.getOwner(), project2.getOwner(), message);
		assertObjectEquals("Enabled", project1.getEnabled(), project2.getEnabled(), message);
		assertObjectEquals("Shared", project1.getShared(), project2.getShared(), message);
		//assertSameFile(project1.getFiles(), project2.getFiles(), message);
		assertObjectEquals("CreationDate", project1.getCreationDate(), project2.getCreationDate(), message);
		assertObjectEquals("LastUpdate", project1.getLastUpdate(), project2.getLastUpdate(), message);
	}
	
	public static void assertSameProject(Collection<Project> projectList1, Collection<Project> projectList2) {
		assertSameProject(projectList1, projectList2, "");
	}
	
	public static void assertSameProject(Collection<Project> projectList1, Collection<Project> projectList2, String message) {
		Assert.notNull(projectList1, "Project list1 must be specified");
		Assert.notNull(projectList2, "Project list2 must be specified");
		Assert.equals(projectList1.size(), projectList2.size(), "Project count not equal");
		//Collection<Project> sortedRecords1 = ProjectUtil.sortRecords(projectList1);
		//Collection<Project> sortedRecords2 = ProjectUtil.sortRecords(projectList2);
		Iterator<Project> list1Iterator = projectList1.iterator();
		Iterator<Project> list2Iterator = projectList2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Project project1 = list1Iterator.next();
			Project project2 = list2Iterator.next();
			assertSameProject(project1, project2, message);
		}
	}
	
	public static File createEmptyFile() {
		File file = new File();
		return file;
	}
	
	public static File createFile() {
		File file = createFile(fileCount++);
		return file;
	}
	
	public static File createFile(long index) {
		File file = createEmptyFile();
		file.setType(FileType.XSD);
		file.setName("dummyName" + index);
		file.setPath("dummyPath" + index);
		file.setOwner("dummyOwner" + index);
		file.setCreationDate(new Date(1000L + index));
		file.setLastUpdate(new Date(1000L + index));
		file.setId(10L * index);
		return file;
	}
	
	public static List<File> createEmptyFile_List() {
		return new ArrayList<File>();
	}
	
	public static List<File> createFile_List() {
		return createFile_List(2);
	}
	
	public static List<File> createFile_List(int size) {
		return createFile_List(fileCount++, size);
	}
	
	public static List<File> createFile_List(long index, int size) {
		List<File> fileList = createEmptyFile_List();
		long limit = index + size;
		for (; index < limit; index++)
			fileList.add(createFile(index));
		return fileList;
	}
	
	public static Set<File> createEmptyFile_Set() {
		return new HashSet<File>();
	}
	
	public static Set<File> createFile_Set() {
		return createFile_Set(2);
	}
	
	public static Set<File> createFile_Set(int size) {
		return createFile_Set(fileCount++, size);
	}
	
	public static Set<File> createFile_Set(long index, int size) {
		Set<File> fileSet = createEmptyFile_Set();
		long limit = index + size;
		for (; index < limit; index++)
			fileSet.add(createFile(index));
		return fileSet;
	}
	
	public static void assertFileExists(Collection<File> fileList, File file) {
		Assert.notNull(fileList, "File list must be specified");
		Assert.notNull(file, "File record must be specified");
		Iterator<File> iterator = fileList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			File file1 = iterator.next();
			if (file1.equals(file)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - File should exist: "+file);
	}
	
	public static void assertFileCorrect(File file) {
		long index = file.getId() / 10L;
		assertObjectCorrect("Type", file.getType(), index);
		assertObjectCorrect("Name", file.getName(), index);
		assertObjectCorrect("Path", file.getPath(), index);
		assertObjectCorrect("Owner", file.getOwner(), index);
		assertObjectCorrect("CreationDate", file.getCreationDate(), index);
		assertObjectCorrect("LastUpdate", file.getLastUpdate(), index);
	}
	
	public static void assertFileCorrect(Collection<File> fileList) {
		Assert.isTrue(fileList.size() == 2, "File count not correct");
		Iterator<File> iterator = fileList.iterator();
		while (iterator.hasNext()) {
			File file = iterator.next();
			assertFileCorrect(file);
		}
	}
	
	public static void assertSameFile(File file1, File file2) {
		assertSameFile(file1, file2, "");
	}
	
	public static void assertSameFile(File file1, File file2, String message) {
		assertObjectExists("File1", file1);
		assertObjectExists("File2", file2);
		assertObjectEquals("Type", file1.getType(), file2.getType(), message);
		assertObjectEquals("Name", file1.getName(), file2.getName(), message);
		assertObjectEquals("Path", file1.getPath(), file2.getPath(), message);
		assertObjectEquals("Owner", file1.getOwner(), file2.getOwner(), message);
		assertObjectEquals("CreationDate", file1.getCreationDate(), file2.getCreationDate(), message);
		assertObjectEquals("LastUpdate", file1.getLastUpdate(), file2.getLastUpdate(), message);
	}
	
	public static void assertSameFile(Collection<File> fileList1, Collection<File> fileList2) {
		assertSameFile(fileList1, fileList2, "");
	}
	
	public static void assertSameFile(Collection<File> fileList1, Collection<File> fileList2, String message) {
		Assert.notNull(fileList1, "File list1 must be specified");
		Assert.notNull(fileList2, "File list2 must be specified");
		Assert.equals(fileList1.size(), fileList2.size(), "File count not equal");
		//TODO Collection<File> sortedRecords1 = FileUtil.sortRecords(fileList1);
		//TODO Collection<File> sortedRecords2 = FileUtil.sortRecords(fileList2);
		Iterator<File> list1Iterator = fileList1.iterator();
		Iterator<File> list2Iterator = fileList2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			File file1 = list1Iterator.next();
			File file2 = list2Iterator.next();
			assertSameFile(file1, file2, message);
		}
	}
	
}
