package com.mercury.FreelancerApp.utils;

import com.mercury.FreelancerApp.bean.*;
import com.mercury.FreelancerApp.dto.ordersDto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public class OrderUtils {
    public static OrderFileDto mapToOrderFileDto(OrderFile orderFile, User user){
        return OrderFileDto.builder()
                .id(orderFile.getId())
                .fileName(orderFile.getFileName())
                .fileLocation(orderFile.getFileLocation())
                .username(user.getUsername())
                .thumbImg(user.getProfileImage())
                .createdTime(orderFile.getCreatedTime())
                .build();
    }

    public static OrderInfoDto mapToOrderInfoDto(OrderInfo orderInfo, FreelancerServiceRequirement requirement){
        return OrderInfoDto.builder()
                .id(orderInfo.getId())
                .requirementId(requirement.getId())
                .requirementAnswer(orderInfo.getRequirementAnswer())
                .serviceRequirement(requirement.getDetails())
                .createdTime(orderInfo.getCreatedTime())
                .build();
    }
    public static OrderWithServiceInfoDto mapToOderDetailDto(Order order, FreelancerService freelancerService
            ,List<OrderInfoDto> orderInfoDtoList, List<OrderFileDto> orderFileDtoList,User freelancer, User client){
        return OrderDetailDto.builder()
                .id(order.getId())
                .freelancerId(order.getFreelancerId())
                .clientId(order.getClientId())
                .serviceId(freelancerService.getId())
                .freelancerUsername(freelancer.getUsername())
                .clientUsername(client.getUsername())
                .orderStatus(order.getOrderStatus())
                .orderSubTotal(order.getOrderSubTotal())
                .taxTotal(order.getTaxTotal())
                .tipsTotal(order.getTipsTotal())
                .dueDate(order.getDueDate())
                .createdTime(order.getCreatedTime())
                .updateTime(order.getUpdateTime())
                .serviceTitle(freelancerService.getTitle())
                .serviceThumb(freelancerService.getThumbImage())
                .orderFileDtoList(orderFileDtoList)
                .orderInfoDtoList(orderInfoDtoList)
                .build();
    }

        public static OrderWithServiceInfoDto mapToOrderWithServiceDto(FreelancerService freelancerService, Order order){
        return OrderWithServiceInfoDto.builder()
                .id(order.getId())
                .freelancerId(order.getFreelancerId())
                .clientId(order.getClientId())
                .serviceId(freelancerService.getId())
                .orderStatus(order.getOrderStatus())
                .orderSubTotal(order.getOrderSubTotal())
                .taxTotal(order.getTaxTotal())
                .tipsTotal(order.getTipsTotal())
                .dueDate(order.getDueDate())
                .createdTime(order.getCreatedTime())
                .updateTime(order.getUpdateTime())
                .serviceTitle(freelancerService.getTitle())
                .serviceThumb(freelancerService.getThumbImage())
                .build();
    }
    public static OrderResponse createResponse(List<OrderWithServiceInfoDto> orderWithServiceInfoDtoList, Page<Order> orderPage){
        return OrderResponse.builder()
                .content(orderWithServiceInfoDtoList)
                .pageNo(orderPage.getNumber())
                .pageSize(orderPage.getSize())
                .totalPages(orderPage.getTotalPages())
                .totalElements(orderPage.getTotalElements())
                .last(orderPage.isLast())
                .build();
    }
}
