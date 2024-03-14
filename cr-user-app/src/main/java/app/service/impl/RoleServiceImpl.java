package app.service.impl;

import app.mapper.RoleMapper;
import app.service.RoleService;
import app.service.UserService;
import app.user.entity.Role;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Description：
 * <p>Date: 2024/3/13
 * <p>Time: 19:45
 *
 * @Author SillyBaka
 **/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
