package cn.zaink.mock3.application.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zaink
 **/
@Api(tags = "Url Log API")
@Slf4j
@RestController
@RequestMapping("/system/url/log")
public class UrlLogController {
}
