package be.ward.ticketing.controller.user;

import be.ward.ticketing.entities.user.User;
import be.ward.ticketing.service.user.UserDetailsService;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return Lists.newArrayList(userDetailsService.findAllUsers());
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userDetailsService.findUserWithUsername(username);
    }

    @GetMapping("/loggedin")
    public Principal getLoggedUser(Principal principal) {
        return principal;
    }

    @PostMapping
    public User makeNewUser(@RequestBody User user) {
        return userDetailsService.createUser(user.getUsername(), user.getPassword());
    }
}