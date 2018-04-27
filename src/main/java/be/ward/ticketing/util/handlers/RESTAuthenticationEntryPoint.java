package be.ward.ticketing.util.handlers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An authentication entry point implementation adapted to a REST approach.
 */
public class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        System.out.println("Operation failed");

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}