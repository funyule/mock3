package cn.zaink.mock3.infrastructure.domain;

import cn.zaink.mock3.core.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * mock响应的http header
 *
 * @author zaink
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName(value = "mock_response_header")
@Data
public class MockResponseHeader extends BasePojo {

    /**
     *
     */
    @TableField(value = "name")
    private String name;

    /**
     *
     */
    @TableField(value = "value")
    private String value;

    /**
     *
     */
    @TableField(value = "mock_response_id")
    private Long mockResponseId;


}