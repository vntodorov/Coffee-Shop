package com.brewbox.service;

import com.brewbox.model.DTOs.ProductDTO;
import com.brewbox.model.entity.BrandEntity;
import com.brewbox.model.entity.CategoryEntity;
import com.brewbox.model.entity.ProductEntity;
import com.brewbox.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final BrandService brandService;

    private final ModelMapper mapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService, BrandService brandService, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.mapper = mapper;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.
                findAll().
                stream().
                map(this::mapToProductDTO).
                toList();
    }

    public void addProduct(ProductDTO productDTO) {
        CategoryEntity category = categoryService.findCategoryByName(productDTO.getCategoryName());
        BrandEntity brand = brandService.findBrandByName(productDTO.getBrandName());

        ProductEntity product = mapToProduct(productDTO);
        product.setCategory(category);
        product.setBrand(brand);

        productRepository.save(product);
    }

    private ProductDTO mapToProductDTO(ProductEntity product) {
        return mapper.map(product, ProductDTO.class);
    }

    private ProductEntity mapToProduct(ProductDTO productDTO) {
        return mapper.map(productDTO, ProductEntity.class);
    }
}
