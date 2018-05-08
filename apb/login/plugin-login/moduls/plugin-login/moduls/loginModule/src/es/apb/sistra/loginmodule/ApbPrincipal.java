package es.apb.sistra.loginmodule;

import java.io.Serializable;
import java.security.Principal;

public class ApbPrincipal implements Principal, Serializable {

    private final String user;
    private String nombreCompleto;
    private final String nif;
    private char metodoAutenticacion;
    private Representante representante;

    public ApbPrincipal(final String user, final String nombreCompleto,
            final String nif, final char metodoAutenticacion,
            final Representante representante) {
        this.user = user;
        this.nombreCompleto = nombreCompleto;
        this.nif = nif;
        this.metodoAutenticacion = metodoAutenticacion;
        this.representante = representante;
    }

    public ApbPrincipal(final String user, final String nombreCompleto,
            final String nif, final char metodoAutenticacion) {
        this.user = user;
        this.nombreCompleto = nombreCompleto;
        this.nif = nif;
        this.metodoAutenticacion = metodoAutenticacion;
        this.representante = null;
    }

    public String getName() {
        return user;
    }

    public char getMetodoAutenticacion() {
        return metodoAutenticacion;
    }

    public void setMetodoAutenticacion(final char metodoAutenticacion) {
        this.metodoAutenticacion = metodoAutenticacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(final String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNif() {
        return nif;
    }

    @Override
    public boolean equals(final Object another) {
        if (!(another instanceof Principal))
            return false;
        final String anotherName = ((Principal) another).getName();
        boolean equals = false;
        if (getName() == null)
            equals = anotherName == null;
        else
            equals = getName().equals(anotherName);
        return equals;
    }

    @Override
    public int hashCode() {
        return (getName() == null ? 0 : getName().hashCode());
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * @return the representante
     */
    public Representante getRepresentante() {
        return representante;
    }

    /**
     * @param pRepresentante
     *            the representante to set
     */
    public void setRepresentante(final Representante pRepresentante) {
        representante = pRepresentante;
    }

}
