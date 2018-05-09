package es.sistra.clientcert.model;

/**
 * Respuesta validar certificado.
 * @author rsanz
 *
 */
public class RespuestaValidarCertificado {
	
	/** Indica si es valido. */
	private boolean valido;
	
	/** Nif. */
	private String nif;
	
	/** Nombre. */
	private String nombre;

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
