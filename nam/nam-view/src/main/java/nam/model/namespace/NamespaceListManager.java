package nam.model.namespace;

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

import nam.model.Namespace;
import nam.model.util.NamespaceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("namespaceListManager")
public class NamespaceListManager extends AbstractDomainListManager<Namespace, NamespaceListObject> implements Serializable {
	
	@Inject
	private NamespaceDataManager namespaceDataManager;
	
	@Inject
	private NamespaceEventManager namespaceEventManager;
	
	@Inject
	private NamespaceInfoManager namespaceInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "namespaceList";
	}
	
	@Override
	public String getTitle() {
		return "Namespace List";
	}
	
	@Override
	public Object getRecordKey(Namespace namespace) {
		return NamespaceUtil.getKey(namespace);
	}
	
	@Override
	public String getRecordName(Namespace namespace) {
		return NamespaceUtil.getLabel(namespace);
	}
	
	@Override
	protected Class<Namespace> getRecordClass() {
		return Namespace.class;
	}
	
	@Override
	protected Namespace getRecord(NamespaceListObject rowObject) {
		return rowObject.getNamespace();
	}
	
	@Override
	public Namespace getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? NamespaceUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Namespace namespace) {
		super.setSelectedRecord(namespace);
		fireSelectedEvent(namespace);
	}
	
	protected void fireSelectedEvent(Namespace namespace) {
		namespaceEventManager.fireSelectedEvent(namespace);
	}
	
	public boolean isSelected(Namespace namespace) {
		Namespace selection = selectionContext.getSelection("namespace");
		boolean selected = selection != null && selection.equals(namespace);
		return selected;
	}
	
	public boolean isChecked(Namespace namespace) {
		Collection<Namespace> selection = selectionContext.getSelection("namespaceSelection");
		boolean checked = selection != null && selection.contains(namespace);
		return checked;
	}
	
	@Override
	protected NamespaceListObject createRowObject(Namespace namespace) {
		NamespaceListObject listObject = new NamespaceListObject(namespace);
		listObject.setSelected(isSelected(namespace));
		listObject.setChecked(isChecked(namespace));
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
	protected Collection<Namespace> createRecordList() {
		try {
			Collection<Namespace> namespaceList = namespaceDataManager.getNamespaceList();
			if (namespaceList != null)
				return namespaceList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewNamespace() {
		return viewNamespace(selectedRecordKey);
	}
	
	public String viewNamespace(Object recordKey) {
		Namespace namespace = recordByKeyMap.get(recordKey);
		return viewNamespace(namespace);
	}
	
	public String viewNamespace(Namespace namespace) {
		String url = namespaceInfoManager.viewNamespace(namespace);
		return url;
	}
	
	public String editNamespace() {
		return editNamespace(selectedRecordKey);
	}
	
	public String editNamespace(Object recordKey) {
		Namespace namespace = recordByKeyMap.get(recordKey);
		return editNamespace(namespace);
	}
	
	public String editNamespace(Namespace namespace) {
		String url = namespaceInfoManager.editNamespace(namespace);
		return url;
	}
	
	public void removeNamespace() {
		removeNamespace(selectedRecordKey);
	}
	
	public void removeNamespace(Object recordKey) {
		Namespace namespace = recordByKeyMap.get(recordKey);
		removeNamespace(namespace);
	}
	
	public void removeNamespace(Namespace namespace) {
		try {
			if (namespaceDataManager.removeNamespace(namespace))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelNamespace(@Observes @Cancelled Namespace namespace) {
		try {
			//Object key = NamespaceUtil.getKey(namespace);
			//recordByKeyMap.put(key, namespace);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("namespace");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateNamespace(Collection<Namespace> namespaceList) {
		return NamespaceUtil.validate(namespaceList);
	}
	
	public void exportNamespaceList(@Observes @Export String tableId) {
		//String tableId = "pageForm:namespaceListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
