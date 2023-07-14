package com.mercury.FreelancerApp.http;

import com.mercury.FreelancerApp.bean.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING) // tell spring this is a enum
    private Role role;
}
