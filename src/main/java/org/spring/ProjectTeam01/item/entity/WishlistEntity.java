package org.spring.ProjectTeam01.item.entity;

import lombok.*;
import org.spring.ProjectTeam01.utils.BaseEntity;

import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wishlist")
// N:M 연관관계 쓰지말라해서 중간다리 역할하는 ENTITY입니다 수정필요하시면 알려주세요 -홍성-
public class WishlistEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    private int size;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity itemEntity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;
}
