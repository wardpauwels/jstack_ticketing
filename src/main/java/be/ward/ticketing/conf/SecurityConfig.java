package be.ward.ticketing.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
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
    private CORSFilter corsFilter;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                          @Qualifier("dataSource") DataSource dataSource) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .authorizeRequests()
                .antMatchers("/login", "/tickets")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/tickets")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/*")
                .hasAuthority("ADMIN")
                .and()
                .formLogin()                //login
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()                   // logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .and()
                .cors()
                .and()
                .csrf().disable();
    }

    @Bean
    public CORSFilter corsFilter() {
        return new CORSFilter();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery(SQL_USERS_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(SQL_AUTHORITIES_BY_USERNAME_QUERY);
    }
}