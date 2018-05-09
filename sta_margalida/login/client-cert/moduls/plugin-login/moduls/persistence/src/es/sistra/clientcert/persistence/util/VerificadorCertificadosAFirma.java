package es.sistra.clientcert.persistence.util;

import java.security.cert.X509Certificate;

import es.sistra.clientcert.model.RespuestaValidarCertificado;
import es.sistra.clientcert.wsclient.afirma.ClienteWS;

/**
 * Verificador de certificados contra AFirma.
 * @author rsanz
 *
 */
public class VerificadorCertificadosAFirma implements VerificadorCertificados{

	/**
	 * Valida certificado.
	 * @param configuracion Configuracion
	 * @param certificado Certificado
	 * @return respuesta validacion
	 */
	public RespuestaValidarCertificado validarCertificado(
			Configuracion configuracion, X509Certificate certificado) throws Exception {
		ClienteWS ws = ClienteWS.generarPort();
		RespuestaValidarCertificado resp = ws.validarCertificado(certificado);
		return resp;
	}

}
