package com.shoppingapp.shoppingapp.mapper;


import com.shoppingapp.shoppingapp.dto.request.PermissionRequest;
import com.shoppingapp.shoppingapp.dto.response.PermissionResponse;
import com.shoppingapp.shoppingapp.models.Permission;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
