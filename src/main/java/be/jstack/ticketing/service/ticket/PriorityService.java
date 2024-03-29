package be.jstack.ticketing.service.ticket;

import be.jstack.ticketing.data.ticketing.PriorityRepository;
import be.jstack.ticketing.entities.ticketing.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriorityService {

    private final PriorityRepository priorityRepository;

    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public Iterable<Priority> findAllPriorities() {
        return priorityRepository.findAll();
    }

    public Priority findPriorityWithId(Long id) {
        return priorityRepository.findOne(id);
    }

    public Priority findPriorityByName(String name) {
        return priorityRepository.findByNameEquals(name);
    }
}