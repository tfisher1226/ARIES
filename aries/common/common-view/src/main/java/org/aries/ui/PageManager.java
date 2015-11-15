package org.aries.ui;


public interface PageManager<T> {

	public String getSectionName();

	public void setSectionName(String sectionName);

	public String getSectionType();

	public void setSectionType(String sectionType);

	public String getSectionIcon();

	public void setSectionIcon(String sectionIcon);
	
	public String getSectionTitle();

	public void setSectionTitle(String sectionTitle);

}
