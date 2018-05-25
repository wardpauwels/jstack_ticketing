package be.jstack.ticketing.data.ticketing;

import be.jstack.ticketing.entities.ticketing.Association;
import be.jstack.ticketing.entities.ticketing.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociationRepository extends CrudRepository<Association, Long> {

    Association findByTicket(Ticket ticket);

    Association findByAssociation(Association association);

}