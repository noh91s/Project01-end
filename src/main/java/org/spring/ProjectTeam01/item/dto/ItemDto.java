package org.spring.ProjectTeam01.item.dto;

import lombok.*;
import org.spring.ProjectTeam01.item.entity.ItemEntity;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private Integer price;
    private String category;
    private String detail;
    private int deleteYn;

    private MultipartFile files;
    private int isFile;
    private String saveUrl;
    private String originalName;
    private String saveName;

    public static ItemDto toDto(final ItemEntity item){
        return ItemDto
                .builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .category(item.getCategory())
                .detail(item.getDetail())
                .deleteYn(item.getDeleteYn())
                .isFile(item.getIsFile())
                .saveUrl(item.getSaveUrl())
                .originalName(item.getOriginalName())
                .saveName(item.getSaveName())
                .build();
    }
}
