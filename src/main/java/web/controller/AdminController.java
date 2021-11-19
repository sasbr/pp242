package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    //Create
    @PostMapping("/admin/create")
    public String createUser(@RequestParam("firstname") String firstName,
                             @RequestParam("lastname") String lastName,
                             @RequestParam("telnumber") int telNumber,
                             @RequestParam("username") String userName,
                             @RequestParam("password") String password,
                             @RequestParam(required = false, name = "adm") String adm,
                             @RequestParam(required = false, name = "usr") String usr) {

        Set<Role> roles = new HashSet<>();

        if (adm != null) {
            roles.add(new Role(1L, adm));
        }

        if (usr != null) {
            roles.add(new Role(2L, usr));
        }

        userService.createUser(firstName, lastName, telNumber, userName, password, roles);

        return "redirect:/admin";
    }

    //Read
    @RequestMapping("/admin/user/{id}")
    public String readUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.readUserById(id));
        return "userdata";
    }

    //Update
    @PostMapping("/admin/edituser")
    public String saveUser(@RequestParam("id") Long id,
                           @RequestParam("firstname") String firstName,
                           @RequestParam("lastname") String lastName,
                           @RequestParam("telnumber") int telNumber,
                           @RequestParam("username") String userName,
                           @RequestParam("password") String password,
                           @RequestParam(required = false, name = "adm") String adm,
                           @RequestParam(required = false, name = "usr") String usr) {

        User user = userService.readUserById(id);
        Set<Role> roles = new HashSet<>();

        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setTelNumber(telNumber);
            user.setUserName(userName);
            user.setPassword(password);
            if (adm != null) {
                roles.add(new Role(1L, adm));
            }
            if (usr != null) {
                roles.add(new Role(2L, usr));
            }
            user.setRoles(roles);
            userService.updateUser(user);
        }
        return "redirect:/admin";
    }

    //Delete
    @GetMapping("/admin/deleteuser/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
