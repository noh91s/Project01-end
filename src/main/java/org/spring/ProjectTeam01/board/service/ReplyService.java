package org.spring.ProjectTeam01.board.service;

import lombok.RequiredArgsConstructor;
import org.spring.ProjectTeam01.board.dto.ReplyDto;
import org.spring.ProjectTeam01.board.entity.BoardEntity;
import org.spring.ProjectTeam01.board.entity.ReplyEntity;
import org.spring.ProjectTeam01.board.repository.BoardRepository;
import org.spring.ProjectTeam01.board.repository.ReplyRepository;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.spring.ProjectTeam01.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public ReplyDto replywriteAjax(ReplyDto replyDto) {

        System.out.println(replyDto.getReplyWriter()+" 1");
        System.out.println(replyDto.getReplyContent());
        System.out.println(replyDto.getEmail());
        System.out.println(replyDto.getBoardId()+" 4");

        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(replyDto.getBoardId());
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByEmail(optionalBoardEntity.get().getMemberEntity().getEmail());

        Long replyId = replyRepository.save(ReplyEntity.builder()
//                        .id(replyDto.getId())
                        .replyContent(replyDto.getReplyContent())
                        .replyWriter(replyDto.getReplyWriter())
                        .boardEntity(optionalBoardEntity.get())
                        .memberEntity(optionalMemberEntity.get())
                        .build()).getId();

        Optional<ReplyEntity> optionalReplyEntity = replyRepository.findById(replyId);
        ReplyEntity replyEntity = optionalReplyEntity.get();

        System.out.println(replyEntity.getMemberEntity().getEmail());

        if(optionalReplyEntity.isPresent()){
            return ReplyDto.toReplyDto(replyEntity);
        }else {
            return null;
        }
    }

    public List<ReplyDto> replyList(Long id) {
        List<ReplyDto> replyDtoList = new ArrayList<>();
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);

        List<ReplyEntity> replyEntityList = replyRepository.findAllByBoardEntity(optionalBoardEntity.get());
        for(ReplyEntity replyEntity: replyEntityList){
            ReplyDto replyDto = ReplyDto.toReplyDto(replyEntity);
            replyDtoList.add(replyDto);
        }
        return replyDtoList;
    }

    public int replyDelete(Long id) {
        Optional<ReplyEntity> optionalReplyEntity = Optional.ofNullable(
                replyRepository.findById(id).orElseThrow(()->{
                    throw new IllegalArgumentException("삭제할 댓글이 없습니다");
                }));
        replyRepository.delete(optionalReplyEntity.get());
        if(!replyRepository.findById(id).isPresent()){
            return 1;
        }
        return 0;
    }

    public int replyUpdate(ReplyDto replyDto) {
        Optional<ReplyEntity> optionalReplyEntity = replyRepository.findById(replyDto.getId());
            Long replyEntityId=replyRepository.save(ReplyEntity.builder()
                    .id(replyDto.getId())
                    .replyWriter(replyDto.getReplyWriter())
                    .replyContent(replyDto.getReplyContent())
                    .memberEntity(optionalReplyEntity.get().getMemberEntity())
                    .boardEntity(optionalReplyEntity.get().getBoardEntity())
                    .build()).getId();
        if (replyEntityId != null) {
            return 1;
        }
        return 0;

    }
}
