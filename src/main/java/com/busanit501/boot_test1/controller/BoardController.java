package com.busanit501.boot_test1.controller;

import com.busanit501.boot_test1.dto.BoardDTO;
import com.busanit501.boot_test1.dto.BoardListReplyCountDTO;
import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.PageResponseDTO;
import com.busanit501.boot_test1.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Log4j2
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);
        log.info("BoardController에서, list, responseDTO : {}", responseDTO);
        // 서버 -> 화면으로 데이터 전달.
        model.addAttribute("responseDTO", responseDTO);
    }

    //등록화면, Get
    @GetMapping("/register")
    public void register() {
    }

    //등록 처리, Post
    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info("BoardController, 등록 Post 작업중");
        if (bindingResult.hasErrors()) {
            log.info("BoardController -> registerPost, 입력시 유효성 체크에 해당 사항 있음");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }
        log.info(" 화면으로부터 넘어온 데이터 확인 boardDTO : " + boardDTO);
        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);
        return  "redirect:/board/list";
    }

    // 상세보기화면 = 수정하는화면 동일.
    // 읽기 전용, 수정 가능 Get
    @GetMapping({"/read","/update"})// 화면 경로 : /board/read.html

    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {

        BoardDTO boardDTO = boardService.readOne(bno);
        log.info("BoardController, /read  boardDTO: "+ boardDTO);
        // 서버 -> 화면, 데이터 전달,
        model.addAttribute("dto", boardDTO);

    }

    // 수정 처리 Post
    @PostMapping("/update")
    public String update(PageRequestDTO pageRequestDTO,
                         @Valid BoardDTO boardDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        log.info("BoardController, /update 수정전 데이터 읽어오기  boardDTO:" + boardDTO);
        if (bindingResult.hasErrors()) {
            log.info("/update, 입력중에 유효성 체크에 해당 사항 있음");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            // 오류발생시 다시 수정중인 페이지로
            redirectAttributes.addAttribute("bno", boardDTO.getBno());
            return "redirect:/board/update?"+link;
        }

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "수정됨.");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return  "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {
        log.info("BoardController 에서, remove 작업중 , 넘어온 bno 확인: " + bno);
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "삭제됨.");
        return "redirect:/board/list";
    }

}
