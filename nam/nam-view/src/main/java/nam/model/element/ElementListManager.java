package nam.model.element;

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

import nam.model.Element;
import nam.model.util.ElementUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("elementListManager")
public class ElementListManager extends AbstractDomainListManager<Element, ElementListObject> implements Serializable {
	
	@Inject
	private ElementDataManager elementDataManager;
	
	@Inject
	private ElementEventManager elementEventManager;
	
	@Inject
	private ElementInfoManager elementInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public String getClientId() {
		return "elementList";
	}
	
	@Override
	public String getTitle() {
		return "Element List";
	}
	
	@Override
	public Object getRecordKey(Element element) {
		return ElementUtil.getKey(element);
	}
	
	@Override
	public String getRecordName(Element element) {
		return ElementUtil.getLabel(element);
	}
	
	@Override
	protected Class<Element> getRecordClass() {
		return Element.class;
	}
	
	@Override
	protected Element getRecord(ElementListObject rowObject) {
		return rowObject.getElement();
	}
	
	@Override
	public Element getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ElementUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Element element) {
		super.setSelectedRecord(element);
		fireSelectedEvent(element);
	}
	
	protected void fireSelectedEvent(Element element) {
		elementEventManager.fireSelectedEvent(element);
	}
	
	public boolean isSelected(Element element) {
		Element selection = selectionContext.getSelection("element");
		boolean selected = selection != null && selection.equals(element);
		return selected;
	}
	
	@Override
	protected ElementListObject createRowObject(Element element) {
		ElementListObject listObject = new ElementListObject(element);
		listObject.setSelected(isSelected(element));
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
	protected Collection<Element> createRecordList() {
		try {
			Collection<Element> elementList = elementDataManager.getElementList();
			if (elementList != null)
				return elementList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewElement() {
		return viewElement(selectedRecordKey);
	}

	public String viewElement(Object recordKey) {
		Element element = recordByKeyMap.get(recordKey);
		return viewElement(element);
	}
	
	public String viewElement(Element element) {
		String url = elementInfoManager.viewElement(element);
		return url;
	}
	
	public String editElement() {
		return editElement(selectedRecordKey);
	}
	
	public String editElement(Object recordKey) {
		Element element = recordByKeyMap.get(recordKey);
		return editElement(element);
	}
	
	public String editElement(Element element) {
		String url = elementInfoManager.editElement(element);
		return url;
	}
	
	public void removeElement() {
		removeElement(selectedRecordKey);
	}
	
	public void removeElement(Object recordKey) {
		Element element = recordByKeyMap.get(recordKey);
		removeElement(element);
	}
	
	public void removeElement(Element element) {
		try {
			if (elementDataManager.removeElement(element))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void cancelElement(@Observes @Cancelled Element element) {
		try {
			//Object key = ElementUtil.getKey(element);
			//recordByKeyMap.put(key, element);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("element");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateElement(Collection<Element> elementList) {
		return ElementUtil.validate(elementList);
	}
	
	public void exportElementList(@Observes @Export String tableId) {
		//String tableId = "pageForm:elementListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
