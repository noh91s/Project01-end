package org.spring.ProjectTeam01.item.controller;

import lombok.RequiredArgsConstructor;

import org.spring.ProjectTeam01.item.dto.CartDto;
import org.spring.ProjectTeam01.item.dto.WishlistDto;
import org.spring.ProjectTeam01.item.entity.WishlistEntity;
import org.spring.ProjectTeam01.item.repository.CartRepository;
import org.spring.ProjectTeam01.item.repository.WishlistRepository;
import org.spring.ProjectTeam01.item.service.CartService;
import org.spring.ProjectTeam01.item.service.WishlistService;
import org.spring.ProjectTeam01.member.config.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final WishlistRepository wishlistRepository;
    private final WishlistService wishlistService;
    private final CartRepository cartRepository;
    private final CartService cartService;

//    @GetMapping("/add")
//    public String getadd(@AuthenticationPrincipal MyUserDetails myUserDetails) {
//
//        cartService.cartCreate(myUserDetails.getUsername());
//
//        return "redirect:/cart/list";
//    }
//

    @PostMapping("/add")
    public String postadd(@AuthenticationPrincipal MyUserDetails myUserDetails, WishlistDto wishlistDto) {


        cartService.cartCreate(myUserDetails.getUsername(), wishlistDto);

        return "redirect:/cart/list";
    }

    @GetMapping("/list")
    public String getlist(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {

        CartDto cartDtoList = cartService.cartList(myUserDetails.getUsername());
        List<WishlistDto> wishlistDtoList = new ArrayList<>();
        List<WishlistEntity> wishlistEntityList = wishlistRepository.findAllByCartEntityId(cartDtoList.getId());
        for (WishlistEntity wishlistEntity : wishlistEntityList) {
            wishlistDtoList.add(WishlistDto.builder()
                    .id(wishlistEntity.getId())
                    .size(wishlistEntity.getSize())
                    .itemEntity(wishlistEntity.getItemEntity())
                    .cartEntity(wishlistEntity.getCartEntity())
                    .build());
        }

        model.addAttribute("cart", cartDtoList);
        model.addAttribute("wishList", wishlistDtoList);
        model.addAttribute("myUserDetails", myUserDetails);

        return "cart/list";

    }

}
