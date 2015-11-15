package nam.model.element;

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

import nam.model.Element;
import nam.model.util.ElementUtil;


@SessionScoped
@Named("elementSelectManager")
public class ElementSelectManager extends AbstractSelectManager<Element, ElementListObject> implements Serializable {
	
	@Inject
	private ElementDataManager elementDataManager;
	
	@Inject
	private ElementHelper elementHelper;
	
	
	@Override
	public String getClientId() {
		return "elementSelect";
	}
	
	@Override
	public String getTitle() {
		return "Element Selection";
	}
	
	@Override
	protected Class<Element> getRecordClass() {
		return Element.class;
	}
	
	@Override
	public boolean isEmpty(Element element) {
		return elementHelper.isEmpty(element);
	}
	
	@Override
	public String toString(Element element) {
		return elementHelper.toString(element);
	}
	
	protected ElementHelper getElementHelper() {
		return BeanContext.getFromSession("elementHelper");
	}
	
	protected ElementListManager getElementListManager() {
		return BeanContext.getFromSession("elementListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshElementList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Element> recordList) {
		ElementListManager elementListManager = getElementListManager();
		DataModel<ElementListObject> dataModel = elementListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshElementList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Element> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Element> elementList = BeanContext.getFromConversation(instanceId);
		return elementList;
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
	public void sortRecords(List<Element> elementList) {
		Collections.sort(elementList, new Comparator<Element>() {
			public int compare(Element element1, Element element2) {
				String text1 = ElementUtil.toString(element1);
				String text2 = ElementUtil.toString(element2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
