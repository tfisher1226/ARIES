//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.01.18 at 01:45:53 AM PST 
//


package org.aries.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aries.Assert;
import org.aries.common.Sequence;


/**
 * <p>Java class for message complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="message">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="header" type="{http://www.aries.org/message}Header" minOccurs="0"/>
 *         &lt;element name="parts">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "message", namespace = "http://www.aries.org/message", propOrder = {
    "type",
    "header",
    "parts"
})
@XmlRootElement(name = "message")
public class Message
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected String type;
    protected Header header;
    @XmlElement(required = true)
    protected Message.Parts parts;

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
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link Header }
     *     
     */
    public Header getHeader() {
    	if (header == null)
    		header = new Header();
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link Header }
     *     
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the parts property.
     * 
     * @return
     *     possible object is
     *     {@link Message.Parts }
     *     
     */
    public Message.Parts getParts() {
        return parts;
    }

    /**
     * Sets the value of the parts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Message.Parts }
     *     
     */
    public void setParts(Message.Parts value) {
        this.parts = value;
    }

    /**
     * Gets the value of the correlationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Object getCorrelationId() {
    	return getHeader().getCorrelationId();
    }

    /**
     * Sets the value of the correlationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrelationId(Object correlationId) {
    	getHeader().setCorrelationId(correlationId);
    }
    
    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entries"
    })
    public static class Parts
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlElement(name = "entry")
        protected List<Message.Parts.Entry> entries;

        /**
         * Gets the value of the entries property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entries property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntries().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Message.Parts.Entry }
         * 
         * 
         */
        public List<Message.Parts.Entry> getEntries() {
            if (entries == null) {
                entries = new ArrayList<Message.Parts.Entry>();
            }
            return this.entries;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry
            implements Serializable
        {

            private final static long serialVersionUID = 1L;
            protected String key;
            protected Object value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKey(String value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setValue(Object value) {
                this.value = value;
            }

            
        	@Override
        	public boolean equals(Object object) {
        		if (object == null)
        			return false;
        		if (!object.getClass().isAssignableFrom(this.getClass()))
        			return false;
        		Entry other = (Entry) object;
        		if (this.getKey() == null || other.getKey() == null)
        			return false;
        		if (this.getValue() == null || other.getValue() == null)
        			return false;
        		if (!this.getKey().equals(other.getKey()))
        			return false;
        		if (!this.getValue().equals(other.getValue()))
        			return false;
        		return true;
        	}
        }

    }

    
    
	public static String DEFAULT_PART = "defaultPart";

	public static String DEFAULT_RESULT = "defaultResult";
	
    
	@SuppressWarnings("unchecked")
	public <T> T getPart(String key) {
		String type = getEntry_Type(key);
		if (type == null)
			return (T) getPartAsItem(key);
		return (T) getList(key);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getPartAsItem(String key) {
		List<Message.Parts.Entry> list = getParts().getEntries();
		Iterator<Message.Parts.Entry> iterator = list.iterator();
		while (iterator.hasNext()) {
			Message.Parts.Entry entry = (Message.Parts.Entry) iterator.next();
			if (entry.getKey().equals(key))
				return (T) entry.getValue();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key) {
		String type = getEntry_Type(key);
		if (type != null) {
			Sequence itemSequence = getEntry_Sequence(key);
			if (type.equals("java.util.ArrayList")) {
				List<T> list = new ArrayList<T>();
				List<T> items = (List<T>) itemSequence.getItems();
				Iterator<T> iterator = items.iterator();
				while (iterator.hasNext()) {
					T item = iterator.next();
					list.add(item);
				}
				return list;
			}
		}
		return null;
	}

	protected String getEntry_Type(String key) {
		return getPartAsItem(key+"Type");
	}
	
	protected Sequence getEntry_Sequence(String key) {
		return getPartAsItem(key+"Sequence");
	}
	
	public boolean containsPart(String key) {
		Object value = getPart(key);
		return value != null;
	}

//	public void addPart(String key, Object value) {
//		Message.Parts.Entry entry = new Message.Parts.Entry();
//		entry.setKey(key);
//		entry.setValue(value);
//		getOrCreateParts().getEntries().add(entry);
//	}
	
	protected void addEntry_Type(String key, Object value) {
		addPart(key+"Type", value.getClass().getCanonicalName());
	}

	protected void addEntry_Sequence(String key, Object value) {
		addPart(key+"Sequence", createSequence(value));
	}

	protected Sequence createSequence(Object value) {
		return createSequence((Collection<?>) value);
	}
	
	protected Sequence createSequence(Collection<?> collection) {
		Sequence sequence = new Sequence();
		Iterator<?> iterator = collection.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Object item = iterator.next();
			sequence.getItems().add(item);
		}
		return sequence;
	}

	public void addPart(String key, Object value) {
		if (Collection.class.isInstance(value)) {
			addEntry_Type(key, value);
			addEntry_Sequence(key, value);
		} else {
			addEntry(key, value);
		}
	}

	protected void addEntry(String key, Object value) {
		Message.Parts.Entry entry = new Message.Parts.Entry();
		entry.setKey(key);
		entry.setValue(value);
		getOrCreateParts().getEntries().add(entry);
	}
	
	public <T> T removePart(String key) {
		T value = getPart(key);
		removeEntry(key, value);
		return value;
	}
	
	protected void removeEntry(String key, Object value) {
		Message.Parts.Entry entry = new Message.Parts.Entry();
		entry.setKey(key);
		entry.setValue(value);
		getOrCreateParts().getEntries().remove(entry);
	}
	
	protected Parts getOrCreateParts() {
		Parts parts = getParts();
		if (parts == null) {
			parts = new Parts();
			setParts(parts);
		}
		return parts;
	}
	
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Message.Parts.Entry> list = getOrCreateParts().getEntries();
		Iterator<Message.Parts.Entry> iterator = list.iterator();
		while (iterator.hasNext()) {
			Message.Parts.Entry entry = (Message.Parts.Entry) iterator.next();
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	@Override
	public String toString() {
		//TODO make this better
		return "Message:"+getOrCreateParts().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Message other = (Message) object;
		if (this.getParts() == null || other.getParts() == null)
			return false;
		//if (!this.getParts().equals(other.getParts()))
		if (!partsEqual(this, other))
			return false;
		return true;
	}

	protected boolean partsEqual(Message message0, Message message1) {
		return partsEqual(message1.getParts(), message1.getParts());
	}
	
	protected boolean partsEqual(Parts parts0, Parts parts1) {
		if (parts0.getEntries().size() == parts1.getEntries().size()) {
			int count = parts0.getEntries().size();
			for (int i=0; i < count; i++) {
				Message.Parts.Entry entry0 = parts0.getEntries().get(i);
				Message.Parts.Entry entry1 = parts1.getEntries().get(i);
				if (!entry0.getKey().equals(entry1.getKey()))
					return false;
				if (!entry0.getValue().equals(entry1.getValue()))
					return false;
			}
			return true;
		}
		return false;
	}
	
	
}