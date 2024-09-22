package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.PermissionRequest;
import com.shoppingapp.shoppingapp.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse create(PermissionRequest permissionRequest);

    List<PermissionResponse> getAll();

    void delete(String permission);
}
