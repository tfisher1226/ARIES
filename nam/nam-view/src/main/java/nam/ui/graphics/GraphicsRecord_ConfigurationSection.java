package nam.ui.graphics;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.Graphics;
import nam.ui.util.GraphicsUtil;


@SessionScoped
@Named("graphicsConfigurationSection")
public class GraphicsRecord_ConfigurationSection extends AbstractWizardPage<Graphics> implements Serializable {

	private Graphics graphics;

	
	public GraphicsRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Graphics getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	@Override
	public void initialize(Graphics graphics) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setGraphics(graphics);
	}
	
	@Override
	public void validate() {
		if (graphics == null) {
			validator.missing("Graphics");
		} else {
		}
	}
	
}
