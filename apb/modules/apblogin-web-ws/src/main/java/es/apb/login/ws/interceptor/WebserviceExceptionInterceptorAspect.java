package es.apb.login.ws.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.apb.login.ws.exception.ErroresWs;
import es.apb.login.ws.exception.WException;

/**
 * Lógica de logging de las excepciones de la capa de webservices. Traduce las
 * excepciones de negocio a errores externos.
 * 
 */
@Aspect
@Service
public final class WebserviceExceptionInterceptorAspect {

    /**
     * MONITORIZACION_SERVICE.
     */
    public static final String MONITORIZACION_SERVICE = "MonitorizacionWebService";

    /**
     * Log del interceptor.
     */
    private final Logger log = LoggerFactory
            .getLogger(WebserviceExceptionInterceptorAspect.class);

    /**
     * Intercepta invocacion a metodo.
     * 
     * @param jp
     *            JointPoint
     */
    @Before("@annotation(es.apb.login.ws.interceptor.WebserviceExceptionInterceptor)")
    public void processInvocation(final JoinPoint jp) {
        log.info("Invocando servicio: " + jp.getSignature().getName());
    }

    /**
     * Realiza el log de la excepcion.
     * 
     * @param jp
     *            JoinPoint
     * @param ex
     *            Exception
     */
    @AfterThrowing(pointcut = "@annotation(es.apb.login.ws.interceptor.WebserviceExceptionInterceptor)", throwing = "ex")
    public void processException(final JoinPoint jp, final Throwable ex) {

        // Realizamos log excepcion
        log.trace("Interceptamos excepcion: " + ex.getClass().getName());

        // Traducimos excepcion a WSException
        if (!(ex instanceof WException)) {

            final String descripcionExcepcion = "Excepcion no controlada: "
                    + ex.getClass().getName();
            final String detalleExcepcion = ex.getMessage();

            final WException wse = new WException(
                    ErroresWs.ERROR_NO_CONTROLADO, descripcionExcepcion,
                    detalleExcepcion, null, ex);

            throw wse;
        }
    }

}
