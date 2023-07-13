package com.example.jwtcrud.user.middleware;

import com.example.jwtcrud.security.middleware.JwtAuthenticationEntryPointUser;
import com.example.jwtcrud.user.domain.service.UserService;
import com.example.jwtcrud.vendors.domain.service.VendorService;
import com.example.jwtcrud.vendors.middleware.JwtAuthorizationFilterVendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfigUser extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    VendorService vendorService;

    @Autowired
    JwtAuthenticationEntryPointUser unauthorizedHandler;

    @Bean
    public JwtAuthorizationFilterUser authorizationFilterUser() {
        return new JwtAuthorizationFilterUser();
    }

    @Bean
    public JwtAuthorizationFilterVendor authorizationFilterVendor() {
        return new JwtAuthorizationFilterVendor();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userService);
        builder.userDetailsService(vendorService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/v1/users/auth/**", "/api/v1/vendors/auth/**", "/api/v1/users", "/api/v1/vendors", "/swagger-ui/**", "/v2/api-docs/**", "/swagger-resources/**", "/configuration/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authorizationFilterUser(), UsernamePasswordAuthenticationFilter.class);
    }
}
