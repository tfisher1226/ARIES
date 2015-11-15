package nam.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Sections", namespace = "http://nam/ui", propOrder = {
	"sections"
})
@XmlRootElement(name = "sections", namespace = "http://nam/ui")
public class Sections implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "sections", namespace = "http://nam/ui")
	private List<Section> sections;
	
	
	public Sections() {
		sections = new ArrayList<Section>();
	}
	
	
	public List<Section> getSections() {
		synchronized (sections) {
			return sections;
		}
	}
	
	public void setSections(Collection<Section> sections) {
		if (sections == null) {
			this.sections = null;
		} else {
		synchronized (this.sections) {
				this.sections = new ArrayList<Section>();
				addToSections(sections);
			}
		}
	}

	public void addToSections(Section section) {
		if (section != null ) {
			synchronized (this.sections) {
				this.sections.add(section);
			}
		}
	}

	public void addToSections(Collection<Section> sectionCollection) {
		if (sectionCollection != null && !sectionCollection.isEmpty()) {
			synchronized (this.sections) {
				this.sections.addAll(sectionCollection);
			}
		}
	}

	public void removeFromSections(Section section) {
		if (section != null ) {
			synchronized (this.sections) {
				this.sections.remove(section);
			}
		}
	}

	public void removeFromSections(Collection<Section> sectionCollection) {
		if (sectionCollection != null ) {
			synchronized (this.sections) {
				this.sections.removeAll(sectionCollection);
			}
		}
	}

	public void clearSections() {
		synchronized (sections) {
			sections.clear();
		}
	}
}
