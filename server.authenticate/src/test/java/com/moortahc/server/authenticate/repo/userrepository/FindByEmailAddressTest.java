package com.moortahc.server.authenticate.repo.userrepository;

import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.model.UserRole;
import com.moortahc.server.authenticate.repo.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.moortahc"})
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FindByEmailAddressTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private static final String GIVEN_EMAIL_ADDRESS = "email";
    private static final String GIVEN_PASSWORD = "password";
    
    @Before
    public void setUp(){
        userRepository.deleteAll();
        var givenUserEntity = UserEntity
                .builder()
                .createdDate(Instant.now())
                .emailAddress(GIVEN_EMAIL_ADDRESS)
                .firstName("Tom")
                .lastName("Jerry")
                .generatedName("GEN")
                .passwordHash(passwordEncoder.encode(GIVEN_PASSWORD))
                .id(0L)
                .roles(List.of(UserRole.USER))
                .build();
        userRepository.save(givenUserEntity);
    }
    
    @Test
    public void givenEmailAddressThenFindUserTest(){
        //given
        var givenEmailAddress = GIVEN_EMAIL_ADDRESS;
        
        //when
        var actualUserEntity = userRepository.findByEmailAddress(givenEmailAddress).orElseThrow();
        
        //then
        Assert.assertEquals(GIVEN_EMAIL_ADDRESS, actualUserEntity.getEmailAddress());
    }
    
    @Test
    public void givenWrongEmailAddressThenFailTest(){
        //given
        var givenWrongEmailAddress = GIVEN_EMAIL_ADDRESS+"Wrong";
        Assert.assertTrue(userRepository.findAll().size() == 1);
        
        //when
        var actualOpUserEntity = userRepository.findByEmailAddress(givenWrongEmailAddress);
        
        //then
        Assert.assertTrue(actualOpUserEntity.isEmpty());
    }
}