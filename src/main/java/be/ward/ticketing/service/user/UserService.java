package be.ward.ticketing.service.user;

import be.ward.ticketing.data.user.RoleRepository;
import be.ward.ticketing.data.user.UserRepository;
import be.ward.ticketing.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    public User createUser(String username, String password) {
        User user = new User(username, encoder.encode(password));
        user.setRole(roleRepository.findOne(4L));
        return userRepository.save(user);
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserWithUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}