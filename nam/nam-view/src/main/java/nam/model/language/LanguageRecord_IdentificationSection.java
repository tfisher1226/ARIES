package nam.model.language;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Language;
import nam.model.util.LanguageUtil;


@SessionScoped
@Named("languageIdentificationSection")
public class LanguageRecord_IdentificationSection extends AbstractWizardPage<Language> implements Serializable {
	
	private Language language;
	
	
	public LanguageRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	@Override
	public void initialize(Language language) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setLanguage(language);
	}
	
	@Override
	public void validate() {
		if (language == null) {
			validator.missing("Language");
		} else {
		}
	}
	
}
