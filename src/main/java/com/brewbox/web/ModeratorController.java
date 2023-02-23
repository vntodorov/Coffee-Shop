package com.brewbox.web;

import com.brewbox.service.AdminService;
import com.brewbox.service.ModeratorService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/moderators")
public class ModeratorController {

    private final ModeratorService moderatorService;

    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }


    @GetMapping("/add")
    public String addModerator(Model model){
        model.addAttribute("users", moderatorService.getAllNonModerators());
        return "moderator-add";
    }

    @GetMapping("/add/{id}")
    public String addModerator(@PathVariable("id") Long id){
        moderatorService.addModerator(id);
        return "redirect:/";

    }

    @GetMapping("/remove")
    public String removeModerator(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("moderators", moderatorService.getAllModerators(userDetails));
        return "moderator-remove";
    }

    @GetMapping("/remove/{id}")
    public String removeModerator(@PathVariable("id") Long id){
        moderatorService.removeModerator(id);
        return "redirect:/";
    }
}
