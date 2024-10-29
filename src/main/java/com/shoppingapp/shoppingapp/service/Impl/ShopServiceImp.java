package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.dto.response.StatisticShopResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

    ProductRepository productRepository;
    OrderRepository orderRepository;


    @Override
    public List<ShopResponse> getAllShops() {
        return shopRepository.findAll().stream().map(shopMapper::toShopResponse).collect(Collectors.toList());
    }

    @Override
    public ShopResponse getShopById(long shopId) {
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
    public ShopResponse updateShop(ShopUpdateRequest request, long shopId) {
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

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<StatisticShopResponse> getAllStatisticShops() {
        return shopRepository.findAll().stream()
                .map(shop -> {
                    // Get total products and orders for this shop
                    Long totalProduct = (long) productRepository.countProductsByShopId(shop.getShopId());
                    Long totalOrder = (long) orderRepository.countOrdersByShopId(shop.getShopId());

                    // Create and return the StatisticShopResponse for this shop
                    return StatisticShopResponse.builder()
                            .shopID(shop.getShopId())
                            .shopName(shop.getShopName())
                            .user(shop.getUser()) // assuming Shop entity has a User field
                            .totalProduct(totalProduct)
                            .totalOrder(totalOrder)
                            .address(shop.getAddress())
                            .city(shop.getCity())
                            .state(shop.getState())
                            .country(shop.getCountry())
                            .phone(shop.getPhone())
                            .description(shop.getDescription())
                            .logo(shop.getLogo())
                            .cover(shop.getCover())
                            .build();
                }).toList();
    }


}