package life.maigiang.communtity.provider;

import life.maigiang.communtity.dto.AssessTokenDOT;
import life.maigiang.communtity.provider.dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.*;
import java.io.IOException;
import java.nio.charset.Charset;

import lombok.extern.slf4j.Slf4j;

@Component//后面调用该类时，不需要进行new，直接注入即可
public class GiteeProvider {
    /*
    * 获取得到accessToken
    * 用于下方法getUser(String accessToken)；
    * */
    public String getAccessToken(AssessTokenDOT assessTokenDOT){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(assessTokenDOT));
        //String url = "https://gitee.com/oauth/token?grant_type=authorization_code&code={code}&client_id={client_id}&redirect_uri={redirect_uri}&client_secret={client_secret}";
        String url = "https://gitee.com/oauth/token?grant_type=authorization_code";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String a = response.body().string();
            JSONObject b =  JSON.parseObject(a);//数据类型转换
            System.out.println("转换后的数据："+b);
            String accessToken = b.getString("access_token");
            //String accessToken = b.getString("refresh_token");
            System.out.println("assessTokenDOT是否正确："+a+"/n"+accessToken);
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    * 通过上面方法，获得accessToken，用来获取用户信息，最终授权登录
    * 查看API链接（URL）：gitee网站=》api=》API文档=》用户账号=》获取授权用户的资料（https://gitee.com/api/v5/user+需要传输的值access_token）
    * */
    public GiteeUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //将String的json对象转换成，java类对象
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            return giteeUser;
        } catch (Exception e) {
            //log.error("getUser error,{}", accessToken, e);
            e.printStackTrace();
        }
        return null;
    }
}
