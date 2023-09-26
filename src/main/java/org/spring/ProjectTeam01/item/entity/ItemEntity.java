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
@Table(name = "item")
public class ItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private Integer price;

    private String category;

    private String detail;

    private String seller;

    @Column(name = "delete_yn", length = 1)
    private Integer deleteYn;

    @Column(name = "is_file", nullable = false, length = 1)
    private int isFile;

    @Column(name = "save_url")
    private String saveUrl;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "save_name")
    private String saveName;

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private MemberEntity memberEntity;
//
//    @OneToMany(mappedBy = "itemEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    private List<IFileEntity> iFileEntityList;
//
    @OneToMany(mappedBy = "itemEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<WishlistEntity> wishlistEntityList;

}
