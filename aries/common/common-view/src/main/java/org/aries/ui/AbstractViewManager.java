package org.aries.ui;



public abstract class AbstractViewManager extends AbstractPanelManager implements ViewManager {

//	private List<Breadcrumb> breadcrumbs;


	
//	public List<Breadcrumb> getBreadcrumbs() {
//		return breadcrumbs;
//	}
//	
//	public void clearBreadcrumbs() {
//		if (breadcrumbs != null)
//			breadcrumbs.clear();;
//	}
//	
//	public void addBreadcrumb(Breadcrumb breadcrumb) {
//		if (breadcrumbs == null)
//			breadcrumbs = new ArrayList<Breadcrumb>(); 
//		breadcrumbs.add(breadcrumb);
//	}
	

	/**
	 * Initializes manager state.
	 * Executes at page load time.
	 * @return The path to next page or null. 
	 */
	public void initialize() {
		//nothing for now
	}
	
	/**
	 * Resets manager state.
	 * Executes on demand from user.
	 * @return The path to next page or null. 
	 */
	public void reset() {
		//nothing by default
		initialize();
	}
	
}
