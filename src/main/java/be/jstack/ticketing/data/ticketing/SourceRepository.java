package be.jstack.ticketing.data.ticketing;

import be.jstack.ticketing.entities.ticketing.Source;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends CrudRepository<Source, Long> {
}