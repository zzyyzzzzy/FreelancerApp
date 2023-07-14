package com.mercury.FreelancerApp.vo;

import com.mercury.FreelancerApp.bean.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserVO {
    private int id;
    private String username;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String profileImage;
    private String email;
}
