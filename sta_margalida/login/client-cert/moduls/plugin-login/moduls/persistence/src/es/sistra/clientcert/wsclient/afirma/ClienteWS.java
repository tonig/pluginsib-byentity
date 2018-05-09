package es.sistra.clientcert.wsclient.afirma;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;

import es.caib.util.ws.client.ClientPasswordCallback;
import es.sistra.clientcert.model.RespuestaValidarCertificado;
import es.sistra.clientcert.persistence.util.Configuracion;
import es.sistra.clientcert.wsclient.afirma.services.ValidacionService;
import es.sistra.clientcert.wsclient.afirma.services.ValidacionWS;

/**
 * Cliente de WS para validacion certificado contra AFirma.
 * 
 */
public class ClienteWS {
	
	 /** Ws validacion*/
    private ValidacionWS validacionWS;
    
    /** Log. */
    private static Log log = LogFactory.getLog(ClienteWS.class);
	
    /**
     * Genera cliente.
     * @return Cliente
     * @throws Exception excepcion
     */
	public static ClienteWS generarPort() throws Exception{
		
		// Obtenemos props 
		String url = Configuracion.getInstance().getProperty("login.clientCert.afirma.url") + "/ValidarCertificado";		
		
		javax.xml.ws.Service service =javax.xml.ws.Service.create(ValidacionService.SERVICE); 
		service.addPort(ValidacionService.SERVICE,javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING, url);
		ValidacionWS port = service.getPort(ValidacionService.SERVICE,ValidacionWS.class);
          
		// Configura puerto para log
		configurePort((BindingProvider)port);
		
		
		ClienteWS cliente = new ClienteWS();
		cliente.validacionWS = port;
		
        return cliente;
	}

	/**
	 * Configura port para acceder.
	 * @param port port
	 * @throws Exception Excepcion
	 */
	private static void configurePort(BindingProvider port) throws Exception {
		
		Properties props = new Properties();
		
		props.put("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
		props.put("org.apache.ws.security.crypto.merlin.keystore.type", Configuracion.getInstance().getProperty("login.clientCert.afirma.keystore.type"));
		props.put("org.apache.ws.security.crypto.merlin.file", Configuracion.getInstance().getProperty("login.clientCert.afirma.keystore.file"));
		props.put("org.apache.ws.security.crypto.merlin.keystore.password", Configuracion.getInstance().getProperty("login.clientCert.afirma.keystore.password"));
		
		String aliasCertificate = Configuracion.getInstance().getProperty("login.clientCert.afirma.keystore.certificate.alias");
		String passwdCertificate = Configuracion.getInstance().getProperty("login.clientCert.afirma.keystore.certificate.password");
		
		String trustedKeystorePath = Configuracion.getInstance().getProperty("login.clientCert.afirma.trustedkeystore.file");
		String trustedKeystorePass = Configuracion.getInstance().getProperty("login.clientCert.afirma.trustedkeystore.password");
		String trustedKeystoreType = Configuracion.getInstance().getProperty("login.clientCert.afirma.trustedkeystore.type");
		
		
				
		final Client client = ClientProxy.getClient(port);
		final Endpoint cxfEndpoint = client.getEndpoint();
		final HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
		
		// Configuracion validacion HTTPS
		TLSClientParameters tlsParams = new TLSClientParameters();
	    tlsParams.setDisableCNCheck(true);
	     
	    KeyStore keyStore = KeyStore.getInstance(trustedKeystoreType);
	    String trustpass = trustedKeystorePass;
	
	    File truststore = new File(trustedKeystorePath);
	    keyStore.load(new FileInputStream(truststore), trustpass.toCharArray());
	    TrustManagerFactory trustFactory = 
	    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	    trustFactory.init(keyStore);
	    TrustManager[] tm = trustFactory.getTrustManagers();
	    tlsParams.setTrustManagers(tm);
	    
	    httpConduit.setTlsClientParameters(tlsParams);
		
		// Configuracion firma peticiones soap
		final Map<String, Object> outProps = new HashMap<String, Object>();
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE);
        outProps.put(WSHandlerConstants.SIG_KEY_ID, "DirectReference");
        outProps.put("properties", props);
        outProps.put(WSHandlerConstants.SIG_PROP_REF_ID, "properties");
        outProps.put(WSHandlerConstants.MUST_UNDERSTAND, "false");
        outProps.put(WSHandlerConstants.SIG_KEY_ID, "DirectReference");
        outProps.put(WSHandlerConstants.USER, aliasCertificate);
        outProps.put(WSHandlerConstants.PW_CALLBACK_REF, new ClientPasswordCallback(passwdCertificate));
        
        final WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
        cxfEndpoint.getOutInterceptors().add(wssOut);
        cxfEndpoint.getOutInterceptors().add(new SAAJOutInterceptor());
        
        cxfEndpoint.getInInterceptors().add(new LoggingInInterceptor());
        cxfEndpoint.getOutInterceptors().add(new LoggingOutInterceptor());
        
	}

	
	/**
	 * Valida certificado.
	 * @param certificado certificado
	 * @return respuesta validacion
	 */
	public RespuestaValidarCertificado validarCertificado(X509Certificate certificado) throws Exception {
		
		// Construimos peticion
		String applicationId = Configuracion.getInstance().getProperty("login.clientCert.afirma.applicationId");
		String modoValidacion = Configuracion.getInstance().getProperty("login.clientCert.afirma.modoValidacion");
		String certB64 = new String(Base64.encodeBase64(certificado.getEncoded()));
		
		String xmlRequest;
		xmlRequest =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?><mensajeEntrada xmlns=\"https://afirmaws/ws/validacion\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:SchemaLocation=\"https://_XXX_/afirmaws/mvalidacion/ws.xsd\">";
		xmlRequest += "<peticion>ValidarCertificado</peticion>";
		xmlRequest += "<versionMsg>1.0</versionMsg>";
		xmlRequest += "<parametros>";
		xmlRequest += "	<modoValidacion>" +  modoValidacion + "</modoValidacion>";
		xmlRequest += "	<obtenerInfo>true</obtenerInfo>";
		xmlRequest += "<idAplicacion>" + applicationId + "</idAplicacion><certificado>" + certB64 + "</certificado></parametros>";
		xmlRequest += "</mensajeEntrada>";
				
		// Invocamos a AFirma
		String xmlResponse = validacionWS.validarCertificado(xmlRequest);
		
		// Extrae resultado del xml obtenido
		RespuestaValidarCertificado res = extraerResultado(xmlResponse);
		return res;
	}

	/**
	 * Extre resultado validacion del xml.
	 * @param xmlResponse xml
	 * @return resultado
	 * @throws Exception exception
	 */
	private RespuestaValidarCertificado extraerResultado(String xmlResponse) throws Exception {
		
		Document xml = DocumentHelper.parseText(xmlResponse);
		Element docXML = xml.getRootElement();
		docXML.add(new Namespace("val", "http://afirmaws/ws/validacion")); 		
		Node value;
		
		// Resultado validacion
		boolean resultadoValidacion = false;
		value = docXML.selectSingleNode("/val:mensajeSalida/val:respuesta/val:ResultadoProcesamiento/val:ResultadoValidacion/val:resultado");		
		if (value != null) {			
			resultadoValidacion = "0".equals(value.getText());
		}
		
		// En caso de obtener validacion correcta, extrae nif y nombre
		// Solo permitimos persona fisica o juridica
		String nif = null;
		String nombre = null;
		String apellidos = null;
		String nombreCompleto = null;
		if (resultadoValidacion) {
			// Obtenemos tipo de clasificacion
			String clasificacion = getValorCampoInfoCertificado(docXML,"clasificacion");
			if (StringUtils.isEmpty(clasificacion)) {
				throw new Exception("No se ha devuelto clasificacion certificado");
			}
			
			int clasificacionInt = Integer.parseInt(clasificacion);
			switch (clasificacionInt) {
			case 0: //- Certs persona fisica
			case 5: //- Certs funcionario
				nif = getValorCampoInfoCertificado(docXML,"NIFResponsable");
				nombre = getValorCampoInfoCertificado(docXML,"nombreResponsable");
				apellidos = getValorCampoInfoCertificado(docXML,"ApellidosResponsable");
		        // - Nombre: apellidos nombre (formato FNMT)
		        nombreCompleto = org.apache.commons.lang.StringUtils.defaultString(apellidos)
		                + (apellidos != null ? " " : "") + nombre;
		        log.debug("Datos obtenidos @firma: Nombre=" + nombreCompleto);
				break;
			case 1: //- Certs persona juridica
			case 6: //- Certs entidades sin personalidad juridica
				nif = getValorCampoInfoCertificado(docXML,"NIF-CIF");
				nombreCompleto = getValorCampoInfoCertificado(docXML,"razonSocial");
				break;
			default:
				// Si no son de los tipos anteriores, validacion erronea
				resultadoValidacion = false;
				break;
			}						
		}
		
		
		// Si resultado validacion es correcta, verificamos que tenga valor el nif y nombre
		if (resultadoValidacion) {
			if (StringUtils.isEmpty(nif)) {
				throw new Exception("No se ha podido obtener nif asociado al certificado");
			}
			if (StringUtils.isEmpty(nombre)) {
				throw new Exception("No se ha podido obtener nombre asociado al certificado");
			}
		}
		
		RespuestaValidarCertificado result = new RespuestaValidarCertificado();
		result.setValido(resultadoValidacion);
		result.setNif(nif);
		result.setNombre(nombreCompleto);
		
		return result;
				
	}
	
	/**
	 * Obtiene dato del certificado del xml de respuesta.
	 * @param docXML XML
	 * @param id id dato
	 * @return valor dato
	 */
	private String getValorCampoInfoCertificado(Element docXML, String id) {
		String res = null;
		Node value = docXML.selectSingleNode("/val:mensajeSalida/val:respuesta/val:ResultadoProcesamiento/val:InfoCertificado/val:Campo/val:idCampo[text()='" + id + "']/following-sibling::val:valorCampo");
		if (value != null) {
			res = value.getText();
		}
		return res;
	}
		
}
