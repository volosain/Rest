package ru.itmentor.spring.boot_security.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
//import ru.itmentor.spring.boot_security.demo.services.RegistrationService;
import ru.itmentor.spring.boot_security.demo.services.RoleService;
import ru.itmentor.spring.boot_security.demo.services.UserService;
import ru.itmentor.spring.boot_security.demo.services.UserServiceImpl;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("user", userService.findByUsername(username));
        model.addAttribute("roles", roleService.getAllRole());
        return "admin";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "nameRoles", required = false) String roles) {
        user.setRoles(roleService.getByName(roles));
        userService.createUser(user);
        return "redirect:/admin";
    }

    @PostMapping ("/update/{id}")
    public String update(@ModelAttribute("users") User user,
                         @RequestParam(value = "roleName", required = false) String roles) {
        user.setRoles(roleService.getByName(roles));
        userService.editUser(user);
        return "redirect:/admin";
    }
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

//    @GetMapping(value = "login")
//    public String loginPage() {
//        return "users/login";
//    }
//
//    @RequestMapping("/userPage")
//    public String getUserPage(Model model,Authentication authentication){
//        model.addAttribute("users", userService.findAll());
//        model.addAttribute("authentication",authentication);
//        return "users/userPage";
//    }
//    @RequestMapping("/adminPage")
//    public String getAdminPage(Model model, Authentication authentication ){
//        model.addAttribute("users", userService.findAll());
//        model.addAttribute("authentication",authentication);
//        return "admin/adminPage";
//    }

//    @RequestMapping("/user")
//    public String getUserPage(Model model) {
//        model.addAttribute("users", userService.findAll());
//        return "user";
//    }
//
//    @RequestMapping("/admin")
//    public String getAdminPage(Model model) {
//        model.addAttribute("users", userService.findAll());
//        return "admin";
//    }

//    @GetMapping("/registration")
//    public String registrationPage(Model model) {
//        model.addAttribute("user", new User());
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String performRegistration(@ModelAttribute("user") @Valid User user,
//                                      BindingResult bindingResult, Authentication auth) {
//        if(bindingResult.hasErrors())
//            return "registration";
//        registrationService.register(user);
//        return userAdminRedirect(auth);
//    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userServiceImpl.findOne(id));
//        return "admin/adminPage";
//    }
//
//    @GetMapping("/add")
//    public String newUser(Model model, Authentication authentication) {
//        model.addAttribute("user", new User());
//        model.addAttribute("authentication", authentication);
//        return "admin/newUser";
//    }
//
//    @PostMapping("/new")
//    public String create(@ModelAttribute("user") @Valid User user, //добавляем нового юзера
//                         BindingResult bindingResult,
//                         Authentication auth) {
//        if (bindingResult.hasErrors())               //проверяем на валидацию
//            return "new";
//        userServiceImpl.save(user);
//        return userAdminRedirect(auth); //при добавлении возвращает нас на главную страницу с списком
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id){;
//        model.addAttribute("user", userServiceImpl.findOne(id));
//        return "adminPage";
//    }
//    @PatchMapping("/{id}/update")
//    public String update(@ModelAttribute("user") @Valid User user,
//                         BindingResult bindingResult,
//                         @PathVariable("id") int id,
//                         Authentication auth){
//        if (bindingResult.hasErrors())
//            return "update";
//        userServiceImpl.update(id, user);
//        return userAdminRedirect(auth);
//
//    }
//    @DeleteMapping("/{id}/delete")
//    public String delete(@PathVariable("id") int id, Authentication auth){
//        userServiceImpl.delete(id);
//        return userAdminRedirect(auth);
//    }
//
//    private String userAdminRedirect(Authentication auth) {
//        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//        Optional<? extends GrantedAuthority> roleAdmin = authorities.stream()
//                .filter(a -> Objects.equals("ROLE_ADMIN", a.getAuthority()))
//                .findAny();
//        if (roleAdmin.isPresent()) return "redirect:/adminPage";
//        else return "redirect:/login";
//    }
}
