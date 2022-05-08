package life.maigiang.communtity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller//
//@RestController这个注解返回的参数是，tostring类型，不会以页面的形式展示
public class helloController {
    //@RequestParam,不清楚请求参数是什么时，快捷键ctrl+p
    //其中，@RequestParam(name="he")，他的参数名叫什么，在这里他的参数名命名为：he(看访问链接)
    //启动后进入，使用http://localhost:8080/hello?he=jijie
    //k相当于属性，将he的值在赋予k
    @GetMapping("/hello")//访问时：http://localhost:8080/holle?he=women
    public String hello(@RequestParam(name="he") String k, Model model){
            model.addAttribute("name1",k);
        return "holle";
    }

    @GetMapping("/holle2")//访问时：http://localhost:8080/holle2?k2=women
    public  String Hell2(String k2,Model model){
        model.addAttribute("name1",k2);
        return "holle";//返回到这个holle页面
    }
}
