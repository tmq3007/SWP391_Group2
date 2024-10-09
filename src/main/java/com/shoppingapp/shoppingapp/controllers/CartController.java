package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.AddItemRequest;
import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.CartCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.response.CartResponse;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
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
    public ResponseEntity<Cart> findUserCartHandler(@PathVariable("userId") Long userId) {
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
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setMessage("Added");

        Cart cart = cartService.findUserCart(user);
        return new ResponseEntity<Cart>(cart, HttpStatus.OK); // Trả về cart
    }



    @PutMapping("/add/{userId}")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req,
                                               @PathVariable("userId") Long userId
                                               ) {
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


        Product product = productService.getProductById(req.getProductId());

        CartItem cartItem = cartService.addCartItem(
                user, product, req.getBuyUnit(), req.getQuantity()
        );

//        ApiResponse<CartItem> apiResponse = new ApiResponse<>();
//        apiResponse.setMessage("Add item to cart success");
        return new ResponseEntity<>(cartItem, HttpStatus.ACCEPTED); // Trả về cart
    }

    @DeleteMapping("/delete/user/{userId}/cartItem/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCart( @PathVariable("userId") Long userId
                                        , @PathVariable("cartItemId") Long cartItemId) throws Exception {
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

        cartItemService.removeCartItem(user.getId(),cartItemId);
        ApiResponse<CartItem> apiResponse = new ApiResponse<>(); // Khai báo kiểu dữ liệu cho ApiResponse
        apiResponse.setMessage("delete item from cart success");
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED); // Trả về cart
    }

    @PutMapping("/delete/user/{userId}/cartItem/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler( @PathVariable("userId") Long userId,
                                                           @PathVariable("cartItemId") Long cartItemId,
                                                           @RequestBody CartItem cartItem ) throws Exception{
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

        CartItem updatedCartitem = null;
        if(cartItem.getQuantity()>0){
            updatedCartitem = cartItemService.updateCartItem(user.getId(),cartItemId,cartItem);
        }
        return new ResponseEntity<>(updatedCartitem, HttpStatus.ACCEPTED); // Trả về cart
    }


}
