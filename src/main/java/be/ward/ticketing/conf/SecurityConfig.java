package be.ward.ticketing.conf;

import be.ward.ticketing.util.filters.CustomCorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SQL_USERS_BY_USERNAME_QUERY =
            "SELECT username, password, true AS enabled FROM user WHERE username = ?;";
    private static final String SQL_AUTHORITIES_BY_USERNAME_QUERY =
            "SELECT username, role AS authority FROM user JOIN role ON user.role_id = role.role_id WHERE username = ?;";
    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Resource
    private CustomCorsFilter customCorsFilter;
    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                          @Qualifier("dataSource") DataSource dataSource) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/*/**").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/logout").authenticated()
//                .antMatchers("/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/**").permitAll();

        // Handlers and entry points
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        http.formLogin().successHandler(authenticationSuccessHandler);
        http.formLogin().failureHandler(authenticationFailureHandler);

        // Logout
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessHandler(logoutSuccessHandler);

        //CORS
        http.cors();
        http.addFilterBefore(customCorsFilter, ChannelProcessingFilter.class);

        //CSRF
        http.csrf().disable();

        http.httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery(SQL_USERS_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(SQL_AUTHORITIES_BY_USERNAME_QUERY);
    }
}