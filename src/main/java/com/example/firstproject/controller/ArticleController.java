package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j //로깅을 위한 어노테이션
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    //스프링 부트가 알아서 객체를 생성해서 연결해주기 때문에 new사용 안해도 된다

    //localhost:8080/articles/new 이 경로에 articles/new파일이 보이도록 mapping해줌
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
        //templates파일을 기준으로 경로를 작성하기 때문
    }


    //localhost:8080/articles/create 이 경로에서 실행되는 코드
    @PostMapping("/articles/create") //정보 전송
    public String createArticle(ArticleForm form){
        //System.out.println(form.toString());-> 로깅기능으로 대체!
        log.info(form.toString());

        //1. Dto를 변환! Entity!
        Article article=form.toEntity();
        log.info(form.toString());
        //System.out.println(article.toString());

        //2. Repository에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());
        return "";
    }
}
