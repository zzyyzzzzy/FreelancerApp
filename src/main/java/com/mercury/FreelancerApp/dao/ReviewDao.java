package com.mercury.FreelancerApp.dao;

import com.mercury.FreelancerApp.bean.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewDao extends PagingAndSortingRepository<Review, Integer> {
    Page<Review> findByFreelancerId(int id, Pageable pageable);
    Optional<Review> findByFreelancerIdAndClientId(@Param("freelancerId") int freelancerId, @Param("clientId") int clientId);

    Optional<Review> findByOrderId(Integer orderId);
}


