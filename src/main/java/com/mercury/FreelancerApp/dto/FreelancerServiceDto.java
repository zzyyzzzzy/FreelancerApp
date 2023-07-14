package com.mercury.FreelancerApp.dto;

import com.mercury.FreelancerApp.bean.constant.FreelancerServiceCategory;
import com.mercury.FreelancerApp.bean.constant.FreelancerServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FreelancerServiceDto {
    private int id;
    private int userId;
    private String title;
    private double price;
    private String description;
    private String thumbImage;
    private int reviewCount;
    private int rating;
    private FreelancerServiceStatus serviceStatus;
    private FreelancerServiceCategory serviceCategory;
    private Instant createdTime;
    private Instant updateTime;

}