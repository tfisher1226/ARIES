package nam.ui.graphics;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.ui.Graphics;
import nam.ui.util.GraphicsUtil;


public class GraphicsListObject extends AbstractListObject<Graphics> implements Comparable<GraphicsListObject>, Serializable {
	
	private Graphics graphics;
	
	
	public GraphicsListObject(Graphics graphics) {
		this.graphics = graphics;
	}
	
	
	public Graphics getGraphics() {
		return graphics;
	}
	
	@Override
	public Object getKey() {
		return getKey(graphics);
	}
	
	public Object getKey(Graphics graphics) {
		return GraphicsUtil.getKey(graphics);
	}
	
	@Override
	public String getLabel() {
		return getLabel(graphics);
	}
	
	public String getLabel(Graphics graphics) {
		return GraphicsUtil.getLabel(graphics);
	}
	
	@Override
	public String toString() {
		return toString(graphics);
	}
	
	@Override
	public String toString(Graphics graphics) {
		return GraphicsUtil.toString(graphics);
	}
	
	@Override
	public int compareTo(GraphicsListObject other) {
		Object thisKey = getKey(this.graphics);
		Object otherKey = getKey(other.graphics);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		GraphicsListObject other = (GraphicsListObject) object;
		Object thisKey = GraphicsUtil.getKey(this.graphics);
		Object otherKey = GraphicsUtil.getKey(other.graphics);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
