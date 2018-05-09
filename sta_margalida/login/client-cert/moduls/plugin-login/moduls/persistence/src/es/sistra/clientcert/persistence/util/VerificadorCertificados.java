package es.sistra.clientcert.persistence.util;

import java.security.cert.X509Certificate;

import es.sistra.clientcert.model.RespuestaValidarCertificado;

/**
 * Verificador de certificados.
 * @author rsanz
 *
 */
public interface VerificadorCertificados {

	/**
	 * Valida certificado.
	 * @param configuracion Configuracion
	 * @param certificado Certificado
	 * @return respuesta validacion
	 */
	RespuestaValidarCertificado validarCertificado(Configuracion configuracion, X509Certificate certificado) throws Exception;
	
}
