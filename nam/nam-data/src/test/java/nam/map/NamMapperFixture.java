package nam.map;



public class NamMapperFixture {

	private static FileMapper fileMapper;

	private static ProjectMapper projectMapper;

	private static WorkspaceMapper workspaceMapper;


	public static FileMapper getFileMapper() {
		if (fileMapper == null) {
			fileMapper = new FileMapper();
		}
		return fileMapper;
	}

	public static ProjectMapper getProjectMapper() {
		if (projectMapper == null) {
			projectMapper = new ProjectMapper();
			projectMapper.fileMapper = NamMapperFixture.getFileMapper();
		}
		return projectMapper;
	}

	public static WorkspaceMapper getWorkspaceMapper() {
		if (workspaceMapper == null) {
			workspaceMapper = new WorkspaceMapper();
		}
		return workspaceMapper;
	}

}
