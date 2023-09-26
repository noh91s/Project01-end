package org.spring.ProjectTeam01.item.dto;

import lombok.*;
import org.spring.ProjectTeam01.item.entity.WishlistEntity;
import org.spring.ProjectTeam01.member.entity.MemberEntity;


import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDto {

    private Long id;
    private List<WishlistEntity> wishlistEntityList;
    private MemberEntity memberEntity;
}
