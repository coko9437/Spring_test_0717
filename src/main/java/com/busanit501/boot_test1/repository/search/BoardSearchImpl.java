package com.busanit501.boot_test1.repository.search;

import com.busanit501.boot_test1.domain.Board;
import com.busanit501.boot_test1.domain.QBoard;
import com.busanit501.boot_test1.domain.QReply;
import com.busanit501.boot_test1.dto.BoardListReplyCountDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

// 인터페이스이름 + Impl, 이름 규칙, 동일하게 작성,
// QuerydslRepositorySupport : 부모클래스, Querydsl 사용하기 위한 도구함.
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        // 부모 클래스에서 엔티티 클래스 전달
        super(Board.class);
    }

    @Override
    public Page<Board> search(Pageable pageable) {  // search
// QueryDSL
        // 1
        QBoard board = QBoard.board;
        // 순서2
        JPQLQuery<Board> query = from(board); // QBoard : select .. from board 형식과 동일함.
        // 순서3
        query.where(board.title.contains("1")); // where title like...
        // 순서4
        List<Board> list = query.fetch(); // db에서 데이터 가져오기.
        // 순서5
        long count = query.fetchCount(); // 가져온 DB 갯수
        // 순서6
        this.getQuerydsl().applyPagination(pageable,query); // 페이징 조건 추가. 검색 쿼리에 페이징 조건 탑재
        // 순서7
        List<Board> list2 = query.fetch();  // 페이징 조건을 적용한 데이터 가져오기
        // 순서8
        long count2 = query.fetchCount(); // 페이징 조건 적용한 전체 갯수 담기

        return null;
    }

    // 페이징 정보 + 검색 정보를 이용해서,
    // 자바 메서드로 -> sql 전달하기.
    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) { // searchAll
        // 순서1, 고정
        QBoard board = QBoard.board; // (board)
        // 순서2, 고정
        JPQLQuery<Board> query = from(board); // select .. from board
        // 순서3, 옵션
        // select .. from board where ....  BooleanBuilder 를 이용해서 조건 추가.
        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder builder = new BooleanBuilder(); // 쓰는이유 : OR, 조건, AND 조건을 사용하기 쉽다. 묶기도 쉽다.

            for(String type : types){  // types = {"t","w","c"}
                switch (type) {
                    case "t":
                        builder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        builder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        builder.or(board.writer.contains(keyword));
                        break;
                }

            }
            query.where(builder); // select * from board where like %keyword%

        } //end if

        // bno >0 조건 추가히기.
        query.where(board.bno.gt(0L));

        //  페이징 처리시,
        this.getQuerydsl().applyPagination(pageable,query);

        // 데이터를 가지고 올때... 위의 검색 조건, 페이징 조건으로 sql  전달하기.  데이터 가져오기
        List<Board> list = query.fetch();

        // 순서6, 고정, 데이터 가지고 올때
        long count = query.fetchCount();

        // 순서7, 리턴 타입에 맞추기
        return new PageImpl<>(list,pageable,count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        // 순서1, 고정
        QBoard board = QBoard.board; // (board)
        QReply reply = QReply.reply; // (reply)
        // 순서2, 고정
        JPQLQuery<Board> query = from(board); // select .. from board

        // 순서3,
        // left join,-> 게시글의 댓글이 없는 경우도 표기해야함. 그래서, 사용함.
        query.leftJoin(reply).on(reply.board.eq(board));
        query.groupBy(board);


        // 순서4, 옵션
        // where 조건절 추가. 위의 내용 재사용.
        // where 조건절 , BooleanBuilder 를 이용해서 조건 추가.
        // select .. from board where .... 검색조건 : 1) types, 2) keyword
        if ((types != null && types.length > 0) && keyword != null) {
            // or , 조건, and 조건을 사용하기 싶다. 묶기도 쉽다.
            BooleanBuilder builder = new BooleanBuilder();
            // types = {"t","w","c"}
            for(String type : types){
                switch (type) {
                    case "t":
                        builder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        builder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        builder.or(board.writer.contains(keyword));
                        break;
                } // end switch
            } // end for
            query.where(builder); // select * from board where like %keyword%
        } //end if
        // bno >0 조건 추가히기.
        query.where(board.bno.gt(0L));

        // 순서5,
        //   Projections.bean 를 이용해서 QueryDSL로, 자동 형변환하기.
        // DTO <-> Entity(VO), 서비스에서 모델 맵퍼 이용해서, 변환
        // 이번에는 자동으로 변환 해보기.
        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(
                Projections.bean(BoardListReplyCountDTO.class,
                        board.bno,
                        board.title,
                        board.writer,
                        board.regDate,
                        reply.count().as("replyCount")
                )//bean
        );// select

        // 순서6,
        // 기존의 1)페이징 정보 2) 검색정보 + 3) 댓글의 갯수
        // 페이징 적용하기.
        this.getQuerydsl().applyPagination(pageable,dtoQuery);

        // 순서7,
        // 실제 디비를 가져오기 작업, fetch 작업.
        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList,pageable,count);
    }
}
