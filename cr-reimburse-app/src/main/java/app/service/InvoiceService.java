package app.service;

import app.reimburse.dto.InvoiceAddDTO;
import app.reimburse.dto.InvoiceQryDTO;
import app.reimburse.dto.InvoiceResultDTO;
import app.reimburse.dto.InvoiceUpdateDTO;
import app.reimburse.entity.Invoice;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface InvoiceService extends IService<Invoice> {

    /**
     * 上传发票信息
     *
     * @param invoiceAddDTO
     * @return
     */
    Long upload(InvoiceAddDTO invoiceAddDTO);

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
    List<InvoiceResultDTO> getOwnInvoice(Long ownerId);

    /**
     * 上传发票文件
     * @param file 发票文件
     * @param invoiceId 发票id
     * @return
     */
    Boolean uploadInvoiceFile(MultipartFile file, Long invoiceId) throws IOException;

    /**
     * 删除指定的发票列表
     * @param invoiceIds 发票id列表
     * @return
     */
    Boolean deleteInvoiceList(List<Long> invoiceIds);

    List<InvoiceResultDTO> getInvoiceListSelective(InvoiceQryDTO qryDTO);

    /**
     * 获取目标用户不可报销的发票列表
     * @param ownerId 用户id
     * @return
     */
    List<InvoiceResultDTO> getInvoiceListCanNotReimburse(Long ownerId);

    /**
     * 根据报销单id获取相关发票列表
     * @param sheetId 报销单id
     * @return
     */
    List<InvoiceResultDTO> getInvoiceListBySheetId(Long sheetId);

    /**
     * 更新指定发票列表的sheetId
     * @param invoiceIds
     * @param sheetId
     * @return
     */
    Boolean updateInvoiceListSheetId(List<Long> invoiceIds , Long sheetId);
}
