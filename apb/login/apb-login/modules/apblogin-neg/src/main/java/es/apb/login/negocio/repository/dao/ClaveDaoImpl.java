package es.apb.login.negocio.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import es.apb.login.negocio.exception.NoExisteSesionException;
import es.apb.login.negocio.model.comun.ConstantesNumero;
import es.apb.login.negocio.model.login.DatosRepresentante;
import es.apb.login.negocio.model.login.DatosSesion;
import es.apb.login.negocio.model.login.DatosUsuario;
import es.apb.login.negocio.model.login.TicketClave;
import es.apb.login.negocio.model.login.TicketDatos;
import es.apb.login.negocio.repository.model.TicketSesion;
import es.apb.login.negocio.repository.model.TicketSesionExterna;
import es.apb.login.negocio.util.GeneradorId;

/**
 * Interfaz de acceso a base de datos para los datos del ticket de acceso clave.
 * 
 * @author Indra
 * 
 */
@Repository("claveDao")
public final class ClaveDaoImpl implements ClaveDao {

    /** EntityManager. */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String crearSesion(final String pUrlCallback,
            final String pUrlDestino, final String idioma, final String idps,
            final boolean sistra) {
        String idTicket = null;

        if (sistra) {
            // Crea sesion para Sistra
            final TicketSesion ticketSistra = new TicketSesion();
            ticketSistra.setFechaInicioSesion(new Date());
            ticketSistra.setUrlCallback(pUrlCallback);
            ticketSistra.setUrlDestino(pUrlDestino);
            ticketSistra.setIdioma(idioma);
            ticketSistra.setIdps(idps);
            entityManager.persist(ticketSistra);
            idTicket = ticketSistra.getId().toString();
        } else {
            // Crea sesion para aplicacion externa
            final TicketSesionExterna ticketExterna = new TicketSesionExterna();
            ticketExterna.setFechaInicioSesion(new Date());
            ticketExterna.setUrlCallback(pUrlCallback);
            ticketExterna.setIdioma(idioma);
            ticketExterna.setIdps(idps);
            entityManager.persist(ticketExterna);
            idTicket = ticketExterna.getId().toString();
        }

        return idTicket;
    }

    @Override
    public DatosSesion obtenerDatosSesion(final String idSesion,
            final boolean sistra) {
        final DatosSesion ds = new DatosSesion();
        if (sistra) {
            final TicketSesion ticket = entityManager.find(TicketSesion.class,
                    Long.parseLong(idSesion));
            if (ticket == null) {
                throw new NoExisteSesionException();
            }
            ds.setFechaInicioSesion(ticket.getFechaInicioSesion());
            ds.setIdioma(ticket.getIdioma());
            ds.setIdps(ticket.getIdps());
            ds.setFechaTicket(ticket.getFechaTicket());
        } else {
            final TicketSesionExterna ticket = entityManager.find(
                    TicketSesionExterna.class, Long.parseLong(idSesion));
            if (ticket == null) {
                throw new NoExisteSesionException();
            }
            ds.setFechaInicioSesion(ticket.getFechaInicioSesion());
            ds.setIdioma(ticket.getIdioma());
            ds.setIdps(ticket.getIdps());
            ds.setFechaTicket(ticket.getFechaTicket());
        }
        return ds;
    }

    @Override
    public TicketClave generateTicket(final String idSesion, final String idp,
            final String pNif, final String pNombre, final String pApellidos,
            final DatosRepresentante representante, final boolean sistra) {
        TicketClave ticket;
        if (sistra) {
            String nivelAutenticacion = "U";
            if ("aFirma".equalsIgnoreCase(idp)) {
                nivelAutenticacion = "C";
            }
            ticket = generaTicketSistra(idSesion, nivelAutenticacion, pNif,
                    pNombre, pApellidos, representante);
        } else {
            ticket = generaTicketExterna(idSesion, idp, pNif, pNombre,
                    pApellidos, representante);
        }
        return ticket;
    }

    /**
     * Genera ticket para sistra.
     * 
     * @param idSesion
     *            id sesion
     * @param pNivelAutenticacion
     *            nivel autenticacion (C: certificado / U: usuario)
     * @param pNif
     *            Nif
     * @param pNombre
     *            Nombre
     * @param pApellidos
     *            Apelllidos
     * @param pRepresentante
     * @return Ticket
     */
    private TicketClave generaTicketSistra(final String idSesion,
            final String pNivelAutenticacion, final String pNif,
            final String pNombre, final String pApellidos,
            final DatosRepresentante pRepresentante) {
        // Recupera sesion
        final TicketSesion ticket = entityManager.find(TicketSesion.class,
                Long.parseLong(idSesion));
        if (ticket == null) {
            throw new NoExisteSesionException();
        }

        // - Nombre: apellidos nombre (formato FNMT)
        final String nombreCompleto = StringUtils.defaultString(pApellidos)
                + (pApellidos != null ? " " : "") + pNombre;

        // Genera ticket y almacena en sesion
        final String ticketId = GeneradorId.generarId();
        ticket.setTicket(ticketId);
        ticket.setFechaTicket(new Date());
        ticket.setNif(pNif);
        ticket.setNombreApellidos(nombreCompleto);
        ticket.setNivelAutenticacion(pNivelAutenticacion);
        if (pRepresentante != null) {
            ticket.setRepresentanteNif(pRepresentante.getNif());
            ticket.setRepresentanteNombre(pRepresentante.getNombre());
            ticket.setRepresentanteApellido1(pRepresentante.getApellido1());
            ticket.setRepresentanteApellido2(pRepresentante.getApellido2());
        }

        entityManager.merge(ticket);

        final TicketClave respuesta = new TicketClave();
        respuesta.setTicket(ticketId);
        respuesta.setUrlCallback(ticket.getUrlCallback());
        respuesta.setIdioma(ticket.getIdioma());
        return respuesta;
    }

    /**
     * Genera ticket para aplicacion externa.
     * 
     * @param idSesion
     *            id sesion
     * @param pIdp
     *            nivel autenticacion (C: certificado / U: usuario)
     * @param pNif
     *            Nif
     * @param pNombre
     *            Nombre
     * @param pApellidos
     *            Apelllidos
     * @param pRepresentante
     *            representante
     * @return Ticket
     */
    private TicketClave generaTicketExterna(final String idSesion,
            final String pIdp, final String pNif, final String pNombre,
            final String pApellidos, final DatosRepresentante pRepresentante) {
        // Recupera sesion
        final TicketSesionExterna ticket = entityManager.find(
                TicketSesionExterna.class, Long.parseLong(idSesion));
        if (ticket == null) {
            throw new NoExisteSesionException();
        }

        // Genera ticket y almacena en sesion
        final String ticketId = GeneradorId.generarId();
        ticket.setTicket(ticketId);
        ticket.setFechaTicket(new Date());
        ticket.setNif(pNif);
        ticket.setNombre(pNombre);
        ticket.setApellidos(pApellidos);
        ticket.setIdp(pIdp);
        if (pRepresentante != null) {
            ticket.setRepresentanteNif(pRepresentante.getNif());
            ticket.setRepresentanteNombre(pRepresentante.getNombre());
            ticket.setRepresentanteApellido1(pRepresentante.getApellido1());
            ticket.setRepresentanteApellido2(pRepresentante.getApellido2());
        }

        entityManager.merge(ticket);

        final TicketClave respuesta = new TicketClave();
        respuesta.setTicket(ticketId);
        respuesta.setUrlCallback(ticket.getUrlCallback());
        respuesta.setIdioma(ticket.getIdioma());
        return respuesta;
    }

    @Override
    public TicketDatos consumirTicketSesionExterna(final String pTicket) {

        TicketDatos t = null;

        final Query query = entityManager
                .createQuery("Select p From TicketSesionExterna p Where p.ticket = :ticket");
        query.setParameter("ticket", pTicket);

        final List<TicketSesionExterna> results = query.getResultList();

        if (results != null && results.size() > 0) {
            // Obtenemos ticket
            final TicketSesionExterna tck = results.get(0);
            // Lo borramos para que no se pueda volver a usar
            entityManager.remove(tck);
            // Devolvemos datos ticket
            t = new TicketDatos();
            t.setFecha(tck.getFechaTicket());

            final DatosUsuario du = new DatosUsuario();
            du.setNivelAutenticacion(tck.getIdp());
            du.setNif(tck.getNif());
            du.setNombre(tck.getNombre());
            du.setApellidos(tck.getApellidos());

            if (tck.getRepresentanteNif() != null) {
                final DatosRepresentante dr = new DatosRepresentante();
                dr.setNif(tck.getRepresentanteNif());
                dr.setNombre(tck.getRepresentanteNombre());
                dr.setApellido1(tck.getRepresentanteApellido1());
                dr.setApellido2(tck.getRepresentanteApellido2());
                du.setRepresentante(dr);
            }

            t.setUsuario(du);

        }

        return t;
    }

    @Override
    public void purgaTicketSesionExterna(final long pTimeoutSesion,
            final long pTimeoutTicket) {
        // Recupera lista tickets aplicaciones externas
        final List<TicketSesionExterna> listaTickets = entityManager
                .createQuery("SELECT t FROM TicketSesionExterna t")
                .getResultList();
        final Date ahora = new Date();
        for (final TicketSesionExterna t : listaTickets) {
            if (t.getTicket() == null) {
                if (ahora.getTime()
                        - (t.getFechaInicioSesion().getTime() + (pTimeoutSesion * ConstantesNumero.N1000)) > 0) {
                    entityManager.remove(t);
                }
            } else {
                if (ahora.getTime()
                        - (t.getFechaTicket().getTime() + (pTimeoutSesion * ConstantesNumero.N1000)) > 0) {
                    entityManager.remove(t);
                }
            }
        }
    }

}
