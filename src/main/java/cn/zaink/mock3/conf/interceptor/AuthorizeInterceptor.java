package cn.zaink.mock3.conf.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.Header;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * 登陆用户拦截器
 *
 * @author yaoxiangping
 **/
@Slf4j
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String token = ServletUtil.getHeader(request, Header.AUTHORIZATION.getValue(), Charset.defaultCharset());
        if (isBlank(token)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        return super.preHandle(request, response, handler);
    }

}
