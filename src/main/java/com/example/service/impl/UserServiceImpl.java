package com.example.service.impl;

import com.example.entity.Phone;
import com.example.entity.User;
import com.example.exception.CustomErrorException;
import com.example.repository.UserRepository;
import com.example.request.SaveUserRequest;
import com.example.response.SaveUserResponse;
import com.example.security.JWTUtil;
import com.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SaveUserResponse register(SaveUserRequest request) {
        User userInDb = userRepository.findByEmail(request.getEmail());

        if (userInDb != null) {
            throw new CustomErrorException("The email of the user exist.");
        }

        User userToSave = getUser(request);

        User registeredUser = userRepository.save(userToSave);

        return getSaveUserResponse(registeredUser);
    }

    private User getUser(SaveUserRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setUsername(request.getEmail());
        user.setActived(true);
        user.setCreationDate(new Date());

        List<Phone> listPhones = request.getPhones().stream().map(saveUserPhoneRequest -> {
            Phone phone = new Phone();
            BeanUtils.copyProperties(saveUserPhoneRequest, phone);
            phone.setUser(user);
            return phone;
        }).collect(Collectors.toList());

        user.setPhones(listPhones);

        return user;
    }

    private SaveUserResponse getSaveUserResponse(User user) {
        SaveUserResponse response = new SaveUserResponse();
        BeanUtils.copyProperties(user, response);
        response.setToken(JWTUtil.generarToken(user.getUsername()));
        response.setLastLogin(getFormatDate(user.getCreationDate()));
        response.setCreatedDate(getFormatDate(user.getCreationDate()));
        response.setModificationDate(user.getModificationDate() != null ? getFormatDate(user.getCreationDate()) : null);
        return response;
    }

    private String getFormatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        return simpleDateFormat.format(date);
    }

}
