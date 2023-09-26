package org.spring.ProjectTeam01.item.service;

import lombok.RequiredArgsConstructor;
import org.spring.ProjectTeam01.item.dto.FileDto;
import org.spring.ProjectTeam01.item.dto.ItemDto;
import org.spring.ProjectTeam01.item.entity.ItemEntity;
import org.spring.ProjectTeam01.item.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemEntity> getItemList() {
        List<ItemEntity> result = itemRepository.findAll();
        return result;
    }

    public ItemDto getItem(Long id) {
        ItemDto itemDto = new ItemDto();
        Optional<ItemEntity> result = Optional.ofNullable(itemRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("ID에 해당하는 객체가 존재하지 않습니다");
        }));
        if (result.isPresent()) {
            return ItemDto.builder()
                    .id(result.get().getId())
                    .name(result.get().getName())
                    .price(result.get().getPrice())
                    .category(result.get().getCategory())
                    .detail(result.get().getDetail())
                    .deleteYn(result.get().getDeleteYn())
                    .isFile(result.get().getIsFile())
                    .saveUrl(result.get().getSaveUrl())
                    .originalName(result.get().getOriginalName())
                    .saveName(result.get().getSaveName())
                    .build();
        }
        return itemDto;
    }

    @Transactional
    public Long save(ItemDto itemDto, FileDto fileDto) {
        ItemEntity item = ItemEntity.builder()
                .name(itemDto.getName())
                .price(itemDto.getPrice())
                .category(itemDto.getCategory())
                .detail(itemDto.getDetail())
                .deleteYn(itemDto.getDeleteYn())
                .isFile(itemDto.getIsFile())
                .saveUrl(fileDto.getSaveUrl())
                .saveName(fileDto.getSaveName())
                .originalName(fileDto.getOriginalName())
                .build();

        Long itemId = itemRepository.save(item).getId();
        return itemId;
    }

    @Transactional
    public Long update(ItemDto itemDto, FileDto fileDto) {
        ItemEntity item = new ItemEntity();
        Optional<ItemEntity> result = Optional.ofNullable(itemRepository.findById(itemDto.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("ID에 해당하는 객체가 존재하지 않습니다");
        }));
        if (itemDto.getIsFile() == 1) {
            item = ItemEntity.builder()
                    .id(itemDto.getId())
                    .name(itemDto.getName())
                    .price(itemDto.getPrice())
                    .category(itemDto.getCategory())
                    .detail(itemDto.getDetail())
                    .deleteYn(itemDto.getDeleteYn())
                    .isFile(itemDto.getIsFile())
                    .saveUrl(fileDto.getSaveUrl())
                    .saveName(fileDto.getSaveName())
                    .originalName(fileDto.getOriginalName())
                    .build();
        } else {
            item = ItemEntity.builder()
                    .id(itemDto.getId())
                    .name(itemDto.getName())
                    .price(itemDto.getPrice())
                    .category(itemDto.getCategory())
                    .detail(itemDto.getDetail())
                    .deleteYn(itemDto.getDeleteYn())
                    .isFile(result.get().getIsFile())
                    .saveUrl(result.get().getSaveUrl())
                    .saveName(result.get().getSaveName())
                    .originalName(result.get().getOriginalName())
                    .build();
        }
        Long itemId = itemRepository.save(item).getId();
        return itemId;
    }

    public Page<ItemDto> itemListPage(Pageable pageable, String subject, String search) {
        Page<ItemEntity> itemEntityPage = null;
        Page<ItemDto> itemDtoPage = null;

        if (subject.equals("category")) {
            itemEntityPage = itemRepository.findByCategoryContainingAndDeleteYn(pageable, search, 0);
        } else if (subject.equals("name")) {
            itemEntityPage = itemRepository.findByNameContainingAndDeleteYn(pageable, search, 0);
        } else if (subject.equals("detail")) {
            itemEntityPage = itemRepository.findByDetailContainingAndDeleteYn(pageable, search, 0);
        } else {
            itemEntityPage = itemRepository.findByDeleteYn(pageable, 0);
        }

        itemDtoPage = itemEntityPage.map(ItemDto::toDto);
        return itemDtoPage;

    }

    public List<ItemDto> searchList(String category) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        List<ItemEntity> itemEntityList = itemRepository.findByCategoryAndDeleteYn(category, 0);
        for (ItemEntity itemEntity : itemEntityList) {
            itemDtoList.add(ItemDto.toDto(itemEntity));
        }
        return itemDtoList;
    }

    public Page<ItemDto> itemAdminListPage(Pageable pageable) {
        Page<ItemEntity> itemEntityPage = itemRepository.findByAll(pageable);
        Page<ItemDto> itemDtoPage = itemEntityPage.map(ItemDto::toDto);
        return itemDtoPage;
    }
}
