package app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Description：用户模块启动类
 * <p>Date: 2023/11/21
 * <p>Time: 18:08
 *
 * @Author SillyBaka
 **/
@SpringBootApplication
@MapperScan("app.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
