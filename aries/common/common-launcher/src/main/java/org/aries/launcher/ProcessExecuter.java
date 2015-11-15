package org.aries.launcher;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ProcessExecuter {
    
	private Runtime runtime;
	
	
	public ProcessExecuter() {
		this.runtime = Runtime.getRuntime(); 
	}
	
	void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}
	
    public Process exec(List<String> command, Map<String, String> environment, File location) throws IOException {
        String[] commandArray = command.toArray(new String[command.size()]);
        String[] environmentArray = toVariableArray(environment);
		return runtime.exec(commandArray, environmentArray, location);
    }

	protected String[] toCommandArray(List<String> list) {
        String[] array = list.toArray(new String[list.size()]);
        return array;
	}
	
	protected String[] toVariableArray(Map<String, String> map) {
		String[] array = new String[map.size()];
        Iterator<String> iterator = map.keySet().iterator();
        for (int i=0; iterator.hasNext(); i++) {
			String name = iterator.next();
			String value = map.get(name);
			array[i] = name+"="+value;
		}
		return array;
	}
    
}
