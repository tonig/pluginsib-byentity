package es.apb.firma.ws.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerId;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.Store;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfPKCS7;
import com.lowagie.text.pdf.PdfReader;

import es.apb.firma.ws.exception.WException;
import es.apb.firma.ws.model.ErrorFirmaWs;
import es.apb.firma.ws.v1.model.FormatoFirma;
import es.gob.afirma.signature.SignatureConstants;
import es.gob.afirma.signature.SignatureProperties;
import es.gob.afirma.signature.SigningException;
import es.gob.afirma.signature.cades.CadesSigner;
import es.gob.afirma.signature.pades.PadesSigner;
import es.gob.afirma.signature.xades.XadesSigner;
import es.gob.afirma.utils.UtilsBase64;

/**
 * Clase que incluye diversos ficheros de ayuda, como por ejemplo, obtener el
 * storeKey entre otros.
 * 
 * @author slromero
 * 
 */
public class IntegraUtils {

    /** Logger. **/
    private static final Logger logger = Logger.getLogger(IntegraUtils.class);

    /** Map de los certificados para cachear. **/
    private static Map<String, PrivateKeyEntry> certificatesPrivateKey = new HashMap<String, KeyStore.PrivateKeyEntry>();

    /**
     * Método para firmar mediante Cades un documento.
     * 
     * @param dataToSign
     *            Datos para firmar.
     * @param perfil
     *            El alias para el certificado
     * @return Devuelve la firma.
     * @throws SigningException
     *             signingException
     */
    public static final byte[] firmaCades(final byte[] dataToSign,
            final String perfil) throws SigningException {
        logger.debug("Firmando cades...");
        final CadesSigner cs = new CadesSigner();
        PrivateKeyEntry certificatePrivateKey;
        try {
            certificatePrivateKey = getCertificatePrivateKey(perfil);
        } catch (final Exception e) {
            throw new SigningException("No se puede acceder al certificado: "
                    + perfil, e);
        }

        final byte[] resultado = cs.sign(dataToSign,
                SignatureConstants.SIGN_ALGORITHM_SHA512WITHRSA,
                SignatureConstants.SIGN_MODE_EXPLICIT, certificatePrivateKey,
                null);
        logger.debug("Firmado cades");
        return resultado;
    }

    /**
     * Firma en xades.
     * 
     * @param dataToSign
     *            Datos para firmar.
     * @param perfil
     *            El alias para el certificado
     * @return Firma Devuelve la firma.
     * @throws SigningException
     *             signingException
     */
    public static byte[] firmaXades(final byte[] dataToSign, final String perfil)
            throws SigningException {

        // TODO PENDIENTE REVISAR. FALLA AL VALIDAR.

        logger.debug("Firma XADES...");

        final XadesSigner xadesSign = new XadesSigner();

        final Properties dataFormatProp = new Properties();
        dataFormatProp.put(
                SignatureProperties.XADES_DATA_FORMAT_DESCRIPTION_PROP,
                "Texto plano");
        dataFormatProp.put(SignatureProperties.XADES_DATA_FORMAT_ENCODING_PROP,
                "utf-8");
        dataFormatProp.put(SignatureProperties.XADES_DATA_FORMAT_MIME_PROP,
                "text/plain");

        PrivateKeyEntry certificatePrivateKey;
        try {
            certificatePrivateKey = getCertificatePrivateKey(perfil);
        } catch (final Exception e) {
            throw new SigningException("No se puede acceder al certificado: "
                    + perfil, e);
        }

        final byte[] signature = xadesSign.sign(dataToSign,
                SignatureConstants.SIGN_ALGORITHM_SHA512WITHRSA,
                // SignatureConstants.SIGN_ALGORITHM_SHA384WITHRSA,
                SignatureConstants.SIGN_FORMAT_XADES_DETACHED,
                certificatePrivateKey, dataFormatProp);

        logger.debug("Firmado xades");

        return signature;
    }

    /**
     * Crea firma PADES.
     * 
     * @param pDataToSign
     *            Datos a firmar
     * @param pAliasCertificado
     *            Alias certificado
     * @return firma
     * @throws SigningException
     */
    public static byte[] firmaPades(final byte[] pDataToSign,
            final String pAliasCertificado) throws SigningException {
        logger.debug("Firma PADES...");

        PrivateKeyEntry certificatePrivateKey;
        try {
            certificatePrivateKey = getCertificatePrivateKey(pAliasCertificado);
        } catch (final Exception e) {
            throw new SigningException("No se puede acceder al certificado: "
                    + pAliasCertificado, e);
        }

        final PadesSigner ps = new PadesSigner();
        final byte[] result = ps.sign(pDataToSign,
                SignatureConstants.SIGN_ALGORITHM_SHA512WITHRSA,
                SignatureConstants.SIGN_MODE_IMPLICIT, certificatePrivateKey,
                null);

        logger.debug("Firmado PADES...");
        return result;
    }

    /**
     * Obtiene formato firma segun valores afirma.
     * 
     * @param formato
     *            formato.
     * @return formato firma segun valores afirma.
     */
    public static String getFormatoFirma(final FormatoFirma formato) {
        String result = null;
        if (formato != null) {
            switch (formato) {
            case CADES_DETACHED:
                result = SignatureConstants.SIGN_FORMAT_CADES;
                break;
            case XADES_DETACHED:
                result = SignatureConstants.SIGN_FORMAT_XADES_DETACHED;
                break;
            case PADES:
                // result = SignatureConstants.SIGN_FORMAT_PADES;
                result = "PADES-BES";
                break;
            }
        }

        if (result == null) {
            throw new WException(ErrorFirmaWs.ERROR_FORMATO_FIRMA_NO_SOPORTADO,
                    "Formato de firma no soportado",
                    formato != null ? formato.name() : "nulo", null, null);
        }

        return result;
    }

    /**
     * Devuelve la storekey.
     * 
     * @param aliasCertificado
     *            (opcional)
     * @return Retrun
     * @throws Exception
     */
    private static PrivateKeyEntry getCertificatePrivateKey(
            final String aliasCertificado) throws Exception {

        PrivateKeyEntry result = null;

        if (certificatesPrivateKey.get(aliasCertificado) != null) {
            result = certificatesPrivateKey.get(aliasCertificado);
        } else {
            KeyStore.Entry key = null;

            final Configuracion configuracion = Configuracion.getInstance();

            final String keystoreName = configuracion
                    .getKeystoreName(aliasCertificado);
            final String keystoreType = configuracion
                    .getAlgorithm(aliasCertificado);
            final String keystorePwd = configuracion
                    .getPasswordKey(aliasCertificado);
            final String certificatePwd = configuracion
                    .getPasswordAlias(aliasCertificado);
            final String certificateAlias = configuracion
                    .getAlias(aliasCertificado);
            final String pathCertificados = configuracion.getPathCertificados();

            final InputStream is = new FileInputStream(pathCertificados
                    + keystoreName);

            final KeyStore ks = KeyStore.getInstance(keystoreType);
            final char[] passwordKeystore = keystorePwd.toCharArray();
            ks.load(is, passwordKeystore);

            final char[] passwordCert = certificatePwd.toCharArray();

            key = ks.getEntry(certificateAlias,
                    new KeyStore.PasswordProtection(passwordCert));

            result = (KeyStore.PrivateKeyEntry) key;

            certificatesPrivateKey.put(aliasCertificado, result);

        }

        return result;

    }

    /**
     * Busca certificado asociado al firmante.
     * 
     * @param certs
     *            Certs
     * @param signerId
     *            id signer
     * @return certificado
     */
    private static X509CertificateHolder getCertificateBySignerId(
            final Store certs, final SignerId signerId) {
        X509CertificateHolder res = null;
        for (final Iterator iterator = certs.getMatches(null).iterator(); iterator
                .hasNext();) {
            final X509CertificateHolder cert = (X509CertificateHolder) iterator
                    .next();
            if (signerId.match(cert)) {
                res = cert;
                break;
            }
        }
        return res;
    }

    /**
     * Obtiene certificado en B64 de la firma.
     * 
     * @param contenidoFirma
     *            firma
     * @param formatoFirma
     *            formato
     * @return certificado B64
     */
    public static String getCertificateFromSignature(
            final byte[] contenidoFirma, final FormatoFirma formatoFirma) {
        String certificateB64 = null;
        switch (formatoFirma) {
        case CADES_DETACHED:
            certificateB64 = getCertificateB64FromCades(contenidoFirma);
            break;
        case PADES:
            certificateB64 = getCertificateB64FromPades(contenidoFirma);
            break;
        default:
            // TODO PENDIENTE IMPLEMENTAR PARA XADES
            throw new WException(
                    ErrorFirmaWs.ERROR_FORMATO_FIRMA_NO_SOPORTADO,
                    "No se puede obtener certficado de la firma para formato de firma ",
                    formatoFirma.name(), null, null);
        }

        return certificateB64;
    }

    /**
     * Obtiene certificado con el que se ha firmado.
     * 
     * @param pContenidoFirma
     *            pdf firmado
     * @return certificado
     */
    private static String getCertificateB64FromPades(
            final byte[] pContenidoFirma) {
        try {
            final InputStream bis = new ByteArrayInputStream(pContenidoFirma);
            final PdfReader reader = new PdfReader(bis);
            final AcroFields fields = reader.getAcroFields();
            final ArrayList<String> names = fields.getSignatureNames();

            if (names != null && names.size() > 0) {
                final PdfPKCS7 pkcs7 = fields.verifySignature(names.get(0));
                final X509Certificate cert = pkcs7.getSigningCertificate();
                final String certB64 = UtilsBase64.encodeBytes(cert
                        .getEncoded());
                return certB64;
            } else {
                throw new Exception("No se ha encontrado firma");
            }

        } catch (final Exception ex) {
            throw new WException(ErrorFirmaWs.ERROR_FORMATO_FIRMA_NO_SOPORTADO,
                    "Error al obtener certificado de firma CADES ",
                    ex.getMessage(), null, ex);
        }
    }

    /**
     * Obtiene certificado en B64 de una firma CADES.
     * 
     * @param cadesSign
     *            firma cades
     * @return certificado en B64
     */
    public static String getCertificateB64FromCades(final byte[] cadesSign) {
        try {
            final CMSSignedData signedData = new CMSSignedData(cadesSign);
            final Store certs = signedData.getCertificates();
            final SignerInformationStore signersInfo = signedData
                    .getSignerInfos();
            final SignerInformation signerInformation = (SignerInformation) signersInfo
                    .getSigners().iterator().next();
            final X509CertificateHolder certificate = getCertificateBySignerId(
                    certs, signerInformation.getSID());
            final String certificateB64 = UtilsBase64.encodeBytes(certificate
                    .getEncoded());
            return certificateB64;
        } catch (final Exception ex) {
            throw new WException(ErrorFirmaWs.ERROR_FORMATO_FIRMA_NO_SOPORTADO,
                    "Error al obtener certificado de firma CADES ",
                    ex.getMessage(), null, ex);
        }
    }

}
