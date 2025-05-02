package net.edigest.journal.controller;

import net.edigest.journal.entity.User;
import net.edigest.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAll()
    {
        return userService.getAll();
    }

    @PutMapping
    public ResponseEntity<User> updateUserByuserName(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return  userService.updateUserByuserName(name,user);

    }


    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserById()
    {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByuserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
