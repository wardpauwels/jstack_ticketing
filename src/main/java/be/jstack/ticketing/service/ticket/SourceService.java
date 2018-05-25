package be.jstack.ticketing.service.ticket;

import be.jstack.ticketing.data.ticketing.SourceRepository;
import be.jstack.ticketing.entities.ticketing.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SourceService {

    private final SourceRepository sourceRepository;

    @Autowired
    public SourceService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public Iterable<Source> findAllSources() {
        return sourceRepository.findAll();
    }

    Source findSourceWithId(Long id) {
        return sourceRepository.findOne(id);
    }
}