package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Transacted", namespace = "http://nam/model")
@XmlRootElement(name = "transacted", namespace = "http://nam/model")
public class Transacted
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    
    @XmlAttribute(name = "local")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean local;
    
    @XmlAttribute(name = "use")
    protected TransactionUsage use;
    
    @XmlAttribute(name = "scope")
    protected TransactionScope scope;

    /**
     * Gets the value of the local property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getLocal() {
        if (local == null) {
            return new BooleanAdapter().unmarshal("false");
        } else {
            return local;
        }
    }

    /**
     * Sets the value of the local property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocal(Boolean value) {
        this.local = value;
    }

    /**
     * Gets the value of the use property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionUsage }
     *     
     */
    public TransactionUsage getUse() {
        return use;
    }

    /**
     * Sets the value of the use property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionUsage }
     *     
     */
    public void setUse(TransactionUsage value) {
        this.use = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionScope }
     *     
     */
    public TransactionScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionScope }
     *     
     */
    public void setScope(TransactionScope value) {
        this.scope = value;
    }

}
