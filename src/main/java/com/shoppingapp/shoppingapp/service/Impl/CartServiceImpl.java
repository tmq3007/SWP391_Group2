package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.CartCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CartUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CartResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.CartMapper;
import com.shoppingapp.shoppingapp.models.Cart;
import com.shoppingapp.shoppingapp.models.CartItem;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.CartItemRepository;
import com.shoppingapp.shoppingapp.repository.CartRepository;
import com.shoppingapp.shoppingapp.repository.ProductRepository;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import com.shoppingapp.shoppingapp.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;


    @Override
    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartItem addCartItem(User user, Product product, String unitBuy, int quantity) {
        if (quantity <= 0) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        // Check if the product exists
//       Product optionalProduct = productRepository.findById(product.getProductId())
//               .orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_EXISTED));


        Cart cart = findUserCart(user);

        // Tìm kiếm sản phẩm trong giỏ hàng với cùng sản phẩm và đơn vị mua
        CartItem isPresent = cartItemRepository.findByCartAndProductAndBuyUnit(cart, product, unitBuy);

        // If the item is not present in the cart, add it
        if (isPresent == null) {
            // Sản phẩm chưa có trong giỏ hàng, thêm mới
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());
            cartItem.setBuyUnit(unitBuy);

            // Tính tổng giá cho sản phẩm mới
            Double totalPrice = quantity * product.getUnitSellPrice()*(1-product.getDiscount());
            cartItem.setTotalPrice(totalPrice);

            // Thêm vào giỏ hàng
            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart); // Gắn giỏ hàng cho item mới

            // Cập nhật tổng số lượng và giá của giỏ hàng
            Double currentTotalPrice = cart.getTotalPrice() != null ? cart.getTotalPrice() : 0.0;

            // Cập nhật tổng số lượng và giá của giỏ hàng
            cart.setTotalItem(cart.getTotalItem() + quantity);
            cart.setTotalPrice(currentTotalPrice + totalPrice);

            return cartItemRepository.save(cartItem); // Lưu và trả về item mới
        } else {
            // Update existing cart item
            int newQuantity = isPresent.getQuantity() + quantity;
            Double oldTotalPrice = isPresent.getTotalPrice();
            double newTotalPrice = newQuantity * product.getUnitSellPrice()*(1-product.getDiscount());

            // Cập nhật số lượng và tổng giá cho item đã tồn tại
            isPresent.setQuantity(newQuantity);
            isPresent.setTotalPrice(newTotalPrice);

            // Cập nhật tổng số lượng và giá của giỏ hàng
            cart.setTotalItem(cart.getTotalItem() + quantity);
            cart.setTotalPrice(cart.getTotalPrice() + oldTotalPrice + newTotalPrice);

            return cartItemRepository.save(isPresent); // Lưu và trả về item đã cập nhật
        }
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new HashSet<>());
            cart.setTotalItem(0);
            cart.setTotalPrice(0.0);
            cart = cartRepository.save(cart); // Nếu cart là null, hãy tạo mới và lưu
        }

        return cart;
    }



}
