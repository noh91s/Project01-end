package org.spring.ProjectTeam01.home.controller;

import lombok.AllArgsConstructor;
import org.spring.ProjectTeam01.board.dto.BoardDto;
import org.spring.ProjectTeam01.board.service.BoardService;
import org.spring.ProjectTeam01.item.dto.CategoryDto;
import org.spring.ProjectTeam01.item.dto.ItemDto;
import org.spring.ProjectTeam01.item.entity.ItemEntity;
import org.spring.ProjectTeam01.item.service.CategoryService;
import org.spring.ProjectTeam01.item.service.ItemService;
import org.spring.ProjectTeam01.member.config.MyUserDetails;
import org.spring.ProjectTeam01.member.dto.MemberDto;
import org.spring.ProjectTeam01.member.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final CategoryService categoryService;
    private final MemberService memberService;
    private final BoardService boardService;
    private final ItemService itemService;

    @GetMapping({"/","/index"})
    public String index(@RequestParam(value = "category", required = false) String category, Model model){
        if(category==null) {
            category = "영화";
        }
        List<CategoryDto> categoryDtoList = categoryService.categoryList();
        List<ItemDto> searchList = itemService.searchList(category);
        model.addAttribute("categoryP", category);
        model.addAttribute("categoryList", categoryDtoList);
        model.addAttribute("searchList", searchList);
        return "index";
    }
    @GetMapping("/admin")
    public String getadmin(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
        List<MemberDto> memberDtoList = memberService.memberList();
        List<BoardDto> boardDtoList = boardService.boardList();
        List<ItemEntity> itemEntityList = itemService.getItemList();

        model.addAttribute("myUserDetails", myUserDetails);
        model.addAttribute("memberList", memberDtoList);
        model.addAttribute("boardList", boardDtoList);
        model.addAttribute("itemList", itemEntityList);

        return "admin";
    }

    @GetMapping("/search")
    public String getsearch(@RequestParam(value = "menu", required = false) String menu,
                            @RequestParam(value = "search", required = false) String search) throws UnsupportedEncodingException {
        String encodeSearch = URLEncoder.encode(search, "UTF-8");

        if (menu.equals("board")){
            return "redirect:/board/list?subject=title&search="+encodeSearch;
        }else if(menu.equals("item")) {
            return "redirect:/item/list?subject=name&search="+encodeSearch;
        }
        return "index";
    }

}
