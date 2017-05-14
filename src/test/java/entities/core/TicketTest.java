package entities.core;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TicketTest {

    @Test
    public void testTicketLongTicketState() {
        Ticket ticket = new Ticket(666);
        assertTrue(ticket.getReference().length() > 20);
    }
}