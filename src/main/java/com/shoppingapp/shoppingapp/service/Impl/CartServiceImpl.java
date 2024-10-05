package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.CartCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CartUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CartResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.CartMapper;
import com.shoppingapp.shoppingapp.models.Cart;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.CartRepository;
import com.shoppingapp.shoppingapp.repository.ProductRepository;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import com.shoppingapp.shoppingapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartMapper cartMapper;


    @Override
    public List<Cart> getAllCartsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public CartResponse addToCart(Long productId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        // Kiểm tra xem Cart có tồn tại với userId và productId không
        Cart existingCart = cartRepository.findCartByUserIdAndProductId(userId, productId);

        if (existingCart == null) {
            // Nếu không tồn tại, tạo mới
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(1);
            cart.setTotalPrice(product.getUnitSellPrice() - product.getDiscount());
            Cart savedCart = cartRepository.save(cart);
            return cartMapper.toCartResponse(savedCart);
        } else {
            // Nếu đã tồn tại, cập nhật số lượng
            existingCart.setQuantity(existingCart.getQuantity() + 1);
            existingCart.setTotalPrice(existingCart.getQuantity() * (product.getUnitSellPrice() - product.getDiscount()));
            Cart updatedCart = cartRepository.save(existingCart);
            return cartMapper.toCartResponse(updatedCart);
        }
    }

    @Override
    public String deleteCart(Long productId, Long userId) {
        
        Cart existingCart = cartRepository.findCartByUserIdAndProductId(userId, productId);

        if (existingCart != null) {

            if (existingCart.getQuantity() > 1) {
                existingCart.setQuantity(existingCart.getQuantity() - 1);

                cartRepository.save(existingCart);
                return "Reduced quantity of the product in cart to " + existingCart.getQuantity();
            } else {

                cartRepository.delete(existingCart);
                return "Product removed from cart successfully";
            }
        } else {
            return "Product not found in cart";
        }
    }



}
