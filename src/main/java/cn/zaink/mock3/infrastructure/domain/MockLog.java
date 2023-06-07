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
 * mock_log
 *
 * @author zaink
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName(value = "mock_log")
@Data
public class MockLog extends BasePojo {

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
    @TableField(value = "response_header")
    private String responseHeader;

    /**
     *
     */
    @TableField(value = "response_body")
    private String responseBody;

    /**
     *
     */
    @TableField(value = "remark")
    private String remark;

}