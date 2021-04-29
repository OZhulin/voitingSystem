package ru.zhulin.oleg.restsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.zhulin.oleg.restsystem.security.UserAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    @Autowired
    private UserDetailsService userService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/v1/restaurants/**").hasAnyRole(ADMIN, USER)
                    .antMatchers( "/api/v1/restaurants/**").hasRole(ADMIN)
                    .antMatchers(HttpMethod.GET, "/api/v1/menus/**").hasAnyRole(ADMIN, USER)
                    .antMatchers( "/api/v1/menus/**").hasRole(ADMIN)
                    .antMatchers(HttpMethod.POST, "/api/v1/votes/**").hasAnyRole(ADMIN, USER)
                    .antMatchers( "/api/v1/votes/**").hasRole(ADMIN)
                    .antMatchers("/api/v1/users/authorized").hasAnyRole(ADMIN, USER)
                    .antMatchers( "/api/v1/users/**").hasRole(ADMIN)
                    .antMatchers("/v2/api-docs/**", "/v3/api-docs/**",
                            "/swagger-resources/**","/swagger-ui/**", "/swagger-ui.html").permitAll()
                    .antMatchers("/**").denyAll()
                .and().httpBasic()
                .and().csrf().disable()
                .authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public UserAuthenticationEntryPoint getEntryPoint(){
        return new UserAuthenticationEntryPoint();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
