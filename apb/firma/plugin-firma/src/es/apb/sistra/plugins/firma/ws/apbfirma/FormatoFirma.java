package es.apb.sistra.plugins.firma.ws.apbfirma;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for formatoFirma.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="formatoFirma">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CADES_DETACHED"/>
 *     &lt;enumeration value="XADES_DETACHED"/>
 *     &lt;enumeration value="PADES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "formatoFirma")
@XmlEnum
public enum FormatoFirma {

    CADES_DETACHED, XADES_DETACHED, PADES;

    public String value() {
        return name();
    }

    public static FormatoFirma fromValue(final String v) {
        return valueOf(v);
    }

}
