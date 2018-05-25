package be.jstack.ticketing.entities.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(notes = "The database generated user ID")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @ApiModelProperty(notes = "The username of the user", required = true)
    private String username;

    @Column(name = "password", nullable = false)
    @ApiModelProperty(notes = "The password of the user", required = true)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @ApiModelProperty(notes = "The role of the user", required = true)
    private Role role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
