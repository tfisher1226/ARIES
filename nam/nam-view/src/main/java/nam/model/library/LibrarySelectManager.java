package nam.model.library;

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

import nam.model.Library;
import nam.model.util.LibraryUtil;


@SessionScoped
@Named("librarySelectManager")
public class LibrarySelectManager extends AbstractSelectManager<Library, LibraryListObject> implements Serializable {
	
	@Inject
	private LibraryDataManager libraryDataManager;
	
	@Inject
	private LibraryHelper libraryHelper;
	
	
	@Override
	public String getClientId() {
		return "librarySelect";
	}
	
	@Override
	public String getTitle() {
		return "Library Selection";
	}
	
	@Override
	protected Class<Library> getRecordClass() {
		return Library.class;
	}
	
	@Override
	public boolean isEmpty(Library library) {
		return libraryHelper.isEmpty(library);
	}
	
	@Override
	public String toString(Library library) {
		return libraryHelper.toString(library);
	}
	
	protected LibraryHelper getLibraryHelper() {
		return BeanContext.getFromSession("libraryHelper");
	}
	
	protected LibraryListManager getLibraryListManager() {
		return BeanContext.getFromSession("libraryListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshLibraryList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Library> recordList) {
		LibraryListManager libraryListManager = getLibraryListManager();
		DataModel<LibraryListObject> dataModel = libraryListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshLibraryList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Library> refreshRecords() {
		try {
			Collection<Library> records = libraryDataManager.getLibraryList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
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
	public void sortRecords(List<Library> libraryList) {
		Collections.sort(libraryList, new Comparator<Library>() {
			public int compare(Library library1, Library library2) {
				String text1 = LibraryUtil.toString(library1);
				String text2 = LibraryUtil.toString(library2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
