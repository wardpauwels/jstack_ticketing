package be.jstack.ticketing.service.ticket;

import be.jstack.ticketing.data.ticketing.AssociationRepository;
import be.jstack.ticketing.entities.ticketing.Association;
import be.jstack.ticketing.entities.ticketing.Ticket;
import be.jstack.ticketing.entities.user.User;
import be.jstack.ticketing.service.user.UserDetailsService;
import be.jstack.ticketing.util.ticket.TicketStatus;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociationService {

    private final AssociationRepository associationRepository;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AssociationService(AssociationRepository associationRepository, UserDetailsService userDetailsService) {
        this.associationRepository = associationRepository;
        this.userDetailsService = userDetailsService;
    }

    public Association findAssociation(Long associationId) {
        return associationRepository.findOne(associationId);
    }

    public Association createAssociation(String associationType, Ticket ticket) {
        Association association = new Association(associationType, ticket);
        return associationRepository.save(association);
    }

    void linkNewAssociationToAssociation(Association newAssociation, Association oldAssociation) {
        Association association = findAssociationById(oldAssociation.getId());
        association.setAssociation(newAssociation);
        associationRepository.save(association);
    }

    public List<Association> getAllAssociations() {
        return Lists.newArrayList(associationRepository.findAll());
    }

    public Association getTopAssociationFromTicket(Ticket ticket) {
        Association association = findAssociationByTicket(ticket);
        Association aboveAssociation = findAssociationByAssociation(association);
        while (aboveAssociation != null) {
            association = aboveAssociation;
            aboveAssociation = findAssociationByAssociation(association);
            if (aboveAssociation == null) return association;
        }
        return association;
    }

    // TODO: Use optional and remove while loop
    public Association getLastAssociationFromTicket(Ticket ticket) {
        Association association = findAssociationByTicket(ticket);
        while (association.getAssociation() != null) {
            association = association.getAssociation();
        }
        return association;
    }

    public Association findAssociationById(Long associationId) {
        return associationRepository.findOne(associationId);
    }

    private Association findAssociationByTicket(Ticket ticket) {
        return associationRepository.findByTicket(ticket);
    }

    private Association findAssociationByAssociation(Association association) {
        return associationRepository.findByAssociation(association);
    }

    private List<Association> getAllGroupedAssociations() {
        List<Association> allAssociations = getAllAssociations();
        List<Association> topAssociations = new ArrayList<>();
        for (Association association : allAssociations) {
            Association topAssociation = getTopAssociationFromTicket(association.getTicket());
            if (!topAssociations.contains(topAssociation)) {
                topAssociations.add(topAssociation);
            }
        }
        return topAssociations;
    }

    public List<Association> getConversationOfTopAssociationAsList(String associationId) {
        List<Association> associationsList = new ArrayList<>();
        Association association = associationRepository.findOne(Long.valueOf(associationId));
        associationsList.add(association);

        if (!association.getTicket().getCreator().equalsIgnoreCase(userDetailsService.getLoggedInUser().getUsername()) && userDetailsService.getLoggedInUser().getRole().getRole().equalsIgnoreCase("user")) {
            return null;
        }

        while (association.getAssociation() != null) {
            association = association.getAssociation();
            associationsList.add(association);
        }
        return associationsList;
    }

    public List<Association> getAssociationsForAuthorisedUser() {
        User loggedInUser = userDetailsService.getLoggedInUser();
        List<Association> associations =
                getAllGroupedAssociations()
                        .stream()
                        .filter(association ->
                                !association.getTicket().getStatus().equalsIgnoreCase(TicketStatus.ticketSolved))
                        .collect(Collectors.toList());

        if (loggedInUser.getRole().getRole().equalsIgnoreCase("user")) {
            associations.removeIf(association -> (!association.getTicket().getCreator().equals(loggedInUser.getUsername())));
        }
        return associations;
    }
}