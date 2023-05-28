package cn.zaink.mock3.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统字典表
 *
 * @author zaink
 */
@TableName(value = "sys_dict")
@Data
public class SysDict implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 字典类型编码
     */
    @TableField(value = "type_code")
    private String typeCode;
    /**
     * 字典编码
     */
    @TableField(value = "dict_code")
    private String dictCode;
    /**
     * 字典名称
     */
    @TableField(value = "dict_name")
    private String dictName;
    /**
     * 排序
     */
    @TableField(value = "order")
    private Integer order;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
    /**
     * 字典值
     */
    @TableField(value = "dict_value")
    private String dictValue;
}