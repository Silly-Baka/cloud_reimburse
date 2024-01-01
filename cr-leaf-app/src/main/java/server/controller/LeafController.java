package server.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import core.common.Result;
import core.common.Status;
import server.exception.LeafServerException;
import server.exception.NoKeyException;
import server.service.SegmentService;
import server.service.SnowflakeService;

@RestController
public class LeafController {
    private Logger logger = LoggerFactory.getLogger(LeafController.class);

    @Autowired
    private SegmentService segmentService;
    @Autowired
    private SnowflakeService snowflakeService;

    public LeafController() {
        System.out.println("debug一下下");
    }

    @ApiOperation("使用号段模式获取id")
    @GetMapping(value = "/leaf/segment")
    public String getSegmentId(@RequestParam("key") String key) {
        return get(key, segmentService.getId(key));
    }

    @ApiOperation("使用雪花模式获取id")
    @GetMapping(value = "/leaf/snowflake")
    public String getSnowflakeId(@RequestParam("key") String key) {
        return get(key, snowflakeService.getId(key));
    }

    private String get(@RequestParam("key") String key, Result id) {
        Result result;
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }
        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return String.valueOf(result.getId());
    }
}
