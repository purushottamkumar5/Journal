package net.edigest.journal.service;

import net.edigest.journal.controller.UserController;
import net.edigest.journal.entity.User;
import net.edigest.journal.repository.UserRepoitory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepoitory userRepoitory;
    @Autowired
    UserController userController;

    @Test
    public void testfindByUserName()
    {
        User puru = userRepoitory.findByUserName("puru");
        assertTrue(!puru.getJournalEnteries().isEmpty());
//        assertNotNull(userRepoitory.findByUserName("puru"));
    }

    @Test
    public void testgetAllUsers()
    {
        assertNotNull(userController.getAll());
    }


  
}