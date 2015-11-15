package nam.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import nam.model.SubNode;

import org.aries.adapter.BooleanAdapter;


/**
 * <p>Java class for TreeNode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TreeNode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subNode" type="{http://nam/ui}SubNode" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="element" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="data" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="folder" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="domain" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="expanded" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreeNode", namespace = "http://nam/ui", propOrder = {
    "subNodes"
})
@XmlRootElement(name = "treeNode", namespace = "http://nam/ui")
public class TreeNode
    implements Serializable
{

    private final static long serialVersionUID = 1L;

    @XmlElement(name = "subNode", namespace = "http://nam/ui")
    protected List<SubNode> subNodes;

    @XmlAttribute(name = "type")
    protected String type;

    @XmlAttribute(name = "label")
    protected String label;

    @XmlAttribute(name = "element")
    protected String element;

    @XmlAttribute(name = "data")
    protected String data;

    @XmlAttribute(name = "folder")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean folder;

    @XmlAttribute(name = "domain")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean domain;

    @XmlAttribute(name = "expanded")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean expanded;

    public TreeNode() {
    }
    
    /**
     * Gets the value of the subNodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subNodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubNodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubNode }
     * 
     * 
     */
    public List<SubNode> getSubNodes() {
        if (subNodes == null) {
            subNodes = new ArrayList<SubNode>();
        }
        return this.subNodes;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the element property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElement() {
        return element;
    }

    /**
     * Sets the value of the element property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElement(String value) {
        this.element = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(String value) {
        this.data = value;
    }

    /**
     * Gets the value of the folder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getFolder() {
        if (folder == null) {
            return new BooleanAdapter().unmarshal("false");
        } else {
            return folder;
        }
    }

    /**
     * Sets the value of the folder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolder(Boolean value) {
        this.folder = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getDomain() {
        if (domain == null) {
            return new BooleanAdapter().unmarshal("false");
        } else {
            return domain;
        }
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(Boolean value) {
        this.domain = value;
    }

    /**
     * Gets the value of the expanded property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getExpanded() {
        if (expanded == null) {
            return new BooleanAdapter().unmarshal("false");
        } else {
            return expanded;
        }
    }

    /**
     * Sets the value of the expanded property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpanded(Boolean value) {
        this.expanded = value;
    }

}
