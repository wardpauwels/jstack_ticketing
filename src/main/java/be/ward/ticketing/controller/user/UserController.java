package be.ward.ticketing.controller.user;

import be.ward.ticketing.entities.user.User;
import be.ward.ticketing.service.user.UserService;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return Lists.newArrayList(userService.findAllUsers());
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findUserWithUsername(username);
    }

    @GetMapping("/loggedin")
    public Principal getLoggedUser(Principal principal) {
        return principal;
    }

    @PostMapping
    public User makeNewUser(@RequestBody User user) {
        return userService.createUser(user.getUsername(), user.getPassword());
    }
}