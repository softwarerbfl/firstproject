package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        return "redirect:/articles/"+saved.getId(); //id별 목록으로 redirect
    }

    //localhost:8080/articles/1이라면 id가 1인 사람의 정보 조회
    @GetMapping("/articles/{id}")
    public String show(@PathVariable long id, Model model){
        log.info("id = "+id); //id를 잘 받아왔는지 확인

        //1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //2. 가져온 데이터를 모델에 등록
        model.addAttribute("article",articleEntity);

        //3. 보여줄 페이지를 설정
        return "articles/show";
    }

    //articles의 전체 목록을 조회
    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 Article을 가져온다
        List<Article> articleEntityList=articleRepository.findAll();

        //2. 가져온 Article묶음을 뷰로 전달!
        model.addAttribute("articleList",articleEntityList);

        //3. 뷰 페이지를 설정!
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable long id, Model model){ //url경로에서 id를 가져온다
        //수정할 데이터를 가져오기!
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //모델에 데이터를 등록!
        model.addAttribute("article", articleEntity);
        //뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        //1 : DTO를 엔티티로 변환
        Article articleEntity=form.toEntity();
        log.info(form.toString());

        //2 : 엔티티를 DTO로 저장
        //2-1 : DB에서 기존 데이터를 가져온다
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        //2-2 : 기존 데이터에 값을 갱신한다
        if(target!=null){
            articleRepository.save(articleEntity);
        }

        //3 : 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/"+articleEntity.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes rttr){ //RedirectAttributes는 삭제가 이루어졌을 때 사용된다.
        log.info("삭제 요청이 들어왔습니다!!");

        // 1 : 삭제 대상을 가져온다
        Article target =articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 2 : 대상을 삭제한다!
        if(target!=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다");
            //redirect된 페이지에서 사용되는 휘발성 데이터
        }

        // 3 : 결과 페이지로 리다이렉트한다!
        return "redirect:/articles";
    }
}

