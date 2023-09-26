package org.spring.ProjectTeam01.board.entity;

import lombok.*;

import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.spring.ProjectTeam01.utils.BaseEntity;


import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reply")
public class ReplyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String replyContent;

    private String replyWriter;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;
}
