package com.mercury.FreelancerApp.dto.ordersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderInfoDto {
    private int id;
    private String requirementAnswer;
    private String serviceRequirement;
    private int requirementId;
    private Instant createdTime;

}
