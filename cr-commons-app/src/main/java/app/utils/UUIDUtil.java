package app.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Description：UUID专用工具类
 * <p>Date: 2024/1/15
 * <p>Time: 21:38
 *
 * @Author SillyBaka
 **/
public class UUIDUtil {

    private static final Random RANDOM = new Random();
    /**
     * 获取一个唯一的随机字符串
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
