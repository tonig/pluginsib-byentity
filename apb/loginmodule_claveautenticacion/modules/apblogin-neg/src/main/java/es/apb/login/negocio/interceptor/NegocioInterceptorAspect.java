package es.apb.login.negocio.interceptor;
 
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import es.apb.login.negocio.exception.DatabaseException;
import es.apb.login.negocio.exception.ErrorNoControladoException;
import es.apb.login.negocio.exception.ServiceException;
import es.apb.login.negocio.exception.ServiceRollbackException;
import es.apb.login.negocio.model.comun.ConstantesNumero;

/**
 * Lógica de logging de las excepciones de la capa de servicios.
 * 
 * Las excepciones de la capa de datos serán descendientes de
 * org.springframework.dao.DataAccessException y se encapsularan en una
 * excepción DatabaseException. El resto serán descendientes de
 * ServiceNoRollbackException o ServiceRollbackException, excepto las runtime:
 * nullpointer, etc. que se encapsularan en una excepción
 * ErrorNoControladoException.
 * 
 * 
 * @author Indra
 */
@Aspect
@Order(ConstantesNumero.N1)
@Service
public final class NegocioInterceptorAspect {

    /**
     * Log del interceptor.
     */
    private final Logger log = LoggerFactory
            .getLogger(NegocioInterceptorAspect.class);

    /**
     * Intercepta invocacion a metodo.
     * 
     * @param jp
     *            JointPoint
     */
    @Before("@annotation(es.apb.login.negocio.interceptor.NegocioInterceptor)")
    public void processInvocation(final JoinPoint jp) {
    }

    /**
     * Intercepta retorno de un metodo.
     * 
     * @param jp
     *            JointPoint
     * @param retVal
     *            Objeto retornado
     */
    @AfterReturning(pointcut = "@annotation(es.apb.login.negocio.interceptor.NegocioInterceptor)", returning = "retVal")
    public void processReturn(final JoinPoint jp, final Object retVal) {
    }

    /**
     * Intercepta excepcion y realiza auditoria.
     * 
     * @param jp
     *            JoinPoint
     * @param ex
     *            Exception
     */
    @AfterThrowing(pointcut = "@annotation(es.apb.login.negocio.interceptor.NegocioInterceptor)", throwing = "ex")
    public void processException(final JoinPoint jp, final Throwable ex) {

        // Audita excepcion (recupera en caso necesario si se debe encapsular la
        // excepcion original)
        final ServiceRollbackException exNew = auditaExcepcion(jp, ex);

        // Comprobamos si debemos encapsular la excepcion original
        if (exNew != null) {
            throw exNew;
        }

    }

    // ----------------------------------------------------------------------------------
    // METODOS UTILIDADES
    // ----------------------------------------------------------------------------------

    /**
     * Audita excepcion e indica si se debe generar una nueva excepcion que
     * encapsule la original (para las excepciones de BBDD y runtime exception
     * genera una nueva excepcion de negocio).
     * 
     * @param jp
     *            JointPoint
     * @param ex
     *            Excepcion
     * @return En caso necesario se indica si se debe generar una nueva
     *         excepcion que encapsule la original.
     */
    private ServiceRollbackException auditaExcepcion(final JoinPoint jp,
            final Throwable ex) {

        ServiceRollbackException exNew;
        // Para excepciones de BBDD encapsulamos en una excepcion de servicio y
        // auditamos
        if (ex instanceof DataAccessException) {
            final DatabaseException exBD = new DatabaseException(
                    (DataAccessException) ex);
            realizarLog(exBD.getMessage(), ex, jp);
            exNew = exBD;
        } else if (ex instanceof ServiceException) {
            realizarLog(ex.getMessage(), ex, jp);
            exNew = null;
        } else if (ex instanceof RuntimeException) {
            // Para excepciones no controladas que no sean excepciones de
            // servicio las encapsulamos en una excepcion de servicio
            final ErrorNoControladoException exNC = new ErrorNoControladoException(
                    ex);
            realizarLog(exNC.getMessage(), ex, jp);
            exNew = exNC;
        } else if (ex instanceof java.lang.Error) {
            // Para excepciones no controladas que no sean excepciones de
            // servicio las encapsulamos en una excepcion de servicio
            final ErrorNoControladoException exNC = new ErrorNoControladoException(
                    ex);
            realizarLog(exNC.getMessage(), ex, jp);
            exNew = exNC;
        } else {
            // Resto de excepciones (checked)
            // ESTE CASO NO DEBERIA SER POSIBLE YA QUE TODAS LAS EXCEPCIONES
            // CHECKED DEBERIAN SER DE NEGOCIO.
            // EN ESTE CASO SOLO AUDITAMOS POR FICHERO
            log.warn("---- SE HA PRODUCIDO EXCEPCION CHECKED QUE NO SE HA TRATADO COMO EXCEPCION DE NEGOGIO ------");
            realizarLog(ex.getMessage(), ex, jp);
            exNew = null;
        }

        // Indicamos si se debe generar una nueva excepcion encapsulando la
        // original
        return exNew;
    }

    /**
     * Realiza log.
     * 
     * @param mensaje
     *            Mensaje excepcion
     * @param ex
     *            Excepcion
     * @param jp
     *            joint point
     */
    private void realizarLog(final String mensaje, final Throwable ex,
            final JoinPoint jp) {      
            final Logger logJP = LoggerFactory.getLogger(jp.getTarget()
                    .getClass());
            logJP.error("Mensaje pasado:" + mensaje);
            logJP.error(ex.getMessage(), ex);
    }
    
}
