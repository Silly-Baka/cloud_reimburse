package app.service.impl;

import app.constants.CommonState;
import app.reimburse.dto.DailyReimburseReqDTO;
import app.reimburse.dto.DailySheetInfoReqDTO;
import app.reimburse.entity.DailySheetInfo;
import app.reimburse.entity.ReimburseSheet;
import app.mapper.DailySheetInfoMapper;
import app.mapper.ReimburseSheetMapper;
import app.service.ProcessNodeService;
import app.service.ReimburseService;
import app.utils.IdGenerator;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Description：
 * <p>Date: 2023/12/18
 * <p>Time: 20:40
 *
 * @Author SillyBaka
 **/
@Service
public class ReimburseServiceImpl extends ServiceImpl<ReimburseSheetMapper, ReimburseSheet> implements ReimburseService {

    @Resource
    private ProcessNodeService processNodeService;

    @Resource
    private DailySheetInfoMapper dailySheetInfoMapper;

    @Override
    @Transactional
    public Long applyDaily(DailyReimburseReqDTO dailyReimburseReqDTO) throws Exception {
        //DONE：补充逻辑

        Long sheetId = IdGenerator.getUniqueId(ReimburseSheet.class);

        //1、生成流程节点
        Long curNodeId = processNodeService.generateReimburseProcessMap(dailyReimburseReqDTO.getApplicantId(), sheetId);

        //2、插入报销单表
        ReimburseSheet reimburseSheet = new ReimburseSheet();
        BeanUtil.copyProperties(dailyReimburseReqDTO, reimburseSheet);
        reimburseSheet.setId(sheetId);
        reimburseSheet.setCurNodeId(curNodeId);
        reimburseSheet.setState(CommonState.CONTINUE.getVal());
        this.save(reimburseSheet);

        //3、插入日常报销信息表
        for (DailySheetInfoReqDTO sheetInfoReqDTO : dailyReimburseReqDTO.getDailySheetInfoReqDTOList()) {
            DailySheetInfo dailySheetInfo = new DailySheetInfo();
            BeanUtil.copyProperties(sheetInfoReqDTO, dailySheetInfo);

            dailySheetInfo.setId(IdGenerator.getUniqueId(DailySheetInfo.class));
            dailySheetInfo.setSheetId(sheetId);
            dailySheetInfoMapper.insert(dailySheetInfo);
        }

        //4、返回报销单id
        return sheetId ;
    }
}
