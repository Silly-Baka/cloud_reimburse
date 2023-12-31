package app.service.impl;

import app.mapper.InvoiceInfoMapper;
import app.mapper.InvoiceMapper;
import app.reimburse.dto.InvoiceAddDTO;
import app.reimburse.dto.InvoiceUpdateDTO;
import app.reimburse.entity.Invoice;
import app.reimburse.entity.InvoiceInfo;
import app.service.InvoiceService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description：
 * <p>Date: 2023/12/31
 * <p>Time: 20:34
 *
 * @Author SillyBaka
 **/
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {

    @Resource
    private InvoiceInfoMapper invoiceInfoMapper;

    @Override
    @Transactional
    public Boolean upload(InvoiceAddDTO invoiceAddDTO) {

        Invoice invoice = BeanUtil.copyProperties(invoiceAddDTO, Invoice.class);
        invoice.setIsReimbursed(0);   //TODO：补充常量值

        // 入库发票
        this.save(invoice);

        // 入库发票详细条目
        for (InvoiceInfo invoiceInfo : invoiceAddDTO.getInvoiceInfoList()) {
            invoiceInfo.setInvId(invoice.getId());
            invoiceInfoMapper.insert(invoiceInfo);
        }

        return true;
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
}
