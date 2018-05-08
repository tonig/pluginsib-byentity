package es.apb.sistra.loginmodule;

import java.io.Serializable;

public class Representante implements Serializable {

    private String nif;
    private String nombre;
    private String apellido1;
    private String apellido2;

    /**
     * @return the nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * @param pNif
     *            the nif to set
     */
    public void setNif(final String pNif) {
        nif = pNif;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param pNombre
     *            the nombre to set
     */
    public void setNombre(final String pNombre) {
        nombre = pNombre;
    }

    /**
     * @return the apellido1
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * @param pApellido1
     *            the apellido1 to set
     */
    public void setApellido1(final String pApellido1) {
        apellido1 = pApellido1;
    }

    /**
     * @return the apellido2
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * @param pApellido2
     *            the apellido2 to set
     */
    public void setApellido2(final String pApellido2) {
        apellido2 = pApellido2;
    }

}
