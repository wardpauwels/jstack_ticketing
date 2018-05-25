package be.jstack.ticketing.controller.ticket;

import be.jstack.ticketing.entities.ticketing.TicketType;
import be.jstack.ticketing.service.ticket.TicketTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticketypes")
@Api(value = "Ticket type controller", description = "View info about all the ticket types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    @Autowired
    public TicketTypeController(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @GetMapping
    @ApiOperation(value = "View a list of all the ticket types", response = TicketType.class, responseContainer = "List")
    public List<TicketType> findAllTicketTypes() {
        return Lists.newArrayList(ticketTypeService.findAllTicketTypes());
    }
}