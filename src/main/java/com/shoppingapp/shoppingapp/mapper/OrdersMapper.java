package com.shoppingapp.shoppingapp.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    /*@Mapping(target = "userId",ignore = true)
    @Mapping(target = "paymentId",ignore = true)
    Orders toOrders (OrdersCreationRequest request);

    @Mapping(target = "userId",ignore = true)
    @Mapping(target = "paymentId",ignore = true)
    void updateOrders(@MappingTarget Orders orders, OrdersUpdateRequest request);

    OrdersResponse toOrdersReponse (Orders orders);*/
}
