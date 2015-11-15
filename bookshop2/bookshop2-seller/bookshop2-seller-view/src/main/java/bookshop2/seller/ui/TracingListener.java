package bookshop2.seller.ui;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TracingListener implements PhaseListener {

	private static Log log = LogFactory.getLog(TracingListener.class);
	
	
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    public void beforePhase(PhaseEvent event) {
    	FacesContext facesContext = event.getFacesContext();
    	RenderKit renderKit = facesContext.getRenderKit();
    	UIViewRoot viewRoot = facesContext.getViewRoot();
    	long currentTime = System.currentTimeMillis();
    	String dateTime = DateFormatUtils.format(currentTime, "yyyy-MM-dd' 'HH:mm:ss.SSS");
		if (viewRoot != null) {
        	String clientId = viewRoot.getClientId();
        	String id = viewRoot.getId();
        	String viewId = viewRoot.getViewId();
    		log.info(dateTime + " START: "+event.getPhaseId()+", "+viewId);
    	} else {
    		log.info(dateTime + " START: "+event.getPhaseId());
    	}
    	if (event.getPhaseId().getOrdinal() == 6)
    		System.out.println("HI");
    }

    public void afterPhase(PhaseEvent event) {
    	FacesContext facesContext = event.getFacesContext();
    	UIViewRoot viewRoot = facesContext.getViewRoot();
    	String viewId = viewRoot.getViewId();
    	long currentTime = System.currentTimeMillis();
    	String dateTime = DateFormatUtils.format(currentTime, "yyyy-MM-dd' 'HH:mm:ss.SSS");
		log.info(dateTime + " END: "+event.getPhaseId()+", "+viewId);
    }

}
