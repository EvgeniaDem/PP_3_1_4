package bootstrap.controller;

import bootstrap.model.Role;
import bootstrap.model.User;
import bootstrap.service.RoleService;
import bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @ModelAttribute("roles")
    public List<Role> roles() {
        return roleService.getAll();
    }

    @GetMapping(value = "/")
    public ModelAndView indexPage(Authentication authentication) {
        List<User> users = userService.getAllUsers();
        User user = (User) authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("mainPaige");
        mav.addObject("users", users);
        mav.addObject("currentUser", user);
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping(value = "/addUser")
    public String addUserAction(User user) {
        userService.addUser(user);
        return "redirect:/";
    }

    @PostMapping(value = "/editUser")
    public String editUserAction(User user) {
        userService.editUser(user);
        return "redirect:/";
    }

    @PostMapping(value = "/deleteUser")
    public String delete(User user) {
        System.out.println("ID controller: " + user.getId());
        userService.deleteUserById(user.getId());
        return "redirect:/";
    }

}
