package org.spring.ProjectTeam01.member.controller;

import lombok.RequiredArgsConstructor;
import org.spring.ProjectTeam01.member.dto.EmailPostDto;
import org.spring.ProjectTeam01.member.dto.EmailResponseDto;
import org.spring.ProjectTeam01.member.dto.MemberDto;
import org.spring.ProjectTeam01.member.entity.EmailMessage;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.spring.ProjectTeam01.member.service.EmailService;
import org.spring.ProjectTeam01.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mail")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final MemberService memberService;

//
//    @GetMapping("/password")
//    public String sendPasswordMail(MemberDto memberDto) {
//        return "mail/password";
//    }

    @PostMapping("/password")
    public String sendPasswordMailPost(@RequestBody MemberEntity memberEntity) {
        // memberEntity에서 email과 nickName을 가져옵니다.
        String email = memberEntity.getEmail();
        String nickName = memberEntity.getNickName();

        // email과 nickName이 일치하는 회원을 DB에서 조회합니다. 이 예제에서는 memberService를 사용합니다.
        MemberEntity foundMember = memberService.findByEmailAndNickName(email, nickName);

        if (foundMember != null) {
            // email과 nickName이 일치하는 회원이 존재하는 경우에만 이메일을 보냅니다.
            EmailMessage emailMessage = EmailMessage.builder()
                    .to(email)
                    .subject("[SAVIEW] 임시 비밀번호 발급")
                    .build();

            emailService.sendMail(memberEntity, "password");

            return "ok";
        } else {
            // email과 nickName이 일치하지 않는 경우에 대한 처리를 여기에 추가하세요.

            return "fail";
        }
    }

        @PostMapping("/emailCheck")
        public String sendJoinMail(@RequestBody EmailPostDto emailPostDto) {

            EmailMessage emailMessage = EmailMessage.builder()
                    .to(emailPostDto.getEmail())
                    .subject("[천천히가조] 이메일 인증을 위한 인증 코드 발송")
                    .build();

            String code = emailService.MailCheck(emailMessage, "email");

            EmailResponseDto emailResponseDto = new EmailResponseDto();
            emailResponseDto.setCode(code);

            return code;
    }





}
