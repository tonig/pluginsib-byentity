package es.apb.login.negocio.model.login;

/**
 * Datos usuario.
 * 
 * @author indra
 * 
 */
public final class DatosUsuario {

    /** NIF. */
    private String nif;

    /** Nombre. */
    private String nombre;

    /** Apellidos. */
    private String apellidos;

    /** Nivel autenticacion. */
    private String nivelAutenticacion;

    /**
     * Gets the nif.
     * 
     * @return the nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * Sets the nif.
     * 
     * @param pNif
     *            the new nif
     */
    public void setNif(final String pNif) {
        nif = pNif;
    }

    /**
     * Gets the nombre.
     * 
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     * 
     * @param pNombre
     *            the new nombre
     */
    public void setNombre(final String pNombre) {
        nombre = pNombre;
    }

    /**
     * Gets the apellidos.
     * 
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Sets the apellidos.
     * 
     * @param pApellidos
     *            the new apellidos
     */
    public void setApellidos(final String pApellidos) {
        apellidos = pApellidos;
    }

    /**
     * Gets the nivel autenticacion.
     * 
     * @return the nivel autenticacion
     */
    public String getNivelAutenticacion() {
        return nivelAutenticacion;
    }

    /**
     * Sets the nivel autenticacion.
     * 
     * @param pNivelAutenticacion
     *            the new nivel autenticacion
     */
    public void setNivelAutenticacion(final String pNivelAutenticacion) {
        nivelAutenticacion = pNivelAutenticacion;
    }

}
