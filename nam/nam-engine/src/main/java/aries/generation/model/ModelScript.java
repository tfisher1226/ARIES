package aries.generation.model;


/**
 * Class modeling a script file.
 * 
 */
public class ModelScript extends AbstractGeneratedObject {
	
	protected String sourceFolder;
	
	protected String sourceFile;

	protected String targetFolder;

	protected String targetFile;
	
	private String scriptType;

	private String version;

	private String content;

	
	/**
	 * Constructs an uninitialized @{link ModelClass}.
	 */
	public ModelScript() {
		//nothing for now
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getTargetFolder() {
		return targetFolder;
	}

	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}

	public String getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	public String getScriptType() {
		return scriptType;
	}

	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}