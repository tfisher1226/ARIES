package nam.model.process;

import java.io.Serializable;

import nam.model.Process;
import nam.model.util.ProcessUtil;

import org.aries.ui.AbstractListObject;


public class ProcessListObject extends AbstractListObject<Process> implements Comparable<ProcessListObject>, Serializable {
	
	private Process process;
	
	
	public ProcessListObject(Process process) {
		this.process = process;
	}
	
	
	public Process getProcess() {
		return process;
	}
	
	@Override
	public Object getKey() {
		return getKey(process);
	}
	
	public Object getKey(Process process) {
		return ProcessUtil.getKey(process);
	}
	
	@Override
	public String getLabel() {
		return getLabel(process);
	}
	
	public String getLabel(Process process) {
		return ProcessUtil.getLabel(process);
	}
	
	@Override
	public String toString() {
		return toString(process);
	}
	
	@Override
	public String toString(Process process) {
		return ProcessUtil.toString(process);
	}
	
	@Override
	public int compareTo(ProcessListObject other) {
		Object thisKey = getKey(this.process);
		Object otherKey = getKey(other.process);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ProcessListObject other = (ProcessListObject) object;
		Object thisKey = ProcessUtil.getKey(this.process);
		Object otherKey = ProcessUtil.getKey(other.process);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
