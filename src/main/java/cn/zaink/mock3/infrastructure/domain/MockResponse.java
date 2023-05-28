package cn.zaink.mock3.infrastructure.domain;

import cn.zaink.mock3.core.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * mock_response
 *
 * @author zaink
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName(value = "mock_response")
@Data
public class MockResponse extends BasePojo {

    @TableField("name")
    private String name;
    /**
     *
     */
    @TableField(value = "url_id")
    private Long urlId;

    /**
     * 内容体
     */
    @TableField(value = "content")
    private String content;

    /**
     *
     */
    @TableField(value = "http_status")
    private Integer httpStatus;

    /**
     *
     */
    @TableField(value = "enable")
    private Boolean enable;

    /**
     *
     */
    @TableField(value = "description")
    private String description;

    /**
     *
     */
    @TableField(value = "method")
    private String httpMethod;

    /**
     *
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(value = "DEL_FLAG")
    @TableLogic
    private Integer delFlag;
}