package org.spring.ProjectTeam01.item.service;

import lombok.AllArgsConstructor;
import org.spring.ProjectTeam01.item.dto.CartDto;
import org.spring.ProjectTeam01.item.dto.WishlistDto;
import org.spring.ProjectTeam01.item.entity.CartEntity;
import org.spring.ProjectTeam01.item.entity.WishlistEntity;
import org.spring.ProjectTeam01.item.repository.CartRepository;
import org.spring.ProjectTeam01.item.repository.WishlistRepository;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.spring.ProjectTeam01.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistService wishlistService;
    private final CartRepository cartRepository;

//    public void cartCreate(String email){
//        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(() -> {
//            throw new IllegalArgumentException("이메일 존재하지 않음");
//        });
//        Optional<CartEntity> optionalCartEntity = cartRepository.findByMemberEntityId(memberEntity.getId());
//
//        if (optionalCartEntity.isEmpty()) {
//            CartEntity cartEntityno = cartRepository.save(CartEntity.builder()
//                    .memberEntity(memberEntity)
//                    .build());
//        }
//
//        Optional<CartEntity> optionalCartEntityId = cartRepository.findByMemberEntityEmail(email);
//
//        cartRepository.save(CartEntity.builder()
//                .id(optionalCartEntityId.get().getId())
//                .memberEntity(memberEntity)
//                .build());
//    }

    public void cartCreate(String email, WishlistDto wishlistDto) {
        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new IllegalArgumentException("이메일 존재하지 않음");
        });
        Optional<CartEntity> optionalCartEntity = cartRepository.findByMemberEntityId(memberEntity.getId());

        if (optionalCartEntity.isEmpty()) {
            CartEntity cartEntityno = cartRepository.save(CartEntity.builder()
                    .memberEntity(memberEntity)
                    .build());
        }

        Optional<CartEntity> optionalCartEntityId = cartRepository.findByMemberEntityEmail(email);

        wishlistService.wishlistAdd(wishlistDto, optionalCartEntityId.get().getId());
        List<WishlistEntity> wishlistEntityList = wishlistRepository.findAllByCartEntityId(optionalCartEntityId.get().getId());

        cartRepository.save(CartEntity.builder()
                .id(optionalCartEntityId.get().getId())
                .memberEntity(memberEntity)
                .wishlistEntityList(wishlistEntityList)
                .build());
    }

    public CartDto cartList(String email) {
        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new IllegalArgumentException("이메일 존재하지 않음");
        });
        Optional<CartEntity> optionalCartEntity = cartRepository.findByMemberEntityEmail(memberEntity.getEmail());
        if (optionalCartEntity.isEmpty()) {
            CartEntity cartEntityno = cartRepository.save(CartEntity.builder()
                    .memberEntity(memberEntity)
                    .build());
        }
        Optional<CartEntity> optionalCartEntityId = cartRepository.findByMemberEntityEmail(email);
        List<WishlistEntity> wishlistEntityList = wishlistRepository.findAllByCartEntityId(optionalCartEntityId.get().getId());

        CartDto cartDto = CartDto.builder()
                .id(optionalCartEntityId.get().getId())
                .memberEntity(memberEntity)
                .wishlistEntityList(wishlistEntityList)
                .build();

        return cartDto;
    }
}
