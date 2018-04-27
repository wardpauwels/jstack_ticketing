package be.ward.ticketing.conf.csrf;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CsrfTokenResponseCookieBindingFilter extends OncePerRequestFilter {

    private static final String REQUEST_ATTRIBUTE_NAME = "_csrf";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        CsrfToken token = (CsrfToken) request.getAttribute(REQUEST_ATTRIBUTE_NAME);

        Cookie cookie = new Cookie(CSRF.RESPONSE_COOKIE_NAME, token.getToken());
        cookie.setPath("/");

        response.addCookie(cookie);

        chain.doFilter(request, response);
    }
}
