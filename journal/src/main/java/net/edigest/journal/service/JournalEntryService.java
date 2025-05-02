package net.edigest.journal.service;

import net.edigest.journal.entity.JournalEntry;
import net.edigest.journal.entity.User;
import net.edigest.journal.repository.JournalEntryRepository;
import net.edigest.journal.repository.UserRepoitory;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;

    private static final Logger logger= LoggerFactory.getLogger(JournalEntryService.class);

    @Autowired
    UserRepoitory userRepoitory;

    @Autowired
    UserService userService;


    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String byUserName) {
        User user = userRepoitory.findByUserName(byUserName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry save = journalEntryRepository.save(journalEntry);  //saved to journalEntry
        user.getJournalEnteries().add(save); //saved to user
        userService.saveEntry(user);
    }

    public boolean deleteEntry(ObjectId id) {
        journalEntryRepository.deleteById((id));
        return true;
    }

    public ResponseEntity<JournalEntry> updateJournalById(ObjectId id, JournalEntry newEntry,String userName) {
       JournalEntry old = journalEntryRepository.findById(id).orElse(null);
       if(old!=null)
       {
           old.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().equals("")?newEntry.getTitle() :old.getTitle());
           old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent():old.getContent());
           saveEntry(newEntry);
       }
       journalEntryRepository.save(old);
       return new ResponseEntity<>(old,HttpStatus.OK);

    }

    private void saveEntry(JournalEntry newEntry) {
        journalEntryRepository.save(newEntry);
    }

    public ResponseEntity<JournalEntry> getById(ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
        if(journalEntry.isPresent())
        {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public String deleteAll() {
        journalEntryRepository.deleteAll();
        return "All records Deleted";
    }

    public void deleteById(ObjectId myId,String userName) {
        User user = userRepoitory.findByUserName(userName);
        user.getJournalEnteries().removeIf(x->x.getId().equals(myId));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(myId);
    }

    public List<JournalEntry> findByuserName(String userName)
    {
        return journalEntryRepository.findAll();
    }
}
