package app.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Description：雪花服务api
 * <p>Date: 2023/11/21
 * <p>Time: 23:10
 *
 * @Author SillyBaka
 **/
@FeignClient(name = "api-leaf", url = "${api.url.api-leaf}", path = "/leaf")
public interface LeafApi {

    @ApiOperation("使用号段模式获取id")
    @GetMapping(value = "/segment")
    String getSegmentId(@RequestParam("key") String key);

    @ApiOperation("使用雪花模式获取id")
    @GetMapping(value = "/snowflake")
    String getSnowflakeId(@RequestParam("key") String key);
}
