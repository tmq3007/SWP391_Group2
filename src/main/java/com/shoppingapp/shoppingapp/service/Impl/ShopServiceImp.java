package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.ShopMapper;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.repository.CategoryRepository;
import com.shoppingapp.shoppingapp.repository.ShopRepository;
import com.shoppingapp.shoppingapp.service.ShopService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShopServiceImp implements ShopService {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopMapper shopMapper;


    @Override
    public List<Shop> getAllShops() {
        return (List<Shop>) shopRepository.findAll();
    }

    @Override
    public ShopResponse getShopById(long shopId) {
        return shopMapper.toShopResponse(shopRepository.findById(shopId).orElseThrow(()-> new RuntimeException("Shop not found")));
    }

    @Override
    public Shop createShop(ShopCreationRequest request) {
        if(shopRepository.existsByShopName(request.getShopName())){
            throw new AppException(ErrorCode.SHOP_EXISTED);
        }
        Shop shop = shopMapper.toShop(request);
        return shopRepository.save(shop);
    }

    @Override
    public Shop updateShop(ShopUpdateRequest request, long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(()-> new RuntimeException("Shop not found"));
        shopMapper.updateShop(shop,request);
        return shopRepository.save(shop);
    }

    @Override
    public String deleteShop(Long shopId) {
        shopRepository.deleteById(shopId);
        return "Deleted";
    }




}
