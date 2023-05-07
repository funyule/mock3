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
public class NavItemMeta {

    private String icon;
    private String title;
    private Boolean show;
    private String target;

    private Boolean hideHeader;
    private Boolean hideChildren;
    private Boolean hiddenHeaderContent;
}
