package org.spring.ProjectTeam01.member.service;

import lombok.RequiredArgsConstructor;
import org.spring.ProjectTeam01.contrant.Role;
import org.spring.ProjectTeam01.member.dto.MemberDto;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.spring.ProjectTeam01.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /* 회원등록 */
    @Transactional
    public void memberInsert(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberEntity.setNickName(memberDto.getNickName());
        memberEntity.setPhone(memberDto.getPhone());
        memberEntity.setRole(Role.MEMBER);

        Long memberId = memberRepository.save(memberEntity).getId();

        MemberEntity memberEntity1 = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("아이디가없습니다"));

    }

    /* 회원목록 */
    public List<MemberDto> memberList() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            MemberDto memberDto = MemberDto.builder()
                    .id(memberEntity.getId())
                    .email(memberEntity.getEmail())
                    .nickName(memberEntity.getNickName())
                    .phone(memberEntity.getPhone())
                    .role(memberEntity.getRole())
                    .createTime(memberEntity.getCreateTime())
                    .updateTime(memberEntity.getUpdateTime())
                    .build();

            memberDtoList.add(memberDto);
        }
        return memberDtoList;
    }


    @Transactional
    public MemberDto memberDetail(Long id) {

        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        });

        return MemberDto.builder()
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .role(memberEntity.getRole())
                .nickName(memberEntity.getNickName())
                .phone(memberEntity.getPhone())
                .boardEntityList(memberEntity.getBoardEntityList())
                .createTime(memberEntity.getCreateTime())
                .updateTime(memberEntity.getUpdateTime())
                .build();
    }

    @Transactional
    public MemberDto memberUpdate(Long id) {

        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        });

        MemberDto memberDto =
                MemberDto.builder()
                        .id(memberEntity.getId())
                        .email(memberEntity.getEmail())
                        .password(memberEntity.getPassword())
                        .nickName(memberEntity.getNickName())
                        .phone(memberEntity.getPhone())
                        .role(memberEntity.getRole())
                        .build();

        return memberDto;
    }

    @Transactional
    public int memberUpdateOk(MemberDto memberDto) {

        MemberEntity memberEntity = memberRepository.findById(memberDto.getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        });

        // 새로운 비밀번호가 null 또는 빈 문자열이면 비밀번호 변경 없이 업데이트
        if (memberDto.getPassword() != null && !memberDto.getPassword().isEmpty()) {
            if (!memberDto.getPassword().equals(memberEntity.getPassword())) {
                memberEntity.setPassword(passwordEncoder.encode(memberDto.getPassword()));

            }
            memberEntity.setEmail(memberDto.getEmail());
            memberEntity.setNickName(memberDto.getNickName());
            memberEntity.setPhone(memberDto.getPhone());
            memberEntity.setRole(memberDto.getRole());

            memberRepository.save(memberEntity);
        }

        // 업데이트가 성공하면 1, 실패하면 0 반환
        return 1;
    }

    @Transactional
    public MemberDto memberInsertInfoOk(MemberDto memberDto) {
        memberRepository.findByEmail(memberDto.getEmail()).orElseThrow(() -> {
            throw new IllegalArgumentException("이메일이 존재하지 않습니다.");
        });
        MemberEntity memberEntity = memberRepository.save(MemberEntity.builder()
                .id(memberDto.getId())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .nickName(memberDto.getNickName())
                .phone(memberDto.getPhone())
                .role(Role.MEMBER)
                .build()
        );
        return MemberDto.builder()
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .role(memberEntity.getRole())
                .nickName(memberEntity.getNickName())
                .phone(memberEntity.getPhone())
                .build();
    }

    @Transactional
    public void memberDelete(Long id) {

        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("삭제할 아이디가 존재하지 않습니다.");
        });

        memberRepository.delete(memberEntity);

    }

    public int emailCheck(String email) {

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByEmail(email);
        if (optionalMemberEntity.isPresent()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Transactional
    public void SetTempPassword(String email, String tempPassword) {
        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new IllegalArgumentException("이메일이 존재하지 않습니다.");
        });

        // 여기에서 tempPassword를 사용하여 회원의 비밀번호를 설정하고 업데이트하는 로직을 구현합니다.
        // 예를 들어, 비밀번호를 암호화하고 저장할 수 있습니다.
        String encryptedPassword = passwordEncoder.encode(tempPassword);
        memberEntity.setPassword(encryptedPassword);

        memberRepository.save(memberEntity);
    }


    public MemberEntity findByEmailAndNickName(String email, String nickName) {

        return memberRepository.findByEmailAndNickName(email, nickName);

    }
}
