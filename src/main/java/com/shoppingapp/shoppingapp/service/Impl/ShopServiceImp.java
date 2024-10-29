package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.ShopMapper;
import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.repository.*;
import com.shoppingapp.shoppingapp.service.ShopService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShopServiceImp implements ShopService {
    @Override
    public Shop getShopProfile(String jwt) {
        return null;
    }



    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Shop getShopByUserId(Long userId) {
        return shopRepository.findAll()
                .stream()
                .filter((a) -> a.getUser().getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ShopResponse> getAllShops() {
        return shopRepository.findAll().stream().map(shopMapper::toShopResponse).collect(Collectors.toList());
    }

    @Override
    public ShopResponse getShopById(Long shopId) {
        return shopMapper.toShopResponse(shopRepository.findById(shopId).orElseThrow(()-> new AppException(ErrorCode.SHOP_NOT_EXISTED)));
    }

    @Override
    public ShopResponse createShop(ShopCreationRequest request) {
        if(shopRepository.existsByShopName(request.getShopName())){
            throw new AppException(ErrorCode.SHOP_EXISTED);
        }
        Shop shop = shopMapper.toShop(request);
        var userOp = userRepository.findById(Long.valueOf(request.getUser()));
        if (!userOp.isPresent()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);  // Xử lý khi không tìm thấy user
        }

        shop.setUser(userOp.get());
        Shop savedShop = shopRepository.save(shop);

        return shopMapper.toShopResponse(savedShop);
    }

    @Override
    public ShopResponse updateShop(ShopUpdateRequest request, Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(()-> new AppException(ErrorCode.SHOP_NOT_EXISTED));
        shopMapper.updateShop(shop,request);

        return shopMapper.toShopResponse(shopRepository.save(shop));
    }

    @Override
    public String deleteShop(Long shopId) {
        shopRepository.deleteById(shopId);
        return "Deleted";
    }

    @Override
    public int getTotalShops() {
       return shopRepository.findAll().size();
    }

    @Override
    public Long getShopIdByUserId(Long userId) {
        Optional<Shop> shop = shopRepository.findByUserId(userId);
        if (shop.isEmpty()) {
            throw new AppException(ErrorCode.SHOP_NOT_EXISTED);
        }
        return shop.get().getShopId();
    }


}