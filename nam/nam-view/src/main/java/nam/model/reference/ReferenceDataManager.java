package nam.model.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Element;
import nam.model.Reference;
import nam.model.util.ElementUtil;
import nam.model.util.ReferenceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("referenceDataManager")
public class ReferenceDataManager implements Serializable {
	
	@Inject
	private ReferenceEventManager referenceEventManager;
	
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
	
	@SuppressWarnings("unchecked")
	public Collection<Reference> getReferenceList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("element")) {
			return getReferenceList((Element) owner);
		} else {
			return getDefaultList();
		}
	}
	
	protected Collection<Reference> getReferenceList(Element element) {
		return ElementUtil.getReferences(element);
	}
	
	public Collection<Reference> getDefaultList() {
		return null;
	}
	
	public void saveReference(Reference reference) {
		if (scope != null) {
			Object owner = getOwner();
			if (scope.equals("element")) {
			ElementUtil.addReference((Element) owner, reference);
	}
		}
	}
	
	public boolean removeReference(Reference reference) {
		if (scope != null) {
			Object owner = getOwner();
			if (scope.equals("element")) {
			return ElementUtil.removeReference((Element) owner, reference);
			}
		}
		return false;
	}
	
}
