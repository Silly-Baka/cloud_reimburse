package app.service.impl;

import app.file.entity.File;
import app.mapper.FileMapper;
import app.service.FileService;
import app.utils.IdGenerator;
import app.utils.UUIDUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Description：
 * <p>Date: 2024/1/15
 * <p>Time: 21:16
 *
 * @Author SillyBaka
 **/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Value("${file.path.invoice}")
    private String invoiceFilePath;

    @Override
    @Transactional
    public Long saveInvoiceFile(MultipartFile file) throws IOException {
        File entity = new File();
        entity.setId(IdGenerator.getUniqueId(File.class));

        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));

        String newFileName = UUIDUtil.generateUUID() + suffix;
        String filePath = invoiceFilePath + newFileName;

        // 存储文件信息到数据库中
        entity.setName(newFileName);
        entity.setUrl(filePath);   //TODO：待修改url
        entity.setIsLocal(1);
        this.save(entity);

        // 将文件存储到本地中
        file.transferTo(new java.io.File(filePath));

        // 返回文件id
        return entity.getId();
    }
}
