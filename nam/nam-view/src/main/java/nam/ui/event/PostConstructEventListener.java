package nam.ui.event;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PostConstructEventListener implements SystemEventListener {

	private static Log log = LogFactory.getLog(PostConstructEventListener.class);

	
	@Override
	public boolean isListenerForSource(Object source) {
		return source instanceof Application;
	}

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
	}

}
