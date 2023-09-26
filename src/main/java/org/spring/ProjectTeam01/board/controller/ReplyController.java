package org.spring.ProjectTeam01.board.controller;

import lombok.RequiredArgsConstructor;
import org.spring.ProjectTeam01.board.dto.ReplyDto;
import org.spring.ProjectTeam01.board.entity.ReplyEntity;
import org.spring.ProjectTeam01.board.repository.BoardRepository;
import org.spring.ProjectTeam01.board.repository.ReplyRepository;
import org.spring.ProjectTeam01.board.service.ReplyService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;


    @PostMapping("/writeBtn")
    public @ResponseBody ReplyDto postwriteBtn(@ModelAttribute ReplyDto replyDto){

        ReplyDto replyRs=replyService.replywriteAjax(replyDto);
        return replyRs;
    }

    @GetMapping("/delete/{id}")
    public String getdelete(@PathVariable("id") Long id) {
        Optional<ReplyEntity> optionalReplyEntity = replyRepository.findById(id);
        Long boardId = optionalReplyEntity.get().getBoardEntity().getId();

        int rs = replyService.replyDelete(id);
        if (rs == 1) {
            System.out.println(("삭제 Success!"));
        } else {
            System.out.println(("삭제 Fail!"));
        }
        return "redirect:/board/detail/"+boardId;
    }

    @PostMapping("/update")
    public String postupdate(ReplyDto replyDto){
        Optional<ReplyEntity> optionalReplyEntity=replyRepository.findById(replyDto.getId());
        Long boardId=optionalReplyEntity.get().getBoardEntity().getId();
        int rs = replyService.replyUpdate(replyDto);
        if (rs == 1) {
            System.out.println(("수정 Success!"));
        } else {
            System.out.println(("수정 Fail!"));
        }
        return "redirect:/board/detail/"+boardId;
    }
}
