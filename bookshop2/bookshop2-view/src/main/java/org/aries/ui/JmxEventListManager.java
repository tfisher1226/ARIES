package org.aries.ui;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;


@SessionScoped
@Named("jmxEventListManager")
public class JmxEventListManager extends AbstractTabListManager<JmxEvent, JmxEventListObject> implements Serializable {

	@PostConstruct
	public void postConstruct() {
		BeanContext.set("jmxEventListManager", this);
	}

	@PreDestroy
	public void preDestroy() {
		BeanContext.set("jmxEventListManager", null);
	}
	
	@Override
	public String getModule() {
		return "JmxEventList";
	}

	@Override
	public String getTitle() {
		return "JMX Event List";
	}

	@Override
	public Object getRecordId(JmxEvent jmxEvent) {
		return jmxEvent.getSequenceNumber();
	}

	@Override
	public String getRecordName(JmxEvent jmxEvent) {
		return JmxEventUtil.toString(jmxEvent);
	}

	@Override
	protected Class<JmxEvent> getRecordClass() {
		return JmxEvent.class;
	}

	@Override
	protected JmxEvent getRecord(JmxEventListObject rowObject) {
		return rowObject.getJmxEvent();
	}
	
	@Override
	protected JmxEventListObject createRowObject(JmxEvent jmxEvent) {
		return new JmxEventListObject(jmxEvent);
	}

//	protected JmxEventHelper getJmxEventHelper() {
//		return BeanContext.getFromSession("jmxEventHelper");
//	}
	
//	protected JmxEventInfoManager getJmxEventInfoManager() {
//		return BeanContext.getFromSession("jmxEventInfoManager");
//	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		recordList = new ArrayList<JmxEvent>();
	}
	
//	public void initialize(String correlationId) {
//		recordList = new ArrayList<JmxEvent>();
//		setRunning(true);
//	}

	public void clear() {
		if (recordList != null)
			recordList.clear();
		refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(recordList);
	}
	
	public void addJmxEvent(JmxEvent jmxEvent) {
		recordList.add(jmxEvent);
		refreshModel();
	}

	public void openJmxEvent() {
		openJmxEvent(selectedRecordId);
	}
	
	public void openJmxEvent(String recordId) {
		openJmxEvent(Long.parseLong(recordId));
	}
	
	public void openJmxEvent(Long recordId) {
		JmxEvent jmxEvent = recordByIdMap.get(recordId);
		openJmxEvent(jmxEvent);
	}
	
	public void openJmxEvent(JmxEvent jmxEvent) {
		//JmxEventInfoManager jmxEventInfoManager = BeanContext.getFromSession("jmxEventInfoManager");
		//jmxEventInfoManager.editJmxEvent(jmxEvent);
	}

}
