package nam.model.statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class BlockStatement extends Statement {

	private List<ExceptionBlock> exceptionBlocks = new ArrayList<ExceptionBlock>();

	private List<TimeoutBlock> timeoutBlocks = new ArrayList<TimeoutBlock>();

	private Set<ConditionStatement> requiredConditions = new LinkedHashSet<ConditionStatement>();
	
	private Set<Branch> requiredBranches = new LinkedHashSet<Branch>();
	
	
	public BlockStatement() {
	}

	public List<ExceptionBlock> getExceptionBlocks() {
		return exceptionBlocks;
	}

	public void setExceptionBlocks(List<ExceptionBlock> exceptionBlocks) {
		this.exceptionBlocks = exceptionBlocks;
	}

	public void addExceptionBlock(ExceptionBlock exceptionBlock) {
		exceptionBlocks.add(exceptionBlock);
	}

	public void addExceptionBlocks(List<ExceptionBlock> exceptionBlocks) {
		this.exceptionBlocks.addAll(exceptionBlocks);
	}

	public List<TimeoutBlock> getTimeoutBlocks() {
		return timeoutBlocks;
	}

	public void setTimeoutBlocks(List<TimeoutBlock> timeoutBlocks) {
		this.timeoutBlocks = timeoutBlocks;
	}

	public void addTimeoutBlock(TimeoutBlock timeoutBlock) {
		timeoutBlocks.add(timeoutBlock);
	}
	
	public void addTimeoutBlocks(List<TimeoutBlock> timeoutBlocks) {
		this.timeoutBlocks.addAll(timeoutBlocks);
	}

	public Set<ConditionStatement> getRequiredConditions() {
		return requiredConditions;
	}

	public void setRequiredConditions(Collection<ConditionStatement> requiredConditions) {
		this.requiredConditions = new LinkedHashSet<ConditionStatement>();
		addRequiredConditions(requiredConditions);
	}
	
	public void addRequiredConditions(Collection<ConditionStatement> requiredConditions) {
		this.requiredConditions.addAll(requiredConditions);
	}
	
	public void addRequiredConditions(ConditionStatement requiredCondition) {
		this.requiredConditions.add(requiredCondition);
	}

	public Set<Branch> getRequiredBranches() {
		return requiredBranches;
	}

	public void setRequiredBranches(Collection<Branch> requiredBranches) {
		this.requiredBranches = new LinkedHashSet<Branch>();
		addRequiredBranches(requiredBranches);
	}
	
	public void addRequiredBranches(Collection<Branch> requiredBranches) {
		this.requiredBranches.addAll(requiredBranches);
	}
	
	public void addRequiredBranches(Branch requiredBranch) {
		this.requiredBranches.add(requiredBranch);
	}

}
