package app.service.impl;

import app.user.dto.UserDTO;
import app.user.entity.User;
import app.mapper.UserMapper;
import app.service.UserService;
import app.utils.PwdUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    @Override
    public Boolean register(User user) {
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
}
