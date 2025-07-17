package com.busanit501.boot_test1.domain;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class BoardImage implements Comparable<BoardImage>{
    @Id
    private String uuid;
    private String fileName;
    private int ord;
    
    @ManyToOne
    private Board board;

    // 오름차순, 내림 차순 정렬 할 때 사용
    @Override
    public int compareTo(BoardImage other) {
        return this.ord - other.ord;
    }
    public void chageBoard(Board board) {
        this.board = board;
    }
}
