package nam.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Layouts", namespace = "http://nam/ui", propOrder = {
	"layouts"
})
@XmlRootElement(name = "layouts", namespace = "http://nam/ui")
public class Layouts implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "layouts", namespace = "http://nam/ui")
	private List<Layout> layouts;
	
	
	public Layouts() {
		layouts = new ArrayList<Layout>();
	}
	
	
	public List<Layout> getLayouts() {
		synchronized (layouts) {
			return layouts;
		}
	}
	
	public void setLayouts(Collection<Layout> layouts) {
		if (layouts == null) {
			this.layouts = null;
		} else {
		synchronized (this.layouts) {
				this.layouts = new ArrayList<Layout>();
				addToLayouts(layouts);
			}
		}
	}

	public void addToLayouts(Layout layout) {
		if (layout != null ) {
			synchronized (this.layouts) {
				this.layouts.add(layout);
			}
		}
	}

	public void addToLayouts(Collection<Layout> layoutCollection) {
		if (layoutCollection != null && !layoutCollection.isEmpty()) {
			synchronized (this.layouts) {
				this.layouts.addAll(layoutCollection);
			}
		}
	}

	public void removeFromLayouts(Layout layout) {
		if (layout != null ) {
			synchronized (this.layouts) {
				this.layouts.remove(layout);
			}
		}
	}

	public void removeFromLayouts(Collection<Layout> layoutCollection) {
		if (layoutCollection != null ) {
			synchronized (this.layouts) {
				this.layouts.removeAll(layoutCollection);
			}
		}
	}

	public void clearLayouts() {
		synchronized (layouts) {
			layouts.clear();
		}
	}
}
