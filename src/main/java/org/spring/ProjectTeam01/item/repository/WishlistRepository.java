package org.spring.ProjectTeam01.item.repository;


import org.spring.ProjectTeam01.item.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
    List<WishlistEntity> findAllByCartEntityId(Long id);


    @Query(value = "select * " +
            "from cart c inner join wishlist w " +
            "on c.cart_id = w.cart_id " +
            "where c.cart_id = :id1 and w.item_id = :id2", nativeQuery = true)
    Optional<WishlistEntity> findAllByCartEntityIdandItemEntityId(Long id1, Long id2);
}
