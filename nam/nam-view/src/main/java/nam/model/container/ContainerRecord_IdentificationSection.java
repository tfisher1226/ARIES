package nam.model.container;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Container;
import nam.model.util.ContainerUtil;


@SessionScoped
@Named("containerIdentificationSection")
public class ContainerRecord_IdentificationSection extends AbstractWizardPage<Container> implements Serializable {
	
	private Container container;
	
	
	public ContainerRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
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
