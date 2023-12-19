package app.service;

import app.common.CommonResult;
import app.user.dto.UserDTO;
import app.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Description：
 * <p>Date: 2023/12/1
 * <p>Time: 22:02
 *
 * @Author SillyBaka
 **/
public interface UserService extends IService<User> {

    Boolean register(User user);

    UserDTO login(String username, String password);

    Boolean updatePwd(String username, String email, String curPwd, String newPwd);

    /**
     * 获取指定部门和角色的用户id
     * @param deptName 部门名
     * @param roleName 角色名
     * @return 用户id列表
     */
    List<Long> getUserByDeptAndRole(String deptName, String roleName);
}
