package aries.bpel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


public class AssignGeneratorTestOrig extends AbstractCommandGeneratorTest {

	private static String FILE_NAME = "SimpleInvoke/SimpleInvoke.bpel";
	//private static String FILE_NAME = "TestAssignActivity1/TestAssign.bpel";
	
	private AssignGenerator fixture;
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
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
	public void testGenerate() throws Exception {
		String[] files = new String[] {FILE_NAME};
		Process process = getBPELProcessForTest(files);
		fixture = new AssignGenerator(context, process);
		fixture.setGlobalVariables(BPELUtil.createVariablesMap(process.getVariables()));
		Sequence sequence = (Sequence) process.getActivity();
		EList<Activity> activities = sequence.getActivities();
		Iterator<Activity> iterator = activities.iterator();
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		for (int i=0; iterator.hasNext(); i++) {
			Activity activity = iterator.next();
			if (activity instanceof Assign) {
				Assign assignActivity = (Assign) activity;
				fixture.generate(assignActivity);
				ModelOperation operation = fixture.getInstanceOperations().get(0);
				Assert.notNull(operation, "Operation must exist");
				modelOperations.add(operation);
			}
		}
		Iterator<ModelOperation> operationIterator = modelOperations.iterator();
		for (int i=0; operationIterator.hasNext(); i++) {
			ModelOperation operation = operationIterator.next();
			System.out.println("\nOperation: "+operation.getName());
			System.out.println(operation.getInitialSource());
		}
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
