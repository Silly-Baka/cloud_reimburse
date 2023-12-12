package app.service;

import app.dto.UserDTO;
import app.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
