package app.service.impl;

import app.event.dto.TodoEventResultDTO;
import app.event.entity.TodoEvent;
import app.service.RoleAuthService;
import app.service.UserRoleRelService;
import app.user.dto.UpdateUserRoleReqDTO;
import app.user.dto.UserDTO;
import app.user.dto.UserQryDTO;
import app.user.entity.Role;
import app.user.entity.User;
import app.mapper.UserMapper;
import app.service.UserService;
import app.user.entity.UserRoleRel;
import app.utils.IdGenerator;
import app.utils.PwdUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Description：
 * <p>Date: 2023/12/1
 * <p>Time: 22:52
 *
 * @Author SillyBaka
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleAuthService roleAuthService;
    @Resource
    private UserRoleRelService userRoleRelService;

    @Override
    public Boolean register(User user) {

        user.setId(IdGenerator.getUniqueId(User.class));

        // 将密码加密后存入库中
        String encodePwd = PwdUtil.encodePwd(user.getPassword());
        user.setPassword(encodePwd);

        return this.save(user);
    }

    @Override
    public UserDTO login(String username, String password) {

        // 校验密码
        User user = this.query()
                .eq("username", username)
                .one();
        if(user == null) {
            return null;
        }
        // 账号被禁用
        if((Integer.valueOf(0).equals(user.getState()))) {
            return null;
        }
        String realPassword = user.getPassword();
        password = PwdUtil.encodePwd(password);

        if(!StrUtil.equals(realPassword, password)) {
            return null;
        }

        //TODO：将token和用户信息放入redis中
        String token = UUID.randomUUID().toString();

        // 返回用户信息
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        userDTO.setToken(token);

        return userDTO;
    }

    @Override
    public Boolean updatePwd(String username, String email, String curPwd, String newPwd) {

        // 查询对应username或email的用户
        QueryChainWrapper<User> wrapper = this.query();
        if(StrUtil.isNotBlank(username)) {
            wrapper.eq("username", username);
        }
        if(StrUtil.isNotBlank(email)) {
            wrapper.eq("email", email);
        }
        User user = wrapper.one();
        // 没有对应用户，修改错误
        if(user == null) {
            return false;
        }
        // 比对密码
        curPwd = PwdUtil.encodePwd(curPwd);

        if(!StrUtil.equals(user.getPassword(), curPwd)) {
            return false;
        }

        // 修改密码为newPwd
        newPwd = PwdUtil.encodePwd(newPwd);
        user.setPassword(newPwd);

        return this.updateById(user);
    }

    @Override
    public List<Long> getUserByDeptAndRole(String deptName, String roleName) {
        return userMapper.getUserByDeptAndRole(deptName, roleName);
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = this.getById(userId);

        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    @Override
    public Page<UserDTO> getUserListSelective(UserQryDTO qryDTO) {

        Page<User> page = new Page<>(qryDTO.getPageNum(), qryDTO.getPageSize());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(qryDTO.getRealName())) {
            queryWrapper.like("real_name", qryDTO.getRealName());
        }
        if(StrUtil.isNotBlank(qryDTO.getDeptName())) {
            queryWrapper.like("dept_name", qryDTO.getDeptName());
        }
        if(StrUtil.isNotBlank(qryDTO.getPhone())) {
            queryWrapper.like("phone", qryDTO.getPhone());
        }

        Page<User> userPage = this.page(page, queryWrapper);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userPage.getRecords()) {
            UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
            // 获取该用户的角色
            Role role = roleAuthService.getRoleByUserId(user.getId());
            if(role == null) {
                userDTO.setRoleName("暂无角色");
            } else {
                userDTO.setRoleName(role.getRoleName());
            }
            userDTOList.add(userDTO);
        }
        Page<UserDTO> result = new Page<>();
        BeanUtil.copyProperties(userPage, result);
        result.setRecords(userDTOList);

        return result;
    }

    @Override
    public Boolean updateUserRole(UpdateUserRoleReqDTO reqDTO) {
        boolean result = userRoleRelService.update()
                .eq("user_id", reqDTO.getUserId())
                .set("role_id", reqDTO.getRoleId())
                .update();

        // 该角色暂无角色，新增记录
        if(!result) {
            Long uniqueId = IdGenerator.getUniqueId(UserRoleRel.class);
            UserRoleRel userRoleRel = new UserRoleRel();
            userRoleRel.setId(uniqueId);
            userRoleRel.setUserId(reqDTO.getUserId());
            userRoleRel.setRoleId(reqDTO.getRoleId());

            userRoleRelService.save(userRoleRel);
        }
        return true;
    }
}
