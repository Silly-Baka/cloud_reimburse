package app.service;

import app.common.CommonResult;
import app.user.dto.UpdateUserRoleReqDTO;
import app.user.dto.UserDTO;
import app.user.dto.UserQryDTO;
import app.user.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
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

    /**
     * 根据id获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    UserDTO getUserById(Long userId);

    /**
     * 条件查询用户信息
     * @param qryDTO
     * @return
     */
    Page<UserDTO> getUserListSelective(UserQryDTO qryDTO);


    Boolean updateUserRole(UpdateUserRoleReqDTO reqDTO);
}
