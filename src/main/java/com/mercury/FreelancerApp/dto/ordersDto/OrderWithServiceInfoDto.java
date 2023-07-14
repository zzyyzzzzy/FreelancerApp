package com.mercury.FreelancerApp.dto.ordersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class OrderWithServiceInfoDto {
    private int id;
    private int freelancerId;
    private int clientId;
    private int serviceId;
    private int orderStatus;
    private double orderSubTotal;
    private double taxTotal;
    private double tipsTotal;
    private Timestamp dueDate;
    private Instant createdTime;
    private Instant updateTime;
    private String serviceTitle;
    private String serviceThumb;
}
