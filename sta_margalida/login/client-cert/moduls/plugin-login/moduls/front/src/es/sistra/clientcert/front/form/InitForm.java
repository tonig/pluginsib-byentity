package es.sistra.clientcert.front.form;

import org.apache.struts.validator.ValidatorForm;

public class InitForm extends ValidatorForm
{
	/** Url callback sistra (B64). */
	private String urlCallbackLogin;
	/** Url destino (B64). */
	private String urlDestino;
	/** Idioma. */
	private String idioma;
	
	public String getUrlCallbackLogin() {
		return urlCallbackLogin;
	}
	public void setUrlCallbackLogin(String urlCallbackLoginB64) {
		this.urlCallbackLogin = urlCallbackLoginB64;
	}
	public String getUrlDestino() {
		return urlDestino;
	}
	public void setUrlDestino(String urlDestinoB64) {
		this.urlDestino = urlDestinoB64;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
		
}
