package nam.model;

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
@XmlType(name = "Languages", namespace = "http://nam/model", propOrder = {
	"languages"
})
@XmlRootElement(name = "languages", namespace = "http://nam/model")
public class Languages implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "languages", namespace = "http://nam/model")
	private List<Language> languages;
	
	
	public Languages() {
		languages = new ArrayList<Language>();
	}
	
	
	public List<Language> getLanguages() {
		synchronized (languages) {
			return languages;
		}
	}
	
	public void setLanguages(Collection<Language> languages) {
		if (languages == null) {
			this.languages = null;
		} else {
		synchronized (this.languages) {
				this.languages = new ArrayList<Language>();
				addToLanguages(languages);
			}
		}
	}

	public void addToLanguages(Language language) {
		if (language != null ) {
			synchronized (this.languages) {
				this.languages.add(language);
			}
		}
	}

	public void addToLanguages(Collection<Language> languageCollection) {
		if (languageCollection != null && !languageCollection.isEmpty()) {
			synchronized (this.languages) {
				this.languages.addAll(languageCollection);
			}
		}
	}

	public void removeFromLanguages(Language language) {
		if (language != null ) {
			synchronized (this.languages) {
				this.languages.remove(language);
			}
		}
	}

	public void removeFromLanguages(Collection<Language> languageCollection) {
		if (languageCollection != null ) {
			synchronized (this.languages) {
				this.languages.removeAll(languageCollection);
			}
		}
	}

	public void clearLanguages() {
		synchronized (languages) {
			languages.clear();
		}
	}
}
