package be.jstack.ticketing.controller.ticket;

import be.jstack.ticketing.entities.ticketing.Source;
import be.jstack.ticketing.service.ticket.SourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sources")
@Api(value = "Source controller", description = "View info about the sources")
public class SourceController {

    private final SourceService sourceService;

    @Autowired
    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping
    @ApiOperation(value = "View a list of all the sources", response = Source.class, responseContainer = "List")
    public List<Source> findAllSources() {
        return Lists.newArrayList(sourceService.findAllSources());
    }
}