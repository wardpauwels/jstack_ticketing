package be.jstack.ticketing.controller.user;

import be.jstack.ticketing.entities.user.User;
import be.jstack.ticketing.service.user.UserDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value = "Users controller", description = "Get all the info about the registered users.")
public class UserController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    @ApiOperation(value = "View a list of all the registered users", response = User.class, responseContainer = "List")
    public List<User> getAllUsers() {
        return Lists.newArrayList(userDetailsService.findAllUsers());
    }

    @GetMapping("/{username}")
    @ApiOperation(value = "View details of the asked user", response = User.class)
    public User getUserByUsername(@PathVariable String username) {
        return userDetailsService.findUserWithUsername(username);
    }

    @GetMapping("/loggedin")
    @ApiOperation(value = "View details of the logged in user", response = User.class)
    public User getLoggedUser() {
        return userDetailsService.getLoggedInUser();
    }

    @PostMapping
    @ApiOperation(value = "Register a new user", response = User.class)
    public User makeNewUser(@RequestBody User user) {
        return userDetailsService.createUser(user.getUsername(), user.getPassword());
    }
}