package cn.zaink.mock3.application.controller.api;

import cn.zaink.mock3.core.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

/**
 * @author zaink
 **/
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController extends FakeController {

    @GetMapping("")
    public Result<Role> role() throws IOException {
        URL roleResource = readSource("role.json");
        Role role = objectMapper.readValue(roleResource, Role.class);
        return Result.ok(role);
    }
}
