package cn.zaink.mock3.application.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author zaink
 **/
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Permission {

    private String roleId;
    private String permissionId;
    private String permissionName;
    private List<PermissionAction> actions;
}
