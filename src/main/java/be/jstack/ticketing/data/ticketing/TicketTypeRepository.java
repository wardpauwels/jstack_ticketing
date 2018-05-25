package be.jstack.ticketing.data.ticketing;

import be.jstack.ticketing.entities.ticketing.TicketType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepository extends CrudRepository<TicketType, Long> {
}