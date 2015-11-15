package nam;

import nam.model.Module;


@SuppressWarnings("serial")
public class ProjectException extends RuntimeException {

	private Module module;
	
	public ProjectException(String message) {
		super(message);
	}

	public ProjectException(Module module, String message) {
		super(message);
		this.module = module;
	}

	public Module getModule() {
		return module;
	}
	
}
