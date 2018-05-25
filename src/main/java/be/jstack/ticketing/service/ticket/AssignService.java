package be.jstack.ticketing.service.ticket;

import be.jstack.ticketing.entities.ticketing.Ticket;
import be.jstack.ticketing.entities.user.User;
import be.jstack.ticketing.service.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignService {
    private final TicketService ticketService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AssignService(TicketService ticketService, UserDetailsService userDetailsService) {
        this.ticketService = ticketService;
        this.userDetailsService = userDetailsService;
    }

    public Ticket addResolverToTicket(Long ticketId, String username) {
        Ticket ticket = ticketService.findTicket(ticketId);
        User user = userDetailsService.findUserWithUsername(username);

        if (user != null) {
            ticket.setAssignedUser(username);
            return ticketService.saveTicket(ticket);
        } else return null;
    }
}