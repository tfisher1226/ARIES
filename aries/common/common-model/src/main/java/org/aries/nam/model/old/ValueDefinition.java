package org.aries.nam.model.old;


public class ValueDefinition extends Definition implements ValueDescripter {

	private static final long serialVersionUID = 1L;
	
    private Object value;
    
    private boolean isStatic;

    private boolean isFinal;
    
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    public boolean isFinal() {
        return isFinal;
    }
    
    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

}
