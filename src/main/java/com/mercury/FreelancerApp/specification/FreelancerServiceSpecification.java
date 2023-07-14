package com.mercury.FreelancerApp.specification;

import com.mercury.FreelancerApp.bean.FreelancerService;
import com.mercury.FreelancerApp.bean.constant.FreelancerServiceCategory;
import com.mercury.FreelancerApp.bean.constant.FreelancerServiceStatus;
import org.springframework.data.jpa.domain.Specification;

public class FreelancerServiceSpecification {
    public static Specification<FreelancerService> filterByPriceRange(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    public static Specification<FreelancerService> hasMinPrice(Double minPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<FreelancerService> hasMaxPrice(Double maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
    public static Specification<FreelancerService> filterByCategory(FreelancerServiceCategory category) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("serviceCategory"), category);
    }

    public static Specification<FreelancerService> filterByTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%");
    }

    public static Specification<FreelancerService> filterByServiceStatus(FreelancerServiceStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status != null) {
                return criteriaBuilder.equal(root.get("serviceStatus"), status);
            }
            return null;
        };
    }
}
