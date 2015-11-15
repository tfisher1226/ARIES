package nam.model.language;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Language;
import nam.model.util.LanguageUtil;


@SessionScoped
@Named("languageSelectManager")
public class LanguageSelectManager extends AbstractSelectManager<Language, LanguageListObject> implements Serializable {
	
	@Inject
	private LanguageDataManager languageDataManager;
	
	@Inject
	private LanguageHelper languageHelper;
	
	
	@Override
	public String getClientId() {
		return "languageSelect";
	}
	
	@Override
	public String getTitle() {
		return "Language Selection";
	}
	
	@Override
	protected Class<Language> getRecordClass() {
		return Language.class;
	}
	
	@Override
	public boolean isEmpty(Language language) {
		return languageHelper.isEmpty(language);
	}
	
	@Override
	public String toString(Language language) {
		return languageHelper.toString(language);
	}
	
	protected LanguageHelper getLanguageHelper() {
		return BeanContext.getFromSession("languageHelper");
	}
	
	protected LanguageListManager getLanguageListManager() {
		return BeanContext.getFromSession("languageListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshLanguageList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Language> recordList) {
		LanguageListManager languageListManager = getLanguageListManager();
		DataModel<LanguageListObject> dataModel = languageListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshLanguageList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Language> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Language> languageList = BeanContext.getFromConversation(instanceId);
		return languageList;
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Language> languageList) {
		Collections.sort(languageList, new Comparator<Language>() {
			public int compare(Language language1, Language language2) {
				String text1 = LanguageUtil.toString(language1);
				String text2 = LanguageUtil.toString(language2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
