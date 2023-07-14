package com.mercury.FreelancerApp.controller;

import com.mercury.FreelancerApp.bean.FreelancerService;
import com.mercury.FreelancerApp.bean.Order;
import com.mercury.FreelancerApp.bean.OrderFile;
import com.mercury.FreelancerApp.dao.OrderDao;
import com.mercury.FreelancerApp.dto.ordersDto.OrderResponse;
import com.mercury.FreelancerApp.dto.ordersDto.OrderWithServiceInfoDto;
import com.mercury.FreelancerApp.http.Response;
import com.mercury.FreelancerApp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping
    public Response save(@RequestBody Order order){
//        String name = authentication.getName();
        System.out.println("triggerd");
        return orderService.save(order);
    }
//
//    @GetMapping
//    public List<Order> getAll(){
//        return orderService.getAll();
//    }

    @GetMapping
    public OrderResponse getOrderByUserId(
            Authentication authentication,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "orderStatus", defaultValue = "-100", required = false) int orderStatus){
        return orderService.getByUserId(authentication, pageNo, pageSize, orderStatus);
    }

    @GetMapping("/{orderId}")
    public OrderWithServiceInfoDto getOrderById(@PathVariable int orderId, Authentication authentication){
        return orderService.getById(orderId, authentication);
    }

    @PutMapping("/{orderId}/status/{newStatus}")
    public Response updateOrderStatus(@PathVariable int orderId, @PathVariable int newStatus){
        return orderService.updateOrderStatus(orderId, newStatus);
    }

    @PostMapping("/{orderId}/files")
    public Response saveOrderFiles(@PathVariable int orderId, @RequestBody List<OrderFile> orderFiles, Authentication authentication){
        return orderService.saveOrderFile(orderId, orderFiles, authentication);
    }
}
