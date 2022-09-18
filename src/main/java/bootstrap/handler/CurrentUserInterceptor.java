package bootstrap.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.StringJoiner;

// используем interceptor для добавления во View cookies в ответе клиенту
@Component
public class CurrentUserInterceptor implements HandlerInterceptor {

    @Override
    // перехватчик для предварительной обработки request (до контроллера)
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //It tells Spring to further process the request (true)
        return true;
    }

    // перехватчик для post-обработки request (после контроллера, но до view - добавляем аттрибут cookies во view)
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // достаем данные о logIn user-е
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // объединение строк
            StringJoiner roles = new StringJoiner("|");
            // достаем из юзера (объект authentication) его роли (authorities)
            // объединяем в одну строку список ролей и убираем "ROLE_"
            authentication.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()
                    .replace("ROLE_", "")));
            Cookie cookieRoles = new Cookie("roles", roles.toString());
            Cookie cookieEmail = new Cookie("email", authentication.getName());
            // добавляем куки в ответ клиенту HttpServletResponse
            response.addCookie(cookieRoles);
            response.addCookie(cookieEmail);
        }
    }
}
