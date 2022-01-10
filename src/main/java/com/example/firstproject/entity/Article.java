package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity //DB가 해당 객체를 인식 가능 -> 해당 클래스를 테이블로 만든다
@AllArgsConstructor
@ToString
@NoArgsConstructor //디폴트 생성자 만들어주는 어노테이션
@Getter //Article객체의 id return해주는 getId()함수 사용 가능
public class Article {

    @Id //대표값을 지정! like 주민등록번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 ID를 자동 생성 어노테이션!
    private Long id;

    @Column
    private String title;
    @Column
    private String content;


}
