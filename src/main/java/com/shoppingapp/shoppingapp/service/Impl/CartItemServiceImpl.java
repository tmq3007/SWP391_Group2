    package com.shoppingapp.shoppingapp.service.Impl;

    import com.shoppingapp.shoppingapp.models.CartItem;
    import com.shoppingapp.shoppingapp.models.User;
    import com.shoppingapp.shoppingapp.repository.CartItemRepository;
    import com.shoppingapp.shoppingapp.service.CartItemService;
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
        public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws  Exception {
            CartItem item = findCartItemById(id);
            User cartItemUser = item.getCart().getUser();

            if(cartItemUser.getId().equals(userId)) {
                item.setQuantity(cartItem.getQuantity());
                item.setTotalPrice(item.getQuantity()*item.getProduct().getUnitSellPrice());
                return cartItemRepository.save(item);

            }
            throw new Exception("You cant update this cart item");
        }

        @Override
        @PreAuthorize("hasRole('CUSTOMER')")
        public void removeCartItem(Long userId, Long cartItemId) throws Exception {
            CartItem item = findCartItemById(cartItemId);
            User cartItemUser = item.getCart().getUser();

            if(cartItemUser.getId().equals(userId)) {
                 cartItemRepository.delete(item);

            }
            else throw new Exception("You cant delete this cart item");
        }

        @Override
        @PreAuthorize("hasRole('CUSTOMER')")
        public CartItem findCartItemById(Long id) throws Exception {

            return cartItemRepository.findById(id)
                    .orElseThrow(()->new Exception("Cart item not found with id " + id));
        }
    }
