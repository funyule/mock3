package cn.zaink.mock3.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * mock_log
 * @author zaink
 */
@TableName(value = "mock_log")
@Data
public class MockLog implements Serializable {
    /**
     *
     */
    @TableId(value = "log_id")
    private Long logId;

    /**
     *
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     *
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     *
     */
    @TableField(value = "update_by")
    private Long updateBy;

    /**
     *
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     *
     */
    @TableField(value = "request_url")
    private String requestUrl;

    /**
     *
     */
    @TableField(value = "request_ip")
    private String requestIp;

    /**
     *
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     *
     */
    @TableField(value = "hit_url")
    private String hitUrl;

    /**
     *
     */
    @TableField(value = "request_detail")
    private String requestDetail;

    /**
     *
     */
    @TableField(value = "response_detail")
    private String responseDetail;

    /**
     *
     */
    @TableLogic
    private Integer delFlag;

    /**
     *
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MockLog other = (MockLog) that;
        return (this.getLogId() == null ? other.getLogId() == null : this.getLogId().equals(other.getLogId()))
                && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getRequestUrl() == null ? other.getRequestUrl() == null : this.getRequestUrl().equals(other.getRequestUrl()))
                && (this.getRequestIp() == null ? other.getRequestIp() == null : this.getRequestIp().equals(other.getRequestIp()))
                && (this.getRequestMethod() == null ? other.getRequestMethod() == null : this.getRequestMethod().equals(other.getRequestMethod()))
                && (this.getHitUrl() == null ? other.getHitUrl() == null : this.getHitUrl().equals(other.getHitUrl()))
                && (this.getRequestDetail() == null ? other.getRequestDetail() == null : this.getRequestDetail().equals(other.getRequestDetail()))
                && (this.getResponseDetail() == null ? other.getResponseDetail() == null : this.getResponseDetail().equals(other.getResponseDetail()))
                && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogId() == null) ? 0 : getLogId().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getRequestUrl() == null) ? 0 : getRequestUrl().hashCode());
        result = prime * result + ((getRequestIp() == null) ? 0 : getRequestIp().hashCode());
        result = prime * result + ((getRequestMethod() == null) ? 0 : getRequestMethod().hashCode());
        result = prime * result + ((getHitUrl() == null) ? 0 : getHitUrl().hashCode());
        result = prime * result + ((getRequestDetail() == null) ? 0 : getRequestDetail().hashCode());
        result = prime * result + ((getResponseDetail() == null) ? 0 : getResponseDetail().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", requestUrl=").append(requestUrl);
        sb.append(", requestIp=").append(requestIp);
        sb.append(", requestMethod=").append(requestMethod);
        sb.append(", hitUrl=").append(hitUrl);
        sb.append(", requestDetail=").append(requestDetail);
        sb.append(", responseDetail=").append(responseDetail);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}