package org.spring.ProjectTeam01.item.service;

import lombok.AllArgsConstructor;
import org.spring.ProjectTeam01.item.dto.WishlistDto;
import org.spring.ProjectTeam01.item.entity.CartEntity;
import org.spring.ProjectTeam01.item.entity.ItemEntity;
import org.spring.ProjectTeam01.item.entity.WishlistEntity;
import org.spring.ProjectTeam01.item.repository.CartRepository;
import org.spring.ProjectTeam01.item.repository.ItemRepository;
import org.spring.ProjectTeam01.item.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WishlistService {

    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;
    private final ItemRepository itemRepository;

    public void wishlistAdd(WishlistDto wishlistDto, Long id) {

        Optional<CartEntity> optionalCartEntity = cartRepository.findById(id);
        Optional<ItemEntity> optionalItemEntity = itemRepository.findById(wishlistDto.getId());
        Optional<WishlistEntity> optionalWishlistEntity = wishlistRepository.findAllByCartEntityIdandItemEntityId(optionalCartEntity.get().getId(), wishlistDto.getId());
        if (optionalWishlistEntity.isEmpty()) {
            wishlistRepository.save(WishlistEntity.builder()
                    .size(wishlistDto.getSize())
                    .cartEntity(optionalCartEntity.get())
                    .itemEntity(optionalItemEntity.get())
                    .build());
        } else {
            wishlistRepository.save(WishlistEntity.builder()
                    .id(optionalWishlistEntity.get().getId())
                    .size(wishlistDto.getSize() + optionalWishlistEntity.get().getSize())
                    .cartEntity(optionalWishlistEntity.get().getCartEntity())
                    .itemEntity(optionalWishlistEntity.get().getItemEntity())
                    .build());
        }
    }

    public int wishlistDelete(Long id) {
        Optional<WishlistEntity> wishlistRepositoryOptional = Optional.ofNullable(wishlistRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("삭제할 바구니 리스트 없음");
        }));

        wishlistRepository.delete(wishlistRepositoryOptional.get());
        Optional<WishlistEntity> optionalWishlistEntity = wishlistRepository.findById(id);
        if (!optionalWishlistEntity.isPresent()) {
            return 1;
        }
        return 0;
    }
}
