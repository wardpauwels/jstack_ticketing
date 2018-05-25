package be.jstack.ticketing.entities.ticketing;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "domain")
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(notes = "The database generated association ID")
    private Long id;

    @Column(name = "lock_version")
    private Integer lockVersion;

    @Column(name = "name")
    @ApiModelProperty(notes = "The name of the domain", required = true)
    private String name;

    public Domain(Long domainId) {
        this.id = domainId;
    }
}