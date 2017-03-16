package es.apb.login.negocio.model.login;

/**
 * Codigos error.
 * 
 * @author rsanz
 * 
 */
public enum ErrorCodes {

    /** Error general. */
    ERROR_GENERAL("errorGeneral"),
    /** Clave deshabilitado. */
    CLAVE_DESHABILITADO("deshabilitado");

    /**
     * Valor como string.
     */
    private final String stringValue;

    /**
     * Constructor.
     * 
     * @param value
     *            Valor como string.
     */
    private ErrorCodes(final String value) {
        stringValue = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return stringValue;
    }

    /**
     * Método para From string de la clase ErrorCodes.
     * 
     * @param text
     *            Parámetro text
     * @return el type autenticacion
     */
    public static ErrorCodes fromString(final String text) {
        ErrorCodes respuesta = null;
        if (text != null) {
            for (final ErrorCodes b : ErrorCodes.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }

}
