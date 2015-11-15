package nam.model.result;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Project;
import nam.model.Result;
import nam.model.util.ProjectUtil;
import nam.model.util.ResultUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("resultInfoManager")
public class ResultInfoManager extends AbstractNamRecordManager<Result> implements Serializable {
	
	@Inject
	private ResultWizard resultWizard;
	
	@Inject
	private ResultDataManager resultDataManager;
	
	@Inject
	private ResultPageManager resultPageManager;
	
	@Inject
	private ResultEventManager resultEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ResultHelper resultHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ResultInfoManager() {
		setInstanceName("result");
	}
	
	
	public Result getResult() {
		return getRecord();
	}
	
	public Result getSelectedResult() {
		return selectionContext.getSelection("result");
	}
	
	@Override
	public Class<Result> getRecordClass() {
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
	
	@Override
	public void initialize() {
		Result result = selectionContext.getSelection("result");
		if (result != null)
			initialize(result);
	}
	
	protected void initialize(Result result) {
		ResultUtil.initialize(result);
		resultWizard.initialize(result);
		setContext("result", result);
	}
	
	public void handleResultSelected(@Observes @Selected Result result) {
		selectionContext.setSelection("result",  result);
		resultPageManager.updateState(result);
		resultPageManager.refreshMembers();
		setRecord(result);
	}
	
	@Override
	public String newRecord() {
		return newResult();
	}
	
	public String newResult() {
		try {
			Result result = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("result",  result);
			String url = resultPageManager.initializeResultCreationPage(result);
			resultPageManager.pushContext(resultWizard);
			initialize(result);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Result create() {
		Result result = ResultUtil.create();
		return result;
	}
	
	@Override
	public Result clone(Result result) {
		result = ResultUtil.clone(result);
		return result;
	}
	
	@Override
	public String viewRecord() {
		return viewResult();
	}
	
	public String viewResult() {
		Result result = selectionContext.getSelection("result");
		String url = viewResult(result);
		return url;
	}
	
	public String viewResult(Result result) {
		try {
			String url = resultPageManager.initializeResultSummaryView(result);
			resultPageManager.pushContext(resultWizard);
			initialize(result);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editResult();
	}
	
	public String editResult() {
		Result result = selectionContext.getSelection("result");
		String url = editResult(result);
		return url;
	}
	
	public String editResult(Result result) {
		try {
			//result = clone(result);
			selectionContext.resetOrigin();
			selectionContext.setSelection("result",  result);
			String url = resultPageManager.initializeResultUpdatePage(result);
			resultPageManager.pushContext(resultWizard);
			initialize(result);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveResult() {
		Result result = getResult();
		if (validateResult(result)) {
			saveResult(result);
		}
	}
	
	public void persistResult(Result result) {
		saveResult(result);
	}
	
	public void saveResult(Result result) {
		try {
			saveResultToSystem(result);
			resultEventManager.fireAddedEvent(result);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveResultToSystem(Result result) {
		resultDataManager.saveResult(result);
	}
	
	public void handleSaveResult(@Observes @Add Result result) {
		saveResult(result);
	}
	
	public void enrichResult(Result result) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Result result) {
		return validateResult(result);
	}
	
	public boolean validateResult(Result result) {
		Validator validator = getValidator();
		boolean isValid = ResultUtil.validate(result);
		Display display = getFromSession("display");
		display.setModule("resultInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveResult() {
		display = getFromSession("display");
		display.setModule("resultInfo");
		Result result = selectionContext.getSelection("result");
		if (result == null) {
			display.error("Result record must be selected.");
		}
	}
	
	public String handleRemoveResult(@Observes @Remove Result result) {
		display = getFromSession("display");
		display.setModule("resultInfo");
		try {
			display.info("Removing Result "+ResultUtil.getLabel(result)+" from the system.");
			removeResultFromSystem(result);
			selectionContext.clearSelection("result");
			resultEventManager.fireClearSelectionEvent();
			resultEventManager.fireRemovedEvent(result);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeResultFromSystem(Result result) {
		if (resultDataManager.removeResult(result))
			setRecord(null);
	}
	
	public void cancelResult() {
		BeanContext.removeFromSession("result");
		resultPageManager.removeContext(resultWizard);
	}
	
}
