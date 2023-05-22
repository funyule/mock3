package cn.zaink.mock3.application.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

/**
 * @author zaink
 **/
@Api(tags = "Fake API")
public abstract class FakeController {
    @Autowired
    protected ObjectMapper objectMapper;

    protected URL readSource(String name) {
        return this.getClass().getClassLoader().getResource(name);
    }
}
