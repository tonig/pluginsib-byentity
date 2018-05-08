
package es.apb.sistra.plugins.firma.ws.apbfirma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for firmarRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="firmarRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="peticion" type="{urn:es:apb:firma:ws:v1:firma}peticionFirmar" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "firmarRequest", propOrder = {
    "peticion"
})
public class FirmarRequest {

    protected PeticionFirmar peticion;

    /**
     * Gets the value of the peticion property.
     * 
     * @return
     *     possible object is
     *     {@link PeticionFirmar }
     *     
     */
    public PeticionFirmar getPeticion() {
        return peticion;
    }

    /**
     * Sets the value of the peticion property.
     * 
     * @param value
     *     allowed object is
     *     {@link PeticionFirmar }
     *     
     */
    public void setPeticion(PeticionFirmar value) {
        this.peticion = value;
    }

}
