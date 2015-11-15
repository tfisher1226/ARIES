package nam.model.attribute;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Attribute;
import nam.model.Element;
import nam.model.Fault;
import nam.model.util.AttributeUtil;
import nam.model.util.ElementUtil;
import nam.model.util.FaultUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("attributeDataManager")
public class AttributeDataManager implements Serializable {
	
	@Inject
	private AttributeEventManager attributeEventManager;
	
	@Inject
	private SelectionContext selectionContext;

	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Attribute> getAttributeList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
			
		if (scope.equals("element")) {
			return getAttributeList((Element) owner);
		
		} else if (scope.equals("fault")) {
			return getAttributeList((Fault) owner);
			
		} else {
			return getDefaultList();
		}
	}
	
	protected Collection<Attribute> getAttributeList(Element element) {
		return ElementUtil.getAttributes(element);
	}
	
	protected Collection<Attribute> getAttributeList(Fault fault) {
		return FaultUtil.getAttributes(fault);
	}
	
	public Collection<Attribute> getDefaultList() {
		return null;
	}

	public void saveAttribute(Attribute attribute) {
		if (scope != null) {
			Object owner = getOwner();
			
			if (scope.equals("element")) {
				ElementUtil.addAttribute((Element) owner, attribute);
				
			} else if (scope.equals("fault")) {
				FaultUtil.addAttribute((Fault) owner, attribute);
			}
		}
	}

	public boolean removeAttribute(Attribute attribute) {
		if (scope != null) {
			Object owner = getOwner();
			
			if (scope.equals("element")) {
				return ElementUtil.removeAttribute((Element) owner, attribute);
				
			} else if (scope.equals("fault")) {
				return FaultUtil.removeAttribute((Fault) owner, attribute);
			}
		}
		return false;
	}
	
}
