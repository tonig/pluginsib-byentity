
package es.apb.sistra.plugins.firma.ws.apbfirma;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.apb.sistra.plugins.firma.ws.apbfirma package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ValidarResponse_QNAME = new QName("urn:es:apb:firma:ws:v1:firma", "validarResponse");
    private final static QName _ObtenerInfoCertificadoResponse_QNAME = new QName("urn:es:apb:firma:ws:v1:firma", "obtenerInfoCertificadoResponse");
    private final static QName _FirmarResponse_QNAME = new QName("urn:es:apb:firma:ws:v1:firma", "firmarResponse");
    private final static QName _ExcepcionWS_QNAME = new QName("urn:es:apb:firma:ws:v1:firma", "ExcepcionWS");
    private final static QName _FirmarRequest_QNAME = new QName("urn:es:apb:firma:ws:v1:firma", "firmarRequest");
    private final static QName _ValidarRequest_QNAME = new QName("urn:es:apb:firma:ws:v1:firma", "validarRequest");
    private final static QName _ObtenerInfoCertificadoRequest_QNAME = new QName("urn:es:apb:firma:ws:v1:firma", "obtenerInfoCertificadoRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.apb.sistra.plugins.firma.ws.apbfirma
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RespuestaValidar }
     * 
     */
    public RespuestaValidar createRespuestaValidar() {
        return new RespuestaValidar();
    }

    /**
     * Create an instance of {@link ObtenerInfoCertificadoResponse }
     * 
     */
    public ObtenerInfoCertificadoResponse createObtenerInfoCertificadoResponse() {
        return new ObtenerInfoCertificadoResponse();
    }

    /**
     * Create an instance of {@link PeticionObtenerInfoCertificado }
     * 
     */
    public PeticionObtenerInfoCertificado createPeticionObtenerInfoCertificado() {
        return new PeticionObtenerInfoCertificado();
    }

    /**
     * Create an instance of {@link FirmarRequest }
     * 
     */
    public FirmarRequest createFirmarRequest() {
        return new FirmarRequest();
    }

    /**
     * Create an instance of {@link WPropiedadesError }
     * 
     */
    public WPropiedadesError createWPropiedadesError() {
        return new WPropiedadesError();
    }

    /**
     * Create an instance of {@link FirmarResponse }
     * 
     */
    public FirmarResponse createFirmarResponse() {
        return new FirmarResponse();
    }

    /**
     * Create an instance of {@link ValidarRequest }
     * 
     */
    public ValidarRequest createValidarRequest() {
        return new ValidarRequest();
    }

    /**
     * Create an instance of {@link ValidarResponse }
     * 
     */
    public ValidarResponse createValidarResponse() {
        return new ValidarResponse();
    }

    /**
     * Create an instance of {@link PeticionValidar }
     * 
     */
    public PeticionValidar createPeticionValidar() {
        return new PeticionValidar();
    }

    /**
     * Create an instance of {@link ObtenerInfoCertificadoRequest }
     * 
     */
    public ObtenerInfoCertificadoRequest createObtenerInfoCertificadoRequest() {
        return new ObtenerInfoCertificadoRequest();
    }

    /**
     * Create an instance of {@link RespuestaObtenerInfoCertificado }
     * 
     */
    public RespuestaObtenerInfoCertificado createRespuestaObtenerInfoCertificado() {
        return new RespuestaObtenerInfoCertificado();
    }

    /**
     * Create an instance of {@link ExcepcionWS }
     * 
     */
    public ExcepcionWS createExcepcionWS() {
        return new ExcepcionWS();
    }

    /**
     * Create an instance of {@link WPropiedadError }
     * 
     */
    public WPropiedadError createWPropiedadError() {
        return new WPropiedadError();
    }

    /**
     * Create an instance of {@link PeticionFirmar }
     * 
     */
    public PeticionFirmar createPeticionFirmar() {
        return new PeticionFirmar();
    }

    /**
     * Create an instance of {@link RespuestaFirmar }
     * 
     */
    public RespuestaFirmar createRespuestaFirmar() {
        return new RespuestaFirmar();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:apb:firma:ws:v1:firma", name = "validarResponse")
    public JAXBElement<ValidarResponse> createValidarResponse(ValidarResponse value) {
        return new JAXBElement<ValidarResponse>(_ValidarResponse_QNAME, ValidarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerInfoCertificadoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:apb:firma:ws:v1:firma", name = "obtenerInfoCertificadoResponse")
    public JAXBElement<ObtenerInfoCertificadoResponse> createObtenerInfoCertificadoResponse(ObtenerInfoCertificadoResponse value) {
        return new JAXBElement<ObtenerInfoCertificadoResponse>(_ObtenerInfoCertificadoResponse_QNAME, ObtenerInfoCertificadoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FirmarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:apb:firma:ws:v1:firma", name = "firmarResponse")
    public JAXBElement<FirmarResponse> createFirmarResponse(FirmarResponse value) {
        return new JAXBElement<FirmarResponse>(_FirmarResponse_QNAME, FirmarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExcepcionWS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:apb:firma:ws:v1:firma", name = "ExcepcionWS")
    public JAXBElement<ExcepcionWS> createExcepcionWS(ExcepcionWS value) {
        return new JAXBElement<ExcepcionWS>(_ExcepcionWS_QNAME, ExcepcionWS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FirmarRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:apb:firma:ws:v1:firma", name = "firmarRequest")
    public JAXBElement<FirmarRequest> createFirmarRequest(FirmarRequest value) {
        return new JAXBElement<FirmarRequest>(_FirmarRequest_QNAME, FirmarRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:apb:firma:ws:v1:firma", name = "validarRequest")
    public JAXBElement<ValidarRequest> createValidarRequest(ValidarRequest value) {
        return new JAXBElement<ValidarRequest>(_ValidarRequest_QNAME, ValidarRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerInfoCertificadoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:apb:firma:ws:v1:firma", name = "obtenerInfoCertificadoRequest")
    public JAXBElement<ObtenerInfoCertificadoRequest> createObtenerInfoCertificadoRequest(ObtenerInfoCertificadoRequest value) {
        return new JAXBElement<ObtenerInfoCertificadoRequest>(_ObtenerInfoCertificadoRequest_QNAME, ObtenerInfoCertificadoRequest.class, null, value);
    }

}
