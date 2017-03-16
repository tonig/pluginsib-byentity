package es.apb.login.negocio.util;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * Validacion nif.
 * 
 * @author Indra
 */
public class NifCif {

    public static final int DOCUMENTO_NO_VALIDO = -1;
    public static final int TIPO_DOCUMENTO_NIF = 1;
    public static final int TIPO_DOCUMENTO_CIF = 2;
    public static final int TIPO_DOCUMENTO_NIE = 3;
    public static final int TIPO_DOCUMENTO_NSS = 4;

    // public static String LETRAS_ESPECIAL = "ABCDEFGHIJ";
    // public static String SIN_NIF_ESPECIAL = "[K|L][0-9]{7}[" +
    // LETRAS_ESPECIAL + "]{1}";

    public static String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";
    public static String SIN_NIF = "[K|L|M]?[0-9]{0,8}[" + LETRAS + "]{1}";
    public static String SIN_CIAS = "[0-9]{0,13}[" + LETRAS + "]{1}";
    public static String SIN_CIF = "[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}";
    // public static String SIN_NIE =
    // "^\\s*[X|Y][\\/|\\s\\-]?[0-9]{1,8}[\\/|\\s\\-]?[A-Z]{1}\\s*$";
    public static String SIN_NIE = "[X|Y][0-9]{1,8}[A-Z]{1}";
    public static String SIN_SS = "^\\s*[0-9]{2}[\\/|\\s\\-]?[0-9]{7,8}[\\/|\\s\\-]?[0-9]{2}\\s*$";

    public static HashMap sociedad = new HashMap();

    public NifCif() {
    }

    static {
        sociedad.put("A", "Sociedades An�nimas");
        sociedad.put("B", "Sociedades de responsabilidad limitada");
        sociedad.put("C", "Sociedades colectivas");
        sociedad.put("D", "Sociedades comanditarias");
        sociedad.put("E", "Comunidades de bienes");
        sociedad.put("F", "Sociedades cooperativas");
        sociedad.put("G", "Asociaciones y otros tipos no definidos");
        sociedad.put("H", "Comunidades de propietarios");
        sociedad.put("J", "Sociedades Civiles con o sin personalidad jur�dica");
        sociedad.put("K",
                "seguramente para compatibilidad con formatos antiguos");
        sociedad.put("L",
                "seguramente para compatibilidad con formatos antiguos");
        sociedad.put("M",
                "seguramente para compatibilidad con formatos antiguos");
        sociedad.put("N", "Entidades extranjeras");
        sociedad.put("P", "Corporaciones locales");
        sociedad.put("Q", "Organismos aut�nomos");
        sociedad.put("R", "Congregaciones e instituciones religiosas");
        sociedad.put("S", "Organos de la administraci�n");
        sociedad.put("U", "Uniones temporales de Empresas");
        sociedad.put("V", "Otros tipos no definidos");
        sociedad.put("X", "Extranjeros");
        sociedad.put("Y", "Extranjeros");
    }

    /**
     * Obtiene los d�gitos de una cadena
     * 
     * @param valor
     * @return
     */
    private static String getDigitos(final String cadena) {
        String digitos = new String();

        for (int i = 0; i < cadena.length(); i++) {
            final char c = cadena.substring(i, i + 1).toCharArray()[0];
            if (Character.isDigit(c))
                digitos += c;
        }

        return digitos;
    }

    /**
     * Obtiene las letras de una cadena
     * 
     * @param valor
     * @return
     */
    private static String getLetras(final String cadena) {
        String letras = new String();

        for (int i = 0; i < cadena.length(); i++) {
            final char c = cadena.substring(i, i + 1).toCharArray()[0];
            if (Character.isLetter(c))
                letras += c;
        }

        return letras;
    }

    /**
     * Determina si es un NIF
     * 
     * @param valor
     * @return
     */
    public static boolean esNIF(String valor) {
        try {
            if (!Pattern.matches(SIN_NIF, valor))
                return false;

            if (valor.startsWith("K") || valor.startsWith("L")
                    || valor.startsWith("M")) {
                valor = "0" + valor.substring(1);
            }

            final String letra = getLetras(valor);
            final String letraNIF = getLetraNIF(getDigitos(valor));
            if (letraNIF == null)
                return false;

            return letraNIF.equals(letra);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getLetraNIF(final String DNINumerico) {
        final int DNI = Integer.parseInt(DNINumerico, 10);
        int baremo;

        final String letras = LETRAS;
        baremo = DNI % 23;
        /*
         * if (baremo == 0) return null;
         */
        return letras.substring(baremo, baremo + 1);

    }

    /**
     * Determina si es un CIF
     * 
     * @param valor
     * @return
     */
    public static boolean esCIF(final String valor) {
        try {
            // valor = valor.replaceAll("[\\/\\s\\-]", "" );
            if (!Pattern.matches(SIN_CIF, valor))
                return false;

            final String codigoControl = valor.substring(valor.length() - 1,
                    valor.length());

            final int[] v1 = {0, 2, 4, 6, 8, 1, 3, 5, 7, 9};
            final String[] v2 = {"J", "A", "B", "C", "D", "E", "F", "G", "H",
                    "I"};
            int suma = 0;

            for (int i = 2; i <= 6; i += 2) {
                suma += v1[Integer.parseInt(valor.substring(i - 1, i))];
                suma += Integer.parseInt(valor.substring(i, i + 1));
            }

            suma += v1[Integer.parseInt(valor.substring(7, 8))];

            suma = (10 - (suma % 10));

            if (suma == 10)
                suma = 0;

            if (codigoControl.equals("" + suma)
                    || codigoControl.toUpperCase().equals(v2[suma]))
                return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean esNIE(final String valor) {
        try {
            if (!Pattern.matches(SIN_NIE, valor))
                return false;
            // valor = valor.replaceAll("[\\/\\s\\-]", "" );
            return esNIEDC(valor);
        } catch (final Exception exc) {
            exc.printStackTrace();
        }
        return false;
    }

    private static boolean esNIEDC(final String valor) {
        if (!Pattern.matches("^[X|Y]{1}[0-9]{1,8}[" + LETRAS + "]{1}$", valor)) {
            return false;
        }
        String numero = valor.replaceAll("[a-zA-Z]", "");

        if (valor.startsWith("Y"))
            numero = "1" + numero;
        if (valor.startsWith("Z"))
            numero = "2" + numero;

        final String letra = valor.substring(1).replaceAll("[^a-z^A-Z]", "");
        if (!letra.equals(getLetraNIF(numero))) {
            return false;
        }
        return true;
    }

    public static boolean esNumeroSeguridadSocial(String valor) {
        try {
            if (!Pattern.matches(SIN_SS, valor))
                return false;
            valor = valor.replaceAll("[\\/\\s\\-]", "");
            return esNumeroSSDC(valor);
        } catch (final Exception exc) {
            exc.printStackTrace();
        }
        return false;
    }

    private static boolean esNumeroSSDC(final String valor) {
        if (!Pattern.matches("^[0-9]{11,12}$", valor)) {
            return false;
        }

        /*
         * if ( valor.charAt(0) == '0' ) { valor = valor.substring( 1 ); }
         */

        final String part1 = valor.substring(0, 2);
        String part2 = valor.substring(2, valor.length() - 2);
        final String part3 = valor.substring(valor.length() - 2);

        if (part2.length() == 8 && part2.charAt(0) == '0') {
            part2 = part2.substring(1);
        }

        final String numero = part1 + part2;
        final String dc = part3;

        // int iNumero = Integer.parseInt( numero, 10 );
        // int iDc = Integer.parseInt( dc, 10 );
        final long iNumero = Long.parseLong(numero, 10);
        final long iDc = Long.parseLong(dc, 10);

        if (iNumero % 97 != iDc) {
            return false;
        }

        // return nuevaValidacionSS( valor );
        return true;
    }

    public static int validaDocumento(final String valor) {
        if (esNIF(valor))
            return TIPO_DOCUMENTO_NIF;
        if (esCIF(valor))
            return TIPO_DOCUMENTO_CIF;
        if (esNIE(valor))
            return TIPO_DOCUMENTO_NIE;
        if (esNumeroSeguridadSocial(valor))
            return TIPO_DOCUMENTO_NSS;
        return DOCUMENTO_NO_VALIDO;
    }

    /**
     * Normaliza documento de identificaci�n pasando a mayusculas y quitando
     * espacios,/,\ y -
     * 
     * @param doc
     * @return
     */
    public static String normalizarDocumento(final String nif) {
        String doc = null;
        if (nif != null) {
            // Quitamos espacios y otros caracteres
            doc = nif.toUpperCase();
            doc = doc.replaceAll("[\\/\\s\\-]", "");
            // Rellenamos con 0
            if (doc.length() == 0) {
                return "";
            }
            final String primerCaracter = doc.substring(0, 1);
            if (Pattern.matches("[^A-Z]", primerCaracter)) {
                // Es nif
                doc = StringUtils.leftPad(doc, 9, '0');
            } else {
                // es cif o nie
                final String letraInicio = doc.substring(0, 1);
                final String resto = doc.substring(1);
                doc = letraInicio + StringUtils.leftPad(resto, 8, '0');
            }
        }
        return doc;
    }

    /**
     * Determina si es un CIAS. Es como un NIF pero con 13 digitos
     * 
     * @param valor
     * @return
     */
    public static boolean esCIAS(final String valor) {
        try {
            if (!Pattern.matches(SIN_CIAS, valor))
                return false;

            final String letra = getLetras(valor);

            final String letraNIF = getLetraNIF(getDigitos(valor));
            if (letraNIF == null)
                return false;

            return letraNIF.equals(letra);

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
