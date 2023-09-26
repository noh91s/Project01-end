package org.spring.ProjectTeam01.board.entity;

import lombok.*;
import org.spring.ProjectTeam01.utils.BaseEntity;



import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "file")
public class BFileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bfile_id")
    private Long id;

    @Column(nullable = false)
    private String fileOldName;

    @Column(nullable = false)
    private String fileNewName;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

}
