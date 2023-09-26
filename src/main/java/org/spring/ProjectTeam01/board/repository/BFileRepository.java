package org.spring.ProjectTeam01.board.repository;


import org.spring.ProjectTeam01.board.entity.BFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BFileRepository extends JpaRepository<BFileEntity, Long> {
    Optional<BFileEntity> findByBoardEntityId(Long id);
}
