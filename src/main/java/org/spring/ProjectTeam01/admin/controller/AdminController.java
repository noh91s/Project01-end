package org.spring.ProjectTeam01.admin.controller;

import lombok.RequiredArgsConstructor;
import org.spring.ProjectTeam01.board.dto.BoardDto;
import org.spring.ProjectTeam01.board.service.BoardService;
import org.spring.ProjectTeam01.item.dto.CategoryDto;
import org.spring.ProjectTeam01.item.dto.FileDto;
import org.spring.ProjectTeam01.item.dto.ItemDto;
import org.spring.ProjectTeam01.item.service.CategoryService;
import org.spring.ProjectTeam01.item.service.ItemService;
import org.spring.ProjectTeam01.item.util.FileUtils;
import org.spring.ProjectTeam01.member.config.MyUserDetails;
import org.spring.ProjectTeam01.member.dto.MemberDto;
import org.spring.ProjectTeam01.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FileUtils fileUtils;
    private final MemberService memberService;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final BoardService boardService;

    //대시보드 페이지
    @GetMapping({"/","/index"})
    public String index(){
        return "admin/index";
    }

    //사용자관리
    @GetMapping("/user")
    public String memberList(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        List<MemberDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        model.addAttribute("myUserDetails", myUserDetails);

        return "admin/user/user";
    }


    @GetMapping("/userDetail/{id}")
    public String detail(@PathVariable("id") Long id,
                         Model model,
                         @AuthenticationPrincipal MyUserDetails myUserDetails) {

        MemberDto member = memberService.memberDetail(id);

        model.addAttribute("member", member);
        model.addAttribute("myUserDetails", myUserDetails);

        return "admin/user/userDetail";
    }

    @GetMapping("/userUpdate/{id}")
    public String update(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        MemberDto member = memberService.memberUpdate(id);

        if (member != null) {
            model.addAttribute("member", member);
            model.addAttribute("myUserDetails", myUserDetails);
            return "admin/user/userUpdate";
        }

        return "redirect:/admin/user";
    }

    @PostMapping("/userUpdate")
    public String updateOk(MemberDto memberDto, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        int rs = memberService.memberUpdateOk(memberDto);

        return "redirect:/admin/user";
    }

    //상품관리
    @GetMapping("/item")
    public String getlist(@AuthenticationPrincipal MyUserDetails myUserDetails,
                          @PageableDefault(page = 0, size = 5, sort = "item_id", direction = Sort.Direction.DESC) Pageable pageable,
                          Model model){

        Page<ItemDto> itemDtoPage = itemService.itemAdminListPage(pageable);

        int totalPage = itemDtoPage.getTotalPages();
        int nowPage = itemDtoPage.getNumber();
        int blockNum = 5;
        int pSize = itemDtoPage.getSize();

        int startPage = (int) ((Math.ceil(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.ceil(nowPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        model.addAttribute("result", itemDtoPage);
        model.addAttribute("myUserDetails", myUserDetails);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "admin/item/list";
    }
    @GetMapping("/item/{id}")
    public String getItemDetail(@PathVariable(value="id")Long id, Model model){
        ItemDto item = itemService.getItem(id);
        model.addAttribute("item", item);
        return "admin/item/detail";
    }

    @GetMapping("/item/create")
    public String createItem(@RequestParam(value="id", required = false)Long id, CategoryDto categoryDto, Model model){
        ItemDto item = new ItemDto();
        if(id != null){
            item = itemService.getItem(id);
        }
        List<CategoryDto> categoryDtoList = categoryService.categoryList();
        model.addAttribute("categoryList", categoryDtoList);
        model.addAttribute("itemDto", item);
        return "admin/item/create";
    }

    @PostMapping("/item/save")
    public String saveItem(ItemDto itemDto){
        FileDto fileDto = new FileDto();
        if (itemDto.getIsFile() > 0){
            fileDto = fileUtils.uploadFile(itemDto.getFiles());
        }
        Long saveId = itemService.save(itemDto, fileDto);
        return "redirect:/admin/item";
    }

    @PostMapping("/item/update")
    public String updateItem(ItemDto itemDto){
        FileDto fileDto = new FileDto();
        if (itemDto.getIsFile() > 0){
            fileDto = fileUtils.uploadFile(itemDto.getFiles());
        }
        Long updateId = itemService.update(itemDto,fileDto);
        return "redirect:/admin/item";
    }

    //게시판관리
    @GetMapping("/board")
    public String boardList(@AuthenticationPrincipal MyUserDetails myUserDetails,
                            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "subject", required = false) String subject,
                            @RequestParam(value = "search", required = false) String search,
                            Model model) {

        if(subject==null){
            subject="";
        }

        Page<BoardDto> boardDtoPage = boardService.boardListPage(pageable, subject, search);

        int totalPage = boardDtoPage.getTotalPages();
        int nowPage = boardDtoPage.getNumber();
        int blockNum = 5;

        int startPage = (int) ((Math.ceil(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.ceil(nowPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        model.addAttribute("result", boardDtoPage);
        model.addAttribute("myUserDetails", myUserDetails);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/board/list";
    }

    @GetMapping("/board/{id}")
    public String getBoardDetail(@PathVariable(value="id")Long id, Model model){
        BoardDto board = boardService.boardDetail(id);
        model.addAttribute("board", board);
        return "admin/board/detail";
    }

    @GetMapping("/board/create")
    public String createBoard(@RequestParam(value="id", required = false)Long id, Model model){
        BoardDto item = new BoardDto();
        if(id != null){
            item = boardService.boardDetail(id);
        }
        model.addAttribute("boardDto", item);
        return "admin/board/create";
    }
//
//    @PostMapping("/board/save")
//    public String saveBoard(BoardDto boardDto){
//        FileDto fileDto = new FileDto();
//        if (boardDto.getIs_file() > 0){
//            fileDto = fileUtils.uploadFile(boardDto.getBoardFile());
//        }
//        Long saveId = boardService.save(boardDto, fileDto);
//        return "redirect:/admin/board";
//    }
//
//    @PostMapping("/board/update")
//    public String updateBoard(BoardDto boardDto){
//        FileDto fileDto = new FileDto();
//        if (boardDto.getIs_file() > 0){
//            fileDto = fileUtils.uploadFile(boardDto.getBoardFile());
//        }
//        Long updateId = boardService.update(boardDto,fileDto);
//        return "redirect:/admin/board";
//    }


}
