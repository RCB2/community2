package life.maigiang.communtity.controller;

import life.maigiang.communtity.Mapper.UserMapper;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    /* @GetMapping("/")
    public String my(){
        return "index";
    }*/
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie :cookies) {//遍历cookies
            if (cookie.getName().equals("token")){//找到cookies数组中，token名字的cookie
                String token= cookie.getValue();//得到token的cookie，里面的值
                User user = userMapper.findByToken(token);
                if (user!=null) {
                    //request.setAttribute("user", user);
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        return "index";
    }
}
