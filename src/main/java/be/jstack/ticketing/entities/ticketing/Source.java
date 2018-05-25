package be.jstack.ticketing.entities.ticketing;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "source")
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(notes = "The database generated association ID")
    private Long id;

    @Column(name = "lock_version")
    private Integer lockVersion;

    @Column(name = "name")
    @ApiModelProperty(notes = "The name of the source", required = true)
    private String name;

    public Source(long id) {
        this.id = id;
    }
}
