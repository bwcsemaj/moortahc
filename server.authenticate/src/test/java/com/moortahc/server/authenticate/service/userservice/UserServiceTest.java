package com.moortahc.server.authenticate.service.userservice;

import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.model.UserRole;
import com.moortahc.server.authenticate.repo.UserRepository;
import com.moortahc.server.authenticate.repo.exception.UserDoesNotExistException;
import com.moortahc.server.authenticate.service.UserService;
import com.moortahc.server.authenticate.service.exceptions.InvalidUserCredentialsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
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
public class UserServiceTest {
    
    @MockBean
    private UserRepository userRepositoryMock;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserService userService;
    
    private static final String GIVEN_EMAIL_ADDRESS = "emailAddress";
    private static final String GIVEN_PASSWORD = "password";
    private UserEntity GIVEN_USER_ENTITY;
    
    @Before
    public void setUp(){
        GIVEN_USER_ENTITY = UserEntity
                .builder()
                .createdDate(LocalDateTime.now())
                .emailAddress(GIVEN_EMAIL_ADDRESS)
                .firstName("Tom")
                .lastName("Jerry")
                .generatedName("GEN")
                .passwordHash(passwordEncoder.encode(GIVEN_PASSWORD))
                .id(0L)
                .roles(List.of(UserRole.USER))
                .build();
    }
    
    @Test
    public void givenValidCredsThenReturnUserEntityTest() throws UserDoesNotExistException, InvalidUserCredentialsException {
        //given
        Mockito.when(userRepositoryMock.findByEmailAddress(GIVEN_EMAIL_ADDRESS)).thenReturn(Optional.of(GIVEN_USER_ENTITY));
        
        //when
        var actualUserEntity = userService.validateCredentials(GIVEN_EMAIL_ADDRESS, GIVEN_PASSWORD);
        
        //then
        Assert.assertEquals(GIVEN_USER_ENTITY, actualUserEntity);
    }
    
    @Test(expected = InvalidUserCredentialsException.class)
    public void givenInValidPasswordThenThrowInvalidUserCredentialsExceptionTest() throws UserDoesNotExistException, InvalidUserCredentialsException {
        //given
        var givenInvalidPassword = GIVEN_PASSWORD+"1";
        Mockito.when(userRepositoryMock.findByEmailAddress(GIVEN_EMAIL_ADDRESS)).thenReturn(Optional.of(GIVEN_USER_ENTITY));
    
        //when
        var actualUserEntity = userService.validateCredentials(GIVEN_EMAIL_ADDRESS, givenInvalidPassword);
    
        //then
        // throw exception
    }
    
    @Test(expected = UserDoesNotExistException.class)
    public void givenInValidEmailAddressThenThrowInvalidUserCredentialsExceptionTest() throws UserDoesNotExistException, InvalidUserCredentialsException {
        //given
        String givenInvalidEmailAddress = null;
        Mockito.when(userRepositoryMock.findByEmailAddress(GIVEN_EMAIL_ADDRESS)).thenReturn(Optional.of(GIVEN_USER_ENTITY));
        
        //when
        var actualUserEntity = userService.validateCredentials(givenInvalidEmailAddress, GIVEN_PASSWORD);
        
        //then
        // throw exception
    }
}
