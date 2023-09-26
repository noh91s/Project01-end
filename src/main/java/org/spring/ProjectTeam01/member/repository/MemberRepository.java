package org.spring.ProjectTeam01.member.repository;

import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByEmail(String email);
    MemberEntity findByEmailAndNickName(String email, String nickName);
}
