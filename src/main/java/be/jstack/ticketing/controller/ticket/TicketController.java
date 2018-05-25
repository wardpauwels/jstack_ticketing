package be.jstack.ticketing.controller.ticket;

import be.jstack.ticketing.entities.ticketing.Association;
import be.jstack.ticketing.entities.ticketing.Ticket;
import be.jstack.ticketing.entities.user.User;
import be.jstack.ticketing.service.ticket.AssociationService;
import be.jstack.ticketing.service.ticket.TicketService;
import be.jstack.ticketing.service.user.UserDetailsService;
import be.jstack.ticketing.util.annotation.Admin;
import be.jstack.ticketing.util.ticket.AssociationTypes;
import be.jstack.ticketing.util.ticket.Constants;
import be.jstack.ticketing.util.ticket.Messages;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.collect.Lists;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@Api(value = "Ticket controller", description = "View info about and manage the user tickets")
public class TicketController {

    private final RabbitTemplate rabbitTemplate;
    private final TicketService ticketService;
    private final AssociationService associationService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public TicketController(RabbitTemplate rabbitTemplate,
                            TicketService ticketService,
                            AssociationService associationService,
                            UserDetailsService userDetailsService1) {
        this.rabbitTemplate = rabbitTemplate;
        this.ticketService = ticketService;
        this.associationService = associationService;
        this.userDetailsService = userDetailsService1;
    }

    @Admin
    @GetMapping
    @ApiOperation(value = "View a list of all the tickets", response = Ticket.class, responseContainer = "List")
    public List<Ticket> findAllTickets() {
        return Lists.newArrayList(ticketService.findAllTickets());
    }

    @GetMapping("/{ticketId}")
    @ApiOperation(value = "View the details of a ticket", response = Ticket.class)
    public Ticket findTicketWithId(@PathVariable String ticketId) {
        return ticketService.findTicket(Long.valueOf(ticketId));
    }

    @GetMapping("/noresolver")
    @ApiOperation(value = "View a list of all the tickets without resolver assigned", response = Ticket.class, responseContainer = "List")
    public List<Ticket> findTicketsWithoutResolver() {
        return Lists.newArrayList(ticketService.findTicketsWithoutResolver());
    }

    @GetMapping("/user/{username}")
    @ApiOperation(value = "View a list of all the tickets for a certain resolver", response = Ticket.class, responseContainer = "List")
    public List<Ticket> getTicketForResolver(@PathVariable String username) {
        return Lists.newArrayList(ticketService.findTicketsForResolver(username));
    }

    @PostMapping
    @ApiOperation(value = "Submit a new ticket", response = Ticket.class)
    public Ticket createNewTicket(@RequestBody String ticketDetails) {
        JSONObject jsonTicketDetails = new JSONObject(ticketDetails);
        User user = userDetailsService.getLoggedInUser();

        Ticket ticket = ticketService.createTicket(user.getUsername(), jsonTicketDetails.getString("message"));
        Association association = associationService.createAssociation(AssociationTypes.ticket, ticket);

        rabbitTemplate.convertAndSend(Constants.VAR_DEFAULT_EXCHANGE, Messages.MSG_NEW_TICKET, association.getId());
        return ticket;
    }

    @PostMapping("/answers/{ticketId}")
    @ApiOperation(value = "Answer on a ticket", response = Ticket.class)
    public Ticket answerOnTicketWithId(@PathVariable String ticketId, @RequestBody String answer) {
        User user = userDetailsService.getLoggedInUser();
        JSONObject jsonAnswer = new JSONObject(answer);

        Ticket oldTicket = ticketService.findTicket(Long.valueOf(ticketId));
        Association latestAssociation = associationService.getLastAssociationFromTicket(oldTicket);
        Ticket newTicket = ticketService.answerOnTicketWithId(latestAssociation.getTicket().getId(), user.getUsername(), jsonAnswer.getString("answer"));

        rabbitTemplate.convertAndSend(Constants.VAR_DEFAULT_EXCHANGE, Messages.MSG_TICKET_ANSWERED, oldTicket.getId());
        return newTicket;
    }

    @PostMapping("/solved/{ticketId}")
    @ApiOperation(value = "Mark a ticket as solved", response = Ticket.class)
    public Ticket ticketSolved(@PathVariable String ticketId) {
        Association association = associationService.getTopAssociationFromTicket(ticketService.findTicket(Long.valueOf(ticketId)));
        Ticket ticket = association.getTicket();

        rabbitTemplate.convertAndSend(Constants.VAR_DEFAULT_EXCHANGE, Messages.MSG_PROBLEM_SOLVED, ticket.getId());
        return ticket;
    }

    @PostMapping("/notsolved/{ticketId}")
    @ApiOperation(value = "Mark a ticket as unsolved", response = Ticket.class)
    public Ticket ticketNotSolved(@PathVariable String ticketId, @RequestBody String comment) {
        JSONObject jsonComment = new JSONObject(comment);
        Association association = associationService.getTopAssociationFromTicket(ticketService.findTicket(Long.valueOf(ticketId)));
        Ticket ticket = association.getTicket();

        rabbitTemplate.convertAndSend(Constants.VAR_DEFAULT_EXCHANGE, Messages.MSG_PROBLEM_NOT_SOLVED, new Object[]{ticket.getId(), jsonComment.getString("comment")});
        return ticket;
    }
}