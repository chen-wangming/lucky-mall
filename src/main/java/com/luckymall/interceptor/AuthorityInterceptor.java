package com.luckymall.interceptor;

import com.luckymall.entity.Admin;
import com.luckymall.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: 权限验证拦截器
 * @Author: wangming.chen
 * @Date: 2019/8/10 16:39
 */
public class AuthorityInterceptor implements HandlerInterceptor {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityInterceptor.class);

    /**
     * 方法说明：进入controller之前执行的方法
     *
     * @param request  客户端请求
     * @param response 服务端响应
     * @param handler  控制器
     * @return boolean 是否拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            LOGGER.info("===================登录拦截====================");
            String requestPath = request.getServletPath();
            LOGGER.info("请求路径: " + requestPath);
            if (requestPath.startsWith("/admin")) {
                //检查管理员是否登录
                Admin admin = (Admin) request.getSession().getAttribute("admin");
                if (admin == null) {
                    //未登录，跳到登录界面
                    LOGGER.info("AuthorityInterceptor检查到管理员未登录系统，强制跳转到管理员登录页面");
                    response.sendRedirect("/admin/login");
                }
            } else {
                //检查用户是否登录
                User user = (User) request.getSession().getAttribute("user");
                if (user == null) {
                    //未登录，跳到登录界面
                    LOGGER.info("AuthorityInterceptor 检查到未登录系统，强制跳转到登录页面");
                    response.sendRedirect("/notLogin");
                }
            }
        }
        return true;
    }

    /**
     * 检查用户是否包含所要求的所有权限
     *
     * @param auths 用户role
     * @param roles 要求的role
     * @return true/有权限 false/无权限
     */
    private boolean checkRole(List<String> auths, String[] roles) {
        boolean isContain = true;
        for (String role : roles) {
            if (!auths.contains(role)) {
                isContain = false;
            }
        }
        return isContain;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
}

