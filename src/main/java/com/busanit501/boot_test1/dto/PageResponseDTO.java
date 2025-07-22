package com.busanit501.boot_test1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

// 서버 -> 웹 화면 , 페이징 정보 전달.

@Getter
@ToString
public class PageResponseDTO<E> {
    private int page;
    private int size;
    private int total;

    private int start;
    private int end;

    private boolean prev;
    private boolean next;

    private List<E> dtoList;

    // 빌더 패턴을 이용한, 생성자 구성,
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {

        if(total <= 0){
            return;
        }
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page/10.0)) * 10;
        this.start = this.end - 9 ;
        int last = (int)(Math.ceil((total/(double)size)));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}
