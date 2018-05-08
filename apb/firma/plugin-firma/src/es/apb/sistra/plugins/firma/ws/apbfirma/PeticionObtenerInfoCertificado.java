
package es.apb.sistra.plugins.firma.ws.apbfirma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for peticionObtenerInfoCertificado complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="peticionObtenerInfoCertificado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="certificadoB64" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contenidoFirma" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="formatoFirma" type="{urn:es:apb:firma:ws:v1:firma}formatoFirma" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "peticionObtenerInfoCertificado", propOrder = {
    "certificadoB64",
    "contenidoFirma",
    "formatoFirma"
})
public class PeticionObtenerInfoCertificado {

    protected String certificadoB64;
    protected byte[] contenidoFirma;
    protected FormatoFirma formatoFirma;

    /**
     * Gets the value of the certificadoB64 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificadoB64() {
        return certificadoB64;
    }

    /**
     * Sets the value of the certificadoB64 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificadoB64(String value) {
        this.certificadoB64 = value;
    }

    /**
     * Gets the value of the contenidoFirma property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContenidoFirma() {
        return contenidoFirma;
    }

    /**
     * Sets the value of the contenidoFirma property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContenidoFirma(byte[] value) {
        this.contenidoFirma = ((byte[]) value);
    }

    /**
     * Gets the value of the formatoFirma property.
     * 
     * @return
     *     possible object is
     *     {@link FormatoFirma }
     *     
     */
    public FormatoFirma getFormatoFirma() {
        return formatoFirma;
    }

    /**
     * Sets the value of the formatoFirma property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormatoFirma }
     *     
     */
    public void setFormatoFirma(FormatoFirma value) {
        this.formatoFirma = value;
    }

}
