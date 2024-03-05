package app.service.impl;

import app.mapper.InvoiceInfoMapper;
import app.mapper.InvoiceMapper;
import app.reimburse.dto.InvoiceAddDTO;
import app.reimburse.dto.InvoiceQryDTO;
import app.reimburse.dto.InvoiceResultDTO;
import app.reimburse.dto.InvoiceUpdateDTO;
import app.reimburse.entity.Invoice;
import app.reimburse.entity.InvoiceInfo;
import app.service.FileService;
import app.service.InvoiceService;
import app.utils.IdGenerator;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
        invoice.setUploadDate(new Date());

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
    public List<InvoiceResultDTO> getOwnInvoice(Long ownerId) {

        List<Invoice> invoiceList = this.query()
                .eq("owner_id", ownerId)
                .ne("is_reimbursed", -1)   // 逻辑删除字段，但暂未启用-1这个值
                .list();

        return wrapInvoiceList(invoiceList);
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

    @Override
    @Transactional
    public Boolean deleteInvoiceList(List<Long> invoiceIds) {
        for (Long invoiceId : invoiceIds) {
            //删发票文件
            Long invFileId = this.query()
                    .eq("id", invoiceId)
                    .one().getInvFileId();
            if(invFileId != null) {
                fileService.removeFile(invFileId);
            }

            // 删发票详细信息
            invoiceInfoMapper.delete(new QueryWrapper<InvoiceInfo>()
                    .eq("inv_id", invoiceId));

            // 删发票信息
            this.removeById(invoiceId);
        }
        return true;
    }

    @Override
    public List<InvoiceResultDTO> getInvoiceListSelective(InvoiceQryDTO qryDTO) {
        QueryChainWrapper<Invoice> queryChainWrapper = this.query();
        if(qryDTO.getOwnerId() != null) {
            queryChainWrapper.eq("owner_id", qryDTO.getOwnerId());
        }
        if(qryDTO.getIsReimbursed() != null) {
            queryChainWrapper.eq("is_reimbursed", qryDTO.getIsReimbursed());
        }
        List<Invoice> invoiceList = queryChainWrapper.list();

        invoiceList.sort((o1, o2) -> {
            return -o1.getUploadDate().compareTo(o2.getUploadDate());
        });

        return wrapInvoiceList(invoiceList);
    }

    @Override
    public List<InvoiceResultDTO> getInvoiceListCanNotReimburse(Long ownerId) {
        List<Invoice> invoiceList = this.query()
                .eq("owner_id", ownerId)
                // 除了未报销之外的所有发票
                .ne("is_reimbursed", 0)
                .list();
        return wrapInvoiceList(invoiceList);
    }

    @Override
    public List<InvoiceResultDTO> getInvoiceListBySheetId(Long sheetId) {
        List<Invoice> invoiceList = this.query()
                .eq("sheet_id", sheetId)
                .list();
        return wrapInvoiceList(invoiceList);
    }

    @Override
    public Boolean updateInvoiceListSheetId(List<Long> invoiceIds, Long sheetId) {
        if(invoiceIds == null || invoiceIds.size() == 0) {
            return true;
        }
        boolean result = this.update()
                .in("id", invoiceIds)
                .set("sheet_id", sheetId)
                .update();
        return result;
    }


    private List<InvoiceResultDTO> wrapInvoiceList(List<Invoice> invoiceList) {
        List<InvoiceResultDTO> invoiceResultDTOList = new ArrayList<>();
        for (Invoice invoice : invoiceList) {
            InvoiceResultDTO invoiceResultDTO = BeanUtil.copyProperties(invoice, InvoiceResultDTO.class);

            // 获取发票详细信息列表
            QueryWrapper<InvoiceInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("inv_id", invoice.getId());

            List<InvoiceInfo> invoiceInfoList = invoiceInfoMapper.selectList(queryWrapper);
            invoiceResultDTO.setInvoiceInfoList(invoiceInfoList);
            invoiceResultDTOList.add(invoiceResultDTO);
        }
        return invoiceResultDTOList;
    }
}
