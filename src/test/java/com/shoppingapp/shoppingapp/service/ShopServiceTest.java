package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.ShopMapper;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.ShopRepository;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ShopServiceTest {

    @Autowired
    private ShopService shopService;

    @MockBean
    private ShopRepository shopRepository;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ShopMapper shopMapper;

    private ShopCreationRequest request;
    private ShopCreationRequest request1;
    private ShopResponse shopResponse;


    @BeforeEach
    void initData() {
        User user = new User();
        user.setId(1L);

        request = ShopCreationRequest.builder()
                .shopName("Pet Shops")
                .logo("logo.img")
                .cover("cover.img")
                .address("Hoa Lac")
                .user(1L)
                .city("Ha Noi")
                .district("Thach That")
                .subdistrict("Thach Hoa")
                .description("Pet Shop selling pet items")
                .phone("0123456789")
                .build();

        request1 = ShopCreationRequest.builder()
                .shopName("Pet Shops") // Same name to test existing shop case
                .logo("logo.img")
                .cover("cover.img")
                .address("Hoa Lac")
                .user(1L)
                .city("Ha Noi")
                .district("Thach That")
                .subdistrict("Thach Hoa")
                .description("Pet Shop selling pet items")
                .phone("0123456789")
                .build();



        shopResponse = ShopResponse.builder()
                .shopID(1L)
                .shopName("Pet Shops")
                .logo("logo.img")
                .cover("cover.img")
                .address("Hoa Lac")
                .user(user)
                .city("Ha Noi")
                .district("Thach That")
                .subdistrict("Thach Hoa")
                .description("Pet Shop selling pet items")
                .phone("0123456789")
                .build();
    }
    ShopUpdateRequest updateRequest = ShopUpdateRequest.builder()
            .shopName("Pet Shops") // Same name to test existing shop case
            .logo("logo.img")
            .cover("cover.img")
            .address("Hoa Lac")
            .city("Ha Noi")
            .district("Thach That")
            .subdistrict("Thach Hoa")
            .description("Pet Shop selling pet items")
            .phone("0123456789")
            .build();
    Shop shop = Shop.builder()
            .shopId(1L)
            .shopName("Updated Shop Name")
            .address("Updated Address")
            .city("Updated City")
            .phone("0987654321")
            .description("Updated Description")
            .logo("updated_logo.png")
            .cover("updated_cover.png")
            .build();

    @Test
    void createShop_success() {
        // Given
        Mockito.when(shopRepository.existsByShopName("Pet Shops")).thenReturn(false); // No existing shop with the same name
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User())); // Mocking the user repository to return a user
        Mockito.when(shopMapper.toShop(request)).thenReturn(shop); // Mocking the mapping from request to Shop
        Mockito.when(shopRepository.save(ArgumentMatchers.any(Shop.class))).thenReturn(shop); // Mocking save operation to return the created shop
        Mockito.when(shopMapper.toShopResponse(shop)).thenReturn(shopResponse); // Mocking the mapping to ShopResponse

        // When
        ShopResponse result = shopService.createShop(request);

        // Then
        Assertions.assertThat(result.getShopID()).isEqualTo(1L); // Verify that the returned shop ID matches
        Assertions.assertThat(result.getShopName()).isEqualTo("Pet Shops"); // Verify that the returned shop name matches
    }



    @Test
    void createShop_shopExists_fail() {
        // Given
        when(shopRepository.existsByShopName(request.getShopName())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> shopService.createShop(request))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.SHOP_EXISTED.getMessage());
    }

    @Test
    void updateShop_success() {
        // Given
        // Mocking the behavior of shopRepository to return an existing shop
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop)); // Return the existing shop
        when(shopMapper.toShopResponse(Mockito.any(Shop.class))).thenReturn(shopResponse); // Mock mapping to ShopResponse
        when(shopRepository.save(Mockito.any(Shop.class))).thenReturn(shop); // Mock saving the shop

        // Mocking the update method
        doNothing().when(shopMapper).updateShop(any(Shop.class), any(ShopUpdateRequest.class)); // Assume updateShop does not return anything

        // When
        ShopResponse result = shopService.updateShop(updateRequest, 1L); // Call the updateShop method

        // Then
        Assertions.assertThat(result).isNotNull(); // Check that the result is not null
        Assertions.assertThat(result.getShopID()).isEqualTo(1L); // Verify that the returned shop ID matches
        Assertions.assertThat(result.getShopName()).isEqualTo("Pet Shops"); // Verify that the shop name matches
        Assertions.assertThat(result.getAddress()).isEqualTo("Hoa Lac"); // Verify that the address matches
        Assertions.assertThat(result.getCity()).isEqualTo("Ha Noi"); // Verify that the city matches
        Assertions.assertThat(result.getDistrict()).isEqualTo("Thach That"); // Verify that the district matches
        Assertions.assertThat(result.getSubdistrict()).isEqualTo("Thach Hoa"); // Verify that the subdistrict matches
        Assertions.assertThat(result.getPhone()).isEqualTo("0123456789"); // Verify that the phone matches
        Assertions.assertThat(result.getDescription()).isEqualTo("Pet Shop selling pet items"); // Verify that the description matches
        Assertions.assertThat(result.getLogo()).isEqualTo("logo.img"); // Verify that the logo matches
        Assertions.assertThat(result.getCover()).isEqualTo("cover.img"); // Verify that the cover matches

        // Verify that the update method was called once
        verify(shopMapper, times(1)).updateShop(shop, updateRequest);
        // Verify that the save method was called once
        verify(shopRepository, times(1)).save(shop);
    }



    @Test
    void updateShop_shopNotFound_fail() {
        // Given
        when(shopRepository.findById(shop.getShopId())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> shopService.updateShop(ShopUpdateRequest.builder().build(), shop.getShopId()))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.SHOP_NOT_EXISTED.getMessage());
    }

    @Test
    void getAllShops_success() {
        // Given
        when(shopRepository.findAll()).thenReturn(Collections.singletonList(shop));
        when(shopMapper.toShopResponse(shop)).thenReturn(shopResponse);

        // When
        List<ShopResponse> result = shopService.getAllShops();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getShopName()).isEqualTo("Pet Shops");
    }

    @Test
    void getShopById_success() {
        // Given
        long shopId = 1L;
        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(shopMapper.toShopResponse(shop)).thenReturn(shopResponse);

        // When
        ShopResponse result = shopService.getShopById(shopId);

        // Then
        assertThat(result.getShopName()).isEqualTo("Pet Shops");
    }

    @Test
    void getShopById_shopNotFound_fail() {
        // Given
        long shopId = 1L;
        when(shopRepository.findById(shopId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> shopService.getShopById(shopId))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.SHOP_NOT_EXISTED.getMessage());
    }

    @Test
    void deleteShop_success() {
        // Given
        long shopId = 1L;

        // When
        shopService.deleteShop(shopId);

        // Then
        verify(shopRepository, times(1)).deleteById(shopId);
    }
    @Test
    void getTotalShops_success() {
        // Given
        List<Shop> shops = List.of(new Shop(), new Shop()); // Mocking a list of 2 shops
        when(shopRepository.findAll()).thenReturn(shops); // Mock the repository method

        // When
        int totalShops = shopService.getTotalShops(); // Call the service method

        // Then
        assertThat(totalShops).isEqualTo(2); // Verify that the total number of shops is correct
    }

    @Test
    void getShopIdByUserId_shopExists() {
        // Given
        Long userId = 1L;
        Shop shop = Shop.builder().shopId(1L).user(new User()).build(); // Mock a shop associated with the user
        when(shopRepository.findByUserId(userId)).thenReturn(Optional.of(shop)); // Mock the repository method

        // When
        Optional<Long> shopId = shopService.getShopIdByUserId(userId); // Call the service method

        // Then
        assertThat(shopId).isPresent(); // Verify that a shop ID is present
        assertThat(shopId.get()).isEqualTo(1L); // Verify that the returned shop ID is correct
    }

    @Test
    void getShopIdByUserId_shopNotFound() {
        // Given
        Long userId = 2L; // User ID that doesn't correspond to any shop
        when(shopRepository.findByUserId(userId)).thenReturn(Optional.empty()); // Mock the repository method

        // When
        Optional<Long> shopId = shopService.getShopIdByUserId(userId); // Call the service method

        // Then
        assertThat(shopId).isNotPresent(); // Verify that no shop ID is returned
    }

}
