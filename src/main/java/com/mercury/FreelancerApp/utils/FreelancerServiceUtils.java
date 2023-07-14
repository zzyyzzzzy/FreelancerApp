package com.mercury.FreelancerApp.utils;

import com.mercury.FreelancerApp.bean.FreelancerService;
import com.mercury.FreelancerApp.bean.Review;
import com.mercury.FreelancerApp.bean.User;
import com.mercury.FreelancerApp.dto.FreelancerServiceDto;
import com.mercury.FreelancerApp.dto.FreelancerServiceWithDetailsDto;
import com.mercury.FreelancerApp.dto.FreelancerServiceWithUserDto;
import com.mercury.FreelancerApp.dto.FreelancerServicesResponse;
import com.mercury.FreelancerApp.dto.reviewsDto.ReviewDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class FreelancerServiceUtils {
    private static int calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0;
        }

        int totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        double average = (double) totalRating / reviews.size();
        return (int) Math.round(average);
    }
    public static FreelancerServiceWithDetailsDto mapToFreelancerServiceWithDetailsDto(FreelancerService freelancerService, User user, List<ReviewDto> reviewDtoList){
        List<Review> reviews = freelancerService.getReviews();
        int reviewCount = reviews.size();
        int average = calculateAverageRating(reviews);

        return FreelancerServiceWithDetailsDto.builder()
                .id(freelancerService.getId())
                .userId(freelancerService.getUserId())
                .title(freelancerService.getTitle())
                .price(freelancerService.getPrice())
                .description(freelancerService.getDescription())
                .thumbImage(freelancerService.getThumbImage())
                .serviceStatus(freelancerService.getServiceStatus())
                .serviceCategory(freelancerService.getServiceCategory())
                .serviceFiles(freelancerService.getServiceFiles())
                .serviceRequirements(freelancerService.getServiceRequirements())
                .reviews(reviewDtoList)
                .createdTime(freelancerService.getCreatedTime())
                .updateTime(freelancerService.getUpdateTime())
                .profileImage(user.getProfileImage())
                .username(user.getUsername())
                .freelancerEmail(user.getEmail())
                .reviewCount(reviewCount)
                .rating(average)
                .build();
    }
    public static FreelancerServiceDto mapToFreelancerServiceDto(FreelancerService freelancerService){
        return FreelancerServiceDto.builder()
                .id(freelancerService.getId())
                .userId(freelancerService.getUserId())
                .title(freelancerService.getTitle())
                .price(freelancerService.getPrice())
                .description(freelancerService.getDescription())
                .thumbImage(freelancerService.getThumbImage())
                .serviceStatus(freelancerService.getServiceStatus())
                .serviceCategory(freelancerService.getServiceCategory())
                .createdTime(freelancerService.getCreatedTime())
                .updateTime(freelancerService.getUpdateTime())
                .build();
    }
    public static FreelancerServiceDto mapToFreelancerServiceWithUserDto(FreelancerService freelancerService, User user){
        List<Review> reviews = freelancerService.getReviews();
        int reviewCount = reviews.size();
        int average = calculateAverageRating(reviews);

        return FreelancerServiceWithUserDto.builder()
                .id(freelancerService.getId())
                .userId(freelancerService.getUserId())
                .title(freelancerService.getTitle())
                .price(freelancerService.getPrice())
                .description(freelancerService.getDescription())
                .thumbImage(freelancerService.getThumbImage())
                .serviceStatus(freelancerService.getServiceStatus())
                .serviceCategory(freelancerService.getServiceCategory())
                .createdTime(freelancerService.getCreatedTime())
                .updateTime(freelancerService.getUpdateTime())
                .profileImage(user.getProfileImage())
                .username(user.getUsername())
                .freelancerEmail(user.getEmail())
                .reviewCount(reviewCount)
                .rating(average)
                .build();
    }

    public static FreelancerServicesResponse createResponse(List<FreelancerServiceDto> freelancerServicesDto, Page<FreelancerService> freelancerServicesPage){
        return FreelancerServicesResponse.builder()
                .content(freelancerServicesDto)
                .pageNo(freelancerServicesPage.getNumber())
                .pageSize(freelancerServicesPage.getSize())
                .totalPages(freelancerServicesPage.getTotalPages())
                .totalElements(freelancerServicesPage.getTotalElements())
                .last(freelancerServicesPage.isLast())
                .build();
    }
}