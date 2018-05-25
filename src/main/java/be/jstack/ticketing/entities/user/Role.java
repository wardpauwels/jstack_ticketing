package be.jstack.ticketing.entities.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    @ApiModelProperty(notes = "The database generated role ID")
    private Long id;

    @Column(name = "role")
    @ApiModelProperty(notes = "The name of the role", required = true)
    private String role;
}
