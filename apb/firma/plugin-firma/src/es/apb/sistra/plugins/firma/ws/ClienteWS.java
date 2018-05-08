package es.apb.sistra.plugins.firma.ws;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.util.ws.client.WsClientSistraUtil;

/**
 * Cliente de WS para interfaz version 1 de firma
 * 
 */
public class ClienteWS {
	
	private static Log log = LogFactory.getLog(ClienteWS.class);
	
	public final static QName SERVICE_NAME = new QName("urn:es:apb:firma:ws:v1:firma", "FirmarService_v1_00");
    public final static QName PORT_NAME = new QName("urn:es:apb:firma:ws:v1:firma", "FirmaWebServiceImplPort");
	
	
	public static es.apb.sistra.plugins.firma.ws.apbfirma.FirmaWebService generarPort(String url,String user,String pass) throws Exception{
		javax.xml.ws.Service service =javax.xml.ws.Service.create(SERVICE_NAME); 
		service.addPort(PORT_NAME,javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING, url);
		es.apb.sistra.plugins.firma.ws.apbfirma.FirmaWebService port = service.getPort(PORT_NAME,es.apb.sistra.plugins.firma.ws.apbfirma.FirmaWebService.class);
          
		// Configura puerto
		WsClientSistraUtil.configurePort((BindingProvider)port,url,user,pass);
		
        return port;
	}	
	
}
