package org.spring.ProjectTeam01.board.repository;


import org.spring.ProjectTeam01.board.entity.BoardEntity;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Modifying
    @Query(value = "update BoardEntity b set b.hit=b.hit+1 where b.id=:id")
    void boardHit(@Param("id") Long id);

    List<BoardEntity> findByTitleContaining(String search);
    Page<BoardEntity> findByTitleContaining(Pageable pageable, String search);
    Page<BoardEntity> findByContentContaining(Pageable pageable, String search);
    Page<BoardEntity> findByWriterContaining(Pageable pageable, String search);
    Optional<BoardEntity> findByMemberEntity(MemberEntity memberEntity);
    Page<BoardEntity> findByMemberEntityId(Pageable pageable, String search);
}
