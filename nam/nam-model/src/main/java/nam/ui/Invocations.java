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
@XmlType(name = "Invocations", namespace = "http://nam/ui", propOrder = {
	"invocations"
})
@XmlRootElement(name = "invocations", namespace = "http://nam/ui")
public class Invocations implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "invocations", namespace = "http://nam/ui")
	private List<Invocation> invocations;
	
	
	public Invocations() {
		invocations = new ArrayList<Invocation>();
	}
	
	
	public List<Invocation> getInvocations() {
		synchronized (invocations) {
			return invocations;
		}
	}
	
	public void setInvocations(Collection<Invocation> invocations) {
		if (invocations == null) {
			this.invocations = null;
		} else {
		synchronized (this.invocations) {
				this.invocations = new ArrayList<Invocation>();
				addToInvocations(invocations);
			}
		}
	}

	public void addToInvocations(Invocation invocation) {
		if (invocation != null ) {
			synchronized (this.invocations) {
				this.invocations.add(invocation);
			}
		}
	}

	public void addToInvocations(Collection<Invocation> invocationCollection) {
		if (invocationCollection != null && !invocationCollection.isEmpty()) {
			synchronized (this.invocations) {
				this.invocations.addAll(invocationCollection);
			}
		}
	}

	public void removeFromInvocations(Invocation invocation) {
		if (invocation != null ) {
			synchronized (this.invocations) {
				this.invocations.remove(invocation);
			}
		}
	}

	public void removeFromInvocations(Collection<Invocation> invocationCollection) {
		if (invocationCollection != null ) {
			synchronized (this.invocations) {
				this.invocations.removeAll(invocationCollection);
			}
		}
	}

	public void clearInvocations() {
		synchronized (invocations) {
			invocations.clear();
		}
	}
}
