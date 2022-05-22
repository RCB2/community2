package life.maigiang.communtity.controller;

import life.maigiang.communtity.Mapper.UserMapper;
import life.maigiang.communtity.dto.AssessTokenDOT;
import life.maigiang.communtity.provider.GiteeProvider;
import life.maigiang.communtity.provider.dto.GiteeUser;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GiteeProvider giteeProvider;
    @Autowired
   private UserMapper userMapper;
    /*
    * 注入，配置文档的数据spring的一个注解@Value
    * */
    @Value("${gitee.client_id}")
    private String client_id;
    @Value("${gitee.client_secret}")
    private String client_secret;
    @Value("${gitee.redirect_uri}")
    private String redirect_uri;

    /**
     * 授权后跳转的连接，我们也希望它跳转到index页面
     * 下面椒授权后，github回调的callback数据，code以及state（需要接收一下）
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           Model model,
                           HttpServletRequest request){
        System.out.println("拿到的code："+code);
        //下面中，state是我自己添加进的，可忽略
        AssessTokenDOT assessTokenDOT = new AssessTokenDOT();
        assessTokenDOT.setClient_id(client_id);
        assessTokenDOT.setClient_secret(client_secret);
        assessTokenDOT.setCode(code);
        assessTokenDOT.setRedirect_uri(redirect_uri);//回调路径,先前通过这个路径，返回得到code
       /* assessTokenDOT.setState("1");*/
        String accessToken= giteeProvider.getAccessToken(assessTokenDOT);//通过code，返回access_token
        //最后，通过access_token，获得user信息
        GiteeUser giteeUser= giteeProvider.getUser(accessToken);//通过access_token，获取用户授权信息（或者其他信息，这里仅仅只是其中一个接口API）
        System.out.println("返回用户信息："+giteeUser.getName()+"  "+giteeUser.getAvatarUrl() );
        model.addAttribute("giteeUser1",giteeUser);
        if(giteeUser != null){
            //获取到的数据存入，MySQL数据库：community2
            User user = new User();
            user.setName(giteeUser.getName());
            user.setAccountId(String.valueOf(giteeUser.getId()));
            user.setToken(UUID.randomUUID().toString());//直接从缓存中拿
            user.setGmtCreate(System.currentTimeMillis());//系统当前时间，精确到毫秒数
            user.setGmtModified(user.getGmtCreate());//获取上面设置好的时间，先用着
            userMapper.UserInsert(user);
            //登录成功，写cookie 和 Session;将数据写入cookie中即Session中；（我们手工保存进去，不手工保存浏览器也会自动保存，只是名称随机生成，不好辨别）
            request.getSession().setAttribute("giteeUser",giteeUser);
            return "redirect:/";//重定向,即重新定向到某一个方法，执行这个方法。这里/代表重定向到IndexController控制器的方法里去
        }else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
