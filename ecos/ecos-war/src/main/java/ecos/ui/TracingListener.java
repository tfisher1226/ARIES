package ecos.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
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
	
	private long startTime;
	
	private long endTime;
	
	
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
    	} else {
    		//log.info(dateTime + " START: "+event.getPhaseId());
    	}
    	
    	if (event.getPhaseId().getOrdinal() == 6) {
        	//facesContext = event.getFacesContext();
        	//RenderKit renderKit = facesContext.getRenderKit();
        	//viewRoot = facesContext.getViewRoot();
        	//printChildren(viewRoot);
    	}
    }

    private void printChildren(UIComponent parent) {
    	if (parent == null)
    		return;
    	List<UIComponent> children = parent.getChildren();
    	Iterator<UIComponent> iterator = children.iterator();
    	while (iterator.hasNext()) {
			UIComponent uiComponent = iterator.next();
			//if (uiComponent.getId().contains("XXXX"))
			//	log.info(">>>> "+uiComponent.getClass());
			//if (uiComponent.getClass().equals(javax.faces.component.html.HtmlPanelGrid.class))
			//	log.info(">>>> "+uiComponent.getClass());
			printChildren(uiComponent);
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
		if (event.getPhaseId().getOrdinal() == 6) {
	        Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
			System.out.println();
		}
	}

}
