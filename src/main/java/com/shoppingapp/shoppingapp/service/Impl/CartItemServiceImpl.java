package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.models.CartItem;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.CartItemRepository;
import com.shoppingapp.shoppingapp.service.CartItemService;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private final CartItemRepository cartItemRepository;

    @Override
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) {
        CartItem item = findCartItemById(id);
        User cartItemUser = item.getCart().getUser();

        if (cartItemUser.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setTotalPrice(item.getQuantity() * item.getProduct().getUnitSellPrice());
            return cartItemRepository.save(item);
        } else {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }

    @Override
    @PreAuthorize("hasRole('CUSTOMER')")
    public void removeCartItem(Long userId, Long cartItemId) {
        CartItem item = findCartItemById(cartItemId);
        User cartItemUser = item.getCart().getUser();

        if (cartItemUser.getId().equals(userId)) {
            cartItemRepository.delete(item);
        } else {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }

    @Override
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartItem findCartItemById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXIST));
    }
}
