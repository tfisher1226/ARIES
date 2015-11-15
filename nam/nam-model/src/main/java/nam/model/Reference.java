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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.aries.adapter.BooleanAdapter;


/**
 * <p>Java class for Reference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Reference">
 *   &lt;complexContent>
 *     &lt;extension base="{http://nam/model}Field">
 *       &lt;sequence>
 *         &lt;element name="itemType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="relation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="binding" type="{http://www.w3.org/2001/XMLSchema}string" default="id" />
 *       &lt;attribute name="contained" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="mappedBy" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="twoWay" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="inverse" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="restriction" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="index" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reference", namespace = "http://nam/model", propOrder = {
    "itemTypes"
})
@XmlSeeAlso({
    MapItem.class,
    SetItem.class,
    ListItem.class,
    Item.class
})
public class Reference
    extends Field
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "itemType", namespace = "http://nam/model")
    protected List<String> itemTypes;
    @XmlAttribute(name = "relation")
    protected String relation;
    @XmlAttribute(name = "binding")
    protected String binding;
    @XmlAttribute(name = "contained")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean contained;
    @XmlAttribute(name = "mappedBy")
    protected String mappedBy;
    @XmlAttribute(name = "twoWay")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean twoWay;
    @XmlAttribute(name = "inverse")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean inverse;
    @XmlAttribute(name = "restriction")
    protected String restriction;
    @XmlAttribute(name = "source")
    protected String source;
    @XmlAttribute(name = "index")
    protected String index;
    @XmlAttribute(name = "ref")
    protected String ref;

    /**
     * Gets the value of the itemTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getItemTypes() {
        if (itemTypes == null) {
            itemTypes = new ArrayList<String>();
        }
        return this.itemTypes;
    }

    /**
     * Gets the value of the relation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelation() {
        return relation;
    }

    /**
     * Sets the value of the relation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelation(String value) {
        this.relation = value;
    }

    /**
     * Gets the value of the binding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBinding() {
        if (binding == null) {
            return "id";
        } else {
            return binding;
        }
    }

    /**
     * Sets the value of the binding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBinding(String value) {
        this.binding = value;
    }

    /**
     * Gets the value of the contained property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getContained() {
        if (contained == null) {
            return new BooleanAdapter().unmarshal("false");
        } else {
            return contained;
        }
    }

    /**
     * Sets the value of the contained property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContained(Boolean value) {
        this.contained = value;
    }

    /**
     * Gets the value of the mappedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMappedBy() {
        return mappedBy;
    }

    /**
     * Sets the value of the mappedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMappedBy(String value) {
        this.mappedBy = value;
    }

    /**
     * Gets the value of the twoWay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getTwoWay() {
        if (twoWay == null) {
            return new BooleanAdapter().unmarshal("false");
        } else {
            return twoWay;
        }
    }

    /**
     * Sets the value of the twoWay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTwoWay(Boolean value) {
        this.twoWay = value;
    }

    /**
     * Gets the value of the inverse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getInverse() {
        if (inverse == null) {
            return new BooleanAdapter().unmarshal("false");
        } else {
            return inverse;
        }
    }

    /**
     * Sets the value of the inverse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInverse(Boolean value) {
        this.inverse = value;
    }

    /**
     * Gets the value of the restriction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestriction() {
        return restriction;
    }

    /**
     * Sets the value of the restriction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestriction(String value) {
        this.restriction = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndex(String value) {
        this.index = value;
    }

    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRef(String value) {
        this.ref = value;
    }

}
