package app.service;

import app.file.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Description：
 * <p>Date: 2024/1/15
 * <p>Time: 21:16
 *
 * @Author SillyBaka
 **/
public interface FileService extends IService<File> {

    /**
     * 保存发票文件
     * @param file 文件
     * @return 文件id
     */
    Long saveInvoiceFile(MultipartFile file) throws IOException;

    /**
     * 删除指定id的文件
     * @param fileId 文件id
     * @return
     */
    Boolean removeFile(Long fileId);
}
