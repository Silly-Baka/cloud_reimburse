package app.utils;

import app.api.LeafApi;

/**
 * Description：用于获取随机ID的工具类
 * <p>Date: 2023/11/21
 * <p>Time: 23:05
 *
 * @Author SillyBaka
 **/
public class IdGenerator {

    private static LeafApi leafApi;

    /**
     * 生成指定类的最新唯一id
     * @param clazz 指定类
     * @return 唯一id
     */
    public static Long getUniqueId(Class<?> clazz) {
        if(leafApi == null) {

        }
        // 利用LeafApi对雪花服务进行rpc调用

        return 0L;
    }
}
