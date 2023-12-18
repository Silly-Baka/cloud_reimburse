package app.contoller;

import app.common.CommonResult;
import app.dto.DailyReimburseDTO;
import app.service.ReimburseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description：报销单相关controller
 * <p>Date: 2023/12/18
 * <p>Time: 18:41
 *
 * @Author SillyBaka
 **/
@RestController("/reimburse")
public class ReimburseController {

    @Resource
    private ReimburseService reimburseService;

    @ApiOperation("日常费用报销申请")
    @PostMapping("/applyDaily")
    public CommonResult applyDaily(@RequestParam("dailyReimburseDTO") DailyReimburseDTO dailyReimburseDTO) {
        //TODO：补充逻辑
        return null;
    }
}
