package be.jstack.ticketing.entities.ticketing;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "ticket")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(notes = "The database generated association ID")
    private Long id;

    @Column(name = "assigned_group")
    @ApiModelProperty(notes = "The group assigned to solve the ticket")
    private String assignedGroup;

    @Column(name = "assigned_user")
    @ApiModelProperty(notes = "The name of the assigned resolver")
    private String assignedUser;

    @Column(name = "created_at")
    @ApiModelProperty(notes = "The date when the ticket was created")
    private Date createdAt;

    @Column(name = "creator")
    @ApiModelProperty(notes = "The name of the creator of the ticket")
    private String creator;

    @Column(name = "description")
    @ApiModelProperty(notes = "The description of the problem of the ticket", required = true)
    private String description;

    @Column(name = "due_at")
    @ApiModelProperty(notes = "The date when the ticket has to be resolved")
    private Date dueAt;

    @Column(name = "lock_version")
    private Integer lockVersion;

    @Column(name = "status")
    @ApiModelProperty(notes = "The current status of the ticket")
    private String status;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    @ApiModelProperty(notes = "The topic of the ticket")
    private Topic topic;

    @Column(name = "topic_text")
    @ApiModelProperty(notes = "The text added to the topic")
    private String topicText;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    @ApiModelProperty(notes = "The domain of the ticket")
    private Domain domain;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    @ApiModelProperty(notes = "The priority of the ticket")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "source_id")
    @ApiModelProperty(notes = "The source of the ticket")
    private Source source;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    @ApiModelProperty(notes = "The type of the ticket")
    private TicketType ticketType;

    public Ticket(long id) {
        this.id = id;
    }
}