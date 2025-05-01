package net.edigest.journal.service;

import net.edigest.journal.entity.User;
import net.edigest.journal.repository.UserRepoitory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepoitory userRepoitory;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest()
    {

        when(userRepoitory.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Mockito").password("fkwrugfkhfe").roles(Arrays.asList("USER")).build());
        UserDetails user = userDetailsService.loadUserByUsername("Mockito");
        Assertions.assertNotNull(user);

    }

}