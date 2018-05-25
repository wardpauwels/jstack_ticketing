package be.jstack.ticketing.data.ticketing;

import be.jstack.ticketing.entities.ticketing.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
}