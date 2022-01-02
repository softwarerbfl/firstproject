package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "승준");
        return "greetings"; //templates/greetings.mustache 파일을 찾아 브라우저로 이동
    }
    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "규리");
        return "goodbye";
    }
}
