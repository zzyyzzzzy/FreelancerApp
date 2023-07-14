package com.mercury.FreelancerApp.service;

import com.mercury.FreelancerApp.bean.*;
import com.mercury.FreelancerApp.bean.constant.FreelancerServiceCategory;
import com.mercury.FreelancerApp.bean.constant.FreelancerServiceStatus;
import com.mercury.FreelancerApp.dao.FreelancerServiceDao;
import com.mercury.FreelancerApp.dao.FreelancerServiceFileDao;
import com.mercury.FreelancerApp.dao.FreelancerServiceRequirementDao;
import com.mercury.FreelancerApp.dao.UserDao;
import com.mercury.FreelancerApp.dto.FreelancerServiceDto;
import com.mercury.FreelancerApp.dto.FreelancerServiceWithDetailsDto;
import com.mercury.FreelancerApp.dto.FreelancerServicesResponse;
import com.mercury.FreelancerApp.dto.reviewsDto.ReviewDto;
import com.mercury.FreelancerApp.exception.*;
import com.mercury.FreelancerApp.http.Response;
import com.mercury.FreelancerApp.specification.FreelancerServiceSpecification;
import com.mercury.FreelancerApp.utils.FreelancerServiceUtils;
import com.mercury.FreelancerApp.utils.ReviewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FreelancerServicesService {
    private String DEFAULT_THUMB = "https://firebasestorage.googleapis.com/v0/b/email-replier-b1ad4.appspot.com/o/common%2Fdefault-placeholder.png?alt=media&token=e2335e0f-f665-4afd-ba6a-a10f3c0fb470";
    @Autowired
    private FreelancerServiceDao freelancerServiceDao;
    @Autowired
    private FreelancerServiceRequirementDao freelancerServiceRequirementDao;
    @Autowired
    private FreelancerServiceFileDao freelancerServiceFileDao;
    @Autowired
    private UserDao userDao;
    private List<String> allowedSortFields = List.of("createdTime", "updateTime", "price");

    private void checkIfAuthorized(Authentication authentication, FreelancerService freelancerService){
        User user = (User) authentication.getPrincipal();
        if(freelancerService.getUserId() != user.getId()) throw new UserNotAuthorizedException();
    }

    private FreelancerServicesResponse getFreelancerServicesWithUserResponse(Page<FreelancerService> freelancerServicesPage) {
        List<FreelancerService> freelancerServices = freelancerServicesPage.getContent();
        List<FreelancerServiceDto> freelancerServicesWithUserDto = freelancerServices
                .stream()
                .map(service -> {
                    User user = userDao.findById(service.getUserId()).orElseThrow(UserNotFoundException::new);
                    return FreelancerServiceUtils.mapToFreelancerServiceWithUserDto(service, user);
                }).collect(Collectors.toList());
        return FreelancerServiceUtils.createResponse(freelancerServicesWithUserDto, freelancerServicesPage);
    }
    public FreelancerServiceWithDetailsDto getByFreelancerServiceId(int id){
        FreelancerService freelancerService = freelancerServiceDao.findById(id).orElseThrow(ServiceNotFoundException::new);
        User freelancer = userDao.findById(freelancerService.getUserId()).orElseThrow(UserNotFoundException::new);
        List<ReviewDto> reviewDtoList = freelancerService.getReviews().stream()
                .map(review -> {
                    User client = userDao.findById(review.getClientId()).orElseThrow(UserNotAuthorizedException::new);
                    return ReviewUtils.mapToReviewDto(review, freelancer, client);
                }).collect(Collectors.toList());
        return FreelancerServiceUtils.mapToFreelancerServiceWithDetailsDto(freelancerService, freelancer, reviewDtoList);
    }

//    public FreelancerServicesResponse searchFreelancerServicesByTitle(String keyword, int pageNo, int pageSize){
//        if(keyword.equals("")) return getAll(pageNo, pageSize, FreelancerServiceCategory.ALL, keyword);
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        Page<FreelancerService> freelancerServicesPage = freelancerServiceDao.findFreelancerServicesByTitleContainingIgnoreCase(keyword, pageable);
//        return getFreelancerServicesWithUserResponse(freelancerServicesPage);
//    }

    public FreelancerServicesResponse getByFreelancerId(int id, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<FreelancerService> freelancerServicesPage = freelancerServiceDao.findFreelancerServicesByUserId(id, pageable);
        List<FreelancerService> freelancerServices = freelancerServicesPage.getContent();
        List<FreelancerServiceDto> freelancerServicesDto = freelancerServices.stream()
                .map(FreelancerServiceUtils::mapToFreelancerServiceDto)
                .collect(Collectors.toList());
        return FreelancerServiceUtils.createResponse(freelancerServicesDto, freelancerServicesPage);
    }
    public FreelancerServicesResponse getAll(int pageNo, int pageSize, FreelancerServiceCategory getByCategory, String title, Double minPrice, Double maxPrice, String sortBy, boolean ascending){
        try{
            System.out.println(title);
            Specification<FreelancerService> spec = Specification.where(null);
            Sort sort = null;
            if(title != null) spec = spec.and(FreelancerServiceSpecification.filterByTitle(title));
            if(getByCategory != null) spec = spec.and(FreelancerServiceSpecification.filterByCategory(getByCategory));

            // for minMaxPrice
            if (minPrice != null && maxPrice != null) {
                spec = spec.and(FreelancerServiceSpecification.filterByPriceRange(minPrice, maxPrice));
            } else if (minPrice != null) {
                spec = spec.and(FreelancerServiceSpecification.hasMinPrice(minPrice));
            } else if (maxPrice != null) {
                spec = spec.and(FreelancerServiceSpecification.hasMaxPrice(maxPrice));
            }
            spec = spec.and(FreelancerServiceSpecification.filterByServiceStatus(FreelancerServiceStatus.ACTIVE));

            // for sortBy
            if(sortBy != null && allowedSortFields.contains(sortBy)){
                Sort.Direction sortDirection = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
                sort = Sort.by(sortDirection, sortBy);
            }

            Pageable pageable = sort == null ? PageRequest.of(pageNo, pageSize) : PageRequest.of(pageNo, pageSize, sort);;
            Page<FreelancerService> freelancerServicesPage = freelancerServiceDao.findAll(spec, pageable);

            return getFreelancerServicesWithUserResponse(freelancerServicesPage);
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidInputException("Invalid getAll request");
        }
    }
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Response save(FreelancerService freelancerService, Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            freelancerService.setUserId(user.getId());
            freelancerService.setServiceStatus(FreelancerServiceStatus.ACTIVE);
            if (freelancerService.getThumbImage() == null) freelancerService.setThumbImage(DEFAULT_THUMB);
            freelancerServiceDao.save(freelancerService);
            return new Response(true);
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidInputException("Information provided is not valid, please try again");
        }
    }

    public Response update(FreelancerServiceWithDetailsDto serviceDto, Authentication authentication){
        try{
            User user = (User) authentication.getPrincipal();
            if(!serviceDto.getUsername().equals(user.getUsername())) throw new UserNotAuthorizedException("You cant update this order");

            FreelancerService freelancerService = freelancerServiceDao.findById(serviceDto.getId()).orElseThrow(ServiceNotFoundException::new);
            List<FreelancerServiceFile> oldFiles = freelancerService.getServiceFiles();
            List<FreelancerServiceRequirement> oldRequirements = freelancerService.getServiceRequirements();
            List<FreelancerServiceFile> newFiles = serviceDto.getServiceFiles();
            List<FreelancerServiceRequirement> newRequirements = serviceDto.getServiceRequirements();

            freelancerService.setTitle(serviceDto.getTitle());
            freelancerService.setDescription(serviceDto.getDescription());
            freelancerService.setPrice(serviceDto.getPrice());
            freelancerService.setServiceCategory(serviceDto.getServiceCategory());

            newFiles.forEach(newFile -> {
                newFile.setFreelancerService(freelancerService);
            });
            oldFiles = oldFiles.stream().filter(oldFile -> {
                boolean isDeleted = !newFiles.contains(oldFile);
                if(oldFile.getFileLocation().equals(freelancerService.getThumbImage()) && isDeleted){
                    freelancerService.setThumbImage(DEFAULT_THUMB);
                }
                return isDeleted;
            }).collect(Collectors.toList());

            newRequirements.forEach(newRequirement -> {
                newRequirement.setFreelancerService(freelancerService);
            });
            oldRequirements = oldRequirements.stream()
                    .filter(oldRequirement -> !newRequirements.contains(oldRequirement))
                    .collect(Collectors.toList());

            freelancerService.setServiceFiles(newFiles);
            freelancerService.setServiceRequirements(newRequirements);
            freelancerServiceDao.save(freelancerService);
            freelancerServiceFileDao.deleteAll(oldFiles);
            freelancerServiceRequirementDao.deleteAll(oldRequirements);

            return new Response(true, "Service Updated");
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidInputException("Information provided is not valid, please try again");
        }
    }
    public Response updateServiceStatus(int serviceId, FreelancerServiceStatus newStatus, Authentication authentication){
        FreelancerService service = freelancerServiceDao.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        checkIfAuthorized(authentication, service);
        service.setServiceStatus(newStatus);
        freelancerServiceDao.save(service);
        return new Response(true, "Service Status Updated");
    }
    public Response updateServiceThumbImg(int serviceId, int fileId, Authentication authentication){
        System.out.println("------------------------- " + serviceId + " " + fileId);
        FreelancerService freelancerService = freelancerServiceDao.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        checkIfAuthorized(authentication, freelancerService);
        FreelancerServiceFile file = freelancerServiceFileDao.findById(fileId).orElseThrow(OrderServiceFileNotFoundException::new);
        freelancerService.setThumbImage(file.getFileLocation());
        freelancerServiceDao.save(freelancerService);
        return new Response(true, "Thumb Image Updated");
    }
    public Response saveServiceFile(int serviceId, List<FreelancerServiceFile> serviceFiles, Authentication authentication){
        try{
            FreelancerService freelancerService = freelancerServiceDao.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
            checkIfAuthorized(authentication, freelancerService);
            serviceFiles.forEach(serviceFile -> {
                serviceFile.setFreelancerService(freelancerService);
                freelancerServiceFileDao.save(serviceFile);
            });
            return new Response(true, "file uploaded!");
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidInputException("Information provided is not valid, please try again");
        }
    }
    public Response deleteServiceFile(int fileId, Authentication authentication){
        try{
            FreelancerServiceFile serviceFile = freelancerServiceFileDao.findById(fileId).orElseThrow(OrderServiceFileNotFoundException::new);
            FreelancerService freelancerService =  serviceFile.getFreelancerService();
            checkIfAuthorized(authentication, freelancerService);
            if(serviceFile.getFileLocation().equals(freelancerService.getThumbImage())){
                freelancerService.setThumbImage(DEFAULT_THUMB);
            }
            freelancerServiceFileDao.deleteById(fileId);
            return new Response(true, "file delete!");
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidInputException("Information provided is not valid, please try again");
        }
    }
}
