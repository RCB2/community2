package life.maigiang.communtity.provider.dto;

import lombok.Data;

/**
 * Created by codedrinker on 2019/4/24.
 * 需要获得gitee账号该API的字段：有下面几个，若需要获得更多字段，在下面加上（仅限该API）
 * 上面解释
 */
@Data
public class GiteeUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
    private String email;
}
