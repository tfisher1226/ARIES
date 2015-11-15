package nam.ui.design;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.ui.event.Clear;
import org.aries.ui.event.Selected;
import org.aries.util.ClassUtil;
import org.aries.util.ObjectUtil;


//@SessionScoped
//@Named("eventMulticaster")
//@SuppressWarnings("serial")
public class EventMulticaster<T> implements Serializable {

	private static Log log = LogFactory.getLog(EventMulticaster.class);
	
	@Inject
	@Selected
	private Event<T> selectedEvent;

	@Inject
	@Clear
	private Event<Object> clearEvent;

	
	public void fireEvent(String className) throws ClassNotFoundException {
		Class<?> loadClass = ClassUtil.loadClass(className);
		Object event = ObjectUtil.loadObject(loadClass);
		fireEvent(event);
	}
	
	public void fireEvent(Object event) {
		try {
			BeanManager lookBeanManager = lookBeanManager();
			lookBeanManager.fireEvent(event);
			//SEAM 	Events.instance().raiseEvent(actionEvent);
		} catch (Throwable e) {
			log.error("Error", e);
		}
	}

	protected BeanManager lookBeanManager() {
		try {
			Context initialContext = new InitialContext();
			BeanManager beanManager = (BeanManager) initialContext.lookup("java:comp/BeanManager");
			return beanManager;
		} catch (final Exception e) {
			throw new IllegalStateException("Cannot locate BeanManager", e);
		}
	}

	public void postClearSelectionEvent(Object source) {
		clearEvent.fire(source);
	}

	public void postSelectedEvent(T object) {
		selectedEvent.fire(object);
	}



//	@Inject
//	private Event<ProjectSelectedEvent> projectSelectedEvent;
//
//	@Inject
//	private Event<ApplicationSelectedEvent> applicationSelectedEvent;
//
//	@Inject
//	private Event<ModuleSelectedEvent> moduleSelectedEvent;
//
//	@Inject
//	@Clear
//	private Event<String> clearSelectionEvent;
//
//
//	public void postProjectSelectedEvent(Project project) {
//		ProjectSelectedEvent event = new ProjectSelectedEvent();
//		event.setProject(project);
//		projectSelectedEvent.fire(event);
//	}
//
//	public void postApplicationSelectedEvent(Application application) {
//		ApplicationSelectedEvent event = new ApplicationSelectedEvent();
//		event.setApplication(application);
//		applicationSelectedEvent.fire(event);
//	}
//
//	public void postModuleSelectedEvent(Module module) {
//		ModuleSelectedEvent event = new ModuleSelectedEvent();
//		event.setModule(module);
//		moduleSelectedEvent.fire(event);
//	}
//
//	public void postClearSelectionEvent() {
//		ClearSelectionEvent event = new ClearSelectionEvent();
//		clearSelectionEvent.fire(event);
//	}

//	public void postObjectSelectedEvent(Object object) {
//		String type = object.getClass().getSimpleName();
//		if (type.equals("Project")) {
//			postProjectSelectedEvent((Project) object);
//		} else if (type.equals("Application")) {
//			postApplicationSelectedEvent((Application) object);
//		} else if (type.equals("Module")) {
//			postModuleSelectedEvent((Module) object);
//		}
//	}

}
