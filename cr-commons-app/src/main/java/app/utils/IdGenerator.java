package app.utils;

import app.api.LeafApi;
import app.common.LeafTag;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * 工具类-唯一Id生成器
 * @Author SillyBaka
 */
@Component
public class IdGenerator implements ApplicationContextAware {

    private static LeafApi leafApi;

    /**
     * spring上下文，等待spring为其装载
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        IdGenerator.applicationContext = applicationContext;
    }

    /**
     * 生成指定类的最新唯一id
     * @param clazz 指定类
     * @return 唯一id
     */
    public static Long getUniqueId(Class<?> clazz) {
        if(leafApi == null) {
            leafApi = applicationContext.getBean(LeafApi.class);
        }
        // 利用LeafApi对雪花服务进行rpc调用
        String idStr = leafApi.getSegmentId(clazz.getAnnotation(LeafTag.class).tag());

        return Long.parseLong(idStr);
    }

}
