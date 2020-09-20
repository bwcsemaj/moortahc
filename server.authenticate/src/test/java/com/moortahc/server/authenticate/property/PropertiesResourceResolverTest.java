//package com.moortahc.server.authenticate.property;
//
//import com.moortahc.server.authenticate.AuthenticateDriver;
//import com.moortahc.server.common.property.PropertySourceResolver;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@ComponentScan(basePackages = {"com.moortahc"})
//@SpringBootTest()
//@ActiveProfiles("test")
//public class PropertiesResourceResolverTest {
//
//    @Autowired
//    private PropertySourceResolver propertySourceResolver;
//
//    @Test
//    public void givenWeAreTestingThenTestProfileSelected(){
//        //given
//        var givenActiveProfile = "test";
//        Assert.assertNotNull(propertySourceResolver);
//
//        //when
//        var actualActiveProfile = propertySourceResolver.getActiveProfile();
//
//        //then
//        Assert.assertEquals(givenActiveProfile, actualActiveProfile);
//    }
//
//    @Test
//    public void givenWeAreTestingThenUseTestingPropertiesFile(){
//        //given
//        var givenApplicationPropertiesType = "test";
//        Assert.assertNotNull(propertySourceResolver);
//
//        //when
//        var actualApplicationPropertiesType = propertySourceResolver.getApplicationPropertiesType();
//
//        //then
//        Assert.assertEquals(givenApplicationPropertiesType, actualApplicationPropertiesType);
//    }
//}
