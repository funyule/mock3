package cn.zaink.mock3.application.controller.api;

import cn.hutool.core.lang.Assert;
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
    public Result<UserInfo> login(@RequestBody UserInfo login) throws IOException {
        Assert.equals("admin", login.getUsername(), "用户名错误");
        Assert.equals("54221f4718e290a5f0979819d3db9579", login.getPassword(), "密码错误");
        URL url = readSource("userInfo.json");
        UserInfo userInfo = objectMapper.readValue(url, UserInfo.class);
        userInfo.setToken(IdUtil.fastSimpleUUID());
        return Result.ok(userInfo);
    }


}
