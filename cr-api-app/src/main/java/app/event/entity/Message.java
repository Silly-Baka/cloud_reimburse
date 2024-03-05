package app.event.entity;

import app.common.LeafTag;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;

/**
 * Description：站内信实体类
 * <p>Date: 2023/12/28
 * <p>Time: 20:43
 *
 * @Author SillyBaka
 **/
@LeafTag(tag = "message")
@TableName(value = "`message`")
public class Message {

    /**
     * 唯一标识
     */
    private Long id;

    /**
     * 站内信类型， 1-流程流转
     *            2-新待办事件
     *            3-流程督办
     *            4-事件超时
     *            5-流程失败
     */
    private Integer msgType;

    /**
     * 发起者，0则是admin发出
     * 目前此属性默认为0，暂未启用
     */
    @TableField(value = "`from`")
    private Long from;

    /**
     * 接收者
     */
    @TableField(value = "`to`")
    private Long to;

    /**
     * 站内信内容
     */
    private String content;

    /**
     * 状态，-1-已删除，0-未读，1-已读
     */
    @TableField(value = "`state`")
    private Integer state;

    /**
     * 发送日期
     */
    private Date createTime;

    /**
     * 跳转地址
     */
    private String redirectUrl;

    /**
     * 目标实体id
     */
    private Long entityId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
