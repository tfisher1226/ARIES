package nam.model.result;

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

import nam.model.Result;
import nam.model.util.ResultUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("resultListManager")
public class ResultListManager extends AbstractDomainListManager<Result, ResultListObject> implements Serializable {
	
	@Inject
	private ResultDataManager resultDataManager;
	
	@Inject
	private ResultEventManager resultEventManager;
	
	@Inject
	private ResultInfoManager resultInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "resultList";
	}
	
	@Override
	public String getTitle() {
		return "Result List";
	}
	
	@Override
	public Object getRecordKey(Result result) {
		return ResultUtil.getKey(result);
	}
	
	@Override
	public String getRecordName(Result result) {
		return ResultUtil.getLabel(result);
	}
	
	@Override
	protected Class<Result> getRecordClass() {
		return Result.class;
	}
	
	@Override
	protected Result getRecord(ResultListObject rowObject) {
		return rowObject.getResult();
	}
	
	@Override
	public Result getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ResultUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Result result) {
		super.setSelectedRecord(result);
		fireSelectedEvent(result);
	}
	
	protected void fireSelectedEvent(Result result) {
		resultEventManager.fireSelectedEvent(result);
	}
	
	public boolean isSelected(Result result) {
		Result selection = selectionContext.getSelection("result");
		boolean selected = selection != null && selection.equals(result);
		return selected;
	}
	
	public boolean isChecked(Result result) {
		Collection<Result> selection = selectionContext.getSelection("resultSelection");
		boolean checked = selection != null && selection.contains(result);
		return checked;
	}
	
	@Override
	protected ResultListObject createRowObject(Result result) {
		ResultListObject listObject = new ResultListObject(result);
		listObject.setSelected(isSelected(result));
		listObject.setChecked(isChecked(result));
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
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Result> createRecordList() {
		try {
			Collection<Result> resultList = resultDataManager.getResultList();
			if (resultList != null)
				return resultList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewResult() {
		return viewResult(selectedRecordKey);
	}
	
	public String viewResult(Object recordKey) {
		Result result = recordByKeyMap.get(recordKey);
		return viewResult(result);
	}
	
	public String viewResult(Result result) {
		String url = resultInfoManager.viewResult(result);
		return url;
	}
	
	public String editResult() {
		return editResult(selectedRecordKey);
	}
	
	public String editResult(Object recordKey) {
		Result result = recordByKeyMap.get(recordKey);
		return editResult(result);
	}
	
	public String editResult(Result result) {
		String url = resultInfoManager.editResult(result);
		return url;
	}
	
	public void removeResult() {
		removeResult(selectedRecordKey);
	}
	
	public void removeResult(Object recordKey) {
		Result result = recordByKeyMap.get(recordKey);
		removeResult(result);
	}
	
	public void removeResult(Result result) {
		try {
			if (resultDataManager.removeResult(result))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelResult(@Observes @Cancelled Result result) {
		try {
			//Object key = ResultUtil.getKey(result);
			//recordByKeyMap.put(key, result);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("result");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateResult(Collection<Result> resultList) {
		return ResultUtil.validate(resultList);
	}
	
	public void exportResultList(@Observes @Export String tableId) {
		//String tableId = "pageForm:resultListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
