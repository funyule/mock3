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
 * mock_url
 *
 * @author zaink
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mock_url")
@Data
public class MockUrl extends BasePojo {

    /**
     *
     */
    @TableField(value = "url")
    private String url;

    /**
     *
     */
    @TableField(value = "name")
    private String name;

    /**
     *
     */
    @TableField(value = "description")
    private String description;

    /**
     *
     */
    @TableField(value = "logic")
    private Long logic;

    /**
     *
     */
    @TableField(value = "response_type")
    private Integer responseType;

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
}