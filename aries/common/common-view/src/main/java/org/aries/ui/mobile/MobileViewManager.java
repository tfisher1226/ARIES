package org.aries.ui.mobile;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.faces.context.FacesContext;


@SuppressWarnings("serial")
public class MobileViewManager implements Serializable {
	private Deque<MobileView> viewIds;

	public MobileViewManager() {
		viewIds = new ArrayDeque<MobileView>();
	}
	
	public void back(MobileActionStrategy as) {
		if (as == null) {
			return;
		}
		
		as.action();
	}
	
	public String getPreviousViewId() {
		if (viewIds.isEmpty()) {
			return getCurrentViewId();
		}
		
		return viewIds.pop().getViewId();
	}
	
	public String getPreviousViewName() {
		if (viewIds.isEmpty()) {
			return "";
		}
		
		return viewIds.peek().getViewName();
	}
	
	public String next(String prevViewName, MobileActionStrategy as) {
		viewIds.push(MobileView.newInstance(prevViewName, getCurrentViewId()));
		
		if (as == null) {
			return "";
		}
		
		return as.action();
	}
	
	public boolean isCanReverse() {
		return !viewIds.isEmpty();
	}
	
	private String getCurrentViewId() {
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}
}
