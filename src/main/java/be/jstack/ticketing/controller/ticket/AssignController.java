package be.jstack.ticketing.controller.ticket;

import be.jstack.ticketing.entities.ticketing.Ticket;
import be.jstack.ticketing.service.ticket.AssignService;
import be.jstack.ticketing.util.ticket.Constants;
import be.jstack.ticketing.util.ticket.Messages;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assigns")
@Api(value = "Assign controller", description = "Assign resolvers to tickets")
public class AssignController {

    private final RabbitTemplate rabbitTemplate;
    private final AssignService assignService;

    @Autowired
    public AssignController(RabbitTemplate rabbitTemplate, AssignService assignService) {
        this.rabbitTemplate = rabbitTemplate;
        this.assignService = assignService;
    }

    @PostMapping("/usertoticket")
    @ApiOperation(value = "Assign a new resolver to a ticket", response = String.class)
    public String assignUserToTicket(@RequestBody JSONObject assignDetails) {
        Ticket ticket = assignService.addResolverToTicket(assignDetails.getLong("ticketId"), assignDetails.getString("assignedUser"));

        if (ticket != null) {
            rabbitTemplate.convertAndSend(Constants.VAR_DEFAULT_EXCHANGE, Messages.MSG_RESOLVER_ADDED, ticket.getId());
            return makeJsonObject("User has been added to ticket");
        } else {
            rabbitTemplate.convertAndSend(Constants.VAR_DEFAULT_EXCHANGE, Messages.MSG_RESOLVER_NOT_FOUND, assignDetails.getString("assignedUser"));
            return makeJsonObject("User was not found");
        }
    }

    String makeJsonObject(String resultString) {
        return new JSONObject()
                .put("result", resultString)
                .toString();
    }
}
