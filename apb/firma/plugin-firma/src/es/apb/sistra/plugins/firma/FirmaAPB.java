package es.apb.sistra.plugins.firma;

import es.caib.sistra.plugins.firma.FirmaIntf;

/**
 * Objeto firma APB
 * 
 * @author rsanz
 * 
 */
public class FirmaAPB implements FirmaIntf {

    /**
     * Objeto firma.
     */
    private final byte[] signature;

    /**
     * Formato firma.
     */
    private final String formato;

    /**
     * Nif.
     */
    private final String nif;

    /**
     * Nombre apellidos.
     */
    private final String nombreApellidos;

    /**
     * Certificado B64.
     */
    private final String certificadoBase64;

    /**
     * Nif representante.
     */
    private final String nifRepresentante;

    /**
     * Nombre apellidos representante.
     */
    private final String nombreApellidosRepresentante;

    /**
     * Constructor.
     * 
     * @param signature
     *            firma
     * @param formato
     *            formato firma
     * @param nif
     *            nif firmante
     * @param nombreApellidos
     *            nombre apellidos firmante
     */
    public FirmaAPB(final byte[] signature, final String formato,
            final String nif, final String nombreApellidos,
            final String certBase64, final String nifRepresentante,
            final String nombreApellidosRepresentante) {
        super();
        this.signature = signature;
        this.formato = formato;
        this.nif = nif;
        this.nombreApellidos = nombreApellidos;
        this.certificadoBase64 = certBase64;
        this.nifRepresentante = nifRepresentante;
        this.nombreApellidosRepresentante = nombreApellidosRepresentante;
    }

    /** {@inheritDoc} */
    @Override
    public String getFormatoFirma() {
        return formato;
    }

    /** {@inheritDoc} */
    @Override
    public String getNif() {
        return nif;
    }

    /** {@inheritDoc} */
    @Override
    public String getNombreApellidos() {
        return nombreApellidos;
    }

    /** {@inheritDoc} */
    @Override
    public byte[] getContenidoFirma() {
        return signature;
    }

    /** {@inheritDoc} */
    @Override
    public String getNifRepresentante() {
        return nifRepresentante;
    }

    /** {@inheritDoc} */
    @Override
    public String getNombreApellidosRepresentante() {
        return nombreApellidosRepresentante;
    }

    /** Devuelve cert en B64 */
    public String getCertificadoB64() {
        return certificadoBase64;
    }

}
