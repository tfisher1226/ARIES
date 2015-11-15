package nam.ui;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.runtime.BeanContext;


public class TracingListener implements PhaseListener {

	private static Log log = LogFactory.getLog(TracingListener.class);
	
	private long startTime;
	
	private long endTime;
	
	private String currentView;
	
	
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    public void beforePhase(PhaseEvent event) {
    	FacesContext facesContext = event.getFacesContext();
    	RenderKit renderKit = facesContext.getRenderKit();
    	UIViewRoot viewRoot = facesContext.getViewRoot();
    	startTime = System.currentTimeMillis();
    	String dateTime = DateFormatUtils.format(startTime, "yyyy-MM-dd' 'HH:mm:ss.SSS");
		if (viewRoot != null) {
        	String clientId = viewRoot.getClientId();
        	String id = viewRoot.getId();
        	String viewId = viewRoot.getViewId();
    		//log.info(dateTime + " START: "+event.getPhaseId()+", "+viewId);
        	if (currentView == null)
        		currentView = viewId;
        	if (currentView != null && !currentView.equals(viewId)) {
        		this.currentView = viewId;
        		AdminManager adminManager = BeanContext.getFromSession("adminManager");
        		adminManager.reset();
        	}
    	} else {
    		//log.info(dateTime + " START: "+event.getPhaseId());
    	}
    }

	public void afterPhase(PhaseEvent event) {
    	FacesContext facesContext = event.getFacesContext();
    	UIViewRoot viewRoot = facesContext.getViewRoot();
    	String viewId = viewRoot.getViewId();
    	endTime = System.currentTimeMillis();
    	String dateTime = DateFormatUtils.format(endTime, "yyyy-MM-dd' 'HH:mm:ss.SSS");
    	String elapsedTime = DateFormatUtils.format(endTime - startTime, "ss.SSS");
		log.info(dateTime + " END: "+event.getPhaseId()+", "+viewId+" total time: "+elapsedTime);
    }

}
