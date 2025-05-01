package net.edigest.journal.controller;

import net.edigest.journal.entity.User;
import net.edigest.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @GetMapping("/health-check")
    public String HealthCheck()
    {
        return "Ok";
    }

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user)
    {
        return userService.saveNewUser(user);
    }
}
