package aries.bpel;

import java.util.List;
import java.util.Map;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Variable;

import aries.codegen.util.Buf;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public interface ActivityGenerator<T extends Activity> {

	/*
	 * State:
	 */

	public void clearState();

	public Variable getVariable(String name);

	public Map<String, Variable> getVariables();

	public Map<String, Variable> getGlobalVariables();

	public Map<String, Variable> getLocalVariables();

		
	/*
	 * Inputs:
	 */

	public void setCurrentClass(ModelClass modelClass);

	public void setCurrentOperation(ModelOperation modelOperation);

	//public void setServiceState(ServiceState serviceState);

	public void addGlobalVariables(Map<String, Variable> variables);

	public void addLocalVariables(Map<String, Variable> variables);

	public void setGlobalVariables(Map<String, Variable> variables);

	public void setLocalVariables(Map<String, Variable> variables);

	public void setIndentationLevel(int indentationLevel);

	public void setInsideCodeBlock(boolean insideForEach);

	public void setInsideFlow(boolean insideFlow);

	public void addImportedClass(String className);


	/*
	 * Processor:
	 */

	public void generate(T activity) throws Exception;

	//public <A extends Activity> void generate(A activity) throws Exception;
	
	
	/*
	 * Outputs:
	 */

	public Buf getStatementSources();

	public List<String> getImportedClasses();

	public List<ModelOperation> getInstanceOperations();

	public List<ModelClass> getSupportingClasses();

}
