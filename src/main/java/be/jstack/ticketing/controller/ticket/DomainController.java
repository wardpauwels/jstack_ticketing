package be.jstack.ticketing.controller.ticket;

import be.jstack.ticketing.entities.ticketing.Domain;
import be.jstack.ticketing.service.ticket.DomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/domains")
@Api(value = "Domain controller", description = "View info about the domains")
public class DomainController {

    private final DomainService domainService;

    @Autowired
    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @GetMapping
    @ApiOperation(value = "View a list of all the domains", response = Domain.class, responseContainer = "List")
    public List<Domain> findAllDomains() {
        return Lists.newArrayList(domainService.findAllDomains());
    }

}
