package org.aries.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Attachment", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"name",
	"size",
	"fileName",
	"fileData",
	"contentType"
})
@XmlRootElement(name = "attachment", namespace = "http://www.aries.org/common")
public class Attachment implements Comparable<Attachment>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "name", namespace = "http://www.aries.org/common")
	private String name;
	
	@XmlElement(name = "size", namespace = "http://www.aries.org/common")
	private Long size;
	
	@XmlElement(name = "fileName", namespace = "http://www.aries.org/common")
	private String fileName;
	
	@XmlElement(name = "fileData", namespace = "http://www.aries.org/common")
	private byte[] fileData;
	
	@XmlElement(name = "contentType", namespace = "http://www.aries.org/common")
	private String contentType;
	
	
	public Attachment() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getSize() {
		return size;
	}
	
	public void setSize(Long size) {
		this.size = size;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public byte[] getFileData() {
		return fileData;
	}
	
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Attachment other) {
		int status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(fileName, other.fileName);
		if (status != 0)
			return status;
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Attachment other = (Attachment) object;
		if (id != null)
			return id.equals(other.id);
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (fileName != null)
			hashCode += fileName.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Attachment: name="+name+", fileName="+fileName;
	}
	
}
