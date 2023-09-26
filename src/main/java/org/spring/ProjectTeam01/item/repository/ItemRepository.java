package org.spring.ProjectTeam01.item.repository;

import org.spring.ProjectTeam01.item.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity,Long> {

    @Query(value = "select * "+
            "from item i " +
            "where i.category like %:search% and i.delete_yn = :yn order by i.item_id desc", nativeQuery = true)
    Page<ItemEntity> findByCategoryContainingAndDeleteYn(Pageable pageable, @Param("search") String search, @Param("yn") int yn);

    @Query(value = "select * "+
            "from item i " +
            "where i.name like %:search% and i.delete_yn = :yn order by i.item_id desc", nativeQuery = true)
    Page<ItemEntity> findByNameContainingAndDeleteYn(Pageable pageable, @Param("search") String search, @Param("yn") int yn);

    @Query(value = "select * "+
            "from item i " +
            "where i.detail like %:search% and i.delete_yn = :yn order by i.item_id desc", nativeQuery = true)
    Page<ItemEntity> findByDetailContainingAndDeleteYn(Pageable pageable, @Param("search") String search, @Param("yn") int yn);

    @Query(value = "select * " +
            "from item i " +
            "where i.delete_yn = :yn order by i.item_id desc", nativeQuery = true)
    Page<ItemEntity> findByDeleteYn(Pageable pageable, @Param("yn") int yn);

    @Query(value = "select * " +
            "from item i " +
            "where i.category = :category and i.delete_yn = :yn order by i.item_id desc", nativeQuery = true)
    List<ItemEntity> findByCategoryAndDeleteYn(String category, @Param("yn") int yn);

    @Query(value = " select * " +
            "from item i order by i.item_id desc", nativeQuery = true)
    Page<ItemEntity> findByAll(Pageable pageable);
}
