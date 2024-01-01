package app.service.impl;

import app.user.entity.RoleAuthRel;
import app.mapper.RoleAuthRelMapper;
import app.service.RoleAuthRelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Description：
 * <p>Date: 2023/12/12
 * <p>Time: 22:33
 *
 * @Author SillyBaka
 **/
@Service

public class RoleAuthRelServiceImpl extends ServiceImpl<RoleAuthRelMapper, RoleAuthRel> implements RoleAuthRelService {
}
