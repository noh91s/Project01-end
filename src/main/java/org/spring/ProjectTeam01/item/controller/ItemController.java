package org.spring.ProjectTeam01.item.controller;

import lombok.RequiredArgsConstructor;
import org.spring.ProjectTeam01.item.dto.CategoryDto;
import org.spring.ProjectTeam01.item.dto.FileDto;
import org.spring.ProjectTeam01.item.dto.ItemDto;
import org.spring.ProjectTeam01.item.entity.ItemEntity;
import org.spring.ProjectTeam01.item.service.CategoryService;
import org.spring.ProjectTeam01.item.service.ItemService;
import org.spring.ProjectTeam01.item.util.FileUtils;
import org.spring.ProjectTeam01.member.config.MyUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final FileUtils fileUtils;
    private final CategoryService categoryService;

    @GetMapping("/category")
    public String getcategory(CategoryDto categoryDto) {
        categoryService.categoryAdd(categoryDto);
        return "redirect:/admin/item/create";
    }

    @GetMapping("/list")
    public String getlist(@AuthenticationPrincipal MyUserDetails myUserDetails,
                          @PageableDefault(page = 0, size = 8, sort = "item_id", direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam(value = "subject", required = false) String subject,
                          @RequestParam(value = "search", required = false) String search,
                          @RequestParam(value = "category", required = false) String category,
                          Model model
                          ){

        if(subject==null){
            subject="";
        }


        System.out.println(search+" search");
        System.out.println(subject+" subject");
        List<CategoryDto> categoryDtoList = categoryService.categoryList();
        List<ItemDto> searchList = itemService.searchList(category);
        model.addAttribute("categoryP", category);
        model.addAttribute("categoryList", categoryDtoList);
        model.addAttribute("searchList", searchList);


        Page<ItemDto> itemDtoPage = itemService.itemListPage(pageable, subject, search);

        int totalPage = itemDtoPage.getTotalPages();
        int nowPage = itemDtoPage.getNumber();
        int blockNum = 5;
        int pSize = itemDtoPage.getSize();

        int startPage = (int) ((Math.ceil(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.ceil(nowPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        if (!itemDtoPage.isEmpty()) {
            model.addAttribute("itemList", itemDtoPage);
            model.addAttribute("myUserDetails", myUserDetails);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("pSize", pSize);
            model.addAttribute("subject", subject);
            model.addAttribute("search", search);
            return "item/list";
        }
        model.addAttribute("myUserDetails", myUserDetails);
        return "item/list";
    }

    @GetMapping("/detail")
    public String getItemDetail(@RequestParam(value="id")Long id, Model model){
        ItemDto item = itemService.getItem(id);
        model.addAttribute("item", item);
        return "item/detail";
    }

    @GetMapping("/search")
    public String getsearch(@RequestParam(value = "subject", required = false) String subject,
                            @RequestParam(value = "search", required = false) String search,
                            Model model) throws UnsupportedEncodingException {
        String encodeSubject = URLEncoder.encode(subject, "UTF-8");
        String encodeSearch = URLEncoder.encode(search, "UTF-8");
        return "redirect:/item/list?subject="+encodeSubject+"&search="+encodeSearch;
    }


}
