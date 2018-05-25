package be.jstack.ticketing.amqp.receivers;

import be.jstack.ticketing.service.ticket.TicketService;
import be.jstack.ticketing.util.ticket.Constants;
import be.jstack.ticketing.util.ticket.Messages;
import be.jstack.ticketing.util.ticket.TicketStatus;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProblemSolvedReceiver {

    private final ProcessEngine processEngine;
    private final TicketService ticketService;

    @Autowired
    public ProblemSolvedReceiver(ProcessEngine processEngine, TicketService ticketService) {
        this.processEngine = processEngine;
        this.ticketService = ticketService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Messages.MSG_PROBLEM_SOLVED, durable = "true"),
            exchange = @Exchange(value = Constants.VAR_DEFAULT_EXCHANGE, type = "topic", durable = "true"),
            key = Messages.MSG_PROBLEM_SOLVED))
    @Transactional
    void sendMessageTicketIsSolved(String ticketId) {
        ticketService.setTicketStatus(Long.valueOf(ticketId), TicketStatus.ticketSolved);
        processEngine
                .getRuntimeService()
                .createMessageCorrelation(Messages.MSG_PROBLEM_SOLVED)
                .processInstanceBusinessKey(ticketId)
                .correlateWithResult();
    }
}