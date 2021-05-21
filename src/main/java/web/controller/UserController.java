package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    //Create
    @PostMapping("/create")
    public String createUser (@RequestParam("firstname") String firstName,
                              @RequestParam("lastname")String lastName,
                              @RequestParam("telnumber")int telNumber){
        userService.createUser(firstName, lastName, telNumber);
        return "redirect:/";
    }

    //Read
    @RequestMapping("userdata/{id}")
    public String readUser(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.readUserById(id));
        return "userdata";
    }

    //Update
    @PostMapping("/edituser")
    public String saveUser(@RequestParam("id") Long id,
                           @RequestParam("firstname") String firstName,
                           @RequestParam("lastname") String lastName,
                           @RequestParam("telnumber")int telNumber){

        User user = userService.readUserById(id);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setTelNumber(telNumber);
            userService.updateUser(user);
        }
        return "redirect:/";
    }

    //Delete
    @GetMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
