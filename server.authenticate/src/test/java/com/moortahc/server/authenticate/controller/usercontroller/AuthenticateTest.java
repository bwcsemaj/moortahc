package com.moortahc.server.authenticate.controller.usercontroller;

import com.moortahc.server.authenticate.model.LoginCredentials;
import com.moortahc.server.authenticate.model.UserDto;
import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.repo.exception.UserDoesNotExistException;
import com.moortahc.server.authenticate.security.UserCredentials;
import com.moortahc.server.authenticate.service.UserService;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.engine.discovery.PackageSelector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.moortahc"})
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticateTest {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userServiceMock;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Test
    public void contextLoads() throws Exception {
       Assert.assertNotNull(mockMvc);
    }
    
    public static void displayAllBeans(ApplicationContext applicationContext) {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }
    
    @Test
    public void givenWrongCredsWhenAuthThenFailTest() throws Exception {
        //given
        var givenWrongCreds = new UserCredentials("bad", "notpassword");
        
        //when
        Mockito.when(userServiceMock.validateCredentials(givenWrongCreds.getEmailAddress(), givenWrongCreds.getPassword()))
                .thenThrow(new UserDoesNotExistException(""));

        //then
        mockMvc.perform(get(String.format("/authenticate2?emailAddress=%s&password=%s", givenWrongCreds.getEmailAddress(), givenWrongCreds.getPassword())))
                .andExpect(status().is4xxClientError());
    
//        mockMvc.perform(get("/authenticate2"))
//                .andExpect(status().is4xxClientError())
//                .andExpect(status().isOk())
//                .andExpect(view().name("todo/list"))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/list.jsp"))
//                .andExpect(model().attribute("todos", hasSize(2)))
//                .andExpect(model().attribute("todos", hasItem(
//                        allOf(
//                                hasProperty("id", is(1L)),
//                                hasProperty("description", is("Lorem ipsum")),
//                                hasProperty("title", is("Foo"))
//                        )
//                )))
//                .andExpect(model().attribute("todos", hasItem(
//                        allOf(
//                                hasProperty("id", is(2L)),
//                                hasProperty("description", is("Lorem ipsum")),
//                                hasProperty("title", is("Bar"))
//                        )
//                )));
        
        
        verify(userServiceMock, times(1))
                .validateCredentials(givenWrongCreds.getEmailAddress(), givenWrongCreds.getPassword());
        verifyNoMoreInteractions(userServiceMock);
    }
    
    @Test
    public void givenCorrectCredsWhenAuthThenUserDtoTest() throws Exception {
        //given
        var givenCorrectCreds = new UserCredentials("correct", "password");
        
        //when
        Mockito.when(userServiceMock.validateCredentials(givenCorrectCreds.getEmailAddress(), givenCorrectCreds.getPassword()))
                .thenReturn(UserEntity
                        .builder()
                        .createdDate(Instant.now())
                        .emailAddress("correct")
                        .firstName("agreger")
                        .lastName("Jerraasdfgagry")
                        .generatedName("GENFFFFFFFFFFFFFFFFFF")
                        .passwordHash(passwordEncoder.encode("password"))
                        .id(0L)
                        .roles(Lists.emptyList())
                        .build());
        
        //then
        mockMvc.perform(get(String.format("/authenticate2?emailAddress=%s&password=%s", givenCorrectCreds.getEmailAddress(), givenCorrectCreds.getPassword())))
                .andExpect(status().is2xxSuccessful());
        
        verify(userServiceMock, times(1))
                .validateCredentials(givenCorrectCreds.getEmailAddress(), givenCorrectCreds.getPassword());
        verifyNoMoreInteractions(userServiceMock);
    }
}
