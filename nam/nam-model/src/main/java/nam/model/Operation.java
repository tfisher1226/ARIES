package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Operation", namespace = "http://nam/model", propOrder = {
	"name",
	"role",
    "parameters",
	"results",
	"timeouts",
    "faults",
	"commands"
})
@XmlRootElement(name = "operation", namespace = "http://nam/model")
public class Operation implements Comparable<Operation>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model", required = true)
	private String name;
	
	@XmlElement(name = "role", namespace = "http://nam/model")
	private String role;
	
	@XmlElement(name = "parameters", namespace = "http://nam/model")
	private List<Parameter> parameters;
	
	@XmlElement(name = "results", namespace = "http://nam/model")
	private List<Result> results;
	
	@XmlElement(name = "timeouts", namespace = "http://nam/model")
	private List<Timeout> timeouts;
	
	@XmlElement(name = "faults", namespace = "http://nam/model")
	private List<Fault> faults;
	
	@XmlElement(name = "commands", namespace = "http://nam/model")
	private List<Command> commands;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Operation() {
		parameters = new ArrayList<Parameter>();
		results = new ArrayList<Result>();
		timeouts = new ArrayList<Timeout>();
		faults = new ArrayList<Fault>();
		commands = new ArrayList<Command>();
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

    public List<Parameter> getParameters() {
		synchronized (parameters) {
			return parameters;
		}
	}
	
	public void setParameters(Collection<Parameter> parameters) {
        if (parameters == null) {
			this.parameters = null;
		} else {
		synchronized (this.parameters) {
				this.parameters = new ArrayList<Parameter>();
				addToParameters(parameters);
			}
        }
    }

	public void addToParameters(Parameter parameter) {
		if (parameter != null ) {
			synchronized (this.parameters) {
				this.parameters.add(parameter);
			}
		}
	}

	public void addToParameters(Collection<Parameter> parameterCollection) {
		if (parameterCollection != null && !parameterCollection.isEmpty()) {
			synchronized (this.parameters) {
				this.parameters.addAll(parameterCollection);
			}
		}
	}

	public void removeFromParameters(Parameter parameter) {
		if (parameter != null ) {
			synchronized (this.parameters) {
				this.parameters.remove(parameter);
			}
		}
	}

	public void removeFromParameters(Collection<Parameter> parameterCollection) {
		if (parameterCollection != null ) {
			synchronized (this.parameters) {
				this.parameters.removeAll(parameterCollection);
			}
		}
	}

	public void clearParameters() {
		synchronized (parameters) {
			parameters.clear();
		}
	}
	
	public List<Result> getResults() {
		synchronized (results) {
			return results;
		}
	}
	
	public void setResults(Collection<Result> results) {
		if (results == null) {
			this.results = null;
		} else {
		synchronized (this.results) {
				this.results = new ArrayList<Result>();
				addToResults(results);
			}
		}
	}

	public void addToResults(Result result) {
		if (result != null ) {
			synchronized (this.results) {
				this.results.add(result);
			}
		}
	}

	public void addToResults(Collection<Result> resultCollection) {
		if (resultCollection != null && !resultCollection.isEmpty()) {
			synchronized (this.results) {
				this.results.addAll(resultCollection);
			}
		}
	}

	public void removeFromResults(Result result) {
		if (result != null ) {
			synchronized (this.results) {
				this.results.remove(result);
			}
		}
	}

	public void removeFromResults(Collection<Result> resultCollection) {
		if (resultCollection != null ) {
			synchronized (this.results) {
				this.results.removeAll(resultCollection);
			}
		}
	}

	public void clearResults() {
		synchronized (results) {
			results.clear();
		}
	}
	
	public List<Timeout> getTimeouts() {
		synchronized (timeouts) {
			return timeouts;
		}
	}
	
	public void setTimeouts(Collection<Timeout> timeouts) {
		if (timeouts == null) {
			this.timeouts = null;
		} else {
		synchronized (this.timeouts) {
				this.timeouts = new ArrayList<Timeout>();
				addToTimeouts(timeouts);
			}
		}
	}

	public void addToTimeouts(Timeout timeout) {
		if (timeout != null ) {
			synchronized (this.timeouts) {
				this.timeouts.add(timeout);
			}
		}
	}

	public void addToTimeouts(Collection<Timeout> timeoutCollection) {
		if (timeoutCollection != null && !timeoutCollection.isEmpty()) {
			synchronized (this.timeouts) {
				this.timeouts.addAll(timeoutCollection);
			}
		}
	}

	public void removeFromTimeouts(Timeout timeout) {
		if (timeout != null ) {
			synchronized (this.timeouts) {
				this.timeouts.remove(timeout);
			}
		}
	}

	public void removeFromTimeouts(Collection<Timeout> timeoutCollection) {
		if (timeoutCollection != null ) {
			synchronized (this.timeouts) {
				this.timeouts.removeAll(timeoutCollection);
			}
		}
	}

	public void clearTimeouts() {
		synchronized (timeouts) {
			timeouts.clear();
        }
    }

    public List<Fault> getFaults() {
		synchronized (faults) {
			return faults;
		}
	}
	
	public void setFaults(Collection<Fault> faults) {
        if (faults == null) {
			this.faults = null;
		} else {
		synchronized (this.faults) {
				this.faults = new ArrayList<Fault>();
				addToFaults(faults);
			}
        }
    }

	public void addToFaults(Fault fault) {
		if (fault != null ) {
			synchronized (this.faults) {
				this.faults.add(fault);
			}
		}
    }

	public void addToFaults(Collection<Fault> faultCollection) {
		if (faultCollection != null && !faultCollection.isEmpty()) {
			synchronized (this.faults) {
				this.faults.addAll(faultCollection);
			}
		}
	}

	public void removeFromFaults(Fault fault) {
		if (fault != null ) {
			synchronized (this.faults) {
				this.faults.remove(fault);
			}
		}
	}

	public void removeFromFaults(Collection<Fault> faultCollection) {
		if (faultCollection != null ) {
			synchronized (this.faults) {
				this.faults.removeAll(faultCollection);
			}
		}
	}

	public void clearFaults() {
		synchronized (faults) {
			faults.clear();
		}
	}
	
	public List<Command> getCommands() {
		synchronized (commands) {
			return commands;
		}
	}
	
	public void setCommands(Collection<Command> commands) {
		if (commands == null) {
			this.commands = null;
		} else {
		synchronized (this.commands) {
				this.commands = new ArrayList<Command>();
				addToCommands(commands);
			}
		}
	}

	public void addToCommands(Command command) {
		if (command != null ) {
			synchronized (this.commands) {
				this.commands.add(command);
			}
		}
	}

	public void addToCommands(Collection<Command> commandCollection) {
		if (commandCollection != null && !commandCollection.isEmpty()) {
			synchronized (this.commands) {
				this.commands.addAll(commandCollection);
			}
		}
	}

	public void removeFromCommands(Command command) {
		if (command != null ) {
			synchronized (this.commands) {
				this.commands.remove(command);
			}
		}
	}

	public void removeFromCommands(Collection<Command> commandCollection) {
		if (commandCollection != null ) {
			synchronized (this.commands) {
				this.commands.removeAll(commandCollection);
			}
		}
	}

	public void clearCommands() {
		synchronized (commands) {
			commands.clear();
		}
	}
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Operation other) {
		int status = compare(name, other.name);
		if (status != 0)
			return status;
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Operation other = (Operation) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
    }

	@Override
	public String toString() {
		return "Operation: name="+name;
    }

}
