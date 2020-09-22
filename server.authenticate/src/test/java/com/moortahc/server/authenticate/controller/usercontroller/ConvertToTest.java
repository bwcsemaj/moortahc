package com.moortahc.server.authenticate.controller.usercontroller;

import com.moortahc.server.authenticate.AuthenticateDriver;
import com.moortahc.server.authenticate.controller.UserController;
import com.moortahc.server.authenticate.model.UserDto;
import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.model.UserRole;
import com.moortahc.server.common.utility.MoorTahcUtility;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = AuthenticateDriver.class)
@ActiveProfiles("test")
public class ConvertToTest {
    
    @Autowired
    private UserController userController;
    
    public static List<UserEntity> givenUserEntityWhenConvertingThenAllFieldsSameTest() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return List.of(
                UserEntity
                        .builder()
                        .createdDate(Instant.now())
                        .emailAddress("a")
                        .firstName("Tom")
                        .lastName("Jerry")
                        .generatedName("GEN")
                        .passwordHash(passwordEncoder.encode("password"))
                        .id(0L)
                        .roles(List.of(UserRole.USER))
                        .build(),
                UserEntity
                        .builder()
                        .createdDate(Instant.now())
                        .emailAddress("aaaaaaaaaaa!gf")
                        .firstName("agreger")
                        .lastName("Jerraasdfgagry")
                        .generatedName("GENFFFFFFFFFFFFFFFFFF")
                        .passwordHash(passwordEncoder.encode("v"))
                        .id(0L)
                        .roles(Lists.emptyList())
                        .build(),
                UserEntity
                        .builder()
                        .createdDate(Instant.now())
                        .emailAddress("a")
                        .firstName("Tom")
                        .lastName("Jerry")
                        .generatedName("GEN")
                        .passwordHash(passwordEncoder.encode("zgreaaqgaes@3223Fregv"))
                        .id(0L)
                        .roles(List.of(UserRole.USER, UserRole.ADMIN))
                        .build(),
                new UserEntity());
    }
    
    @ParameterizedTest
    @MethodSource
    public void givenUserEntityWhenConvertingThenAllFieldsSameTest(UserEntity userEntity) throws IllegalAccessException {
        //given
        var givenUserEntity = userEntity;
        
        //when
        var actualUserDto = UserController.convertTo(givenUserEntity);
        
        //then
        Assert.assertTrue(MoorTahcUtility.checkSameFieldValues(actualUserDto, givenUserEntity));
    }
}
