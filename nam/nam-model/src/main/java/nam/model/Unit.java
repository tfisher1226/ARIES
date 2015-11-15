package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Unit", namespace = "http://nam/model", propOrder = {
    "name",
    "source",
    "adapter",
    "namespace",
    "transacted",
    "elements",
    "queries",
    "properties",
    "includesAndImports"
})
@XmlRootElement(name = "unit", namespace = "http://nam/model")
public class Unit implements Serializable
{

    private final static long serialVersionUID = 1L;
    
    @XmlElement(namespace = "http://nam/model")
    protected String name;
    
    @XmlElement(namespace = "http://nam/model")
    protected Source source;
    
    @XmlElement(namespace = "http://nam/model")
    protected Adapter adapter;
    
    @XmlElement(namespace = "http://nam/model")
    protected Namespace namespace;
    
    @XmlElement(namespace = "http://nam/model")
    protected Transacted transacted;
    
    @XmlElement(namespace = "http://nam/model")
    protected Elements elements;
    
    @XmlElement(namespace = "http://nam/model")
    protected List<Query> queries;
    
    @XmlElement(namespace = "http://nam/model")
    protected Properties properties;
    @XmlElements({
        @XmlElement(name = "include", namespace = "http://nam/model", type = Include.class),
        @XmlElement(name = "import", namespace = "http://nam/model", type = Import.class)
    })
    protected List<Serializable> includesAndImports;
    
    @XmlAttribute(name = "ref")
    protected String ref;
    
    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link Source }
     *     
     */
    public Source getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link Source }
     *     
     */
    public void setSource(Source value) {
        this.source = value;
    }

    /**
     * Gets the value of the adapter property.
     * 
     * @return
     *     possible object is
     *     {@link Adapter }
     *     
     */
    public Adapter getAdapter() {
        return adapter;
    }

    /**
     * Sets the value of the adapter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Adapter }
     *     
     */
    public void setAdapter(Adapter value) {
        this.adapter = value;
    }

    /**
     * Gets the value of the namespace property.
     * 
     * @return
     *     possible object is
     *     {@link Namespace }
     *     
     */
    public Namespace getNamespace() {
        return namespace;
    }

    /**
     * Sets the value of the namespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link Namespace }
     *     
     */
    public void setNamespace(Namespace value) {
        this.namespace = value;
    }

    /**
     * Gets the value of the transacted property.
     * 
     * @return
     *     possible object is
     *     {@link Transacted }
     *     
     */
    public Transacted getTransacted() {
        return transacted;
    }

    /**
     * Sets the value of the transacted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transacted }
     *     
     */
    public void setTransacted(Transacted value) {
        this.transacted = value;
    }

    /**
     * Gets the value of the elements property.
     * 
     * @return
     *     possible object is
     *     {@link Elements }
     *     
     */
    public Elements getElements() {
        return elements;
    }

    /**
     * Sets the value of the elements property.
     * 
     * @param value
     *     allowed object is
     *     {@link Elements }
     *     
     */
    public void setElements(Elements value) {
        this.elements = value;
    }

    /**
     * Gets the value of the queries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the queries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQueries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Query }
     * 
     * 
     */
    public List<Query> getQueries() {
        if (queries == null) {
            queries = new ArrayList<Query>();
        }
        return this.queries;
    }

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link Properties }
     *     
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link Properties }
     *     
     */
    public void setProperties(Properties value) {
        this.properties = value;
    }

    /**
     * Gets the value of the includesAndImports property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the includesAndImports property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncludesAndImports().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Include }
     * {@link Import }
     * 
     * 
     */
    public List<Serializable> getIncludesAndImports() {
        if (includesAndImports == null) {
            includesAndImports = new ArrayList<Serializable>();
        }
        return this.includesAndImports;
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

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
