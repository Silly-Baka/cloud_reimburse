package app.service;

import app.reimburse.dto.InvoiceAddDTO;
import app.reimburse.dto.InvoiceUpdateDTO;
import app.reimburse.entity.Invoice;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：
 * <p>Date: 2023/12/31
 * <p>Time: 20:33
 *
 * @Author SillyBaka
 **/
@Service
public interface InvoiceService extends IService<Invoice> {

    /**
     * 上传发票信息
     * @param invoiceAddDTO
     * @return
     */
    Boolean upload(InvoiceAddDTO invoiceAddDTO);

    /**
     * 更新发票信息
     * @param invoiceUpdateDTO
     * @return
     */
    Boolean updateInvoice(InvoiceUpdateDTO invoiceUpdateDTO);

    /**
     * 获取指定用户所拥有的发票
     * @param ownerId 指定用户id
     * @return 发票列表
     */
    List<Invoice> getOwnInvoice(Long ownerId);
}
