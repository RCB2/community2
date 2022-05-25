package life.maigiang.communtity.Mapper;


import model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper //表示这是Mybatis的mapper类
@Repository //可以不使用
public interface UserMapper {
    /*下面语句中，前面括号字段对用mysql数据库的字段，后面字段对应的是domel的属性*/
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void UserInsert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(String token);
}
