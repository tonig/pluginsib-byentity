
package es.apb.sistra.plugins.firma.ws.apbfirma;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wPropiedadesError complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wPropiedadesError">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="propiedadError" type="{urn:es:apb:firma:ws:v1:firma}wPropiedadError" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wPropiedadesError", propOrder = {
    "propiedadError"
})
public class WPropiedadesError {

    @XmlElement(nillable = true)
    protected List<WPropiedadError> propiedadError;

    /**
     * Gets the value of the propiedadError property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propiedadError property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropiedadError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WPropiedadError }
     * 
     * 
     */
    public List<WPropiedadError> getPropiedadError() {
        if (propiedadError == null) {
            propiedadError = new ArrayList<WPropiedadError>();
        }
        return this.propiedadError;
    }

}
