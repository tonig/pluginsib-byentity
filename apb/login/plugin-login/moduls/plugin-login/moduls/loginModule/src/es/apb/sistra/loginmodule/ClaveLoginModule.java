package es.apb.sistra.loginmodule;

import java.security.Principal;
import java.security.acl.Group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

/**
 * Login module para clave basado en ticket.
 * 
 * 
 * Username={TICKET-sessionId} Password=ticket
 * 
 */
public class ClaveLoginModule extends UsernamePasswordLoginModule {

    /** Timeout ticket (segundos) */
    private long timeoutTicket;

    /** Purga ticket (minutos) */
    private int purgaTicket;

    /**
     * Principal customizado
     */
    private ApbPrincipal caller;

    /**
     * Role de acceso publico
     */
    private String roleTothom;

    /**
     * Datasource
     */
    private String datasource;

    /**
     * Inicializacion
     */
    @Override
    public void initialize(final Subject subject,
            final CallbackHandler callbackHandler, final Map sharedState,
            final Map options) {
        super.initialize(subject, callbackHandler, sharedState, options);
        roleTothom = (String) options.get("roleTothom");
        datasource = (String) options.get("datasource");
        timeoutTicket = Long.parseLong((String) options.get("timeoutTicket"));
        purgaTicket = Integer.parseInt((String) options.get("purgaTicket"));
    }

    /**
     * Login
     */
    @Override
    public boolean login() throws LoginException {
        // Comprobamos si esta habilitado UseFirstPass
        if (getUseFirstPass() == true) {
            return super.login();
        } else {
            return loginCertificate();
        }
    }

    public boolean loginCertificate() throws LoginException {
        log.debug("enter: login()");
        super.loginOk = false;

        // Obtenemos usuario y password
        final String[] userPass = this.getUsernameAndPassword();
        final String username = userPass[0]; // Usuario: {TICKET-sessionId}
        final String ticketClave = userPass[1]; // Password: ticket
        if (username == null || !username.startsWith("{TICKET-")) {
            return false;
        }

        // Obtenemos sesion id
        final String sesionId = username.substring("{TICKET-".length(),
                username.length() - 1);

        // Creamos principal
        try {
            caller = obtenerInfoSesionAutenticacion(sesionId, ticketClave);
        } catch (final Exception e) {
            log.error("Error creando ApbPrincipal a partir ticket", e);
            throw new LoginException(
                    "Error creando ApbPrincipal a partir ticket");
        }

        // Establecemos shared state
        if (getUseFirstPass() == true) {
            // Add authentication info to shared state map
            sharedState.put("javax.security.auth.login.name", caller.getName());
            sharedState.put("javax.security.auth.login.password", ticketClave);
        }

        // Damos login por realizado
        super.loginOk = true;
        log.debug("Login OK para " + caller.getName());
        return true;

    }

    @Override
    protected Principal getIdentity() {
        Principal identity = caller;
        if (identity == null)
            identity = super.getIdentity();
        return identity;
    }

    /**
     * No utilizada se sobreescribe login
     */
    @Override
    protected String getUsersPassword() throws LoginException {
        return null;
    }

    /**
     * Obtiene roles usuario (modificado para que no llame a createIdentity al
     * crear cada role)
     */
    @Override
    protected Group[] getRoleSets() throws LoginException {
        final Principal principal = getIdentity();

        if (!(principal instanceof ApbPrincipal)) {
            if (log.isTraceEnabled())
                log.trace("Principal " + principal + " not a ApbPrincipal");
            return new Group[0];
        }

        final String username = getUsername();

        List roles = null;
        try {
            roles = getUserRoles(username);
        } catch (final Exception e) {
            log.error("Excepcion obteniendo roles", e);
            throw new LoginException("Excepcion obteniendo roles");
        }

        final Group rolesGroup = new SimpleGroup("Roles");
        for (final Iterator iterator = roles.iterator(); iterator.hasNext();) {
            final String roleName = (String) iterator.next();
            rolesGroup.addMember(new SimplePrincipal(roleName));
        }
        final HashMap setsMap = new HashMap();
        setsMap.put("Roles", rolesGroup);

        // Montamos grupo "CallerPrincipal"
        final Group principalGroup = new SimpleGroup("CallerPrincipal");
        principalGroup.addMember(principal);
        setsMap.put("CallerPrincipal", principalGroup);

        // Devolvemos respuesta
        final Group roleSets[] = new Group[setsMap.size()];
        setsMap.values().toArray(roleSets);
        return roleSets;
    }

    /**
     * Obtiene roles asociados al usuario. En este caso serán accesos por
     * ciudadanos que tendrán el role tothom
     * 
     * @param username
     * @return
     */
    private List getUserRoles(final String username) {
        final List userRoles = new ArrayList();
        userRoles.add(roleTothom);
        return userRoles;
    }

    private ApbPrincipal obtenerInfoSesionAutenticacion(
            final String sesionIdTicket, final String ticket) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            conn = getConnection();

            // Purgamos sesiones caducadas
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, (purgaTicket * -1));
            ps = conn
                    .prepareStatement("DELETE FROM ZPE_TICKET WHERE (TCK_TICKET is not null AND TCK_FCULT is not null AND TCK_FCULT < ?) OR (TCK_TICKET is null AND TCK_FCSES < ?)");
            ps.setTimestamp(1,
                    new java.sql.Timestamp(calendar.getTimeInMillis()));
            ps.setTimestamp(2,
                    new java.sql.Timestamp(calendar.getTimeInMillis()));

            ps = conn
                    .prepareStatement("SELECT TCK_FCALTA, TCK_NIF, TCK_NOMAPE, TCK_NIVAUT, TCK_FCULT, TCK_NIFRPT, TCK_NOMRPT, TCK_AP1RPT, TCK_AP2RPT FROM ZPE_TICKET WHERE TCK_TICKET = ?");

            ps.setString(1, ticket);
            rs = ps.executeQuery();

            if (!rs.next()) {
                throw new LoginException(
                        "No se encuentra informacion de sesion");
            }

            final Date fechaTicket = rs.getTimestamp("TCK_FCALTA");
            final String nif = rs.getString("TCK_NIF");
            final String nombreCompleto = rs.getString("TCK_NOMAPE");
            final String metodoAutenticacion = rs.getString("TCK_NIVAUT");
            final Date fechaUltimoLogin = rs.getDate("TCK_FCULT");
            final String rpteNif = rs.getString("TCK_NIFRPT");
            final String rpteNom = rs.getString("TCK_NOMRPT");
            final String rpteAp1 = rs.getString("TCK_AP1RPT");
            final String rpteAp2 = rs.getString("TCK_AP2RPT");

            // Controlar Timeout (si es el primer login)
            if (fechaUltimoLogin == null) {
                final boolean timeout = (System.currentTimeMillis()
                        - fechaTicket.getTime() > (timeoutTicket * 1000L));
                if (timeout) {
                    throw new LoginException("El ticket ha caducado");
                }
            }

            // Marcamos como que se ha realizado login
            ps = conn
                    .prepareStatement("UPDATE ZPE_TICKET SET TCK_FCULT = ? WHERE TCK_TICKET = ?");
            ps.setTimestamp(1,
                    new java.sql.Timestamp(System.currentTimeMillis()));
            ps.setString(2, ticket);
            ps.execute();

            Representante r = null;
            if (rpteNif != null) {
                r = new Representante();
                r.setNif(rpteNif);
                r.setNombre(rpteNom);
                r.setApellido1(rpteAp1);
                r.setApellido2(rpteAp2);
            }

            return new ApbPrincipal(nif, nombreCompleto, nif,
                    metodoAutenticacion.charAt(0), r);

        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (final SQLException e) {
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (final SQLException e) {
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (final SQLException ex) {
                }
        }
    }

    private Connection getConnection() throws Exception {
        Connection conn;
        final InitialContext ctx = new InitialContext();
        final DataSource ds = (DataSource) ctx.lookup(datasource);
        conn = ds.getConnection();
        return conn;
    }

    @Override
    protected Principal createIdentity(final String username) throws Exception {
        return super.createIdentity(username);
    }

}
