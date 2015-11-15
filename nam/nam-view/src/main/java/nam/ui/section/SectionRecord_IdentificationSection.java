package nam.ui.section;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.Section;
import nam.ui.util.SectionUtil;


@SessionScoped
@Named("sectionIdentificationSection")
public class SectionRecord_IdentificationSection extends AbstractWizardPage<Section> implements Serializable {
	
	private Section section;
	
	
	public SectionRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public Section getSection() {
		return section;
	}
	
	public void setSection(Section section) {
		this.section = section;
	}
	
	@Override
	public void initialize(Section section) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setSection(section);
	}
	
	@Override
	public void validate() {
		if (section == null) {
			validator.missing("Section");
		} else {
		}
	}
	
}
