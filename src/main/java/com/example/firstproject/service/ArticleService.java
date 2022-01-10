package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 선언(서비스 객체를 스프링 부트에 생성)
public class ArticleService {
    @Autowired //DI
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Article create(ArticleForm dto) {
        Article article=dto.toEntity();
        if(article.getId()!=null){ //이는 새로운 data를 생성하는 함수이므로 이미 존재하는 id에 중복하여 작성되는 것을 방지하기 위한 예외처리이다.
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1 : 수정용 엔티티 생성
        Article article = dto.toEntity();

        //2 : 대상 엔티티를 조회
        Article target=articleRepository.findById(id).orElse(null);

        //3 : 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target==null || id!=article.getId()){
            return null;
        }
        //4 : 업데이트 및 정상 응답(200)
        target.patch(article); //내용이 원래 있다면 넣어준다.
        Article updated=articleRepository.save(target); //넣어준 후에 변경
        return updated;
    }

    public Article delete(Long id) {
        //1 : 대상 엔티티 찾기
        Article target=articleRepository.findById(id).orElse(null);
        //2 :잘못된 요청 처리(데이터가 없는 경우)
        if(target==null){
            return null;
        }
        //3 : 대상 삭제 후 응답 반환
        articleRepository.delete(target);
        return target;
    }
    @Transactional //해당 메소드를 트랜젝션으로 묶는다!
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1 : dto 묶음을 entity묶음으로 변환
        List<Article> articleList= dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

//        위의 코드를 for문으로 변환해보았을 때 아래와 같다
//        List<Article> articleList=new ArrayList<>();
//        for(int i=0;i<dtos.size();i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity=dto.toEntity();
//            articleList.add(entity);
//        }

        //2 : entity묶음을 db로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
//        마찬가지로 위의 코드를 for문으로 변환해보았을 때 아래와 같다
//        for(int i=0;i<arrayList.size();i++){
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }
        //3 : 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결재 실패!")
        );
        //4 : 결과값 반환
        return articleList;
    }
}
