package nam.ui.graphics;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.ui.Graphics;
import nam.ui.util.GraphicsUtil;


@SessionScoped
@Named("graphicsHelper")
public class GraphicsHelper extends AbstractElementHelper<Graphics> implements Serializable {
	
	@Override
	public boolean isEmpty(Graphics graphics) {
		return GraphicsUtil.isEmpty(graphics);
	}
	
	@Override
	public String toString(Graphics graphics) {
		return GraphicsUtil.toString(graphics);
	}
	
	@Override
	public String toString(Collection<Graphics> graphicsList) {
		return GraphicsUtil.toString(graphicsList);
	}
	
	@Override
	public boolean validate(Graphics graphics) {
		return GraphicsUtil.validate(graphics);
	}
	
	@Override
	public boolean validate(Collection<Graphics> graphicsList) {
		return GraphicsUtil.validate(graphicsList);
	}
	
}
