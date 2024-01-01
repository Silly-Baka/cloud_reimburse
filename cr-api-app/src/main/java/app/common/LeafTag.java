package app.common;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

/**
 * Description：用于每个实体类，标明其leaf中的tag名
 * <p>Date: 2024/1/2
 * <p>Time: 1:32
 *
 * @Author SillyBaka
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LeafTag {

    String tag() default "";
}
