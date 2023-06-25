package com.blog.config;

import com.blog.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// when add spring Security jar it followed default userName and password
//but in config class  I config my own setting by help of @Configuration annotation . that config follwed by springBoot
// initially when start project by default go fromBase authentication
// but when config by own configuration,authentication go to HttpBasic format
//
@Configuration
@EnableWebSecurity //it suggests spring boot not go default security setting. enable the securityConfig class and use for security setting
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();

        //passwordEncoder() method converting password in encoded format
        // BCryptPasswordEncoder helps to return EncodePassword . It is commonly used to store and validate user passwords in a secure manner.
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
    //any request that entering url,that request convert to http Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()//get endpoint permit to all both post and comment(**) .rest are authorization required.** means both post or comment
                .antMatchers("/api/auth/**").permitAll()//what kind of access is allowed for specific roles or users
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws
            Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


    //In memory authentication
    // @Override
// @Bean
// protected UserDetailsService userDetailsService() {
// UserDetails ramesh =User.builder().username("ramesh").password(passwordEncoder()
// .encode("password")).roles("USER").build();

// UserDetails admin =User.builder().username("admin").password(passwordEncoder()
// .encode("admin")).roles("ADMIN").build();
// return new InMemoryUserDetailsManager(ramesh, admin);
// }
    // .antMatchers(HttpMethod.GET, "/api/**").permitAll()  means get method(both post and comment) permit to all
    //.antMatchers("/api/signup").permitAll , signUp page without Login every body can access
    //BCryptPasswordEncoder store encoder password which coming from passwordEncode
}


