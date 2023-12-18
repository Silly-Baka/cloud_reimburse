package app.service;

import app.common.CommonResult;
import app.dto.DailyReimburseDTO;
import app.entity.ReimburseSheet;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description：
 * <p>Date: 2023/12/18
 * <p>Time: 20:40
 *
 * @Author SillyBaka
 **/
public interface ReimburseService extends IService<ReimburseSheet> {

    /**
     * 发起日常报销
     * @param dailyReimburseDTO
     * @return 报销单id
     */
    Long applyDaily(@RequestParam("dailyReimburseDTO") DailyReimburseDTO dailyReimburseDTO);
}
