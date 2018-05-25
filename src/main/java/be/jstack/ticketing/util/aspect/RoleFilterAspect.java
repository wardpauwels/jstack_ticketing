package be.jstack.ticketing.util.aspect;

import be.jstack.ticketing.entities.user.User;
import be.jstack.ticketing.service.user.UserDetailsService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleFilterAspect {

    private static UserDetailsService _userDetailsService;

    @Autowired
    public RoleFilterAspect(UserDetailsService userDetailsService) {
        _userDetailsService = userDetailsService;
    }

    @Around("@annotation(be.jstack.ticketing.util.annotation.Admin)")
    public Object protectAdminFunctions(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            String username = ((User) auth.getPrincipal()).getUsername();

            if (_userDetailsService == null) {
                System.out.println("No userDetailsService bean");
            }

            User user = _userDetailsService.findUserWithUsername(username);
            if (user.getRole().getRole().equalsIgnoreCase("ADMIN")) {
                return joinPoint.proceed();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}