package vitaliy.kuzmich.controllers;

import vitaliy.kuzmich.model.User;
import vitaliy.kuzmich.services.RoleService;
import vitaliy.kuzmich.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@RestController
public class Users {
    @Autowired
    UserService service;
    @Autowired
    RoleService roleService;
    @Autowired
    ReloadableResourceBundleMessageSource messageSource;

    @RequestMapping(value = "/all-users")
    public List<User> getAll() {
        return service.getAll();

    }

    @RequestMapping(value = "/add-user")
    public User add(User user, String role) throws Exception {
        Collection col = new ArrayList(1);
        col.add(roleService.getByName(role));
        user.setAuthorities(col);

       return service.addOrUpdateUser(user);

    }

    @RequestMapping(value = "/delete-user")
    public void add(Long userId) throws Exception {
        service.delete(userId);
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }


}
