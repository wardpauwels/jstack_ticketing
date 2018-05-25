package be.jstack.ticketing.adapter;

import be.jstack.ticketing.entities.ticketing.Ticket;
import be.jstack.ticketing.service.ticket.TicketService;
import be.jstack.ticketing.util.mail.SendMailTLS;
import be.jstack.ticketing.util.ticket.Constants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailCustomerAdapter implements JavaDelegate {

    private final TicketService ticketService;

    @Value("${adapter.mail.to}")
    private String mailTo;

    @Value("${adapter.mail.subject}")
    private String mailSubject;

    @Autowired
    public EmailCustomerAdapter(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Long ticketId = (Long) delegateExecution.getVariable(Constants.VAR_TICKET_ID);
        Ticket ticket = ticketService.findTicket(ticketId);

        //TODO: Get mail address from the user
        SendMailTLS sendMailTLS = new SendMailTLS();
        sendMailTLS.sendMail(
                mailTo,
                mailSubject,
                "Your ticket has been answered.\n Your question was: \n" + ticket.getDescription()
        );
    }
}