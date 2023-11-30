package com.example.service.impl;

import com.example.entity.Phone;
import com.example.entity.User;
import com.example.exception.CustomErrorException;
import com.example.repository.UserRepository;
import com.example.request.SaveUserPhoneRequest;
import com.example.request.SaveUserRequest;
import com.example.response.SaveUserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void registerOK() {
        // Given
        SaveUserRequest saveUserRequest = new SaveUserRequest();
        saveUserRequest.setName("alexander");
        saveUserRequest.setEmail("alex@gmail.com");
        saveUserRequest.setPassword("123456");

        SaveUserPhoneRequest saveUserPhoneRequest = new SaveUserPhoneRequest();
        saveUserPhoneRequest.setNumber("9876543231");
        saveUserPhoneRequest.setCityCode("PE");
        saveUserPhoneRequest.setCountryCode("51");

        saveUserRequest.setPhones(Collections.singletonList(saveUserPhoneRequest));

        User user = new User();
        user.setIdUser(1L);
        user.setName("alexander");
        user.setEmail("alex@gmail.com");
        user.setUsername("alex@gmail.com");
        user.setPassword("123456");
        user.setCreationDate(new Date());
        user.setActived(true);

        Phone phone = new Phone();
        phone.setNumber("9876543231");
        phone.setCityCode("PE");
        phone.setCountryCode("51");

        user.setPhones(Collections.singletonList(phone));

        when(userRepository.save(any())).thenReturn(user);
        // When
        SaveUserResponse response = userService.register(saveUserRequest);

        // Then
        assertNotNull(response);
        assertNotNull(response.getIdUser());
        assertEquals(1, response.getIdUser().intValue());
    }

    @Test
    void registerErrorEmail() {
        // Given
        SaveUserRequest saveUserRequest = new SaveUserRequest();
        saveUserRequest.setName("alexander");
        saveUserRequest.setEmail("alex@gmail.com");
        saveUserRequest.setPassword("123456");

        User user = new User();
        user.setIdUser(1L);
        user.setName("alexander");
        user.setEmail("alex@gmail.com");
        user.setUsername("alex@gmail.com");
        user.setPassword("123456");
        user.setCreationDate(new Date());
        user.setActived(true);

        when(userRepository.findByEmail(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        // When
        CustomErrorException errorException = assertThrows(CustomErrorException.class,
                () -> userService.register(saveUserRequest));

        // Then
        assertNotNull(errorException);
    }

}