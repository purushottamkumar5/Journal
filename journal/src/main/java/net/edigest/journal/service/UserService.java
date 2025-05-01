package net.edigest.journal.service;

import net.edigest.journal.entity.JournalEntry;
import net.edigest.journal.entity.User;
import net.edigest.journal.repository.UserRepoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepoitory userRepoitory;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();



//    public User saveEntryy(User myEntry,String userName) {
//        return userRepoitory.save(myEntry);
//
//    }

//    public boolean deleteEntry(ObjectId id) {
//        userRepoitory.deleteById((id));
//        return true;
//    }

    public ResponseEntity<User> updateUserByuserName(String userName,User user) {
        User byUserName = userRepoitory.findByUserName(userName);
        if(byUserName !=null)
        {
            byUserName.setUserName(user.getUserName());
            byUserName.setPassword(user.getPassword());
            userRepoitory.save(byUserName);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public List<User> getAll() {
        return userRepoitory.findAll();
    }

    public User saveEntry(User user) {
        return userRepoitory.save(user);
    }

    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userRepoitory.save(user);
    }

    public void deleteByuserName(String name) {
        userRepoitory.deleteByuserName(name);
    }

    public List<User> getAllUsers() {
        return userRepoitory.findAll();
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepoitory.save(user);
    }


//    public ResponseEntity<User> getById(ObjectId id) {
//        Optional<User> user = userRepoitory.findById(id);
//        if(user.isPresent())
//        {
//            return new ResponseEntity<>(user.get(), HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }

//    public String deleteAll() {
//        userRepoitory.deleteAll();
//        return "All records Deleted";
//    }
//    public User findByuserName(String userName)
//    {
//        return userRepoitory.findByUserName(userName);
//    }
}
