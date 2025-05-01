package net.edigest.journal.controller;

import ch.qos.logback.core.joran.spi.ElementSelector;
import net.edigest.journal.entity.JournalEntry;
import net.edigest.journal.entity.User;
import net.edigest.journal.repository.JournalEntryRepository;
import net.edigest.journal.repository.UserRepoitory;
import net.edigest.journal.service.JournalEntryService;
import net.edigest.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournaEntryControllerV2 {

    @Autowired
    JournalEntryService service;

    @Autowired
    UserService userService;

    @Autowired
    UserRepoitory userRepoitory;

    @Autowired
    JournalEntryRepository journalEntryRepository;


//    @GetMapping
//    public List<JournalEntry>getAll()
//    {
//        return service.getAll();
//    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEnteriesOfUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepoitory.findByUserName(name);
        List<JournalEntry> all = user.getJournalEnteries();
        if(all!=null && !all.isEmpty())
        {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry ){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            service.saveEntry(myEntry,name);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepoitory.findByUserName(name);
        List<JournalEntry> collect = user.getJournalEnteries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty())
        {
           return service.getById(id) ;
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Transactional
    @DeleteMapping("/id/{id}")
    public void deleteEntry(@PathVariable ObjectId id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepoitory.findByUserName(name);
        boolean removed = user.getJournalEnteries().removeIf(x -> x.getId().equals(id));
        if(removed)
        {
            userService.saveEntry(user);
            journalEntryRepository.deleteById(id);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry entry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepoitory.findByUserName(name);
        List<JournalEntry> collect = user.getJournalEnteries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty())
        {
            JournalEntry byId = service.getById(id).getBody();
            if(byId!=null)
            {
                return service.updateJournalById(id,entry,name);
            }
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @DeleteMapping("id/{userName}/{myId}")
    public void deleteJournalEntry(@PathVariable ObjectId myId,@PathVariable String userName)
    {
        service.deleteById(myId,userName);

    }

    @DeleteMapping("/deleteAll")
    public String deleteAll()
    {
        return service.deleteAll();
    }
}
