package nam.ui.section;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Section;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("sectionEventManager")
public class SectionEventManager extends AbstractEventManager<Section> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Section getInstance() {
		return selectionContext.getSelection("section");
	}
	
	public void removeSection() {
		Section section = getInstance();
		fireRemoveEvent(section);
	}
	
}
