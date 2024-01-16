package app.service.impl;

import app.mapper.InvoiceInfoMapper;
import app.mapper.InvoiceMapper;
import app.reimburse.dto.InvoiceAddDTO;
import app.reimburse.dto.InvoiceUpdateDTO;
import app.reimburse.entity.Invoice;
import app.reimburse.entity.InvoiceInfo;
import app.service.FileService;
import app.service.InvoiceService;
import app.utils.IdGenerator;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Description：
 * <p>Date: 2023/12/31
 * <p>Time: 20:34
 *
 * @Author SillyBaka
 **/
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {

    @Resource
    private InvoiceInfoMapper invoiceInfoMapper;
    @Resource
    private FileService fileService;

    @Override
    @Transactional
    public Long upload(InvoiceAddDTO invoiceAddDTO) {

        Invoice invoice = BeanUtil.copyProperties(invoiceAddDTO, Invoice.class);
        invoice.setId(IdGenerator.getUniqueId(Invoice.class));
        invoice.setIsReimbursed(0);   //TODO：补充常量值

        // 入库发票
        this.save(invoice);

        // 入库发票详细条目
        for (InvoiceInfo invoiceInfo : invoiceAddDTO.getInvoiceInfoList()) {
            invoiceInfo.setId(IdGenerator.getUniqueId(InvoiceInfo.class));
            invoiceInfo.setInvId(invoice.getId());
            invoiceInfoMapper.insert(invoiceInfo);
        }

        return invoice.getId();
    }

    @Override
    public Boolean updateInvoice(InvoiceUpdateDTO invoiceUpdateDTO) {
        //TODO：补充逻辑
        return null;
    }

    @Override
    public List<Invoice> getOwnInvoice(Long ownerId) {

        return this.query()
                .eq("owner_id", ownerId)
                .ne("is_reimbursed", -1)   // 逻辑删除字段，但暂未启用-1这个值
                .list();
    }

    @Override
    @Transactional
    public Boolean uploadInvoiceFile(MultipartFile file, Long invoiceId) throws IOException {

        // 保存file信息
        Long fileId = fileService.saveInvoiceFile(file);

        // 修改invoice信息中的inv_file_id
        return this.update()
                .eq("id", invoiceId)
                .set("inv_file_id", fileId)
                .update();
    }
}
