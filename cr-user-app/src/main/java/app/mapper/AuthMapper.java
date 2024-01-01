package app.mapper;

import app.user.entity.Authority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description：
 * <p>Date: 2023/12/12
 * <p>Time: 22:35
 *
 * @Author SillyBaka
 **/
@Mapper
public interface AuthMapper extends BaseMapper<Authority> {
}
