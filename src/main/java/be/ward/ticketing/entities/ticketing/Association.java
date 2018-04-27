package be.ward.ticketing.entities.ticketing;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "association")
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "lock_version")
    private Integer lockVersion;

    @OneToOne(optional = false)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "association_type")
    private String associationType;

    @OneToOne
    @JoinColumn(name = "association_id")
    private Association association;

    public Association(String associationType, Ticket ticket) {
        this.associationType = associationType;
        this.ticket = ticket;
    }

    public Association(Long id, String associationType, Ticket ticket) {
        this(associationType, ticket);
        this.id = id;
    }
}