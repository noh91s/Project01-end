package org.spring.ProjectTeam01.board.controller;

import lombok.RequiredArgsConstructor;
import org.spring.ProjectTeam01.board.dto.BoardDto;
import org.spring.ProjectTeam01.board.dto.ReplyDto;
import org.spring.ProjectTeam01.board.repository.BoardRepository;
import org.spring.ProjectTeam01.board.service.BoardService;
import org.spring.ProjectTeam01.board.service.ReplyService;
import org.spring.ProjectTeam01.item.dto.CategoryDto;
import org.spring.ProjectTeam01.member.config.MyUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/write")
    public String getwrite(BoardDto boardDto) {
        return "board/write";
    }

    @PostMapping("/write")
    public String postwrite(@AuthenticationPrincipal MyUserDetails myUserDetails, BoardDto boardDto, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return "board/write";
        }
        String email = myUserDetails.getUsername();
        boardService.boardWrite(boardDto, email);
        return "redirect:/board/list";
    }

//    @GetMapping("/list")
//    public String getlist(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
//        List<BoardDto> boardDtoList = boardService.boardList();
//        model.addAttribute("boardList", boardDtoList);
//        model.addAttribute("myUserDetails", myUserDetails);
//        return "board/list";
//    }

    @GetMapping("/detail/{id}")
    public String getdetail(@PathVariable("id") Long id,
                            @AuthenticationPrincipal MyUserDetails myUserDetails,
                            Model model) {
        BoardDto boardDto = boardService.boardDetail(id);
        List<ReplyDto> replyDtoList = replyService.replyList(boardDto.getId());

        model.addAttribute("board", boardDto);
        model.addAttribute("replyList", replyDtoList);
        model.addAttribute("myUserDetails", myUserDetails);
        return "board/detail";
    }

    @GetMapping("/search/{id}")
    public String getsearch(@PathVariable("id") Long id, Model model) {
        String subject="memberId";

        return "redirect:/board/list?subject="+subject+"&search="+id;
    }

    @GetMapping("/list")
    public String getlist(@AuthenticationPrincipal MyUserDetails myUserDetails,
                          @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam(value = "subject", required = false) String subject,
                          @RequestParam(value = "search", required = false) String search,
                          Model model) {

        if (subject == null) {
            subject = "";
        }

        Page<BoardDto> boardDtoPage = boardService.boardListPage(pageable, subject, search);

        int totalPage = boardDtoPage.getTotalPages();
        int nowPage = boardDtoPage.getNumber();
        int blockNum = 5;
        int pSize = boardDtoPage.getSize();

        int startPage = (int) ((Math.ceil(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.ceil(nowPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        if (!boardDtoPage.isEmpty()) {
            model.addAttribute("boardList", boardDtoPage);
            model.addAttribute("myUserDetails", myUserDetails);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("pSize", pSize);
            model.addAttribute("subject", subject);
            model.addAttribute("search", search);
            return "board/list";
        }
        model.addAttribute("myUserDetails", myUserDetails);
        return "board/list";
    }

    @GetMapping("/search")
    public String getsearch(@RequestParam(value = "subject", required = false) String subject,
                            @RequestParam(value = "search", required = false) String search,
                            Model model) throws UnsupportedEncodingException {
        String encodeSubject = URLEncoder.encode(subject, "UTF-8");
        String encodeSearch = URLEncoder.encode(search, "UTF-8");
        return "redirect:/board/list?subject="+encodeSubject+"&search="+encodeSearch;
    }

    @GetMapping("/update/{id}")
    public String getupdate(@AuthenticationPrincipal MyUserDetails myUserDetails, @PathVariable("id") Long id, CategoryDto categoryDto, Model model) {
        BoardDto boardDto = boardService.boardDetail(id);
        model.addAttribute("myUserDetails", myUserDetails);
        model.addAttribute("board", boardDto);
        return "board/update";
    }


    @PostMapping("/update")
    public String postupdate(@AuthenticationPrincipal MyUserDetails myUserDetails, BoardDto boardDto) throws IOException {

        String email = myUserDetails.getUsername();
        int rs = boardService.boardUpdate(boardDto, email);
        if (rs == 1) {
            System.out.println(("수정 Success!"));
        } else {
            System.out.println(("수정 Fail!"));
        }
        Long boardId = boardDto.getId();
        return "redirect:/board/detail/"+boardId;
    }

    @GetMapping("/delete/{id}")
    public String getdelete(@PathVariable("id") Long id) {
        int rs = boardService.boardDelete(id);
        if (rs == 1) {
            System.out.println(("삭제 Success!"));
        } else {
            System.out.println(("삭제 Fail!"));
        }
        return "redirect:/board/list";
    }



}
