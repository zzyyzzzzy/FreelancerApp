package com.mercury.FreelancerApp.controller;

import com.mercury.FreelancerApp.bean.FreelancerService;
import com.mercury.FreelancerApp.bean.FreelancerServiceFile;
import com.mercury.FreelancerApp.bean.Review;
import com.mercury.FreelancerApp.bean.constant.FreelancerServiceCategory;
import com.mercury.FreelancerApp.bean.constant.FreelancerServiceStatus;
import com.mercury.FreelancerApp.dto.FreelancerServiceWithDetailsDto;
import com.mercury.FreelancerApp.dto.FreelancerServicesResponse;
import com.mercury.FreelancerApp.http.Response;
import com.mercury.FreelancerApp.service.FreelancerServicesService;
import com.mercury.FreelancerApp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/services")
public class FreelancerServiceController {
    private final FreelancerServicesService freelancerServicesService;
    private final ReviewService reviewService;
    @GetMapping
    public FreelancerServicesResponse getAll(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "category", required = false) FreelancerServiceCategory category,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "ascending", required = false) boolean ascending,
            @RequestParam(value = "title", required = false) String title) {
        return freelancerServicesService.getAll(pageNo, pageSize, category, title, minPrice, maxPrice, sortBy, ascending);
    }
    @GetMapping("/{serviceId}")
    public FreelancerServiceWithDetailsDto getByServiceId(@PathVariable int serviceId) {
        return freelancerServicesService.getByFreelancerServiceId(serviceId);
    }
    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping("/reviews")
    public Response saveReview(@RequestBody Review review){
        return reviewService.save(review);
    }

    @PreAuthorize("hasAuthority('FREELANCER')")
    @GetMapping("/freelancers/{freelancerId}")
    public FreelancerServicesResponse getServicesByFreelancerId(
            @PathVariable int freelancerId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return freelancerServicesService.getByFreelancerId(freelancerId, pageNo, pageSize);
    }
    @PreAuthorize("hasAuthority('FREELANCER')")
    @PostMapping
    public Response save(@RequestBody FreelancerService freelancerService, Authentication authentication){
//        String name = authentication.getName();
        return freelancerServicesService.save(freelancerService, authentication);
    }
    @PreAuthorize("hasAuthority('FREELANCER')")
    @PutMapping("/{serviceId}/status/{newStatus}")
    public Response updateServiceStatus(@PathVariable int serviceId, @PathVariable FreelancerServiceStatus newStatus, Authentication authentication){
        return freelancerServicesService.updateServiceStatus(serviceId, newStatus, authentication);
    }

    @PreAuthorize("hasAuthority('FREELANCER')")
    @PutMapping
    public Response update(@RequestBody FreelancerServiceWithDetailsDto serviceDto, Authentication authentication){
//        String name = authentication.getName();
        return freelancerServicesService.update(serviceDto, authentication);
    }

    @PreAuthorize("hasAuthority('FREELANCER')")
    @PostMapping("/{serviceId}/files")
    public Response saveServiceFile(@PathVariable int serviceId, @RequestBody List<FreelancerServiceFile> serviceFiles, Authentication authentication){
        return freelancerServicesService.saveServiceFile(serviceId, serviceFiles, authentication);
    }
    @PreAuthorize("hasAuthority('FREELANCER')")
    @DeleteMapping("/{serviceId}/files/{fileId}")
    public Response deleteServiceFile(@PathVariable int serviceId, @PathVariable int fileId, Authentication authentication){
        return freelancerServicesService.deleteServiceFile(fileId, authentication);
    }

    @PreAuthorize("hasAuthority('FREELANCER')")
    @PutMapping("/{serviceId}/files/{fileId}")
    public Response updateServiceThumbImg(@PathVariable int serviceId, @PathVariable int fileId, Authentication authentication){
        return freelancerServicesService.updateServiceThumbImg(serviceId, fileId, authentication);
    }

}
