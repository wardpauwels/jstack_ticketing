package be.jstack.ticketing.amqp.receivers;

import be.jstack.ticketing.entities.ticketing.Ticket;
import be.jstack.ticketing.service.ticket.TicketService;
import be.jstack.ticketing.util.ticket.Constants;
import be.jstack.ticketing.util.ticket.Messages;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TicketAnsweredReceiver {

    private final TicketService ticketService;

    private final ProcessEngine processEngine;

    @Autowired
    public TicketAnsweredReceiver(ProcessEngine processEngine, TicketService ticketService) {
        this.processEngine = processEngine;
        this.ticketService = ticketService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Messages.MSG_TICKET_ANSWERED, durable = "true"),
            exchange = @Exchange(value = Constants.VAR_DEFAULT_EXCHANGE, type = "topic", durable = "true"),
            key = Messages.MSG_TICKET_ANSWERED))
    @Transactional
    void sendMessageTicketIsAnswered(String mainTicketId) {
        Ticket mainTicket = ticketService.findTicket(Long.valueOf(mainTicketId));

        processEngine
                .getRuntimeService()
                .createMessageCorrelation(Messages.MSG_TICKET_ANSWERED)
                .processInstanceBusinessKey(String.valueOf(mainTicket.getId()))
                .correlateWithResult();
    }
}