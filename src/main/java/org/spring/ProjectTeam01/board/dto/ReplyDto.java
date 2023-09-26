package org.spring.ProjectTeam01.board.dto;

import lombok.*;
import org.spring.ProjectTeam01.board.entity.BoardEntity;
import org.spring.ProjectTeam01.board.entity.ReplyEntity;
import org.spring.ProjectTeam01.member.entity.MemberEntity;


import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplyDto {

    private Long id;
    private String replyContent;
    private String replyWriter;
    private BoardEntity boardEntity;
    private MemberEntity memberEntity;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long boardId;
    private Long memberId;
    private String email;

    public static ReplyDto toReplyDto(ReplyEntity replyEntity) {
        ReplyDto replyDto = new ReplyDto();
        replyDto.setId(replyEntity.getId());
        replyDto.setEmail(replyEntity.getMemberEntity().getEmail());
        replyDto.setReplyContent(replyEntity.getReplyContent());
        replyDto.setReplyWriter(replyEntity.getReplyWriter());
        replyDto.setBoardId(replyEntity.getBoardEntity().getId());
        replyDto.setMemberId(replyEntity.getMemberEntity().getId());
        replyDto.setCreateTime(replyEntity.getCreateTime());
        replyDto.setUpdateTime(replyEntity.getUpdateTime());
        return replyDto;
    }
}
