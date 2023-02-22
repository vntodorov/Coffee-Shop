package com.brewbox.web;

import com.brewbox.model.DTOs.BrandDTO;
import com.brewbox.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @ModelAttribute("brandDTO")
    public BrandDTO brandDTO(){
        return new BrandDTO();
    }

    @GetMapping
    public String allBrands(Model model){
        model.addAttribute("brands", brandService.getAllBrands());

        return "brands";
    }
}
