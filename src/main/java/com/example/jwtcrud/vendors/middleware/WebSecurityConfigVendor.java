//package com.example.jwtcrud.vendors.middleware;
//
//import com.example.jwtcrud.security.middleware.JwtAuthenticationEntryPointVendor;
//import com.example.jwtcrud.vendors.domain.service.VendorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Configuration
//public class WebSecurityConfigVendor extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    VendorService vendorService;
//
//    @Autowired
//    JwtAuthenticationEntryPointVendor unauthorizedHandler;
//
//    @Bean
//    public JwtAuthorizationFilterVendor authorizationFilterVendor() {
//        return new JwtAuthorizationFilterVendor();
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.userDetailsService(vendorService);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                .antMatchers("/api/v1/vendors/auth/**", "/api/v1/vendors", "/swagger-ui/**", "/v2/api-docs/**", "/swagger-resources/**", "/configuration/**").permitAll()
//                .anyRequest().authenticated();
//
//        http.addFilterBefore(authorizationFilterVendor(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//}
