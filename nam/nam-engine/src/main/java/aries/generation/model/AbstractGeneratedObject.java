package aries.generation.model;


public abstract class AbstractGeneratedObject {

	protected String fileName;

	protected String directoryName;

	protected String version = "";

	protected String author;


	/**
	 * Returns the <em>fileName</em> of this <code>Build File</code>.
	 * @return the <em>fileName</em> of this <code>Build File</code>
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the <em>fileName</em> of this <code>Build File</code>.
	 * @param value the <em>fileName</em> for this <code>Build File</code>
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Returns the <em>directoryName</em> of this <code>Build File</code>.
	 * @return the <em>directoryName</em> of this <code>Build File</code>
	 */
	public String getDirectoryName() {
		return directoryName;
	}

	/**
	 * Sets the <em>directoryName</em> of this <code>Build File</code>.
	 * @param value the <em>directoryName</em> for this <code>Build File</code>
	 */
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	/**
	 * Returns the version string to use in the type comment of this <code>Bean</code>.
	 * @return the version string to use in the type comment of this <code>Bean</code>
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version string to use in the type comment of this <code>Bean</code>.
	 * @param version the version string to use in the type comment of this <code>Bean</code>
	 */
	public void setVersion(String version) {
		this.version = version;
	}


	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	

}
