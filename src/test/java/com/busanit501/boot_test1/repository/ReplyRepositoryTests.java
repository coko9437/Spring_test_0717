package com.busanit501.boot_test1.repository;

import com.busanit501.boot_test1.domain.Board;
import com.busanit501.boot_test1.domain.Reply;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {
        // 1) 실제 부모 Board(bno)존재
        Long bno = 208L;

        //Board에 더미 데이터 삽입준비
        Board board = Board.builder()
                .bno(bno).build();

        // 댓글 더미 데이터
        Reply reply = Reply.builder()
                // 부모 게시글 객체는 반드시 필요
                .board(board)
                .replyText("샘플로 내용 넣는중")
                .replyer("샘플로 작성자 넣는중")
                .build();

        replyRepository.save(reply);
    }

    @Test
    @Transactional // Reply에서, @ToString(exclude = "board")

    public void testBoardReplies() {
        Long bno = 207L;
        // 페이징 정보 담기.
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());
        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);
        result.getContent().forEach(
                reply -> {
                    log.info("replyRepositoryTests : 조회된 댓글 확인" + reply);
                }
        );

    }

    @Test
    @Transactional
    @Rollback(false)
    public void testInsertMany() { // 더미데이터 넣기.
        Long bno = 206L;
        Board board = boardRepository.findById(bno).orElseThrow();
        List<Reply> replies = new ArrayList<>();

        for (int i = 0; i < 91; i++) {
            replies.add(Reply.builder()
                    .board(board)
                    .replyText("더미 댓글 내용 : " + i)
                    .replyer("더미 유저_" + i)
                    .build());
        }
        replyRepository.saveAll(replies);
    }


}
