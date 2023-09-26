package org.spring.ProjectTeam01.item.repository;


import org.spring.ProjectTeam01.item.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByMemberEntityId(Long id);

    Optional<CartEntity> findByMemberEntityEmail(String email);
}
