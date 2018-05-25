package be.jstack.ticketing.controller.ticket;

import be.jstack.ticketing.entities.ticketing.Association;
import be.jstack.ticketing.service.ticket.AssociationService;
import be.jstack.ticketing.util.annotation.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/associations")
@Api(value = "Associations controller", description = "View info about all the associations")
public class AssociationController {

    private final AssociationService associationService;

    @Autowired
    public AssociationController(AssociationService associationService) {
        this.associationService = associationService;
    }

    @Admin
    @GetMapping
    @ApiOperation(value = "View a list of all the associations", response = Association.class, responseContainer = "List")
    public List<Association> getAllAssociations() {
        return associationService.getAllAssociations();
    }

    @GetMapping("/{associationId}")
    @ApiOperation(value = "View the details of an association", response = Association.class)
    public Association findAssociationWithId(@PathVariable String associationId) {
        return associationService.findAssociation(Long.valueOf(associationId));
    }

    @GetMapping("/groups")
    @ApiOperation(value = "View a list of all the associations, ordered by conversation", response = Association.class, responseContainer = "List")
    public List<Association> getAllGroupedAssociations() {
        return associationService.getAssociationsForAuthorisedUser();
    }

    @GetMapping("/groups/{associationId}")
    @ApiOperation(value = "View the details of a group of associations", response = Association.class, responseContainer = "List")
    public List<Association> getAllGroupedAssociationsForId(@PathVariable String associationId) {
        return associationService.getConversationOfTopAssociationAsList(associationId);
    }
}