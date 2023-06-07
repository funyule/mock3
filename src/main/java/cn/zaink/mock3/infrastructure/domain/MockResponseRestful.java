package cn.zaink.mock3.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * mock_response_restful
 */
@TableName(value = "mock_response_restful")
@Data
public class MockResponseRestful implements Serializable {
    /**
     *
     */
    @TableId(value = "response_id")
    private Long responseId;

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
    @TableField(value = "url_id")
    private Long urlId;

    /**
     *
     */
    @TableField(value = "content")
    private String content;

    /**
     *
     */
    @TableField(value = "status_code")
    private Integer statusCode;

    /**
     *
     */
    @TableField(value = "description")
    private String description;

    /**
     *
     */
    @TableField(value = "custom_header")
    private String customHeader;

    /**
     *
     */
    @TableField(value = "http_method")
    private Integer httpMethod;

    /**
     *
     */
    @TableField(value = "remark")
    private String remark;


}