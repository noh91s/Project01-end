package org.spring.ProjectTeam01.member.controller;

import lombok.RequiredArgsConstructor;
import org.spring.ProjectTeam01.member.config.MyUserDetails;
import org.spring.ProjectTeam01.member.dto.MemberDto;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.spring.ProjectTeam01.member.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @GetMapping("/join")
    public String join(MemberDto memberDto) {
        return "member/join";
    }

    // 회원가입 Post
    @PostMapping("/join")
    public String postJoin(@Valid MemberDto memberDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "member/join";
        }
        memberService.memberInsert(memberDto);
        return "member/login";

    }

    // 로그인
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    // 회원전체목록 (태그가없어서 url직접입력해서 테스트했어요 admin페이지 만들어지면 옮길예정)
    @GetMapping("/list")
    public String memberList(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        List<MemberDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        model.addAttribute("myUserDetails", myUserDetails);

        return "member/list";
    }

    // 회원상세정보 (my page)
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,
                         Model model,
                         @AuthenticationPrincipal MyUserDetails myUserDetails) {

        MemberDto member = memberService.memberDetail(id);

        model.addAttribute("member", member);
        model.addAttribute("myUserDetails", myUserDetails);

        return "member/detail";
    }

    // 회원 수정
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        MemberDto member = memberService.memberUpdate(id);

        if (member != null) {

            model.addAttribute("member", member);
            model.addAttribute("myUserDetails", myUserDetails);

            return "member/update";
        }

        return "redirect:/member/update";
    }

    // 회원 수정 post
    @PostMapping("/update")
    public String updateOk(MemberDto memberDto, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        Long id = memberDto.getId();
        memberService.memberUpdateOk(memberDto);

        return "redirect:/member/detail/"+memberDto.getId();
    }


    // insertInfo
    @GetMapping("/insertInfo")
    public String insertInfo(MemberDto member, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        model.addAttribute("member", myUserDetails);
        return "member/insertInfo";
    }

    // insertInfo Post
    @PostMapping("/insertInfo")
    public String insertInfo(MemberDto memberDto, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        MemberDto memberDto1 = memberService.memberInsertInfoOk(memberDto);
        myUserDetails.setMemberEntity(MemberEntity.builder()
                .id(memberDto1.getId())
                .email(memberDto1.getEmail())
                .password(memberDto1.getPassword())
                .role(memberDto1.getRole())
                .nickName(memberDto1.getNickName())
                .phone(memberDto1.getPhone())
                .build());

        return "index";
    }

    // 회원 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        memberService.memberDelete(id);
        return "redirect:/member/logout";
    }

    // 이메일 체크
    @PostMapping("/emailCheck")
    public @ResponseBody String emailCheck(@RequestParam("email") String email){

        System.out.println("email = " + email);
        int checkResult = memberService.emailCheck(email);

        if(checkResult == 0) {
            return "no";
        } else {
            return "ok";
        }
    }

    // mail
    @GetMapping("/findPassword")
    public String findPassword(){
        return "member/findPassword";
    }

}
