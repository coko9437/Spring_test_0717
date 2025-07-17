package com.busanit501.boot_test1.repository;

import com.busanit501.boot_test1.domain.Board;
import com.busanit501.boot_test1.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// 설정한 인터페이스(BoardSearch)를 여기에 적용하기.
public interface BoardRepository extends JpaRepository<Board, Long> , BoardSearch {

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Board b where b.bno = :bno")
    Optional<Board> findByIdWithImages(Long bno);

}
// 등록(삽입) INSERT : .save        [boardRepository.save(board)]

// 선택,조회 SELECT : .findByID(tno)        [boardRepository.findByID(tno)]

// 수정 Update : 순서[조회 -> 수정 -> 저장]
    // 예시 ) Optional<Board> result = boardRepository.findById(bno);  --------레포지토리
        // Board board = result.orElseThrow();
        //  -> board.changTitleContent("수정제목","수정, 오늘 점심 뭐 먹지?");
        // -> boardRepository.save(board);  --------레포지토리

// 삭제 : .deleteById(bno)        [boardRepository.deleteByid(bno)

// 검색 :

// 페이징 처리 : Pageable / PageRequest 임폴트
    //Pageable pageable = PageRequest.of(0,10,
        //Sort.by("bno").descending());
    //Page<Board> result = boardRepository.findAll(pageable);
    //List<Board> todoList = result.getContent();