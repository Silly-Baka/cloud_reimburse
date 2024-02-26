package app.service.impl;

import app.api.ReimburseApi;
import app.constants.TodoEventState;
import app.event.dto.TodoEventQryDTO;
import app.event.dto.TodoEventResultDTO;
import app.event.entity.TodoEvent;
import app.mapper.TodoEventMapper;
import app.reimburse.entity.ReimburseSheet;
import app.service.TodoEventService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static app.constants.TodoEventState.*;

@Service
public class TodoEventServiceImpl extends ServiceImpl<TodoEventMapper, TodoEvent> implements TodoEventService {

    @Resource
    private ReimburseApi reimburseApi;

    @Override
    public int getUnhandledEventsNum(Long userId) {
        return this.query()
                .eq("todo_user", userId)
                .ne("state", HANDLED)
                .count();
    }

    @Override
    public int getSupervisedEventsNum(Long userId) {
        return this.query()
                .eq("todo_user", userId)
                .eq("state", SUPERVISED)
                .count();
    }

    @Override
    public int getOutOfTimeEventsNum(Long userId) {
        return this.query()
                .eq("todo_user", userId)
                .ne("state", OUT_OF_TIME)
                .count();
    }

    @Override
    public Page<TodoEventResultDTO> listTodoEvents(TodoEventQryDTO qryDTO) {
        Page<TodoEvent> page = new Page<>(qryDTO.getPageNum(), qryDTO.getPageSize());

        QueryWrapper<TodoEvent> queryWrapper = new QueryWrapper<>();
        if(qryDTO.getType() != null) {
            queryWrapper.eq("type", qryDTO.getType());
        }
        if(qryDTO.getTodoUser() != null) {
            queryWrapper.eq("todo_user", qryDTO.getTodoUser());
        }
        if(qryDTO.getState() != null) {
            queryWrapper.eq("state", qryDTO.getState());
        }

        Page<TodoEvent> todoEventPage = this.page(page, queryWrapper);
        List<TodoEventResultDTO> todoEventResultDTOList = new ArrayList<>();
        for (TodoEvent todoEvent : todoEventPage.getRecords()) {
            TodoEventResultDTO todoEventResultDTO = BeanUtil.copyProperties(todoEvent, TodoEventResultDTO.class);
            // 查找报销单类型
            ReimburseSheet reimburseSheet = reimburseApi.getReimburseSheetBase(todoEventResultDTO.getSheetId()).getData();
            todoEventResultDTO.setSheetType(reimburseSheet.getType());
            todoEventResultDTOList.add(todoEventResultDTO);
        }
        Page<TodoEventResultDTO> result = new Page<>();
        BeanUtil.copyProperties(todoEventPage, result);
        result.setRecords(todoEventResultDTOList);

        return result;
    }

    @Override
    public Boolean doneTodoEvent(Long sheetId, Long todoUser) {
        Date finishTime = new Date();

        return this.update()
                .eq("sheet_id", sheetId)
                .eq("todo_user", todoUser)
                .set("finish_time", finishTime)
                .set("state", HANDLED)
                .update();
    }
}
