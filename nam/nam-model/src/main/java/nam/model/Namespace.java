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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Namespace", namespace = "http://nam/model", propOrder = {
    "schema",
    "filePath",
    "description",
    "imports",
    "annotations",
    "properties",
    "typesAndEnumerationsAndElements"
})
@XmlRootElement(name = "namespace", namespace = "http://nam/model")
public class Namespace implements Serializable
{

    private final static long serialVersionUID = 1L;
    
    @XmlElement(namespace = "http://nam/model")
    protected String schema;
    
    @XmlElement(namespace = "http://nam/model")
    protected String filePath;
    
    @XmlElement(namespace = "http://nam/model")
    protected String description;
    
    @XmlElement(namespace = "http://nam/model")
    protected List<Namespace> imports;
    
    @XmlElement(name = "annotation", namespace = "http://nam/model")
    protected List<Annotation> annotations;
    
    @XmlElement(namespace = "http://nam/model")
    protected Properties properties;
    @XmlElements({
        @XmlElement(name = "type", namespace = "http://nam/model", type = Type.class),
        @XmlElement(name = "enumeration", namespace = "http://nam/model", type = Enumeration.class),
        @XmlElement(name = "element", namespace = "http://nam/model", type = Element.class),
        @XmlElement(name = "cache", namespace = "http://nam/model", type = Cache.class),
        @XmlElement(name = "query", namespace = "http://nam/model", type = Query.class)
    })
    protected List<Serializable> typesAndEnumerationsAndElements;
    
    @XmlAttribute(name = "id")
    protected Long id;
    
    @XmlAttribute(name = "name")
    protected String name;
    
    @XmlAttribute(name = "label")
    protected String label;
    
    @XmlAttribute(name = "prefix")
    protected String prefix;
    
    @XmlAttribute(name = "uri")
    protected String uri;
    
    @XmlAttribute(name = "version")
    protected String version;
    
    @XmlAttribute(name = "global")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean global;
    
    @XmlAttribute(name = "imported")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean imported;
    
    @XmlAttribute(name = "included")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean included = true;
    
    @XmlAttribute(name = "exported")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean exported;

    /**
     * Gets the value of the schema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the value of the schema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchema(String value) {
        this.schema = value;
    }

    /**
     * Gets the value of the filePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the value of the filePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilePath(String value) {
        this.filePath = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the imports property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the imports property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImports().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Namespace }
     * 
     * 
     */
    public List<Namespace> getImports() {
        if (imports == null) {
            imports = new ArrayList<Namespace>();
        }
        return this.imports;
    }

    /**
     * Gets the value of the annotations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the annotations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnnotations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Annotation }
     * 
     * 
     */
    public List<Annotation> getAnnotations() {
        if (annotations == null) {
            annotations = new ArrayList<Annotation>();
        }
        return this.annotations;
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
     * Gets the value of the typesAndEnumerationsAndElements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the typesAndEnumerationsAndElements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTypesAndEnumerationsAndElements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Type }
     * {@link Enumeration }
     * {@link Element }
     * {@link Cache }
     * {@link Query }
     * 
     * 
     */
    public List<Serializable> getTypesAndEnumerationsAndElements() {
        if (typesAndEnumerationsAndElements == null) {
            typesAndEnumerationsAndElements = new ArrayList<Serializable>();
        }
        return this.typesAndEnumerationsAndElements;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
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
     * Gets the value of the prefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the value of the prefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefix(String value) {
        this.prefix = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the global property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getGlobal() {
        if (global == null) {
            return new BooleanAdapter().unmarshal("true");
        } else {
            return global;
        }
    }

    /**
     * Sets the value of the global property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobal(Boolean value) {
        this.global = value;
    }

    /**
     * Gets the value of the imported property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getImported() {
        return imported;
    }

    /**
     * Sets the value of the imported property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImported(Boolean value) {
        this.imported = value;
    }

    /**
     * Gets the value of the included property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getIncluded() {
        return included;
    }

    /**
     * Sets the value of the included property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncluded(Boolean value) {
        this.included = value;
    }

    /**
     * Gets the value of the exported property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getExported() {
        return exported;
    }

    /**
     * Sets the value of the exported property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExported(Boolean value) {
        this.exported = value;
    }

    
    public String toString() {
    	return uri;
    }

}

