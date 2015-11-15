package nam.model.process;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Process;
import nam.model.util.ProcessUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("processListManager")
public class ProcessListManager extends AbstractDomainListManager<Process, ProcessListObject> implements Serializable {
	
	@Inject
	private ProcessDataManager processDataManager;
	
	@Inject
	private ProcessEventManager processEventManager;
	
	@Inject
	private ProcessInfoManager processInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "processList";
	}
	
	@Override
	public String getTitle() {
		return "Process List";
	}
	
	@Override
	public Object getRecordKey(Process process) {
		return ProcessUtil.getKey(process);
	}
	
	@Override
	public String getRecordName(Process process) {
		return ProcessUtil.toString(process);
	}
	
	@Override
	protected Class<Process> getRecordClass() {
		return Process.class;
	}
	
	@Override
	protected Process getRecord(ProcessListObject rowObject) {
		return rowObject.getProcess();
	}
	
	@Override
	public Process getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ProcessUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Process process) {
		super.setSelectedRecord(process);
		fireSelectedEvent(process);
	}
	
	protected void fireSelectedEvent(Process process) {
		processEventManager.fireSelectedEvent(process);
	}
	
	public boolean isSelected(Process process) {
		Process selection = selectionContext.getSelection("process");
		boolean selected = selection != null && selection.equals(process);
		return selected;
	}
	
	@Override
	protected ProcessListObject createRowObject(Process process) {
		ProcessListObject listObject = new ProcessListObject(process);
		listObject.setSelected(isSelected(process));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Process> createRecordList() {
		try {
			Collection<Process> processList = processDataManager.getProcessList();
			if (processList != null)
				return processList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewProcess() {
		return viewProcess(selectedRecordKey);
	}
	
	public String viewProcess(Object recordKey) {
		Process process = recordByKeyMap.get(recordKey);
		return viewProcess(process);
	}
	
	public String viewProcess(Process process) {
		String url = processInfoManager.viewProcess(process);
		return url;
	}
	
	public String editProcess() {
		return editProcess(selectedRecordKey);
	}
	
	public String editProcess(Object recordKey) {
		Process process = recordByKeyMap.get(recordKey);
		return editProcess(process);
	}
	
	public String editProcess(Process process) {
		String url = processInfoManager.editProcess(process);
		return url;
	}
	
	public void removeProcess() {
		removeProcess(selectedRecordKey);
	}
	
	public void removeProcess(Object recordKey) {
		Process process = recordByKeyMap.get(recordKey);
		removeProcess(process);
	}
	
	public void removeProcess(Process process) {
		try {
			if (processDataManager.removeProcess(process))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelProcess(@Observes @Cancelled Process process) {
		try {
			//Object key = ProcessUtil.getKey(process);
			//recordByKeyMap.put(key, process);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("process");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateProcess(Collection<Process> processList) {
		return ProcessUtil.validate(processList);
	}
	
	public void exportProcessList(@Observes @Export String tableId) {
		//String tableId = "pageForm:processListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
