package nam.model.result;

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

import nam.model.Result;
import nam.model.util.ResultUtil;


@SessionScoped
@Named("resultSelectManager")
public class ResultSelectManager extends AbstractSelectManager<Result, ResultListObject> implements Serializable {
	
	@Inject
	private ResultDataManager resultDataManager;
	
	@Inject
	private ResultHelper resultHelper;
	
	
	@Override
	public String getClientId() {
		return "resultSelect";
	}
	
	@Override
	public String getTitle() {
		return "Result Selection";
	}
	
	@Override
	protected Class<Result> getRecordClass() {
		return Result.class;
	}
	
	@Override
	public boolean isEmpty(Result result) {
		return resultHelper.isEmpty(result);
	}
	
	@Override
	public String toString(Result result) {
		return resultHelper.toString(result);
	}
	
	protected ResultHelper getResultHelper() {
		return BeanContext.getFromSession("resultHelper");
	}
	
	protected ResultListManager getResultListManager() {
		return BeanContext.getFromSession("resultListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshResultList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Result> recordList) {
		ResultListManager resultListManager = getResultListManager();
		DataModel<ResultListObject> dataModel = resultListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshResultList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Result> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Result> resultList = BeanContext.getFromConversation(instanceId);
		return resultList;
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
	public void sortRecords(List<Result> resultList) {
		Collections.sort(resultList, new Comparator<Result>() {
			public int compare(Result result1, Result result2) {
				String text1 = ResultUtil.toString(result1);
				String text2 = ResultUtil.toString(result2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
