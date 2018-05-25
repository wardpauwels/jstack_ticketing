package be.jstack.ticketing.data.ticketing;

import be.jstack.ticketing.entities.ticketing.Domain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends CrudRepository<Domain, Long> {
}