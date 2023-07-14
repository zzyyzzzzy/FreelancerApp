package com.mercury.FreelancerApp.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class FreelancerServiceWithUserDto extends FreelancerServiceDto{
    private String profileImage;
    private String username;
    private String freelancerEmail;


}
