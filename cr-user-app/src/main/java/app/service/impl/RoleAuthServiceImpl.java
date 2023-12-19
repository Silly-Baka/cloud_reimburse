package app.service.impl;

import app.user.entity.RoleAuthRel;
import app.user.entity.UserRoleRel;
import app.service.RoleAuthRelService;
import app.service.RoleAuthService;
import app.service.UserRoleRelService;
import app.utils.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description：
 * <p>Date: 2023/12/12
 * <p>Time: 22:21
 *
 * @Author SillyBaka
 **/
@Service
public class RoleAuthServiceImpl implements RoleAuthService {

    @Resource
    private UserRoleRelService userRoleRelService;
    @Resource
    private RoleAuthRelService roleAuthRelService;

    @Override
    public Boolean setRole(Long userId, Long roleId) {

        UserRoleRel entity = new UserRoleRel();
        entity.setId(IdGenerator.getUniqueId(UserRoleRel.class));
        entity.setUserId(userId);
        entity.setRoleId(roleId);

        return userRoleRelService.save(entity);
    }

    @Override
    public Boolean addRoleAuth(Long roleId, Long authId) {

        RoleAuthRel entity = new RoleAuthRel();
        entity.setId(IdGenerator.getUniqueId(RoleAuthRel.class));
        entity.setRoleId(roleId);
        entity.setAuthId(authId);

        return roleAuthRelService.save(entity);
    }
}
