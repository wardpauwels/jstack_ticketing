package be.ward.ticketing.entities.ticketing;

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
    private Long id;

    @Column(name = "assigned_group")
    private String assignedGroup;

    @Column(name = "assigned_user")
    private String assignedUser;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "creator")
    private String creator;

    @Column(name = "description")
    private String description;

    @Column(name = "due_at")
    private Date dueAt;

    @Column(name = "lock_version")
    private Integer lockVersion;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "topic_text")
    private String topicText;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Source source;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    public Ticket(long id) {
        this.id = id;
    }
}