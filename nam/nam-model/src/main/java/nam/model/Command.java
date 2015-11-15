package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Command", namespace = "http://nam/model", propOrder = {
    "name",
    "type",
	"node",
    "actor",
	"tokens",
    "attributes",
    "references",
	"parameters",
	"results",
    "commands",
	"text",
	"indent"
})
@XmlRootElement(name = "command", namespace = "http://nam/model")
public class Command implements Comparable<Command>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model", required = true)
	private CommandName name;
	
	@XmlElement(name = "type", namespace = "http://nam/model", required = true)
	private String type;
	
	@XmlElement(name = "node", namespace = "http://nam/model")
	private Object node;
	
	@XmlElement(name = "actor", namespace = "http://nam/model")
	private Object actor;
	
	@XmlElement(name = "tokens", namespace = "http://nam/model")
	private List<String> tokens;
	
	@XmlElement(name = "attributes", namespace = "http://nam/model")
	private List<Attribute> attributes;
	
	@XmlElement(name = "references", namespace = "http://nam/model")
	private List<Reference> references;
	
	@XmlElement(name = "parameters", namespace = "http://nam/model")
	private List<Parameter> parameters;
	
	@XmlElement(name = "results", namespace = "http://nam/model")
	private List<Result> results;
	
	@XmlElement(name = "commands", namespace = "http://nam/model")
	private List<Command> commands;
	
	@XmlElement(name = "text", namespace = "http://nam/model")
	private String text;
	
	@XmlElement(name = "indent", namespace = "http://nam/model")
	private Integer indent;
	
	
	public Command() {
		tokens = new ArrayList<String>();
		attributes = new ArrayList<Attribute>();
		references = new ArrayList<Reference>();
		parameters = new ArrayList<Parameter>();
		results = new ArrayList<Result>();
		commands = new ArrayList<Command>();
	}
	
	
    public CommandName getName() {
        return name;
    }

	public void setName(CommandName name) {
		this.name = name;
    }

	public String getType() {
		return type;
    }

	public void setType(String type) {
		this.type = type;
    }

	public Object getNode() {
		return node;
	}
	
	public void setNode(Object node) {
		this.node = node;
	}
	
    public Object getActor() {
        return actor;
    }

	public void setActor(Object actor) {
		this.actor = actor;
        }
	
	public List<String> getTokens() {
		synchronized (tokens) {
			return tokens;
		}
	}
	
	public void setTokens(Collection<String> tokens) {
		if (tokens == null) {
			this.tokens = null;
		} else {
		synchronized (this.tokens) {
				this.tokens = new ArrayList<String>();
				addToTokens(tokens);
			}
		}
	}

	public void addToTokens(String tokens) {
		if (tokens != null ) {
			synchronized (this.tokens) {
				this.tokens.add(tokens);
			}
		}
	}

	public void addToTokens(Collection<String> tokensCollection) {
		if (tokensCollection != null && !tokensCollection.isEmpty()) {
			synchronized (this.tokens) {
				this.tokens.addAll(tokensCollection);
			}
		}
	}

	public void removeFromTokens(String tokens) {
		if (tokens != null ) {
			synchronized (this.tokens) {
				this.tokens.remove(tokens);
			}
		}
	}
	
	public void removeFromTokens(Collection<String> tokensCollection) {
		if (tokensCollection != null ) {
			synchronized (this.tokens) {
				this.tokens.removeAll(tokensCollection);
			}
		}
	}

	public void clearTokens() {
		synchronized (tokens) {
			tokens.clear();
		}
    }

    public List<Attribute> getAttributes() {
		synchronized (attributes) {
			return attributes;
		}
	}
	
	public void setAttributes(Collection<Attribute> attributes) {
        if (attributes == null) {
			this.attributes = null;
		} else {
		synchronized (this.attributes) {
				this.attributes = new ArrayList<Attribute>();
				addToAttributes(attributes);
			}
		}
	}

	public void addToAttributes(Attribute attribute) {
		if (attribute != null ) {
			synchronized (this.attributes) {
				this.attributes.add(attribute);
			}
		}
	}

	public void addToAttributes(Collection<Attribute> attributeCollection) {
		if (attributeCollection != null && !attributeCollection.isEmpty()) {
			synchronized (this.attributes) {
				this.attributes.addAll(attributeCollection);
			}
		}
	}

	public void removeFromAttributes(Attribute attribute) {
		if (attribute != null ) {
			synchronized (this.attributes) {
				this.attributes.remove(attribute);
			}
		}
	}

	public void removeFromAttributes(Collection<Attribute> attributeCollection) {
		if (attributeCollection != null ) {
			synchronized (this.attributes) {
				this.attributes.removeAll(attributeCollection);
			}
		}
	}

	public void clearAttributes() {
		synchronized (attributes) {
			attributes.clear();
        }
    }

    public List<Reference> getReferences() {
		synchronized (references) {
			return references;
		}
	}
	
	public void setReferences(Collection<Reference> references) {
        if (references == null) {
			this.references = null;
		} else {
		synchronized (this.references) {
				this.references = new ArrayList<Reference>();
				addToReferences(references);
			}
		}
	}

	public void addToReferences(Reference reference) {
		if (reference != null ) {
			synchronized (this.references) {
				this.references.add(reference);
			}
		}
	}

	public void addToReferences(Collection<Reference> referenceCollection) {
		if (referenceCollection != null && !referenceCollection.isEmpty()) {
			synchronized (this.references) {
				this.references.addAll(referenceCollection);
			}
		}
	}

	public void removeFromReferences(Reference reference) {
		if (reference != null ) {
			synchronized (this.references) {
				this.references.remove(reference);
			}
		}
	}

	public void removeFromReferences(Collection<Reference> referenceCollection) {
		if (referenceCollection != null ) {
			synchronized (this.references) {
				this.references.removeAll(referenceCollection);
			}
		}
	}

	public void clearReferences() {
		synchronized (references) {
			references.clear();
		}
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
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Integer getIndent() {
		return indent;
	}
	
	public void setIndent(Integer indent) {
		this.indent = indent;
    }

	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Command other) {
		int status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(type, other.type);
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
		Command other = (Command) object;
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
		return "Command: name="+name+", type="+type;
    }

}
