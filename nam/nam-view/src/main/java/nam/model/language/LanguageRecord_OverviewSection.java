package nam.model.language;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Language;
import nam.model.util.LanguageUtil;


@SessionScoped
@Named("languageOverviewSection")
public class LanguageRecord_OverviewSection extends AbstractWizardPage<Language> implements Serializable {
	
	private Language language;
	
	
	public LanguageRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
