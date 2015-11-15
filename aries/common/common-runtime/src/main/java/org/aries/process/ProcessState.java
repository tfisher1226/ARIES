package org.aries.process;

import java.io.Serializable;


public class ProcessState implements Serializable {

    private final static long serialVersionUID = 1L;

    protected String host;
    
    protected Integer port;
    
    protected String name;
    
    protected String version;
    
    protected Object correlationId;
    
    protected Object values;

    
    public String getHost() {
        return host;
    }

    public void setHost(String value) {
        this.host = value;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer value) {
        this.port = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public Object getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(Object correlationId) {
        this.correlationId = correlationId;
    }

    public Object getValues() {
        return values;
    }

    public void setValues(Object value) {
        this.values = value;
    }

}
