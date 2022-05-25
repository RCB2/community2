package life.maigiang.communtity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublishController {
    //界面新增发布文章
   @GetMapping("/publish")
    public String publish(){
       return "publish";
   }
}
