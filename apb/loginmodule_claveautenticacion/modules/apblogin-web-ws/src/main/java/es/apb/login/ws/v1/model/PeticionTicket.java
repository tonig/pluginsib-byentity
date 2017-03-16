package es.apb.login.ws.v1.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Peticion datos ticket.
 * 
 * @author indra
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public final class PeticionTicket {

    /**
     * Ticket.
     */
    @XmlElement(required = true)
    private String ticket;

    /**
     * Gets the ticket.
     * 
     * @return the ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Sets the ticket.
     * 
     * @param pTicket
     *            the new ticket
     */
    public void setTicket(final String pTicket) {
        ticket = pTicket;
    }

}
