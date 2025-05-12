package net.edigest.journal.service;

import net.edigest.journal.controller.UserController;
import net.edigest.journal.entity.User;
import net.edigest.journal.repository.UserRepoitory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @Disabled
//    @CsvSource({
//            "puru",
//            "evening"
//    })
    @ValueSource(strings = {
            "puru"
    })
    public void testFindByUserNames(String userName)
    {
        User User = userRepoitory.findByUserName(userName);
        assertNotNull(User,"failed for "+userName);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "3,3,6"
    })
    public void test(int a, int b, int expected)
    {
        assertEquals(expected,a+b);
    }



  
}