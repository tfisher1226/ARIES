package org.aries.ui;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.event.ResetEvent;


@SessionScoped
@Named("jmxEventManager")
public class JmxEventManager implements Serializable {

	@Inject
	private JmxEventListManager jmxEventListManager;
	
	private JmxEventHandler jmxEventHandler;
	
	private boolean isRunning;
	
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	@PostConstruct
	public void postConstruct() {
		BeanContext.set("jmxEventManager", this);
		jmxEventHandler = new JmxEventHandler();
		jmxEventHandler.register();
	}
	
	@PreDestroy
	public void preDestroy() {
		BeanContext.set("jmxEventManager", null);
		jmxEventHandler.unregister();
	}

	public void initialize() {
		jmxEventListManager.initialize();
		setRunning(true);
	}
	
	public void reset(@Observes ResetEvent event) {
		jmxEventListManager.clear();
		jmxEventListManager.reset();
		setRunning(false);
	}
	
	public void refresh() {
	}
	
	public void addEvent(JmxEvent jmxEvent) {
	}

}
