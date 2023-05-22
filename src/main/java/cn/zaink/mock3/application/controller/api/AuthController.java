package cn.zaink.mock3.application.controller.api;

import cn.hutool.core.util.IdUtil;
import cn.zaink.mock3.core.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

/**
 * @author zaink
 **/
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController extends FakeController {


    @PostMapping("/login")
    public Result<UserInfo> login(@RequestBody Object object) throws IOException {
        URL url = readSource("userInfo.json");
        UserInfo userInfo = objectMapper.readValue(url, UserInfo.class);
        userInfo.setToken(IdUtil.fastSimpleUUID());
        return Result.ok(userInfo);
    }


}
