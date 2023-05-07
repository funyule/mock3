package cn.zaink.mock3.application.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zaink
 **/
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Role {

    private String id;
    private String name;
    private String describe;
    private Integer status;
    private String creatorId;
    private Long createTime;
    private Integer deleted;

    private List<Permission> permissions;
}
