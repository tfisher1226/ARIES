package org.aries.launcher;


public enum ProgramType {

	SH("sh"),

	PERL("perl"),

	JAVA("java");

	
    private final String value;

    ProgramType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static ProgramType fromValue(String v) {
        for (ProgramType c: ProgramType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
