//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.13 at 09:40:22 AM PST 
//


package org.aries.validate;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.aries.validate package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.aries.validate
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Checkpoints }
     * 
     */
    public Checkpoints createCheckpoints() {
        return new Checkpoints();
    }

    /**
     * Create an instance of {@link Checkpoint }
     * 
     */
    public Checkpoint createCheckpoint() {
        return new Checkpoint();
    }

    /**
     * Create an instance of {@link Condition }
     * 
     */
    public Condition createCondition() {
        return new Condition();
    }

    /**
     * Create an instance of {@link And }
     * 
     */
    public And createAnd() {
        return new And();
    }

    /**
     * Create an instance of {@link Or }
     * 
     */
    public Or createOr() {
        return new Or();
    }

}