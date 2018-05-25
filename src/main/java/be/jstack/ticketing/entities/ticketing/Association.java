package be.jstack.ticketing.entities.ticketing;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "The database generated association ID")
    private Long id;

    @Column(name = "lock_version")
    @ApiModelProperty(required = false)
    private Integer lockVersion;

    @OneToOne(optional = false)
    @JoinColumn(name = "ticket_id")
    @ApiModelProperty(notes = "The ticket linked to the association", required = true)
    private Ticket ticket;

    @Column(name = "association_type")
    @ApiModelProperty(notes = "The type of the association", required = true)
    private String associationType;

    @OneToOne
    @JoinColumn(name = "association_id")
    @ApiModelProperty(notes = "The association linked to the current association")
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