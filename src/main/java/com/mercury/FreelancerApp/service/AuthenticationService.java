package com.mercury.FreelancerApp.service;

import com.mercury.FreelancerApp.bean.User;
import com.mercury.FreelancerApp.dao.UserDao;
import com.mercury.FreelancerApp.exception.InvalidInputException;
import com.mercury.FreelancerApp.exception.UserAlreadyExistException;
import com.mercury.FreelancerApp.jwt.JwtService;
import com.mercury.FreelancerApp.vo.UserVO;
import com.mercury.FreelancerApp.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public AuthenticationResponse register(RegisterRequest request){
        try{
//            if(request.getRole() == Role.MANAGER){
//                throw new Exception("Cannot crate an manager");
//            }
            User existedUserName =userDao.findByUsername(request.getUsername()).orElse(null);
            User existedEmail =userDao.findByUsername(request.getEmail()).orElse(null);

            if(existedUserName != null || existedEmail != null) throw new UserAlreadyExistException();

            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .profileImage("https://firebasestorage.googleapis.com/v0/b/email-replier-b1ad4.appspot.com/o/common%2Fprofile_image.png?alt=media&token=2b410399-daa7-42b7-9f8b-6e2cf5661655")
                    .build();
            System.out.println(user);
            userDao.save(user);
            String jwtToken = jwtService.generateToken(user);
            String firebaseToken = jwtService.generateFirebaseCustomToken(user.getUsername());
            return new AuthenticationResponse(jwtToken, firebaseToken,true);

        }catch (UserAlreadyExistException e){
            throw new UserAlreadyExistException();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new InvalidInputException();
        }
    }
    // if username or password is incorrect -> throw an exception
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userDao.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        String firebaseToken = jwtService.generateFirebaseCustomToken(user.getUsername());
        return new AuthenticationResponse(jwtToken, firebaseToken,true);
    }

    public Response checkLogin(Authentication authentication) {
        if (authentication != null) {
            User user = userDao.findByUsername(authentication.getName()).orElse(null);
            UserVO userVO = null;
            if(user != null){
                userVO = new UserVO(user.getId(), user.getUsername(), user.getRole(), user.getProfileImage(), user.getEmail());
            }
            Response response = new AuthenticationSuccessResponse(true, 200, "Logged In!", userVO);
            return response;
        } else {
            return new Response(false, "Not logged In");
        }
    }
}
