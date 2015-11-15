package nam.ui2;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;


public class ModuleDescriptor extends BaseDescriptor {

	private Collection<SectionDescriptor> sections;

    public boolean isNewItems() {
        return isNewEnabled() || containsNewTabs();
    }

    public boolean hasEnabledItems() {
        return isCurrentlyEnabled() && containsEnabledTabs();
    }

    @XmlElementWrapper(name = "sections")
    @XmlElement(name = "section")
    public Collection<SectionDescriptor> getSections() {
        if (sections == null) {
            return null;
        }
        return Arrays.asList(sections.iterator().next());
//        return Collections2.filter(sections, new Predicate<SectionDescriptor>() {
//            public boolean apply(SectionDescriptor sample) {
//                return sample.isCurrentlyEnabled();
//            }
//        });
    }

    public void setSections(Collection<SectionDescriptor> sections) {
        this.sections = sections;
    }

    public SectionDescriptor getTabById(String id) {
        for (SectionDescriptor section : getSections()) {
            if (section.getId().equals(id)) {
                return section;
            }
        }
        for (SectionDescriptor section : getSections()) {
            if (section.isCurrentlyEnabled()) {
                return section;
            }
        }
        
        // TODO: We should never reach here, perhaps throw an ISE if we do?
        return sections.iterator().next();
    }

    private boolean containsNewTabs() {
        for (SectionDescriptor section : sections) {
            if (section.isNewEnabled()) {
                return true;
            }
        }
        return false;
    }

    private boolean containsEnabledTabs() {
        for (SectionDescriptor section : sections) {
            if (section.isCurrentlyEnabled()) {
                return true;
            }
        }
        return false;
    }

}
