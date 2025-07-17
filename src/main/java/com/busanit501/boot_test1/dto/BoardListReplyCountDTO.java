package com.busanit501.boot_test1.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {
    // 게시글 목록 표시
    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;

    // 게시글 제목옆, 달린 댓글 갯수 표시하기
    private  Long replyCount;
}
