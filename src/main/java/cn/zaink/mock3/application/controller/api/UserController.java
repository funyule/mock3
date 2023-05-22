package cn.zaink.mock3.application.controller.api;

import cn.zaink.mock3.core.pojo.Result;
import com.fasterxml.jackson.databind.JavaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author zaink
 **/
@RestController
@RequestMapping("/user")
public class UserController extends FakeController {

    @GetMapping("/info")
    public Result<UserInfo> info() throws IOException {
        URL userSource = readSource("userInfo.json");
        UserInfo userInfo = objectMapper.readValue(userSource, UserInfo.class);
        URL roleResource = readSource("role.json");
        Role role = objectMapper.readValue(roleResource, Role.class);
        userInfo.setRole(role);

        URL rolePermissionResource = readSource("role.permision.json");
        JavaType mapType = objectMapper.getTypeFactory().constructCollectionType(List.class, Permission.class);
        List<Permission> permissions = objectMapper.readValue(rolePermissionResource, mapType);
        role.setPermissions(permissions);
        return Result.ok(userInfo);
    }

    @GetMapping("/nav")
    public Result<List<NavItem>> nav() throws IOException {
        URL navResource = readSource("nav.json");
        JavaType mapType = objectMapper.getTypeFactory().constructCollectionType(List.class, NavItem.class);
        List<NavItem> o = objectMapper.readValue(navResource, mapType);
        return Result.ok(o);
    }


}
