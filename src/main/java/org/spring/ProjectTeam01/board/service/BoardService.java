package org.spring.ProjectTeam01.board.service;

import lombok.RequiredArgsConstructor;

import org.spring.ProjectTeam01.board.dto.BoardDto;
import org.spring.ProjectTeam01.board.entity.BFileEntity;
import org.spring.ProjectTeam01.board.entity.BoardEntity;
import org.spring.ProjectTeam01.board.repository.BFileRepository;
import org.spring.ProjectTeam01.board.repository.BoardRepository;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.spring.ProjectTeam01.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BFileRepository bFileRepository;

    @Transactional
    public void boardWrite(BoardDto boardDto, String email) throws IOException {
        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(()->{
            throw new IllegalArgumentException("이메일 존재하지 않음");
        });
        if(boardDto.getBoardFile().isEmpty()) {
            Long boardId = boardRepository.save(BoardEntity.builder()
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .writer(boardDto.getWriter())
                    .hit(0)
                    .isFile(0)
                    .memberEntity(memberEntity)
                    .build()).getId();
            boardRepository.findById(boardId).orElseThrow(() -> {
                throw new IllegalArgumentException("게시글 생성실패");
            });
        }else {
            MultipartFile boardFile = boardDto.getBoardFile();
            String fileOldName = boardFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String fileNewName = uuid + "_" + fileOldName;
            String savePath = "E:/test/saveFile08/bfiles/" + fileNewName;
            boardFile.transferTo(new File(savePath));

            Long boardId = boardRepository.save(BoardEntity.builder()
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .writer(boardDto.getWriter())
                    .hit(0)
                    .isFile(1)
                    .memberEntity(memberEntity)
                    .build()).getId();
            BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> {
                throw new IllegalArgumentException("게시글 생성실패");
            });
            BFileEntity bFileEntity = BFileEntity.builder()
                    .boardEntity(boardEntity)
                    .fileNewName(fileNewName)
                    .fileOldName(fileOldName)
                    .build();
            Long bFileid = bFileRepository.save(bFileEntity).getId();
            bFileRepository.findById(bFileid).orElseThrow(()->{
                throw new IllegalArgumentException("파일등록오류");
            });
        }
    }

    public List<BoardDto> boardList() {
        List<BoardDto> boardDtos = new ArrayList<>();
        List<BoardEntity> boardEntities = boardRepository.findAll();
        for(BoardEntity boardEntity : boardEntities){
            boardDtos.add(BoardDto.builder()
                            .id(boardEntity.getId())
                            .title(boardEntity.getTitle())
                            .content(boardEntity.getContent())
                            .writer(boardEntity.getWriter())
                            .hit(boardEntity.getHit())
                            .isFile(boardEntity.getIsFile())
                            .bFileEntityList(boardEntity.getBFileEntityList())
                            .memberEntity(boardEntity.getMemberEntity())
                            .createTime(boardEntity.getCreateTime())
                            .updateTime(boardEntity.getUpdateTime())
                    .build());
        }
        return boardDtos;
    }

    @Transactional
    public BoardDto boardDetail(Long id) {
        updateHit(id);

        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("조회할 게시글이 없습니다");
        });
        BoardDto boardDto = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .bFileEntityList(boardEntity.getBFileEntityList())
                .isFile(boardEntity.getIsFile())
                .memberEntity(boardEntity.getMemberEntity())
                .createTime(boardEntity.getCreateTime())
                .updateTime(boardEntity.getUpdateTime())
                .hit(boardEntity.getHit())
                .replyEntityList(boardEntity.getReplyEntityList())
                .build();
        return boardDto;
    }

    @Transactional
    private void updateHit(Long id) {
        boardRepository.boardHit(id);
    }

    public List<BoardDto> boardTitleSearch(String search) {
        List<BoardDto> boardDtoList = new ArrayList<>();
        List<BoardEntity> boardEntityList = boardRepository.findByTitleContaining(search);
        for(BoardEntity boardEntity : boardEntityList){
            boardDtoList.add(BoardDto.builder()
                    .id(boardEntity.getId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .writer(boardEntity.getWriter())
                    .hit(boardEntity.getHit())
                    .isFile(boardEntity.getIsFile())
                    .bFileEntityList(boardEntity.getBFileEntityList())
                    .memberEntity(boardEntity.getMemberEntity())
                    .createTime(boardEntity.getCreateTime())
                    .updateTime(boardEntity.getUpdateTime())
                    .build());
        }
        return boardDtoList;
    }

    public Page<BoardDto> boardListPage(Pageable pageable, String subject, String search) {
        System.out.println(subject);
        Page<BoardEntity> boardEntityPage = null;

        if (subject.equals("title")) {
            boardEntityPage = boardRepository.findByTitleContaining(pageable, search);
        } else if (subject.equals("writer")) {
            boardEntityPage = boardRepository.findByWriterContaining(pageable, search);
        } else if (subject.equals("content")) {
            boardEntityPage = boardRepository.findByContentContaining(pageable, search);
        } else if (subject.equals("content")) {
            boardEntityPage = boardRepository.findByMemberEntityId(pageable, search);
        } else {
            boardEntityPage = boardRepository.findAll(pageable);
        }

        Page<BoardDto> boardDtoPage = boardEntityPage.map(BoardDto::toboardDto);
        return boardDtoPage;
    }

    public int boardUpdate(BoardDto boardDto, String email) throws IOException {
        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new IllegalArgumentException("이메일 존재하지 않음");
        });
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardDto.getId());
        Optional<BFileEntity> optionalBFileEntity = bFileRepository.findByBoardEntityId(boardDto.getId());
        if(optionalBFileEntity.isPresent()){
            String fileNewName = optionalBFileEntity.get().getFileNewName();
            String filePath = "E:/test/saveFile08/bfiles/" + fileNewName;
            File deleteFile = new File(filePath);

            System.out.println(fileNewName);
            System.out.println(filePath);
            System.out.println(deleteFile);

            if(deleteFile.exists()) {
                deleteFile.delete();
                System.out.println("파일을 삭제하였습니다.");
            } else {
                System.out.println("파일이 존재하지 않습니다.");
            }
            bFileRepository.delete(optionalBFileEntity.get());
        }

        if (boardDto.getBoardFile().isEmpty()) {
//            System.out.println("파일 X");

            Long boardId = boardRepository.save(BoardEntity.builder()
                    .id(boardDto.getId())
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .writer(boardDto.getWriter())
                    .hit(optionalBoardEntity.get().getHit())
                    .isFile(0)
                    .memberEntity(optionalBoardEntity.get().getMemberEntity())
                    .build()).getId();
            boardRepository.findById(boardId).orElseThrow(() -> {
                throw new IllegalArgumentException("상품등록 오류");
            });
        } else {
//            System.out.println("파일 O");
            MultipartFile boardFile = boardDto.getBoardFile();
            String fileOldName = boardFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String fileNewName = uuid + "_" + fileOldName;
            String savePath = "E:/test/saveFile08/bfiles/" + fileNewName;
            boardFile.transferTo(new File(savePath));

            Long boardId = boardRepository.save(BoardEntity.builder()
                    .id(boardDto.getId())
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .writer(boardDto.getWriter())
                    .hit(optionalBoardEntity.get().getHit())
                    .isFile(1)
                    .memberEntity(optionalBoardEntity.get().getMemberEntity())
                    .build()).getId();
            BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> {
                throw new IllegalArgumentException("게시글등록 오류");
            });
            BFileEntity bFileEntity = BFileEntity.builder()
                    .boardEntity(boardEntity)
                    .fileNewName(fileNewName)
                    .fileOldName(fileOldName)
                    .build();
            Long bFileid = bFileRepository.save(bFileEntity).getId();
            bFileRepository.findById(bFileid).orElseThrow(()->{
                throw new IllegalArgumentException("파일등록오류");
            });
        }
        BoardEntity boardEntityId = boardRepository.findById(boardDto.getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("게시글수정 오류");
        });
        if (boardEntityId != null) {
            return 1;
        }
        return 0;
    }

    @Transactional
    public int boardDelete(Long id) {
        Optional<BFileEntity> optionalBFileEntity = bFileRepository.findByBoardEntityId(id);
        if(optionalBFileEntity.isPresent()){
            String fileNewName = optionalBFileEntity.get().getFileNewName();
            String filePath = "E:/test/saveFile08/bfiles/" + fileNewName;
            File deleteFile = new File(filePath);
            if(deleteFile.exists()) {
                deleteFile.delete();
                System.out.println("파일을 삭제하였습니다.");
            } else {
                System.out.println("파일이 존재하지 않습니다.");
            }
            bFileRepository.delete(optionalBFileEntity.get());
        }
        Optional<BoardEntity> optionalBoardEntity = Optional.ofNullable(
                boardRepository.findById(id).orElseThrow(() -> {
                    throw new IllegalArgumentException("삭제할 아이디 확인불가");
                }));
        boardRepository.delete(optionalBoardEntity.get());
        if (!boardRepository.findById(id).isPresent()) {
            return 1;
        }
        return 0;
    }

}
