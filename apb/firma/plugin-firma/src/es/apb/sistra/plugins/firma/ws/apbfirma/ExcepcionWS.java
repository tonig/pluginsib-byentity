
package es.apb.sistra.plugins.firma.ws.apbfirma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExcepcionWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExcepcionWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoError" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mensajeError" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="detalleError" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="propiedadesError" type="{urn:es:apb:firma:ws:v1:firma}wPropiedadesError"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExcepcionWS", propOrder = {
    "codigoError",
    "mensajeError",
    "detalleError",
    "propiedadesError"
})
public class ExcepcionWS {

    @XmlElement(required = true, nillable = true)
    protected String codigoError;
    @XmlElement(required = true, nillable = true)
    protected String mensajeError;
    @XmlElement(required = true, nillable = true)
    protected String detalleError;
    @XmlElement(required = true, nillable = true)
    protected WPropiedadesError propiedadesError;

    /**
     * Gets the value of the codigoError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * Sets the value of the codigoError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoError(String value) {
        this.codigoError = value;
    }

    /**
     * Gets the value of the mensajeError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * Sets the value of the mensajeError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensajeError(String value) {
        this.mensajeError = value;
    }

    /**
     * Gets the value of the detalleError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetalleError() {
        return detalleError;
    }

    /**
     * Sets the value of the detalleError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetalleError(String value) {
        this.detalleError = value;
    }

    /**
     * Gets the value of the propiedadesError property.
     * 
     * @return
     *     possible object is
     *     {@link WPropiedadesError }
     *     
     */
    public WPropiedadesError getPropiedadesError() {
        return propiedadesError;
    }

    /**
     * Sets the value of the propiedadesError property.
     * 
     * @param value
     *     allowed object is
     *     {@link WPropiedadesError }
     *     
     */
    public void setPropiedadesError(WPropiedadesError value) {
        this.propiedadesError = value;
    }

}
