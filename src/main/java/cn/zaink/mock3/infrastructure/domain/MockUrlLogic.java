package cn.zaink.mock3.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * mock_url_logic
 *
 * @author zaink
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName(value = "mock_url_logic")
@Data
public class MockUrlLogic implements Serializable {
    /**
     *
     */
    @TableId(value = "id")
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
    @TableField(value = "sub_url")
    private String subUrl;

}