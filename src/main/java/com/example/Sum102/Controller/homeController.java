package com.example.Sum102.Controller;

import com.example.Sum102.Domain.User;
import com.example.Sum102.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class homeController {

    /*
    *   made by 이범수
    *   작업 내용:
    *   home.html 으로 매핑
    * */
    private final UserService userService;

    @Autowired
    public homeController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping(value = "createUser")
    public String createUserForm(){
        return "createUserForm";
    }

    @PostMapping(value="createUser")
    public String create(UserForm form){
        User user = new User();
        user.setName(form.getName());
        user.setId(form.getId());
        userService.addUser(user);
        return "redirect:/";
    }
}
