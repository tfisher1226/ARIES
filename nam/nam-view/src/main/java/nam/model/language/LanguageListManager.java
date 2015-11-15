package nam.model.language;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Language;
import nam.model.util.LanguageUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("languageListManager")
public class LanguageListManager extends AbstractDomainListManager<Language, LanguageListObject> implements Serializable {
	
	@Inject
	private LanguageDataManager languageDataManager;
	
	@Inject
	private LanguageEventManager languageEventManager;
	
	@Inject
	private LanguageInfoManager languageInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "languageList";
	}
	
	@Override
	public String getTitle() {
		return "Language List";
	}
	
	@Override
	public Object getRecordKey(Language language) {
		return LanguageUtil.getKey(language);
	}
	
	@Override
	public String getRecordName(Language language) {
		return LanguageUtil.getLabel(language);
	}
	
	@Override
	protected Class<Language> getRecordClass() {
		return Language.class;
	}
	
	@Override
	protected Language getRecord(LanguageListObject rowObject) {
		return rowObject.getLanguage();
	}
	
	@Override
	public Language getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? LanguageUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Language language) {
		super.setSelectedRecord(language);
		fireSelectedEvent(language);
	}
	
	protected void fireSelectedEvent(Language language) {
		languageEventManager.fireSelectedEvent(language);
	}
	
	public boolean isSelected(Language language) {
		Language selection = selectionContext.getSelection("language");
		boolean selected = selection != null && selection.equals(language);
		return selected;
	}
	
	@Override
	protected LanguageListObject createRowObject(Language language) {
		LanguageListObject listObject = new LanguageListObject(language);
		listObject.setSelected(isSelected(language));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Language> createRecordList() {
		try {
			Collection<Language> languageList = languageDataManager.getLanguageList();
			if (languageList != null)
				return languageList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewLanguage() {
		return viewLanguage(selectedRecordKey);
	}
	
	public String viewLanguage(Object recordKey) {
		Language language = recordByKeyMap.get(recordKey);
		return viewLanguage(language);
	}
	
	public String viewLanguage(Language language) {
		String url = languageInfoManager.viewLanguage(language);
		return url;
	}
	
	public String editLanguage() {
		return editLanguage(selectedRecordKey);
	}
	
	public String editLanguage(Object recordKey) {
		Language language = recordByKeyMap.get(recordKey);
		return editLanguage(language);
	}
	
	public String editLanguage(Language language) {
		String url = languageInfoManager.editLanguage(language);
		return url;
	}
	
	public void removeLanguage() {
		removeLanguage(selectedRecordKey);
	}
	
	public void removeLanguage(Object recordKey) {
		Language language = recordByKeyMap.get(recordKey);
		removeLanguage(language);
	}
	
	public void removeLanguage(Language language) {
		try {
			if (languageDataManager.removeLanguage(language))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelLanguage(@Observes @Cancelled Language language) {
		try {
			//Object key = LanguageUtil.getKey(language);
			//recordByKeyMap.put(key, language);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("language");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateLanguage(Collection<Language> languageList) {
		return LanguageUtil.validate(languageList);
	}
	
	public void exportLanguageList(@Observes @Export String tableId) {
		//String tableId = "pageForm:languageListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
