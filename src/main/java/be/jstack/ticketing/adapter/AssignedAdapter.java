package be.jstack.ticketing.adapter;

import be.jstack.ticketing.util.ticket.Constants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AssignedAdapter implements JavaDelegate {

    final RabbitTemplate rabbitTemplate;

    Long ticketId;

    public AssignedAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        ticketId = (Long) delegateExecution.getVariable(Constants.VAR_TICKET_ID);
    }
}