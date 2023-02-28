package com.brewbox.web;

import com.brewbox.model.DTOs.ProductDTO;
import com.brewbox.service.BrandService;
import com.brewbox.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProductController {

    private final ProductService productService;

    private final BrandService brandService;

    @Autowired
    public ProductController(ProductService productService, BrandService brandService) {
        this.productService = productService;
        this.brandService = brandService;
    }

    @ModelAttribute("productDTO")
    public ProductDTO productDTO(){
        return new ProductDTO();
    }

    @GetMapping("/products")
    public String getAllProducts(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model){
        model.addAttribute("brands", brandService.getAllBrands());
        return "product-add";
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid ProductDTO productDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("productDTO", productDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productDTO", bindingResult);

            return "redirect:/products/add";
        }

        productService.addProduct(productDTO);
        return "redirect:/products";

    }

    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "product";

    }
}
