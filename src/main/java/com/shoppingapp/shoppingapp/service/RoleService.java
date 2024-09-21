package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.RoleRequest;
import com.shoppingapp.shoppingapp.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest roleRequest);
    List<RoleResponse> getAll();
    void delete(String role);
}
