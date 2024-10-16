package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.OrdersCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.OrdersUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.OrdersResponse;
import com.shoppingapp.shoppingapp.models.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    @Mapping(target = "userId",ignore = true)
    @Mapping(target = "paymentId",ignore = true)
    Orders toOrders (OrdersCreationRequest request);

    @Mapping(target = "userId",ignore = true)
    @Mapping(target = "paymentId",ignore = true)
    void updateOrders(@MappingTarget Orders orders, OrdersUpdateRequest request);

    OrdersResponse toOrdersReponse (Orders orders);
}
