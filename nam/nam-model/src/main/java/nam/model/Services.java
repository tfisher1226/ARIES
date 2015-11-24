//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.11 at 12:10:41 PM PDT 
//


package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://nam/model}import"/>
 *         &lt;element ref="{http://nam/model}domain"/>
 *         &lt;element ref="{http://nam/model}service"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "importsAndDomainsAndServices"
})
@XmlRootElement(name = "services", namespace = "http://nam/model")
public class Services implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "import", namespace = "http://nam/model", type = Import.class),
        @XmlElement(name = "domain", namespace = "http://nam/model", type = Domain.class),
        @XmlElement(name = "service", namespace = "http://nam/model", type = Service.class)
    })
    protected List<Serializable> importsAndDomainsAndServices;

    /**
     * Gets the value of the importsAndDomainsAndServices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importsAndDomainsAndServices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImportsAndDomainsAndServices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Import }
     * {@link Domain }
     * {@link Service }
     * 
     * 
     */
    public List<Serializable> getImportsAndDomainsAndServices() {
        if (importsAndDomainsAndServices == null) {
            importsAndDomainsAndServices = new ArrayList<Serializable>();
        }
        return this.importsAndDomainsAndServices;
    }

}