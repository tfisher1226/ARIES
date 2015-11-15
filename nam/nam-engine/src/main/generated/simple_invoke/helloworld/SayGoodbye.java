
package simple_invoke.helloworld;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayGoodbye", propOrder = {
    "toWhom",
    "toME"
})
public class SayGoodbye {

    protected String toWhom;
    protected String toME;

    /**
     * Gets the value of the toWhom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToWhom() {
        return toWhom;
    }

    /**
     * Sets the value of the toWhom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToWhom(String value) {
        this.toWhom = value;
    }

    /**
     * Gets the value of the toME property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToME() {
        return toME;
    }

    /**
     * Sets the value of the toME property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToME(String value) {
        this.toME = value;
    }

}
