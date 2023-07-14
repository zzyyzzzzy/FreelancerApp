package com.mercury.FreelancerApp.dto;

import com.mercury.FreelancerApp.bean.FreelancerServiceFile;
import com.mercury.FreelancerApp.bean.FreelancerServiceRequirement;
import com.mercury.FreelancerApp.bean.Review;
import com.mercury.FreelancerApp.dto.reviewsDto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class FreelancerServiceWithDetailsDto extends FreelancerServiceWithUserDto{
    private List<FreelancerServiceFile> serviceFiles;
    private List<FreelancerServiceRequirement> serviceRequirements;
    private List<ReviewDto> reviews;

}
