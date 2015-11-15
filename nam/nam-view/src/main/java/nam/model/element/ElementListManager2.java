package nam.model.element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Observes;

import nam.model.Element;
import nam.model.Namespace;
import nam.model.util.ElementUtil;
import nam.model.util.NamespaceUtil;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;
import org.aries.ui.event.ResetEvent;
import org.jboss.seam.annotations.Observer;


public class ElementListManager2 extends AbstractTabListManager<Element, ElementListObject> implements Serializable {

	
	private Namespace namespace; 

	
	@Override
	public String getModule() {
		return "ElementList";
	}
	
	@Override
	public String getTitle() {
		return "Element List";
	}
	
	@Override
	public Object getRecordId(Element element) {
		return null;
	}

	@Override
	public String getRecordName(Element element) {
		return ElementUtil.toString(element);
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
	protected ElementListObject createRowObject(Element element) {
		return new ElementListObject(element);
	}
	
	protected ElementHelper getElementHelper() {
		return BeanContext.getFromSession("elementHelper");
	}
	
	protected ElementInfoManager getElementInfoManager() {
		return BeanContext.getFromSession("elementInfoManager");
	}
	
//	//SEAM @Observer("org.aries.ui.reset")
//	public void reset(@Observes ResetEvent event) {
//		reset();
//	}
	
	public void reset() {
		refresh();
	}

	@Observer("nam.elementsChanged")
	public String refresh() {
		refresh(namespace);
		return null;
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}

	@Override
	//SEAM @Observer("org.aries.refreshElementList")
	public void refreshModel() {
		refreshModel(recordList);
	}
	
	@SuppressWarnings("unchecked")
	public void refresh(Namespace namespace) {
		this.namespace = namespace;
		//clearSelection();
	}

	protected List<ElementListObject> getElements(Namespace namespace) {
		List<Element> elements = NamespaceUtil.getElements(namespace);
		Assert.notNull(elements, "Elements should not be null");
		List<ElementListObject> list = new ArrayList<ElementListObject>();
		for (Element element : elements) {
			ElementListObject item = new ElementListObject(element);
			list.add(item);
		}
		return list;
	}

	public void viewElements() {
		refresh(namespace);
	}

}
