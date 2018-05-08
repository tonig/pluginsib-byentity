
package es.apb.sistra.plugins.firma.ws.apbfirma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtenerInfoCertificadoRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtenerInfoCertificadoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="peticion" type="{urn:es:apb:firma:ws:v1:firma}peticionObtenerInfoCertificado" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerInfoCertificadoRequest", propOrder = {
    "peticion"
})
public class ObtenerInfoCertificadoRequest {

    protected PeticionObtenerInfoCertificado peticion;

    /**
     * Gets the value of the peticion property.
     * 
     * @return
     *     possible object is
     *     {@link PeticionObtenerInfoCertificado }
     *     
     */
    public PeticionObtenerInfoCertificado getPeticion() {
        return peticion;
    }

    /**
     * Sets the value of the peticion property.
     * 
     * @param value
     *     allowed object is
     *     {@link PeticionObtenerInfoCertificado }
     *     
     */
    public void setPeticion(PeticionObtenerInfoCertificado value) {
        this.peticion = value;
    }

}
