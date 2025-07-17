package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.domain.Board;
import com.busanit501.boot_test1.domain.Reply;
import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.PageResponseDTO;
import com.busanit501.boot_test1.dto.ReplyDTO;
import com.busanit501.boot_test1.repository.BoardRepository;
import com.busanit501.boot_test1.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {
    // 디비에 작업할 기능 포함, 외주주기,
    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {
        log.info("ReplyServiceImpl 에서, 화면으로 부터 전달 받은 데이터 확인 replyDTO: " + replyDTO);
        Reply reply = modelMapper.map(replyDTO, Reply.class);
        // 화면에서 replyDTO 받음. -> 해당 bno를 키로 -> board(VO)로 변환
        Optional<Board> result = boardRepository.findById(replyDTO.getBno());
        Board board = result.orElseThrow();
        reply.changeBoard(board); // board내 reply 댓글
        log.info("ReplyServiceImpl 에서, 화면으로 부터 전달 받은 데이터 확인2 reply: " + reply);
        Long rno = replyRepository.save(reply).getRno();
        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {
        // 디비에서 조회 후, -> 옵션널 받고 -> 다시 엔티티 변환,
        Optional<Reply> result = replyRepository.findById(rno);
        Reply reply = result.orElseThrow();
        log.info("ReplyServiceImpl, 데이터 확인 reply: " + reply);
        // 한줄로 표기하기.
//        Reply reply = replyRepository.findById(rno).orElseThrow();
        ReplyDTO replyDTO = modelMapper.map(reply, ReplyDTO.class);
        replyDTO.setBno(reply.getBoard().getBno());
        log.info("ReplyServiceImpl, 데이터 확인2 replyDTO: " + replyDTO);
        return replyDTO;
    }

    @Transactional
    @Override
    public void modify(ReplyDTO replyDTO) {
        log.info("ReplyServiceImpl, modify ,데이터 확인 replyDTO: " + replyDTO);
        // 기존 댓글을 불러와서,
        Reply reply = replyRepository.findById(replyDTO.getRno()).orElseThrow();
        log.info("ReplyServiceImpl, modify ,데이터 확인2 reply: " + reply);
        // 교체할 데이터로 교체 작업 후,
        reply.changeReplyText(replyDTO.getReplyText());
        // 작성자 변경 해보기.
        reply.changeReplyer(replyDTO.getReplyer());

        // 교체 후, 데이터 확인
        log.info("ReplyServiceImpl, modify ,데이터 확인3 reply: " + reply);

        // 다시 저장.
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        Reply reply = replyRepository.findById(rno).orElseThrow();
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {
        // 페이징 준비
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0 ? 0 : pageRequestDTO.getPage()-1
                , pageRequestDTO.getSize(), Sort.by("rno").ascending());
        // 조회
        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);
        List<ReplyDTO> dtoList = result.getContent().stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
