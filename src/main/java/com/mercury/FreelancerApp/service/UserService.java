package com.mercury.FreelancerApp.service;

import com.mercury.FreelancerApp.bean.User;
import com.mercury.FreelancerApp.dao.UserDao;
import com.mercury.FreelancerApp.exception.UserNotFoundException;
import com.mercury.FreelancerApp.http.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Response edit(User user, int userId) {
        System.out.println(user);
        User curUser = userDao.findById(userId).orElseThrow(UserNotFoundException::new);
        if(user.getEmail() != null && !user.getEmail().equals("")) curUser.setEmail(user.getEmail());
        if(user.getPassword() != null && !user.getPassword().equals("")) curUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getUsername() != null && !user.getUsername().equals("")) curUser.setUsername(user.getUsername());
        if(user.getProfileImage() != null) curUser.setProfileImage(user.getProfileImage());
        System.out.println(curUser);
        userDao.save(curUser);
        return new Response(true, "Profile Update Succeeded");
    }
}
