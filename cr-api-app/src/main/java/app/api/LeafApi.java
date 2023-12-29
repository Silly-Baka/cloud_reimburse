package app.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @RequestMapping(value = "/segment/get/{key}")
    String getSegmentId(@PathVariable("key") String key);

    @ApiOperation("使用雪花模式获取id")
    @RequestMapping(value = "/snowflake/get/{key}")
    String getSnowflakeId(@PathVariable("key") String key);
}
