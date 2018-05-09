package es.sistra.clientcert.wsclient.afirma;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl");
		
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		IOUtils.copy(new FileInputStream("/rafa/Escritorio/kkk.xml"), bos);
		String xmlStr = new String(bos.toByteArray(),"UTF-8");
		
		Document xml = DocumentHelper.parseText(xmlStr);
		Element docXML = xml.getRootElement();
		docXML.add(new Namespace("val", "http://afirmaws/ws/validacion")); 		
		Node value;
		
		// Resultado validacion		
		value = docXML.selectSingleNode("/val:mensajeSalida/val:respuesta/val:ResultadoProcesamiento/val:ResultadoValidacion/val:resultado");
		if (value != null) {	
			System.out.println(value.getText());
		}
		
		/*
		value = docXML.selectSingleNode("/val:mensajeSalida/val:respuesta/val:ResultadoProcesamiento/val:InfoCertificado//*[contains(., 'clasificacion')]");
		if (value != null) {	
			System.out.println(value.getText());
		}
		*/
		
		value = docXML.selectSingleNode("/val:mensajeSalida/val:respuesta/val:ResultadoProcesamiento/val:InfoCertificado/val:Campo/val:idCampo[text()='clasificacion']/following-sibling::val:valorCampo");
		if (value != null) {	
			System.out.println(value.getText());
		}
		/*
		value = docXML.selectSingleNode("/val:mensajeSalida/val:respuesta/val:ResultadoProcesamiento/val:InfoCertificado/val:Campo/val:idCampo[text()='clasificacion']/following-sibling::val:valorCampo");
		if (value != null) {	
			System.out.println(value.getText());
		}
		*/
		
		System.out.println("clasificacion = " + getValorCampo(docXML, "clasificacion"));
		System.out.println("entidadSuscriptora = " + getValorCampo(docXML, "entidadSuscriptora"));
		System.out.println("NIFEntidadSuscriptora = " + getValorCampo(docXML, "NIFEntidadSuscriptora"));
		System.out.println("NIFResponsable = " + getValorCampo(docXML, "NIFResponsable"));
		
		
		System.out.println("fin");
	}
	
	private static String getValorCampo(Element docXML, String id) {
		String res = null;
		Node value = docXML.selectSingleNode("/val:mensajeSalida/val:respuesta/val:ResultadoProcesamiento/val:InfoCertificado/val:Campo/val:idCampo[text()='" + id + "']/following-sibling::val:valorCampo");
		if (value != null) {
			res = value.getText();
		}
		return res;
	}

}
