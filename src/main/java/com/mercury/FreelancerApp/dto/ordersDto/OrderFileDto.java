package com.mercury.FreelancerApp.dto.ordersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderFileDto {
    private int id;
    private String fileName;
    private String fileLocation;
    private String username;
    private String thumbImg;
    private Instant createdTime;
}
