package com.brewbox.web;

import com.brewbox.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final BrandService brandService;

    @Autowired
    public HomeController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String home(Model model){
        model.addAttribute("brands", brandService.findAllBrands());

        return "index";
    }
}
