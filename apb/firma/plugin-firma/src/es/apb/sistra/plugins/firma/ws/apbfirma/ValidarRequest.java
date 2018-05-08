
package es.apb.sistra.plugins.firma.ws.apbfirma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validarRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validarRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="peticion" type="{urn:es:apb:firma:ws:v1:firma}peticionValidar" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validarRequest", propOrder = {
    "peticion"
})
public class ValidarRequest {

    protected PeticionValidar peticion;

    /**
     * Gets the value of the peticion property.
     * 
     * @return
     *     possible object is
     *     {@link PeticionValidar }
     *     
     */
    public PeticionValidar getPeticion() {
        return peticion;
    }

    /**
     * Sets the value of the peticion property.
     * 
     * @param value
     *     allowed object is
     *     {@link PeticionValidar }
     *     
     */
    public void setPeticion(PeticionValidar value) {
        this.peticion = value;
    }

}
