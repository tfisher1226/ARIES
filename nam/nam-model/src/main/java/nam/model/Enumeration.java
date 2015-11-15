package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Enumeration", namespace = "http://nam/model", propOrder = {
    "literals"
})
@XmlRootElement(name = "enumeration", namespace = "http://nam/model")
public class Enumeration
    extends Type
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    
    @XmlElement(name = "literal", namespace = "http://nam/model")
    protected List<Literal> literals;
    
    @XmlAttribute(name = "default")
    protected String _default;

    /**
     * Gets the value of the literals property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the literals property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLiterals().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Literal }
     * 
     * 
     */
    public List<Literal> getLiterals() {
        if (literals == null) {
            literals = new ArrayList<Literal>();
        }
        return this.literals;
    }

    /**
     * Gets the value of the default property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public String getDefault() {
        return _default;
    }

    /**
     * Sets the value of the default property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDefault(String value) {
        this._default = value;
    }

}
