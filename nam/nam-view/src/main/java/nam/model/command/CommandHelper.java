package nam.model.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;
import org.aries.ui.model.ListTableModel;

import nam.model.Attribute;
import nam.model.Command;
import nam.model.Parameter;
import nam.model.Reference;
import nam.model.Result;
import nam.model.attribute.AttributeListManager;
import nam.model.attribute.AttributeListObject;
import nam.model.parameter.ParameterListManager;
import nam.model.parameter.ParameterListObject;
import nam.model.reference.ReferenceListManager;
import nam.model.reference.ReferenceListObject;
import nam.model.result.ResultListManager;
import nam.model.result.ResultListObject;
import nam.model.util.CommandUtil;


@SessionScoped
@Named("commandHelper")
public class CommandHelper extends AbstractElementHelper<Command> implements Serializable {
	
	@Override
	public boolean isEmpty(Command command) {
		return CommandUtil.isEmpty(command);
	}
	
	@Override
	public String toString(Command command) {
		return CommandUtil.toString(command);
	}
	
	@Override
	public String toString(Collection<Command> commandList) {
		return CommandUtil.toString(commandList);
	}
	
	@Override
	public boolean validate(Command command) {
		return CommandUtil.validate(command);
	}
	
	@Override
	public boolean validate(Collection<Command> commandList) {
		return CommandUtil.validate(commandList);
	}
	
	public DataModel<AttributeListObject> getAttributes(Command command) {
		if (command == null)
			return null;
		return getAttributes(command.getAttributes());
	}
	
	public DataModel<AttributeListObject> getAttributes(Collection<Attribute> attributesList) {
		AttributeListManager attributeListManager = BeanContext.getFromSession("attributeListManager");
		DataModel<AttributeListObject> dataModel = attributeListManager.getDataModel(attributesList);
		return dataModel;
	}
	
	public DataModel<CommandListObject> getCommands(Command command) {
		if (command == null)
			return null;
		return getCommands(command.getCommands());
	}
	
	public DataModel<CommandListObject> getCommands(Collection<Command> commandsList) {
		CommandListManager commandListManager = BeanContext.getFromSession("commandListManager");
		DataModel<CommandListObject> dataModel = commandListManager.getDataModel(commandsList);
		return dataModel;
	}
	
	public DataModel<ParameterListObject> getParameters(Command command) {
		if (command == null)
			return null;
		return getParameters(command.getParameters());
	}
	
	public DataModel<ParameterListObject> getParameters(Collection<Parameter> parametersList) {
		ParameterListManager parameterListManager = BeanContext.getFromSession("parameterListManager");
		DataModel<ParameterListObject> dataModel = parameterListManager.getDataModel(parametersList);
		return dataModel;
	}
	
	public DataModel<ReferenceListObject> getReferences(Command command) {
		if (command == null)
			return null;
		return getReferences(command.getReferences());
	}
	
	public DataModel<ReferenceListObject> getReferences(Collection<Reference> referencesList) {
		ReferenceListManager referenceListManager = BeanContext.getFromSession("referenceListManager");
		DataModel<ReferenceListObject> dataModel = referenceListManager.getDataModel(referencesList);
		return dataModel;
	}
	
	public DataModel<ResultListObject> getResults(Command command) {
		if (command == null)
			return null;
		return getResults(command.getResults());
	}
	
	public DataModel<ResultListObject> getResults(Collection<Result> resultsList) {
		ResultListManager resultListManager = BeanContext.getFromSession("resultListManager");
		DataModel<ResultListObject> dataModel = resultListManager.getDataModel(resultsList);
		return dataModel;
	}
	
	public DataModel<String> getTokens(Command command) {
		if (command == null)
			return null;
		return getTokens(command.getTokens());
	}
	
	public DataModel<String> getTokens(Collection<String> tokensList) {
		List<String> values = new ArrayList<String>(tokensList);
		@SuppressWarnings("unchecked") DataModel<String> dataModel = new ListTableModel<String>(values);
		return dataModel;
	}
	
}
