package com.mercury.FreelancerApp.service;

import com.mercury.FreelancerApp.bean.FreelancerServiceRequirement;
import com.mercury.FreelancerApp.bean.Order;
import com.mercury.FreelancerApp.bean.OrderFile;
import com.mercury.FreelancerApp.bean.User;
import com.mercury.FreelancerApp.bean.constant.OrderStatus;
import com.mercury.FreelancerApp.bean.constant.Role;
import com.mercury.FreelancerApp.dao.*;
import com.mercury.FreelancerApp.dto.ordersDto.*;
import com.mercury.FreelancerApp.exception.*;
import com.mercury.FreelancerApp.http.Response;
import com.mercury.FreelancerApp.utils.OrderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;
    private final OrderFileDao orderFileDao;
    private final UserDao userDao;
    private final FreelancerServiceRequirementDao requirementDao;

    private void isUserAuthorized(Order order, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        if(order.getClientId() != user.getId() && order.getFreelancerId() != user.getId()){
            throw new UserNotAuthorizedException();
        }
    }

    public List<Order> getAll(){
//        System.out.println(authentication.getAuthorities());
        return  StreamSupport.stream(orderDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    public OrderWithServiceInfoDto getById(int id, Authentication authentication){
        Order order = orderDao.findById(id).orElseThrow(OrderNotFoundException::new);
        isUserAuthorized(order, authentication);
        User freelancer = userDao.findById(order.getFreelancerId()).orElseThrow(UserNotFoundException::new);
        User client = userDao.findById(order.getClientId()).orElseThrow(UserNotFoundException::new);

        List<OrderFileDto> orderFileDtoList = order.getOrderFiles().stream()
                .map(orderFile -> {
                    User user = userDao.findById(orderFile.getUploadBy()).orElseThrow(UserNotFoundException::new);
                    return OrderUtils.mapToOrderFileDto(orderFile, user);
                }).collect(Collectors.toList());
        List<OrderInfoDto> orderInfoDtoList = order.getOrderInfoList().stream()
                .map(orderInfo -> {
                    int reqId = orderInfo.getRequirementId();
                    if(reqId == 0){
                        FreelancerServiceRequirement dummyReq = new FreelancerServiceRequirement(0, "Additional Information", order.getFreelancerService());
                        return OrderUtils.mapToOrderInfoDto(orderInfo, dummyReq);
                    }
                    FreelancerServiceRequirement requirement = requirementDao.findById(reqId).orElseThrow(RequirementNotFoundException::new);
                    return OrderUtils.mapToOrderInfoDto(orderInfo, requirement);
                }).collect(Collectors.toList());
        return OrderUtils.mapToOderDetailDto(order, order.getFreelancerService(), orderInfoDtoList, orderFileDtoList, freelancer, client);
    }
    public OrderResponse getByUserId(Authentication authentication, int pageNo, int pageSize, int orderStatus){
        User user = (User) authentication.getPrincipal();
        Sort sortByDueDate = Sort.by(Sort.Direction.ASC, "dueDate");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortByDueDate);
        Page<Order> orders;
        if(user.getRole() == Role.CLIENT){
            if(!OrderStatus.allStatus.contains(orderStatus))
                orders = orderDao.findByClientId(user.getId(), pageable);
            else{
                orders = orderDao.findByClientIdAndOrderStatus(user.getId(), orderStatus, pageable);
            }
        }else{
            if(!OrderStatus.allStatus.contains(orderStatus))
                orders = orderDao.findByFreelancerId(user.getId(), pageable);
            else
                orders = orderDao.findByFreelancerIdAndOrderStatus(user.getId(), orderStatus, pageable);
        }
        List<Order> orderList = orders.getContent();
        List<OrderWithServiceInfoDto> orderWithServiceInfoDtoList = orderList.stream()
                .map(order -> OrderUtils.mapToOrderWithServiceDto(order.getFreelancerService(), order))
                .collect(Collectors.toList());
        OrderResponse orderResponse = OrderUtils.createResponse(orderWithServiceInfoDtoList, orders);
        return orderResponse;
    }
    public Response save(Order order){
        try {
            order.setOrderStatus(OrderStatus.PENDING);
            order.getOrderInfoList().forEach(orderInfo -> {
                orderInfo.setUploadBy(order.getClientId());
            });
            orderDao.save(order);
            return new Response(true);
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidInputException("Information provided is not valid, please try again");
        }
    }
    public Response updateOrderStatus(int orderId, int newStatus) {
        Order order = orderDao.findById(orderId).orElseThrow(OrderNotFoundException::new);
        if(!OrderStatus.allStatus.contains(newStatus)) throw new InvalidInputException("Invalid Order Status");
        order.setOrderStatus(newStatus);
        orderDao.save(order);
        return new Response(true, "Order Status Updated");
    }

    public Response saveOrderFile(int orderId, List<OrderFile> orderFiles, Authentication authentication){
        try{
            User user = (User) authentication.getPrincipal();
            Order order = orderDao.findById(orderId).orElseThrow(OrderNotFoundException::new);
            orderFiles.forEach(orderFile -> {
                orderFile.setOrder(order);
                orderFile.setUploadBy(user.getId());
                orderFileDao.save(orderFile);
            });
            return new Response(true, "file uploaded!");
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidInputException("Information provided is not valid, please try again");
        }
    }

}
