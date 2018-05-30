package com.mr.order.interceptor;

import com.mr.sso.service.UserService;
import com.mr.util.CookieUtils;
import com.mr.util.DataResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ydd on 2018/5/24.
 */
public class OrderInterceptor implements HandlerInterceptor{

    @Value("${COOKIETOKENKEY}")
    private String COOKIETOKENKEY;

    @Value("${SSOURL}")
    private String SSOURL;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        /**
         * 1:从cookie中获取token
         *      如果token没有。则return false
         * 2:通过token查询用户信息
         *      如果失效。return false
         * 3：如果信息存在。
         *       return true;
         */

        String token = CookieUtils.getCookieValue(request, COOKIETOKENKEY, true);
        String url = request.getRequestURL().toString();
        if(StringUtils.isBlank(token)){
            //跳转到登录页面
            response.sendRedirect(SSOURL+"/page/login?url="+url);
            return false;
        }
        DataResult result = userService.getUserByToken(token);
        if(result.getStatus()!=200){//说明不生效
            response.sendRedirect(SSOURL+"/page/login?url="+url);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
