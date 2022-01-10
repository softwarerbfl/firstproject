package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@Slf4j//log를 찍기 위함
@RestController //RestAPI용 컨트롤러! 데이터(JSON)를 반환
public class ArticleApiContoller {
    @Autowired //DI 생성 객체를 가져와 연결!
    private ArticleService articleService;

    //GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index();
    }
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    //POST -> 상태를 반환한다. (ex.404 error)
    @PostMapping ("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article created = articleService.create(dto);
        return (created!=null)?
                ResponseEntity.status(HttpStatus.OK).body(created): //잘만들어진 데이터를 body에 담아준
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //몸통없이 build해서 전송
    }

    //PATCH -> 상태를 반환한다. (ex.404 error)
    @PatchMapping ("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleForm dto){
        Article updated = articleService.update(id, dto);
        return (updated!=null) ? ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELETE -> 상태를 반환한다. (ex.404 error)
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);
        return (deleted!=null)?
                ResponseEntity.status(HttpStatus.OK).body(null):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    //트랜잭션 -> 실패 -> 롤백!
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList=articleService.createArticles(dtos);
        return (createdList != null)?
                ResponseEntity.status(HttpStatus.OK).body(createdList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
