package net.edigest.journal.controller;

import lombok.extern.slf4j.Slf4j;
import net.edigest.journal.api.response.WeatherResponse;
import net.edigest.journal.entity.User;
import net.edigest.journal.service.UserService;
import net.edigest.journal.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    WeatherService weatherService;

//    @GetMapping
//    public List<User> getAll()
//    {
//        return userService.getAll();
//    }

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

    @GetMapping("External-api")
    public ResponseEntity<String> greetings()
    {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        log.info("This is an external API test.........");
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting="";
        if(weatherResponse!=null)
        {
            greeting=" Weather feels like "+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+authentication.getName()+ greeting ,HttpStatus.OK);
    }

}
