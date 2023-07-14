package com.mercury.FreelancerApp.dto.reviewsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReviewDto {
    private int id;
    private int freelancerId;
    private int clientId;
    private int rating;
    private String comment;
    private Instant createdTime;
    private Instant updateTime;

    private String freelancerName;
    private String freelancerImg;
    private String clientName;
    private String clientImg;
}
