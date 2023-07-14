package com.mercury.FreelancerApp.controller;

import com.mercury.FreelancerApp.bean.FreelancerService;
import com.mercury.FreelancerApp.bean.User;
import com.mercury.FreelancerApp.bean.constant.Role;
import com.mercury.FreelancerApp.dao.FreelancerServiceDao;
import com.mercury.FreelancerApp.dao.UserDao;
import com.mercury.FreelancerApp.http.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/generate")
@RequiredArgsConstructor
public class HelloController {
    private final UserDao userDao;
    private final FreelancerServiceDao freelancerServiceDao;
    @GetMapping
    public String hello(){
        return "Hello World! Updated";
    }
    @PostMapping("/freelancers")
    public Response genFreelancer(@RequestBody List<String> imgUrl){
        int length = userDao.findAll().size() + 1;
        List<User> users = new ArrayList<>();
        for(String url : imgUrl){
            User user = User.builder()
                    .username("free" + length)
                    .email("free" + length +"@gmail.com")
                    .password("$2a$10$GWNufwQq5Y0cRFFDOOl3B.u0orLcFecEr9GS36xasRL81IapdQf2m").role(Role.FREELANCER)
                    .profileImage(url)
                    .build();
            users.add(user);
            length++;
        }
        userDao.saveAll(users);
        return new Response(true);
    }

    @PostMapping("/services")
    public Response genServices(@RequestBody List<FreelancerService> services){
        List<User> users = userDao.findAll().stream().filter(user -> user.getRole() == Role.FREELANCER).collect(Collectors.toList());
        Random random = new Random();
        int min = 0, max = users.size();
        for(FreelancerService service : services){
            int randomNumber = random.nextInt(max - min + 1) + min;
            System.out.println(users.get(randomNumber).getRole());
            service.setUserId(users.get(randomNumber).getId());
            freelancerServiceDao.save(service);
        }
        return new Response(true);
    }

}
