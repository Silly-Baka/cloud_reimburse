package server.config;

import app.common.LeafTag;
import app.leaf.entity.LeafAlloc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Description：自动扫描和插入leaf记录
 * <p>Date: 2024/1/2
 * <p>Time: 1:48
 *
 * @Author SillyBaka
 **/
@Configuration
public class ScanAndInsertLeafConfig {

    private final String[] packageToScan = new String[]{
            "app.reimburse",
            "app.file",
            "app.leaf",
            "app.user",
            "app.event",
            "app.finance"
    };

    @Resource
    private LeafAllocMapper leafAllocMapper;

    @Bean
    public CommandLineRunner scanAndInsert() {
        return args -> {
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(LeafTag.class));

            for (String path : packageToScan) {
                Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(path);
                for (BeanDefinition beanDefinition : candidateComponents) {
                    String className = beanDefinition.getBeanClassName();
                    Class<?> clazz = Class.forName(className);
                    LeafTag leafTag = clazz.getAnnotation(LeafTag.class);
                    if(leafTag != null) {
                        String tagName = leafTag.tag();

                        QueryWrapper<LeafAlloc> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("biz_tag", tagName);

                        LeafAlloc leafAlloc = leafAllocMapper.selectOne(queryWrapper);
                        if(leafAlloc == null) {
                            leafAlloc = new LeafAlloc();
                            leafAlloc.setBizTag(tagName);
                            leafAlloc.setMaxId(1L);
                            leafAlloc.setStep(1000);
                            leafAlloc.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                            leafAllocMapper.insert(leafAlloc);
                        }
                    }
                }
            }
        };
    }
}
