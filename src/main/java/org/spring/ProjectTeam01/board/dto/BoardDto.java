package org.spring.ProjectTeam01.board.dto;

import lombok.*;
import org.spring.ProjectTeam01.board.entity.BFileEntity;
import org.spring.ProjectTeam01.board.entity.BoardEntity;
import org.spring.ProjectTeam01.board.entity.ReplyEntity;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private int hit;
    private int isFile;
    private MemberEntity memberEntity;
    private List<BFileEntity> bFileEntityList;
    private List<ReplyEntity> replyEntityList;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private MultipartFile boardFile;

    public static BoardDto toboardDto(BoardEntity boardEntity) {
        BoardDto boardDto = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .hit(boardEntity.getHit())
                .isFile(boardEntity.getIsFile())
                .bFileEntityList(boardEntity.getBFileEntityList())
                .memberEntity(boardEntity.getMemberEntity())
                .createTime(boardEntity.getCreateTime())
                .updateTime(boardEntity.getUpdateTime())
                .build();
        return boardDto;
    }
}
