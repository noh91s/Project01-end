package org.spring.ProjectTeam01.item.entity;

import lombok.*;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.spring.ProjectTeam01.utils.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart")
public class CartEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToMany(mappedBy = "cartEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<WishlistEntity> wishlistEntityList;

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;


}
