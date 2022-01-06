package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //DB가 해당 객체를 인식 가능
@AllArgsConstructor
@ToString
@NoArgsConstructor //디폴트 생성자 만들어주는 어노테이션
@Getter //Article객체의 id return해주는 getId()함수 사용 가능
public class Article {

    @Id //대표값을 지정! like 주민등록번호
    @GeneratedValue //1,2,3 ...자동 생성 어노테이션!
    private Long id;

    @Column
    private String title;
    @Column
    private String content;


}
