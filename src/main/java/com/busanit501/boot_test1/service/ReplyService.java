package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.PageResponseDTO;
import com.busanit501.boot_test1.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);
    ReplyDTO read(Long rno);// 하나 조회
    void modify(ReplyDTO replyDTO);
    void remove(Long rno);

    // 페이징 처리
    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);
}
