package com.brewbox.service;

import com.brewbox.model.DTOs.CartItemDTO;
import com.brewbox.model.entity.CartItemEntity;
import com.brewbox.model.entity.ProductEntity;
import com.brewbox.model.entity.UserEntity;
import com.brewbox.repository.CartItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final CartItemRepository cartItemRepository;

    private final ProductService productService;

    private final UserService userService;

    private final ModelMapper mapper;

    @Autowired
    public ShoppingCartService(CartItemRepository cartItemRepository, ProductService productService, UserService userService, ModelMapper mapper) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.userService = userService;
        this.mapper = mapper;
    }

    public List<CartItemDTO> getCartItems(UserEntity user) {
        return cartItemRepository.
                findByUser(user).
                stream().
                map(this::mapToCartItemDTO).
                toList();
    }

    public boolean addProduct(CartItemDTO cartItemDTO, Long productId, UserDetails userDetails) {
        UserEntity user = userService.getCurrentUser(userDetails);
        ProductEntity product = mapper.map(productService.getProductById(productId), ProductEntity.class);

        Optional<CartItemEntity> optionalCartItem = cartItemRepository.findByUserAndProduct(user, product);

        CartItemEntity cartItem;

        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            int newQuantity = cartItem.getQuantity() + cartItemDTO.getQuantity();
            if (newQuantity > 9) {
                return false;
            }
            cartItem.setQuantity(newQuantity);
        } else {
            cartItem = mapToCartItem(cartItemDTO);
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }
        cartItemRepository.save(cartItem);
        return true;
    }

//    @Transactional
//    public BigDecimal updateQuantity(Long productId, int quantity, UserEntity user) {
//        cartItemRepository.updateQuantity(quantity, productId, user.getId());
//        ProductEntity product = productRepository.findById(productId).orElseThrow();
//
//        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
//    }

    private CartItemDTO mapToCartItemDTO(CartItemEntity cartItem) {
        return mapper.map(cartItem, CartItemDTO.class);
    }

    private CartItemEntity mapToCartItem(CartItemDTO cartItemDTO) {
        return mapper.map(cartItemDTO, CartItemEntity.class);
    }
}
