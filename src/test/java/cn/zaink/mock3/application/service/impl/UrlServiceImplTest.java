package cn.zaink.mock3.application.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zaink
 **/
@Slf4j
public class UrlServiceImplTest {

    @Test
    public void find() {
        String url = "/example.com/{id}/{name}";
        // 将占位符替换成正则表达式通配符
        String quote = Pattern.quote(url);
        log.debug("quote  {}",quote);
        String replaced = quote
                .replaceAll("/Q/", "/Q\\\\\\/");
        log.debug("replaced  {}",replaced);
        String regexStr = url
                .replaceFirst("/","\\\\/")
                .replace("\\.", "\\\\.")
                .replace("\\/\\{([^{}]+)\\}", "\\\\\\/(\\\\w+)");

        // 将生成的正则表达式用“^”和“$”符号包围起来
        String regex = "^" + regexStr + "$";

        log.info("regex {}",regexStr);

        String realUrl = "/example.com/2/jack";
        Matcher matcher = Pattern.compile(regexStr).matcher(realUrl);
        boolean matches = matcher.matches();
        log.info("matcher {}",matches);

        boolean matches1 = Pattern.quote(realUrl).matches(regex);
        log.info("matches1 {}",matches1);
    }

    @Test
    public void match(){
        String realUrl = "/example.com/2/jack";
        String pattern = "\\/example\\.com\\/(\\w+)\\/(\\w+)";

        Pattern r = Pattern.compile(pattern);
        Matcher matches = r.matcher(realUrl);
        log.info("matcher {}",matches.matches());
    }
}