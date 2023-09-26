package org.spring.ProjectTeam01.board.entity;

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
@Table(name = "board")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String content;

    private String writer;

    private int hit;

    @Column(nullable = false, length = 1)
    private int isFile;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "boardEntity" , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BFileEntity> bFileEntityList;

    @OneToMany(mappedBy = "boardEntity" , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReplyEntity> replyEntityList;

}
