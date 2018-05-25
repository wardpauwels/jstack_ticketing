package be.jstack.ticketing.controller.ticket;

import be.jstack.ticketing.entities.ticketing.Priority;
import be.jstack.ticketing.service.ticket.PriorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/priorities")
@Api(value = "Priority controller", description = "View info about all the associations")
public class PriorityController {

    private final PriorityService priorityService;

    @Autowired
    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping
    @ApiOperation(value = "View a list of all the priorities", response = Priority.class, responseContainer = "List")
    public List<Priority> findAllPriorities() {
        return Lists.newArrayList(priorityService.findAllPriorities());
    }

}
