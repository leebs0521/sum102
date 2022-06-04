package com.example.Sum102.Controller;

import com.example.Sum102.Domain.Comment;
import com.example.Sum102.Domain.Product;
import com.example.Sum102.Domain.Users;
import com.example.Sum102.Service.CommentService;
import com.example.Sum102.Service.ProductService;
import com.example.Sum102.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    /*
    *   made by 이범수
    *   작업 내용:
    *   home.html 으로 매핑
    * */
    private final UsersService usersService;
    private final ProductService productService;
    private final CommentService commentService;

    @Autowired
    public HomeController(UsersService usersService, ProductService productService, CommentService commentService){
        this.usersService = usersService;
        this.productService = productService;
        this.commentService = commentService;
    }



    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("status", "로그인이 필요합니다.");
        return "home";
    }

    @GetMapping(value = "createUser")
    public String createUserForm(Model model){
        model.addAttribute("status", "정보를 입력하세요.");
        return "createUserForm";
    }
    @PostMapping(value="createUser")
    public String create(UsersForm form, Model model){
        Users users = new Users();
        users.setName(form.getName());
        users.setId(form.getId());
        users.setPasswd(form.getPasswd());
        Boolean rs = usersService.addUser(users);
        if(rs) {
            model.addAttribute("status", "회원가입 성공");
            return "home";
        }
        else{
            model.addAttribute("status", "이미 존재하는 아이디 입니다.");
            return "createUserForm";
        }
    }

    @PostMapping("login")
    public String login(Model model, UsersForm form, HttpServletRequest req){
        Users users = new Users();
        users.setId(form.getId());
        users.setPasswd(form.getPasswd());
        users.setName("");
        Boolean res = usersService.login(users);
        HttpSession session = req.getSession();

        if(res) {
            session.setAttribute("userid", users.getId());
            System.out.println(users.getId());
            List<Product> products = productService.findProducts();
            model.addAttribute("userid", session.getAttribute("userid"));
            model.addAttribute("products", products);
            return "/productList";
        }
        else{
            model.addAttribute("status", "로그인 실패");
            return "home";
        }
    }

    @GetMapping(value = "addProduct")
    public String addlist(){
        return "addProduct";
    }

    @PostMapping(value= "/addProduct") // 도서 입력 화면 데이터 인수
    public String addProduct(ProductForm form, Model model, HttpServletRequest req){

        HttpSession session = req.getSession();
        Product product = new Product();
        product.setTitle(form.getTitle());
        product.setName(form.getName());
        product.setPrice(form.getPrice());
        product.setUserid((String)session.getAttribute("userid"));
        product.setInfo(form.getInfo());
        productService.addProduct(product);
        return "redirect:/productList";
    }

    @GetMapping(value = "/productList")
    public String productList(Model model, HttpServletRequest req) {

        HttpSession session = req.getSession();
        System.out.println(" products mapping  ");
        List<Product> products = productService.findProducts();
        model.addAttribute("products", products);
        model.addAttribute("userid",(String)session.getAttribute("userid"));
        return "productList";
    }
    @GetMapping(value ="/logout")
    public String logout(Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        session.invalidate();
        model.addAttribute("status", "로그인이 필요합니다.");
        return "redirect:/";
    }


    @RequestMapping(value="/product/{pid}")
    public String Product(@PathVariable("pid") Long pid, Model model, HttpServletRequest req){

        List<Comment> comments = commentService.findComments(pid);
        Product product = productService.findProduct(pid);

        HttpSession session = req.getSession();
        session.setAttribute("pid", pid);
        model.addAttribute("product", product);
        model.addAttribute("comments", comments);
        model.addAttribute("userid", (String)session.getAttribute("userid"));

        return "productPage";
    }

    @PostMapping(value="/addComment")
    public String addComment(CommentForm form, Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        Comment comment = new Comment();
        System.out.println("1");
        System.out.println((String)session.getAttribute("userid"));
        System.out.println(session.getAttribute("pid"));
        comment.setComment(form.getComment());
        comment.setUserid((String)session.getAttribute("userid"));
        comment.setPid((Long)session.getAttribute("pid"));
        commentService.addComment(comment);
        return "redirect:/product/"+session.getAttribute("pid");
    }

    @PostMapping(value="/searchProduct")
    public String serachProduct(ProductForm form, Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        System.out.println(" products mapping  ");
        List<Product> products = productService.findProducts(form.getTitle());
        model.addAttribute("products", products);
        model.addAttribute("userid",(String)session.getAttribute("userid"));
        return "productList";
    }
}
