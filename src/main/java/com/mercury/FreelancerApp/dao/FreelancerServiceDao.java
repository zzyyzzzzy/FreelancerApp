package com.mercury.FreelancerApp.dao;

import com.mercury.FreelancerApp.bean.FreelancerService;
import com.mercury.FreelancerApp.bean.constant.FreelancerServiceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FreelancerServiceDao extends PagingAndSortingRepository<FreelancerService, Integer>, JpaSpecificationExecutor<FreelancerService> {
    Page<FreelancerService> findFreelancerServicesByUserId(int id, Pageable pageable);
    Page<FreelancerService> findFreelancerServicesByServiceCategory(FreelancerServiceCategory freelancerServiceCategory, Pageable pageable);
    Page<FreelancerService> findFreelancerServicesByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    Page<FreelancerService> findAll(Specification<FreelancerService> spec, Pageable pageable);


//    Page<FreelancerService> findAll(Example<FreelancerService> example, Pageable pageable);

}
