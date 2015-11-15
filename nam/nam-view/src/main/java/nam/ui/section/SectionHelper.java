package nam.ui.section;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.ui.Section;
import nam.ui.util.SectionUtil;


@SessionScoped
@Named("sectionHelper")
public class SectionHelper extends AbstractElementHelper<Section> implements Serializable {
	
	@Override
	public boolean isEmpty(Section section) {
		return SectionUtil.isEmpty(section);
	}
	
	@Override
	public String toString(Section section) {
		return SectionUtil.toString(section);
	}
	
	@Override
	public String toString(Collection<Section> sectionList) {
		return SectionUtil.toString(sectionList);
	}
	
	@Override
	public boolean validate(Section section) {
		return SectionUtil.validate(section);
	}
	
	@Override
	public boolean validate(Collection<Section> sectionList) {
		return SectionUtil.validate(sectionList);
	}
	
}
