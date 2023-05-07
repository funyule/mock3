package cn.zaink.mock3.application.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author zaink
 **/
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class UserInfo {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String avatar;
    private Integer status;
    private String telephone;
    private String lastLoginIp;
    private Long lastLoginTime;
    private String creatorId;
    private Long createTime;
    private String merchantCode;
    private Integer deleted;
    private String roleId;
    private Role role;
}
