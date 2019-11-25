package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.UserConverter;
import com.eltae.compareTout.dto.user.UserDto;
import com.eltae.compareTout.entities.User;
import org.junit.Assert;
import org.springframework.security.core.userdetails.UserDetails;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository ;

    @MockBean
    UserConverter userConverter ;

   // @Test
   // public void testGetUserWhenCorrectMail(){
   //     //Entry
    //    String mail="email@email";
        //Effective
      //  Mockito.when(userRepository.findByEmail(mail)).thenReturn(Optional.of(User.builder().build()));
        //Expected
        //UserDetails result = userService.loadUserByUsername(mail);
        //assertNotNull(result);
    //}

    @Test(expected = ApplicationException.class)
    public void testGetUserWhenUnknownMailReturnApplicationException(){
            //Entry
            String email="Unknown@know";
            //Mock
            Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
            UserDetails result = userService.loadUserByUsername(email);
    }

    @Test
    public void testGetUserWhenCorrectId(){
        //Entry
        Long id= 1l;
        //Effective
        UserDto userdto=new UserDto();
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(User.builder().build()));
        //Expected
        UserDto result = userService.getById(id);
        assertEquals(userdto.getClass(),result.getClass());
    }

    @Test(expected = ApplicationException.class)
    public void testGetUserWhenUnknownIdReturnApplicationException(){
        //Entry
        Long id= 1l;
        //Mock
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        UserDto result = userService.getById(id);
    }

    @Test
    public void testCreateUser(){

        User user = new User();
        user.setEmail("email");
        userRepository.save(user);
        User userresponse=userRepository.findByEmail("email").get();
        Assert.assertNotNull(userresponse);
    }

  }
