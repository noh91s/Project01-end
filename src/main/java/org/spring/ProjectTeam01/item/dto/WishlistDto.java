package org.spring.ProjectTeam01.item.dto;

import lombok.*;
import org.spring.ProjectTeam01.item.entity.CartEntity;
import org.spring.ProjectTeam01.item.entity.ItemEntity;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WishlistDto {

    private Long id;
    private int size;
    private ItemEntity itemEntity;
    private CartEntity cartEntity;

}
