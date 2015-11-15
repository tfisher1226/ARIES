package nam.model.language;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Language;
import nam.model.util.LanguageUtil;


@SessionScoped
@Named("languageHelper")
public class LanguageHelper extends AbstractElementHelper<Language> implements Serializable {
	
	@Override
	public boolean isEmpty(Language language) {
		return LanguageUtil.isEmpty(language);
	}
	
	@Override
	public String toString(Language language) {
		return LanguageUtil.toString(language);
	}
	
	@Override
	public String toString(Collection<Language> languageList) {
		return LanguageUtil.toString(languageList);
	}
	
	@Override
	public boolean validate(Language language) {
		return LanguageUtil.validate(language);
	}
	
	@Override
	public boolean validate(Collection<Language> languageList) {
		return LanguageUtil.validate(languageList);
	}
	
}
