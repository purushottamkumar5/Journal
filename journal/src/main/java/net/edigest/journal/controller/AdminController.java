package net.edigest.journal.controller;

import net.edigest.journal.entity.User;
import net.edigest.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController
{

    @Autowired
    UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> allUsers = userService.getAllUsers();
        if(allUsers!=null && !allUsers.isEmpty())
        {
            return new ResponseEntity<>(allUsers, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user)
    {
        userService.saveAdmin(user);
    }
}
