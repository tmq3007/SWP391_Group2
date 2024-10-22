package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.AddItemRequest;
import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.CartCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.response.CartResponse;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.CartMapper;
import com.shoppingapp.shoppingapp.mapper.UserMapper;
import com.shoppingapp.shoppingapp.models.Cart;
import com.shoppingapp.shoppingapp.models.CartItem;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.service.CartItemService;
import com.shoppingapp.shoppingapp.service.CartService;
import com.shoppingapp.shoppingapp.service.ProductService;
import com.shoppingapp.shoppingapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(("/api/v1/cart"))
@AllArgsConstructor

public class CartController {
    @Autowired
    private  CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartMapper cartMapper;

    @GetMapping("/{userId}")
    public ApiResponse<Cart> findUserCartHandler(@PathVariable("userId") Long userId) {
        UserResponse userResponse = userService.getUserById(userId);


        User user = new User();

        user.setId(userResponse.getId());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setEmail(userResponse.getEmail());
        user.setUsername(userResponse.getUsername());
        user.setPassword(userResponse.getPassword());
        user.setPhone(userResponse.getPhone());
        user.setIsActive(userResponse.getIsActive());
//
        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setMessage("Added");

        Cart cart = cartService.findUserCart(user);
        if (cart == null) {
            throw new AppException(ErrorCode.CART_NOT_EXIST);
        }
        apiResponse.setResult(cart);
        return apiResponse;
    }



    @PutMapping("/add/{userId}")
    public ApiResponse<CartItem> addItemToCart(@RequestBody AddItemRequest req,
                                               @PathVariable("userId") Long userId
                                               ) {
        UserResponse userResponse = userService.getUserById(userId);
        if (userResponse == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        User user = new User();
        user.setId(userResponse.getId());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setEmail(userResponse.getEmail());
        user.setUsername(userResponse.getUsername());
        user.setPassword(userResponse.getPassword());
        user.setPhone(userResponse.getPhone());
        user.setIsActive(userResponse.getIsActive());

        Product product = productService.getProductById(req.getProductId());
        if (product == null) {
            throw new AppException(ErrorCode.PRODUCT_NOT_EXISTED);
        }
        CartItem cartItem = cartService.addCartItem(
                user, product, req.getBuyUnit(), req.getQuantity()
        );

        ApiResponse<CartItem> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartItem);
        return apiResponse;
    }

    @DeleteMapping("/delete/user/{userId}/cartItem/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCart( @PathVariable("userId") Long userId
                                        , @PathVariable("cartItemId") Long cartItemId) throws Exception {
        UserResponse userResponse = userService.getUserById(userId);
        if (userResponse == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        User user = new User();
        user.setId(userResponse.getId());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setEmail(userResponse.getEmail());
        user.setUsername(userResponse.getUsername());
        user.setPassword(userResponse.getPassword());
        user.setPhone(userResponse.getPhone());
        user.setIsActive(userResponse.getIsActive());

        cartItemService.removeCartItem(user.getId(),cartItemId);
        ApiResponse<CartItem> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("delete item from cart success");
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED); // Trả về cart
    }

    @PutMapping("/delete/user/{userId}/cartItem/{cartItemId}")
    public ApiResponse<CartItem> updateCartItemHandler( @PathVariable("userId") Long userId,
                                                           @PathVariable("cartItemId") Long cartItemId,
                                                           @RequestBody CartItem cartItem ) throws Exception{
        UserResponse userResponse = userService.getUserById(userId);
        if (userResponse == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        User user = new User();
        user.setId(userResponse.getId());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setEmail(userResponse.getEmail());
        user.setUsername(userResponse.getUsername());
        user.setPassword(userResponse.getPassword());
        user.setPhone(userResponse.getPhone());
        user.setIsActive(userResponse.getIsActive());

        CartItem updatedCartitem = null;
        if(cartItem.getQuantity()>0){
            updatedCartitem = cartItemService.updateCartItem(user.getId(),cartItemId,cartItem);
        }
        ApiResponse<CartItem> apiResponse = new ApiResponse<>();
        apiResponse.setResult(updatedCartitem);
        return apiResponse;
    }


}
