package app.service.impl;

import app.user.entity.UserRoleRel;
import app.mapper.UserRoleRelMapper;
import app.service.UserRoleRelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Description：
 * <p>Date: 2023/12/12
 * <p>Time: 22:29
 *
 * @Author SillyBaka
 **/
@Service

public class UserRoleRelServiceImpl extends ServiceImpl<UserRoleRelMapper, UserRoleRel> implements UserRoleRelService {
}
