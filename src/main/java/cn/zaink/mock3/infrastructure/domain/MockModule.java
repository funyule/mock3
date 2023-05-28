package cn.zaink.mock3.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 模块url
 *
 * @author zaink
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName(value = "mock_module")
@Data
public class MockModule implements Serializable {
    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 模块名
     */
    @TableField(value = "name")
    private String name;
    /**
     * 服务名
     */
    @TableField(value = "service_name")
    private String serviceName;
    /**
     * 是否注册服务
     */
    @TableField("regist_enable")
    private Boolean registEnable;
}