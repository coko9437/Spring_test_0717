package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.BoardDTO;
import com.busanit501.boot_test1.dto.BoardListReplyCountDTO;
import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.PageResponseDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    //    기존 , 1) 페이징 2) 검색
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    // 기존 , 1) 페이징 2) 검색 3) 댓글 갯수 , 버전으로 목록 출력.
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);
    // <BoardListReplyCountDTO> : private  Long replyCount 추가됨.
}
