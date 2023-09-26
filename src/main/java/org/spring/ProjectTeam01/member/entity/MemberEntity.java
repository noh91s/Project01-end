package org.spring.ProjectTeam01.member.entity;

import lombok.*;
import org.spring.ProjectTeam01.board.entity.BoardEntity;
import org.spring.ProjectTeam01.board.entity.ReplyEntity;
import org.spring.ProjectTeam01.contrant.Role;
import org.spring.ProjectTeam01.utils.BaseEntity;
import javax.persistence.*;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 64,nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    private String nickName;

    private String phone;

//    private String address;       // 할거면 추가해주세요

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<BoardEntity> boardEntityList;

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReplyEntity> replyEntityList;

}
