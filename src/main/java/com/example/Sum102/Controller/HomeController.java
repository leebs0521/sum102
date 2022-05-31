package com.example.Sum102.Controller;

import com.example.Sum102.Domain.Books;
import com.example.Sum102.Domain.User;
import com.example.Sum102.Service.BookService;
import com.example.Sum102.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    /*
    *   made by 이범수
    *   작업 내용:
    *   home.html 으로 매핑
    * */
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public HomeController(UserService userService, BookService bookService){
        this.userService = userService;
        this.bookService = bookService;
    }



    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("status", "로그인이 필요합니다.");
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
        user.setPasswd(form.getPasswd());
        userService.addUser(user);
        return "redirect:/";
    }

    @PostMapping("login")
    public String login(Model model, UserForm form){
        User user = new User();
        user.setId(form.getId());
        user.setPasswd(form.getPasswd());
        user.setName("");
        Boolean res =  userService.login(user);

        if(res) {
            List<Books> lists = bookService.findBooks();
            model.addAttribute("books", lists);
            return "/list";
        }
        else{
            model.addAttribute("status", "로그인 실패");
            return "home";
        }
    }

}
