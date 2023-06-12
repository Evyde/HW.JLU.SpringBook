package ng.hanfe.springbook.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ng.hanfe.springbook.model.Role;
import ng.hanfe.springbook.model.User;
import ng.hanfe.springbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private UserRepository repository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            User user = repository.findByUsername((String) session.getAttribute("username"));
            if (user.getRole().equals(Role.ADMIN)) {
                // 管理员角色可以访问所有页面，放行
                return true;
            } else if (user.getRole().equals(Role.USER)) {
                // 普通用户角色只能访问特定页面，例如购物车页面
                String requestURI = request.getRequestURI();
                if (
                        requestURI.equals("/cart")
                        || requestURI.equals("/checkout")
                        || requestURI.equals("/")
                ) {
                    // 用户有权限访问，放行
                    return true;
                } else {
                    // 其他页面不允许访问，重定向到首页或其他提示页面
                    // response.sendRedirect("/");
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                }
            }
        } else {
            // 用户未登录，重定向到登录页面
            response.sendRedirect("/login");
            return false;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
