package es.apb.login.ws.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.log4j.Logger;

/**
 * Basic auth.
 * 
 * @author Indra
 * 
 */
public final class BasicAuthAuthorizationInterceptor extends
        SoapHeaderInterceptor {

    /** Log. */
    protected Logger log = Logger.getLogger(getClass());

    /** Indica si esta habilitada autenticacion (basic). */
    private boolean webserviceSecurityEnabled;

    /** Usuario autenticacion. */
    private String webserviceSecurityUser;

    /** Password auteticacion. */
    private String webserviceSecurityPassword;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor#handleMessage
     * (org.apache.cxf.message.Message)
     */
    @Override
    public void handleMessage(final Message message) throws Fault {

        // Si no esta habilitado, no procesamos
        if (!(webserviceSecurityEnabled)) {
            return;
        }

        // This is set by CXF
        final AuthorizationPolicy policy = message
                .get(AuthorizationPolicy.class);
        // If the policy is not set, the user did not specify
        // credentials. A 401 is sent to the client to indicate
        // that authentication is required
        if (policy == null) {
            log.error("Invalid username or password");
            sendErrorResponse(message, HttpURLConnection.HTTP_UNAUTHORIZED);
            return;
        }
        // Verify the password
        if (!validatePassword(policy.getUserName(), policy.getPassword())) {
            log.error("Invalid username or password for user: "
                    + policy.getUserName());
            sendErrorResponse(message, HttpURLConnection.HTTP_FORBIDDEN);
        }

    }

    /**
     * Send error response.
     * 
     * @param message
     *            the message
     * @param responseCode
     *            the response code
     */
    private void sendErrorResponse(final Message message, final int responseCode) {
        final Message outMessage = getOutMessage(message);
        outMessage.put(Message.RESPONSE_CODE, responseCode);
        // Set the response headers
        // final Map responseHeaders = message.get( Message.PROTOCOL_HEADERS);
        final Map<String, List<String>> responseHeaders = (Map<String, List<String>>) message
                .get(Message.PROTOCOL_HEADERS);
        if (responseHeaders != null) {
            responseHeaders.put("WWW-Authenticate",
                    Arrays.asList(new String[] {"Basic realm=realm"}));
            responseHeaders.put("Content-Length",
                    Arrays.asList(new String[] {"0"}));
        }
        message.getInterceptorChain().abort();
        try {
            getConduit(message).prepare(outMessage);
            close(outMessage);
        } catch (final IOException e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * Gets the out message.
     * 
     * @param inMessage
     *            the in message
     * @return the out message
     */
    private Message getOutMessage(final Message inMessage) {
        final Exchange exchange = inMessage.getExchange();
        Message outMessage = exchange.getOutMessage();
        if (outMessage == null) {
            final Endpoint endpoint = exchange.get(Endpoint.class);
            outMessage = endpoint.getBinding().createMessage();
            exchange.setOutMessage(outMessage);
        }
        outMessage.putAll(inMessage);
        return outMessage;
    }

    /**
     * Gets the conduit.
     * 
     * @param inMessage
     *            the in message
     * @return the conduit
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private Conduit getConduit(final Message inMessage) throws IOException {
        final Exchange exchange = inMessage.getExchange();
        final EndpointReferenceType target = exchange
                .get(EndpointReferenceType.class);
        final Conduit conduit = exchange.getDestination().getBackChannel(
                inMessage, null, target);
        exchange.setConduit(conduit);
        return conduit;
    }

    /**
     * Close.
     * 
     * @param outMessage
     *            the out message
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void close(final Message outMessage) throws IOException {
        final OutputStream os = outMessage.getContent(OutputStream.class);
        os.flush();
        os.close();
    }

    /**
     * Validate password.
     * 
     * @param pUserName
     *            the user name
     * @param pPassword
     *            the password
     * @return true, if successful
     */
    private boolean validatePassword(final String pUserName,
            final String pPassword) {
        return webserviceSecurityUser.equals(pUserName)
                && webserviceSecurityPassword.equals(pPassword);
    }

    /**
     * Gets the webservice security enabled.
     * 
     * @return the webservice security enabled
     */
    public boolean getWebserviceSecurityEnabled() {
        return webserviceSecurityEnabled;
    }

    /**
     * Sets the webservice security enabled.
     * 
     * @param pWebserviceSecurityEnabled
     *            the new webservice security enabled
     */
    public void setWebserviceSecurityEnabled(
            final boolean pWebserviceSecurityEnabled) {
        webserviceSecurityEnabled = pWebserviceSecurityEnabled;
    }

    /**
     * Gets the webservice security user.
     * 
     * @return the webservice security user
     */
    public String getWebserviceSecurityUser() {
        return webserviceSecurityUser;
    }

    /**
     * Sets the webservice security user.
     * 
     * @param pWebserviceSecurityUser
     *            the new webservice security user
     */
    public void setWebserviceSecurityUser(final String pWebserviceSecurityUser) {
        webserviceSecurityUser = pWebserviceSecurityUser;
    }

    /**
     * Gets the webservice security password.
     * 
     * @return the webservice security password
     */
    public String getWebserviceSecurityPassword() {
        return webserviceSecurityPassword;
    }

    /**
     * Sets the webservice security password.
     * 
     * @param pWebserviceSecurityPassword
     *            the new webservice security password
     */
    public void setWebserviceSecurityPassword(
            final String pWebserviceSecurityPassword) {
        webserviceSecurityPassword = pWebserviceSecurityPassword;
    }
}
