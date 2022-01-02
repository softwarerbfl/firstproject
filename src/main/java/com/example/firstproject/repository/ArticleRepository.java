package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;
//자바 내에 선언되어있는 repository 상속받아 사용
public interface ArticleRepository extends CrudRepository<Article, Long> {

}
