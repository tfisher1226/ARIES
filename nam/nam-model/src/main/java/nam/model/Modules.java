package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "importsAndModules"
})
@XmlRootElement(name = "modules", namespace = "http://nam/model")
public class Modules
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "import", namespace = "http://nam/model", type = Import.class),
        @XmlElement(name = "module", namespace = "http://nam/model", type = Module.class)
    })
    protected List<Serializable> importsAndModules;
    @XmlAttribute(name = "domain")
    protected String domain;

    /**
     * Gets the value of the importsAndModules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importsAndModules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImportsAndModules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Import }
     * {@link Module }
     * 
     * 
     */
    public List<Serializable> getImportsAndModules() {
        if (importsAndModules == null) {
            importsAndModules = new ArrayList<Serializable>();
        }
        return this.importsAndModules;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

}
