package nam.model.container;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Container;
import nam.model.util.ContainerUtil;


@SessionScoped
@Named("containerDocumentationSection")
public class ContainerRecord_DocumentationSection extends AbstractWizardPage<Container> implements Serializable {
	
	private Container container;
	
	
	public ContainerRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}
	
	
	public Container getContainer() {
		return container;
	}
	
	public void setContainer(Container container) {
		this.container = container;
	}
	
	@Override
	public void initialize(Container container) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setContainer(container);
	}
	
	@Override
	public void validate() {
		if (container == null) {
			validator.missing("Container");
		} else {
		}
	}
	
}
