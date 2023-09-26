package org.spring.ProjectTeam01.item.controller;

import lombok.AllArgsConstructor;

import org.spring.ProjectTeam01.item.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlist")
@AllArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/delete/{id}")
    public String getdelete(@PathVariable("id") Long id){
        int rs = wishlistService.wishlistDelete(id);
        if(rs==1) {
            System.out.println(("삭제 Success!"));
        }else {
            System.out.println(("삭제 Fail!"));
        }
        return "redirect:/cart/list";
    }
}
