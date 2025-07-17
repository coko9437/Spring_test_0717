package com.busanit501.boot_test1.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;
    private String content;
    private String writer;

    //연관관계 설정
    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL}
    ,fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private Set<BoardImage>  imageSet = new HashSet<>();

    //이미지 추가
    public void addImage(String uuid, String fileName) {
        BoardImage boardImage = BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(boardImage);
    }

    // 이미지 삭제
    public void clearImages() {
        imageSet.forEach(boardImage -> boardImage.chageBoard(null));
        this.imageSet.clear();
    }

    // 제목, 내용 수정 메소드
    public void changTitleContent(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
