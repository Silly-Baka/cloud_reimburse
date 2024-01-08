package app.mapper;

import app.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Description：
 * <p>Date: 2023/12/1
 * <p>Time: 21:57
 *
 * @Author SillyBaka
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select u.id" +
            "   from user u" +
            "   join user_role_rel urr on u.id = urr.user_id" +
            "   join role r on urr.role_id = r.id" +
            "   and u.dept_name = #{param1}" +
            "   and r.role_name = #{param2}")
    List<Long> getUserByDeptAndRole(String deptName, String roleName);
}
