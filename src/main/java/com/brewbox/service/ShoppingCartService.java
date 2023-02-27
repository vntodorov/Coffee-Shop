package com.brewbox.service;

import com.brewbox.model.DTOs.CartItemDTO;
import com.brewbox.model.entity.CartItemEntity;
import com.brewbox.model.entity.ProductEntity;
import com.brewbox.model.entity.UserEntity;
import com.brewbox.repository.CartItemRepository;
import com.brewbox.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    @Autowired
    public ShoppingCartService(CartItemRepository cartItemRepository, ProductRepository productRepository, ModelMapper mapper) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<CartItemDTO> getCartItems(UserEntity user) {
        return cartItemRepository.
                findByUser(user).
                stream().
                map(this::mapToCartItemDTO).
                toList();
    }

    public Integer addProduct(long productId, int quantity, UserEntity user) {
        int addedQuantity = quantity;

        ProductEntity product = productRepository.findById(productId).orElseThrow();

        CartItemEntity cartItem = cartItemRepository.findByUserAndProduct(user, product);

        if (cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        } else {
            cartItem = new CartItemEntity();
            cartItem.setQuantity(quantity);
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }

        cartItemRepository.save(cartItem);

        return addedQuantity;
    }

    private CartItemDTO mapToCartItemDTO(CartItemEntity cartItem) {
        return mapper.map(cartItem, CartItemDTO.class);
    }
}
