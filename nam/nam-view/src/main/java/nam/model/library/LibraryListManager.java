package nam.model.library;

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

import nam.model.Library;
import nam.model.util.LibraryUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("libraryListManager")
public class LibraryListManager extends AbstractDomainListManager<Library, LibraryListObject> implements Serializable {
	
	@Inject
	private LibraryDataManager libraryDataManager;
	
	@Inject
	private LibraryEventManager libraryEventManager;
	
	@Inject
	private LibraryInfoManager libraryInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "libraryList";
	}
	
	@Override
	public String getTitle() {
		return "Library List";
	}
	
	@Override
	public Object getRecordKey(Library library) {
		return LibraryUtil.getKey(library);
	}
	
	@Override
	public String getRecordName(Library library) {
		return LibraryUtil.getLabel(library);
	}
	
	@Override
	protected Class<Library> getRecordClass() {
		return Library.class;
	}
	
	@Override
	protected Library getRecord(LibraryListObject rowObject) {
		return rowObject.getLibrary();
	}
	
	@Override
	public Library getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? LibraryUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Library library) {
		super.setSelectedRecord(library);
		fireSelectedEvent(library);
	}
	
	protected void fireSelectedEvent(Library library) {
		libraryEventManager.fireSelectedEvent(library);
	}
	
	public boolean isSelected(Library library) {
		Library selection = selectionContext.getSelection("library");
		boolean selected = selection != null && selection.equals(library);
		return selected;
	}
	
	@Override
	protected LibraryListObject createRowObject(Library library) {
		LibraryListObject listObject = new LibraryListObject(library);
		listObject.setSelected(isSelected(library));
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
	protected Collection<Library> createRecordList() {
		try {
			Collection<Library> libraryList = libraryDataManager.getLibraryList();
			if (libraryList != null)
				return libraryList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewLibrary() {
		return viewLibrary(selectedRecordKey);
	}
	
	public String viewLibrary(Object recordKey) {
		Library library = recordByKeyMap.get(recordKey);
		return viewLibrary(library);
	}
	
	public String viewLibrary(Library library) {
		String url = libraryInfoManager.viewLibrary(library);
		return url;
	}
	
	public String editLibrary() {
		return editLibrary(selectedRecordKey);
	}
	
	public String editLibrary(Object recordKey) {
		Library library = recordByKeyMap.get(recordKey);
		return editLibrary(library);
	}
	
	public String editLibrary(Library library) {
		String url = libraryInfoManager.editLibrary(library);
		return url;
	}
	
	public void removeLibrary() {
		removeLibrary(selectedRecordKey);
	}
	
	public void removeLibrary(Object recordKey) {
		Library library = recordByKeyMap.get(recordKey);
		removeLibrary(library);
	}
	
	public void removeLibrary(Library library) {
		try {
			if (libraryDataManager.removeLibrary(library))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelLibrary(@Observes @Cancelled Library library) {
		try {
			//Object key = LibraryUtil.getKey(library);
			//recordByKeyMap.put(key, library);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("library");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateLibrary(Collection<Library> libraryList) {
		return LibraryUtil.validate(libraryList);
	}
	
	public void exportLibraryList(@Observes @Export String tableId) {
		//String tableId = "pageForm:libraryListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
