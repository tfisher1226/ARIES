package nam.model.util;

import java.util.List;

import nam.model.Execution;
import nam.model.Input;
import nam.model.Invocation;
import nam.model.Output;

import org.aries.Assert;


public class ExecutionUtil {

	public static List<Invocation> getActions(Execution execution) {
		List<Invocation> actions = execution.getInvocations();
		return actions;
	}

	public static List<Input> getInputs(Execution execution) {
		List<Input> inputs = execution.getInputs();
		return inputs;
	}

	public static List<Output> getOutputs(Execution execution) {
		List<Output> outputs = execution.getOutputs();
		return outputs;
	}

	public static boolean equals(Execution execution1, Execution execution2) {
		Assert.notNull(execution1, "Execution1 must be specified");
		Assert.notNull(execution2, "Execution2 must be specified");
		Assert.notNull(execution1.getName(), "Execution1 name must be specified");
		Assert.notNull(execution2.getName(), "Execution2 name must be specified");
		if (!execution1.getName().equals(execution2.getName()))
			return false;
		return true;
	}

}
