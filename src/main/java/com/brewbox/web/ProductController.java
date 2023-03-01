package com.brewbox.web;

import com.brewbox.model.DTOs.CommentDTO;
import com.brewbox.model.DTOs.ProductDTO;
import com.brewbox.service.BrandService;
import com.brewbox.service.CommentService;
import com.brewbox.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final CommentService commentService;

    @Autowired
    public ProductController(ProductService productService, BrandService brandService, CommentService commentService) {
        this.productService = productService;
        this.brandService = brandService;
        this.commentService = commentService;
    }

    @ModelAttribute("productDTO")
    public ProductDTO productDTO() {
        return new ProductDTO();
    }

    @ModelAttribute("commentDTO")
    public CommentDTO commentDTO() {
        return new CommentDTO();
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model) {
        model.addAttribute("brands", brandService.getAllBrands());
        return "product-add";
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid ProductDTO productDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productDTO", productDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productDTO", bindingResult);

            return "redirect:/products/add";
        }

        productService.addProduct(productDTO);
        return "redirect:/products";

    }

    @GetMapping("/product/{pid}")
    public String getProductById(@PathVariable("pid") Long pid, Model model) {
        model.addAttribute("product", productService.getProductById(pid));
        model.addAttribute("comments", commentService.getAllCommentsForProduct(pid));

        return "product";
    }

    @PostMapping("/comment/add/product/{pid}")
    public String addComment(@Valid CommentDTO commentDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetails userDetails,
                             @PathVariable("pid") Long pid) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentDTO", commentDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentDTO", bindingResult);

            return "redirect:/product/{pid}";
        }

        commentService.addCommentToProduct(commentDTO, userDetails, pid);

        return "redirect:/product/{pid}";

    }

    @GetMapping("/comment/delete/{cid}/product/{pid}")
    public String deleteCommentOfProduct(@PathVariable("cid") Long cid,
                                         @PathVariable("pid") Long pid){
        commentService.deleteCommentById(cid);

        return "redirect:/product/{pid}";

    }
}
