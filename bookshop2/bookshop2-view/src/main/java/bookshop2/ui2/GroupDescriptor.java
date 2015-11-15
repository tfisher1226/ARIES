package bookshop2.ui2;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;


public class GroupDescriptor extends BaseDescriptor {

	private Collection<ModuleDescriptor> modules;

	
    public boolean isNewItems() {
        return isNewEnabled() || containsNewModules();
    }

    public boolean hasEnabledItems() {
        return isCurrentlyEnabled() && containsEnabledModules();
    }

    /**
     * "This method must be present for JAXB - you should be calling {link #getFilteredModules} instead"
     */
    @XmlElementWrapper(name = "modules")
    @XmlElement(name = "module")
    public Collection<ModuleDescriptor> getModules() {
        if (modules == null) {
            return null;
        }
        return Collections2.filter(modules, new Predicate<ModuleDescriptor>() {
            public boolean apply(ModuleDescriptor module) {
                return module.hasEnabledItems();
            };
        });
    }

    public void setModules(Collection<ModuleDescriptor> modules) {
        this.modules = modules;
    }

    private boolean containsNewModules() {
        for (ModuleDescriptor module : modules) {
            if (module.isNewItems()) {
                return true;
            }
        }
        return false;
    }

    private boolean containsEnabledModules() {
        for (ModuleDescriptor module : modules) {
            if (module.hasEnabledItems()) {
                return true;
            }
        }
        return false;
    }

}
