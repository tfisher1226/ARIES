package nam.ui.design;

import java.util.Stack;


//TODO - no need to be threadsafe here (YET)...
public abstract class AbstractSelectionContext {

	private String url;
	
	private Stack<String> origins = new Stack<String>();
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOrigin() {
		if (!origins.isEmpty())
			return origins.peek();
		return "";
	}

	public String popOrigin() {
		if (origins.isEmpty())
			return null;
		String origin = origins.pop();
		url = origin;
		return origin;
	}
	
	public void setOrigin(String origin) {
		if (origin == null)
			return;
		while (!origins.isEmpty()) {
			String peek = origins.peek();
			if (peek != null)
				break;
			origins.pop();
		} 
		if (origins.isEmpty()) {
			origins.push(origin);
		} else {
			String peek = origins.peek();
			if (!peek.equals(origin)) {
				origins.push(origin);
			}
		}
	}
	
	public void resetOrigin() {
		setOrigin(url);
	}

}
