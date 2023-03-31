package ru.itmentor.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUser();
    void createUser(User user);
    void editUser(User user);
    void editUser(User user, List<Role> roles);
    void deleteUser(Long id);
    User getUserById(Long id);
    User findByUsername(String username);
}
