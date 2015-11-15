package nam.model.operation;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Command;
import nam.model.Fault;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Timeout;
import nam.model.command.CommandListManager;
import nam.model.command.CommandListObject;
import nam.model.fault.FaultListManager;
import nam.model.fault.FaultListObject;
import nam.model.parameter.ParameterListManager;
import nam.model.parameter.ParameterListObject;
import nam.model.result.ResultListManager;
import nam.model.result.ResultListObject;
import nam.model.timeout.TimeoutListManager;
import nam.model.timeout.TimeoutListObject;
import nam.model.util.OperationUtil;


@SessionScoped
@Named("operationHelper")
public class OperationHelper extends AbstractElementHelper<Operation> implements Serializable {
	
	@Override
	public boolean isEmpty(Operation operation) {
		return OperationUtil.isEmpty(operation);
	}
	
	@Override
	public String toString(Operation operation) {
		return OperationUtil.toString(operation);
	}
	
	@Override
	public String toString(Collection<Operation> operationList) {
		return OperationUtil.toString(operationList);
	}
	
	@Override
	public boolean validate(Operation operation) {
		return OperationUtil.validate(operation);
	}
	
	@Override
	public boolean validate(Collection<Operation> operationList) {
		return OperationUtil.validate(operationList);
	}
	
	public DataModel<CommandListObject> getCommands(Operation operation) {
		if (operation == null)
			return null;
		return getCommands(operation.getCommands());
	}
	
	public DataModel<CommandListObject> getCommands(Collection<Command> commandsList) {
		CommandListManager commandListManager = BeanContext.getFromSession("commandListManager");
		DataModel<CommandListObject> dataModel = commandListManager.getDataModel(commandsList);
		return dataModel;
	}
	
	public DataModel<FaultListObject> getFaults(Operation operation) {
		if (operation == null)
			return null;
		return getFaults(operation.getFaults());
	}
	
	public DataModel<FaultListObject> getFaults(Collection<Fault> faultsList) {
		FaultListManager faultListManager = BeanContext.getFromSession("faultListManager");
		DataModel<FaultListObject> dataModel = faultListManager.getDataModel(faultsList);
		return dataModel;
	}
	
	public DataModel<ParameterListObject> getParameters(Operation operation) {
		if (operation == null)
			return null;
		return getParameters(operation.getParameters());
	}
	
	public DataModel<ParameterListObject> getParameters(Collection<Parameter> parametersList) {
		ParameterListManager parameterListManager = BeanContext.getFromSession("parameterListManager");
		DataModel<ParameterListObject> dataModel = parameterListManager.getDataModel(parametersList);
		return dataModel;
	}
	
	public DataModel<ResultListObject> getResults(Operation operation) {
		if (operation == null)
			return null;
		return getResults(operation.getResults());
	}
	
	public DataModel<ResultListObject> getResults(Collection<Result> resultsList) {
		ResultListManager resultListManager = BeanContext.getFromSession("resultListManager");
		DataModel<ResultListObject> dataModel = resultListManager.getDataModel(resultsList);
		return dataModel;
	}
	
	public DataModel<TimeoutListObject> getTimeouts(Operation operation) {
		if (operation == null)
			return null;
		return getTimeouts(operation.getTimeouts());
	}
	
	public DataModel<TimeoutListObject> getTimeouts(Collection<Timeout> timeoutsList) {
		TimeoutListManager timeoutListManager = BeanContext.getFromSession("timeoutListManager");
		DataModel<TimeoutListObject> dataModel = timeoutListManager.getDataModel(timeoutsList);
		return dataModel;
	}
	
}
