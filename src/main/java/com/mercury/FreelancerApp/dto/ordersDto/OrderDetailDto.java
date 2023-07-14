package com.mercury.FreelancerApp.dto.ordersDto;

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
public class OrderDetailDto extends OrderWithServiceInfoDto{
    String freelancerUsername;
    String clientUsername;
    List<OrderFileDto> orderFileDtoList;
    List<OrderInfoDto> orderInfoDtoList;

}
