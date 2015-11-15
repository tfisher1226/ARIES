package aries.bpel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.aries.Assert;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aries.generation.model.ModelOperation;


public class AssignGeneratorTest extends AbstractCommandGeneratorTest {

	private AssignGenerator fixture;

	private Map<String, ModelOperation> modelOperations;
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		modelOperations = new HashMap<String, ModelOperation>();
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		super.tearDown();
	}

//	@Test
//	public void testGenerate() throws Exception {
//		String source = "";
//		//Assign assignActivity = BPELFactory.eINSTANCE.createAssign();
//		ModelOperation operation = fixture.generate(assignActivity);
//	}

	@Test
	public void testGenerateSet1() throws Exception {
		processInput("Testing/TestAssign.bpel");
		verifyOutput("assign1", "strVar = this.myVar_TestPart;");
		verifyOutput("assign2", "this.myVar_TestPart = strVar;");
		verifyOutput("assign3", "strVar = \"blah\";");
		verifyOutput("assign4", "this.intVar = 2 + 1;");
		verifyOutput("assign5", "intVar2 = intVar;");
		verifyOutput("assign6", "intVar = intVar + 1;");
		verifyOutput("assign7", "this.intVar = intVar + 1;");
		verifyOutput("assign8", "this.boolVar = intVar == 5;");
		verifyOutput("assign9", "this.intVar = Math.abs(intVar + Integer.parseInt(\"2\"));");
		verifyOutput("assign10", "this.strVar = this.myVar_TestPart;");
		verifyOutput("assign11", "this.myVar_TestPart = strVar + \" World\" + intVar + boolVar + intVar2;");
		verifyOutput("assign12", "this.otherMsgVar_TestPart = this.myVar_TestPart;");
	}

	@Test
	public void testGenerateSet2() throws Exception {
		processInput("Testing/TestAssignCollections.bpel");
		verifyOutput("addToIntegerArray", "this.IntegerArrayMessage_IntegerArray.getItems().add(Integer.parseInt(\"123\"));");
		verifyOutput("assignIntoIntegerArray", "this.IntegerArrayMessage_IntegerArray.getItems().set(this.indexCounter, Integer.parseInt(\"123\"));");
		verifyOutput("assignIntoStringArray", "this.StringArrayMessage_StringArray.getItems().set(this.indexCounter, \"dummyString\");");
	}
	
	protected void processInput(String fileName) throws Exception {
		String[] files = new String[] {fileName};
		Process process = getBPELProcessForTest(files);
		fixture = new AssignGenerator(context, process);
		fixture.setGlobalVariables(BPELUtil.createVariablesMap(process.getVariables()));
		Sequence sequence = (Sequence) process.getActivity();
		EList<Activity> activities = sequence.getActivities();
		Iterator<Activity> iterator = activities.iterator();
		for (int i=0, count=0; iterator.hasNext(); i++) {
			Activity activity = iterator.next();
			if (activity instanceof Assign) {
				Assign assignActivity = (Assign) activity;
				fixture.generate(assignActivity);
				ModelOperation operation = fixture.getInstanceOperations().get(count);
				Assert.notNull(operation, "Operation must exist");
				modelOperations.put(operation.getName(), operation);
				count++;
			}
		}
	}
	
	protected void verifyOutput(String operationName, String expectedCode) throws Exception {
		ModelOperation operation = modelOperations.get(operationName);
		if (operation == null) {
			System.out.println("WARNING: BPEL operation not found: "+operationName);
			return;
		}
		String actualCode = operation.getInitialSource().toString();
		actualCode = actualCode.replaceAll("\t", "");
		actualCode = actualCode.replaceAll("\\[", "");
		actualCode = actualCode.replaceAll("\\]", "");
		actualCode = actualCode.replaceAll("\n", "");
		if (!actualCode.equals(expectedCode))
			System.out.println("Actual: "+actualCode);		
		Assert.equals(actualCode, expectedCode, "Unexpected \""+operationName+"\" output, expected: "+expectedCode+", actual: "+actualCode);
	}

	protected Process getBPELProcessForTest(String[] fileNames) throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest(fileNames);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		Resource resource = emfResourceSet.getResources().get(0);
		Assert.isInstanceOf(BPELResource.class, resource, "ResourceSet must exist");
		BPELResource bpelResource = (BPELResource) resource;
		Process process = bpelResource.getProcess();
		Assert.notNull(process, "Process must exist");
		return process;
	}

}
