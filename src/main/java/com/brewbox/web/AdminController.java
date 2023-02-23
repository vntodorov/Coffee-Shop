package com.brewbox.web;

import com.brewbox.service.AdminService;
import com.brewbox.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/add")
    public String addAdmin(Model model){
        model.addAttribute("users", adminService.getAllNonAdmins());
        return "admin-add";
    }

    @GetMapping("/add/{id}")
    public String addAdmin(@PathVariable("id") Long id){
        adminService.addAdmin(id);
        return "redirect:/";

    }

    @GetMapping("/remove")
    public String removeAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("admins", adminService.getAllAdmins(userDetails));
        return "admin-remove";
    }

    @GetMapping("/remove/{id}")
    public String removeAdmin(@PathVariable("id") Long id){
        adminService.removeAdmin(id);
        return "redirect:/";
    }
}
