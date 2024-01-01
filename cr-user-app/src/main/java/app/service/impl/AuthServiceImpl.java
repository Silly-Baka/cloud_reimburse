package app.service.impl;

import app.user.entity.Authority;
import app.mapper.AuthMapper;
import app.service.AuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Description：
 * <p>Date: 2023/12/12
 * <p>Time: 22:36
 *
 * @Author SillyBaka
 **/
@Service

public class AuthServiceImpl extends ServiceImpl<AuthMapper, Authority> implements AuthService {
}
