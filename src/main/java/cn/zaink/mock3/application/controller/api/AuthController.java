package cn.zaink.mock3.application.controller.api;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author zaink
 **/
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {


    @PostMapping("/login")
    public Object login(@RequestBody Object object) {
        String user = "{\n" +
                "    'id': 1,\n" +
                "    'name': 'admin',\n" +
                "    'username': 'admin',\n" +
                "    'password': '',\n" +
                "    'avatar': 'https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png',\n" +
                "    'status': 1,\n" +
                "    'telephone': '',\n" +
                "    'lastLoginIp': '27.154.74.117',\n" +
                "    'lastLoginTime': 1534837621348,\n" +
                "    'creatorId': 'admin',\n" +
                "    'createTime': 1497160610259,\n" +
                "    'deleted': 0,\n" +
                "    'roleId': 'admin',\n" +
                "    'lang': 'zh-CN',\n" +
                "    'token': '4291d7da9005377ec9aec4a71ea837f'\n" +
                "  }";
        return JSONUtil.parseObj(user);
    }


}
