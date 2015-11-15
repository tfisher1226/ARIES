package nam.ui.section;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.Assert;

import nam.ui.Section;
import nam.ui.design.SelectionContext;
import nam.ui.util.SectionUtil;


@SessionScoped
@Named("sectionDataManager")
public class SectionDataManager implements Serializable {
	
	@Inject
	private SectionEventManager sectionEventManager;
	
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
	public Collection<Section> getSectionList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Section> getDefaultList() {
		return null;
	}
	
	public void saveSection(Section section) {
		if (scope != null) {
		}
	}
	
	public boolean removeSection(Section section) {
		if (scope != null) {
		}
		return false;
	}
	
}
