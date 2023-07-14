package com.mercury.FreelancerApp.dao;
import org.springframework.data.domain.Page;


import com.mercury.FreelancerApp.bean.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDao extends PagingAndSortingRepository<Order, Integer> {

    Page<Order> findByFreelancerId(int id, Pageable pageable);
    @Query("SELECT o FROM Order o WHERE o.freelancerId = :freelancerId AND o.orderStatus = :orderStatus")
    Page<Order> findByFreelancerIdAndOrderStatus(@Param("freelancerId") int freelancerId, @Param("orderStatus") int orderStatus, Pageable pageable);
    Page<Order> findByClientId(int id, Pageable pageable);
    List<Order> findByClientId(int id);
    @Query("SELECT o FROM Order o WHERE o.clientId = :clientId AND o.orderStatus = :orderStatus")
    Page<Order> findByClientIdAndOrderStatus(@Param("clientId") int clientId, @Param("orderStatus") int orderStatus, Pageable pageable);



}
