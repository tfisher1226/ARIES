package nam.model.process;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Process;
import nam.model.util.ProcessUtil;


@SessionScoped
@Named("processSelectManager")
public class ProcessSelectManager extends AbstractSelectManager<Process, ProcessListObject> implements Serializable {
	
	@Inject
	private ProcessDataManager processDataManager;
	
	@Inject
	private ProcessHelper processHelper;
	
	
	@Override
	public String getClientId() {
		return "processSelect";
	}
	
	@Override
	public String getTitle() {
		return "Process Selection";
	}
	
	@Override
	protected Class<Process> getRecordClass() {
		return Process.class;
	}
	
	@Override
	public boolean isEmpty(Process process) {
		return processHelper.isEmpty(process);
	}
	
	@Override
	public String toString(Process process) {
		return processHelper.toString(process);
	}
	
	protected ProcessHelper getProcessHelper() {
		return BeanContext.getFromSession("processHelper");
	}
	
	protected ProcessListManager getProcessListManager() {
		return BeanContext.getFromSession("processListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshProcessList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Process> recordList) {
		ProcessListManager processListManager = getProcessListManager();
		DataModel<ProcessListObject> dataModel = processListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshProcessList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Process> refreshRecords() {
		try {
			Collection<Process> records = processDataManager.getProcessList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Process> processList) {
		Collections.sort(processList, new Comparator<Process>() {
			public int compare(Process process1, Process process2) {
				String text1 = ProcessUtil.toString(process1);
				String text2 = ProcessUtil.toString(process2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
