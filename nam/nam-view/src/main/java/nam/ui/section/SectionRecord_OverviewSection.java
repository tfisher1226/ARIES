package nam.ui.section;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.Section;
import nam.ui.util.SectionUtil;


@SessionScoped
@Named("sectionOverviewSection")
public class SectionRecord_OverviewSection extends AbstractWizardPage<Section> implements Serializable {
	
	private Section section;
	
	
	public SectionRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
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
