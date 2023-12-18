package app.service.impl;

import app.dto.DailyReimburseDTO;
import app.entity.ReimburseSheet;
import app.mapper.ReimburseSheetMapper;
import app.service.ReimburseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Description：
 * <p>Date: 2023/12/18
 * <p>Time: 20:40
 *
 * @Author SillyBaka
 **/
@Service
public class ReimburseServiceImpl extends ServiceImpl<ReimburseSheetMapper, ReimburseSheet> implements ReimburseService {

    @Override
    public Long applyDaily(DailyReimburseDTO dailyReimburseDTO) {
        //TODO：补充逻辑

        //1、生成流程节点

        //2、插入报销单表

        //3、插入日常报销信息表

        //4、返回报销单id

        return null;
    }
}
