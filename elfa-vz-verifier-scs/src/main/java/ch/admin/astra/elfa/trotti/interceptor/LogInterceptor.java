package ch.admin.astra.elfa.trotti.interceptor;

import ch.admin.astra.elfa.trotti.util.LoggingUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) {
        // remove user added values to keep MDC clean
        MDC.remove(LoggingUtil.KEY_OPERATION);
        MDC.remove(LoggingUtil.KEY_VERIFICATION_ID);
        MDC.remove(LoggingUtil.KEY_ERROR_CODE);
        MDC.remove(LoggingUtil.KEY_USE_CASE_ID);
        MDC.remove(LoggingUtil.KEY_VERIFICATION_STATUS);
    }
}