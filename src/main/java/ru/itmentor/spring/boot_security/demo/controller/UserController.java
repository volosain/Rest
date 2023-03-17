package ru.itmentor.spring.boot_security.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.services.RegistrationService;
import ru.itmentor.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


@Controller
public class UserController {
    private final UserService userService;
    private final RegistrationService registrationService;


    @Autowired
    public UserController(UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @RequestMapping("/user")
    public String getUserPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user";
    }

    @RequestMapping("/admin")
    public String getAdminPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult, Authentication auth) {
        if(bindingResult.hasErrors())
            return "registration";
        registrationService.register(user);
        return userAdminRedirect(auth);
    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "show";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){;
        model.addAttribute("user",userService.findOne(id));
        return "edit";
    }
    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") int id,
                         Authentication auth){
        if (bindingResult.hasErrors())
            return "edit";
        userService.update(id, user);
        return userAdminRedirect(auth);

    }
    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") int id, Authentication auth){
        userService.delete(id);
        return userAdminRedirect(auth);
    }

    private String userAdminRedirect(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Optional<? extends GrantedAuthority> roleAdmin = authorities.stream()
                .filter(a -> Objects.equals("ROLE_ADMIN", a.getAuthority()))
                .findAny();
        if (roleAdmin.isPresent()) return "redirect:/admin";
        else return "redirect:/user";
    }
}
