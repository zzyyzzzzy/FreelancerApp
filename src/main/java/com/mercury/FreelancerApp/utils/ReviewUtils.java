package com.mercury.FreelancerApp.utils;

import com.mercury.FreelancerApp.bean.Review;
import com.mercury.FreelancerApp.bean.User;
import com.mercury.FreelancerApp.dto.reviewsDto.ReviewDto;

public class ReviewUtils {
    public static ReviewDto mapToReviewDto(Review review, User freelancer, User client){
        return ReviewDto.builder()
                .id(review.getId())
                .clientId(review.getClientId())
                .freelancerId(review.getFreelancerId())
                .comment(review.getComment())
                .rating(review.getRating())
                .createdTime(review.getCreatedTime())
                .updateTime(review.getUpdateTime())
                .clientImg(client.getProfileImage())
                .clientName(client.getUsername())
                .freelancerName(freelancer.getUsername())
                .freelancerImg(freelancer.getProfileImage())
                .build();
    }
}
