package com.example.Sum102.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {

    /*
    *   made by 이범수
    *   작업 내용:
    *   home.html 으로 매핑
    * */
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping(value = "createUser")
    public String createUserForm(){
        return "createUser";
    }
}
