package com.moortahc.server.authenticate.service.userdetailsserviceimpl;

import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.model.UserRole;
import com.moortahc.server.authenticate.repo.UserRepository;
import com.moortahc.server.authenticate.repo.exception.UserDoesNotExistException;
import com.moortahc.server.authenticate.service.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.moortahc"})
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserDetailsServiceImplTest {
    
    @MockBean
    private UserRepository userRepositoryMock;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    private static final String GIVEN_EMAIL_ADDRESS = "email";
    private static final String GIVEN_PASSWORD = "password";
    
    @Test
    public void givenValidEmailAddressThenGetUserDetailsTest() {
        //given
        // user the saved UserEntity emailAddress
        
        //when
        Mockito.when(userRepositoryMock.findByEmailAddress(GIVEN_EMAIL_ADDRESS))
                .thenReturn(Optional.of(UserEntity
                        .builder()
                        .createdDate(LocalDateTime.now())
                        .emailAddress(GIVEN_EMAIL_ADDRESS)
                        .firstName("Tom")
                        .lastName("Jerry")
                        .generatedName("GEN")
                        .passwordHash(passwordEncoder.encode(GIVEN_PASSWORD))
                        .id(0L)
                        .roles(List.of(UserRole.USER))
                        .build()));
        var actualUserDetail = userDetailsService.loadUserByUsername(GIVEN_EMAIL_ADDRESS);
        
        //then
        Assert.assertEquals(GIVEN_EMAIL_ADDRESS, actualUserDetail.getUsername());
        Assert.assertTrue(passwordEncoder.matches(GIVEN_PASSWORD, actualUserDetail.getPassword()));
    }
    
    @Test(expected = UsernameNotFoundException.class)
    public void givenInValidEmailAddressThenThrowUsernameNotFoundExceptionTest() {
        //given
        // user the saved UserEntity emailAddress
        
        //when
        Mockito.when(userRepositoryMock.findByEmailAddress(GIVEN_EMAIL_ADDRESS))
                .thenThrow(new UsernameNotFoundException(""));
        var actualUserDetail = userDetailsService.loadUserByUsername(GIVEN_EMAIL_ADDRESS);
        
        //then
        // exception should be thrown
    }
    
}
