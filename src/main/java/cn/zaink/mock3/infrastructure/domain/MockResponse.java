package cn.zaink.mock3.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 
 * mock_response
 * @author zaink
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName(value ="mock_response")
@Data
public class MockResponse {
    /**
     * 
     */
    @TableId(value = "response_id")
    private Long id;

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
    @TableField(value = "main")
    private Integer main;

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
    @TableField(value = "method")
    private String method;


    /**
     * 
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(value = "DEL_FLAG")
    @TableLogic
    private Integer delFlag;
}