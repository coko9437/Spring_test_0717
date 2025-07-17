package com.busanit501.boot_test1.service;


import com.busanit501.boot_test1.domain.Board;
import com.busanit501.boot_test1.dto.BoardDTO;
import com.busanit501.boot_test1.dto.BoardListReplyCountDTO;
import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.PageResponseDTO;
import com.busanit501.boot_test1.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional()
public class BoardServiceImpl implements BoardService{
    // 화면에서 전달 받은 데이터 DTO를 , 엔티티 클래스 타입으로 변환 ->
    // repository 에게 외주 주는 업무.
    private final ModelMapper modelMapper;// 변환 담당자
    private final BoardRepository boardRepository; // 실제 디비에 쓰는 작업하는 담당자

    @Override
    public Long register(BoardDTO boardDTO) {
        // 받은 DTO 확인
        log.info("BoardServiceImpl, 등록과정 중, 변환된 boardDTO : " + boardDTO);

        Board board = modelMapper.map(boardDTO, Board.class); // DTO -> VO
        log.info("BoardServiceImpl, 등록과정 중, 변환된 board : " + board);

        Long bno = boardRepository.save(board).getBno(); // SQL문으로 디비에 입력
        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {

     Optional<Board> result = boardRepository.findById(bno);
     Board board = result.orElseThrow();
     // 엔티티 클래스 타입(VO) -> DTO 타입 변환.
     BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow();
        board.changTitleContent(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) { // 1) 페이징 2) 검색
        // type = "twc" -> getTypes -> {"t","c","w"}
        // 화면으로 부터 전달 받은
        // 1) 검색 조건, 2)페이징 정보
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);
        // Page<Board> result , 들어 있는 정보들 한번더 확인.

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) { //  1) 페이징 2) 검색 3) 댓글 갯수 버전
        // type = "twc" -> getTypes -> {"t","c","w"}
        // 화면으로 부터 전달 받은,
        // 1)검색 조건과 2)페이징 정보
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");


        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types,keyword,pageable);


        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }
}
