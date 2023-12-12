package app.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Description：雪花服务api
 * <p>Date: 2023/11/21
 * <p>Time: 23:10
 *
 * @Author SillyBaka
 **/
@FeignClient(name = "api-leaf", url = "${api.url.api-leaf}")
public interface LeafApi {
}
