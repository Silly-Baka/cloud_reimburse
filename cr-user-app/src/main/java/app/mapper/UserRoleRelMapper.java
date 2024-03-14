package app.mapper;

import app.user.entity.Role;
import app.user.entity.UserRoleRel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Description：
 * <p>Date: 2023/12/12
 * <p>Time: 22:30
 *
 * @Author SillyBaka
 **/
@Mapper
public interface UserRoleRelMapper extends BaseMapper<UserRoleRel> {

    @Select("select r.* from role r" +
            " join user_role_rel urr on urr.role_id = r.id" +
            " where urr.user_id = #{param1}")
    Role getRoleByUserId(Long userId);
}
