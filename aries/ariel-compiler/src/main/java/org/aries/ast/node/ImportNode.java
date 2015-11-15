package org.aries.ast.node;


/**
 * <p>Import node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String type</pre>
 *
 */
public class ImportNode extends AbstractNode {

	private String filePath;

	private String parentDirectory;

	private Object importedObject;

	
	public ImportNode(Object node) {
		super(node);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getParentDirectory() {
		return parentDirectory;
	}

	public void setParentDirectory(String parentDirectory) {
		this.parentDirectory = parentDirectory;
	}
	
	public Object getImportedObject() {
		return importedObject;
	}

	public void setImportedObject(Object importedObject) {
		this.importedObject = importedObject;
	}

}
