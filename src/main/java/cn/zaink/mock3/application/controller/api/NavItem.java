package cn.zaink.mock3.application.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zaink
 **/
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class NavItem {
    private String name;
    private Long parentId;
    private Long id;
    private String component;
    private String redirect;
    private String path;
    private NavItemMeta meta;
}
