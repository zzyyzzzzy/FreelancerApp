package com.mercury.FreelancerApp.controller;

import com.mercury.FreelancerApp.bean.User;
import com.mercury.FreelancerApp.http.Response;
import com.mercury.FreelancerApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/{userId}")
    public Response updateUser(@RequestBody User user, @PathVariable int userId) {
        return userService.edit(user, userId);
    }

}
