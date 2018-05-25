package be.jstack.ticketing.service.ticket;

import be.jstack.ticketing.data.ticketing.TicketTypeRepository;
import be.jstack.ticketing.entities.ticketing.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;

    @Autowired
    public TicketTypeService(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    public Iterable<TicketType> findAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }

    TicketType findTicketTypeWithId(Long id) {
        return ticketTypeRepository.findOne(id);
    }
}