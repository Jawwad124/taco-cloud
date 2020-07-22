package com.practice.tacos.core;

import com.practice.tacos.service.UserRepositoryUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  private DataSource dataSource;
  @Autowired
  private UserRepositoryUserDetailsService userDetailsService;

  @Override
  protected  void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    // Tell here which way to use to store users.

    // In Memory users
    /*auth.inMemoryAuthentication()
            .withUser("fahad")
            .password("1234")
            .roles("USER")
            .and()
            .withUser("user")
            .password("1234")
            .roles("MANAGER");*/

    // JDBC based auth
    /*auth.jdbcAuthentication()
            .dataSource(dataSource)
            .withDefaultSchema()
            .passwordEncoder(getPasswordEncoder())
    .withUser("admin")
    .password(getPasswordEncoder().encode("1234"))
    .roles("MANAGER");*/

    // LDAP auth
    /*
    auth.ldapAuthentication()
            .contextSource()
            .root("dc=tacocloud,dc=com")
            .ldif("classpath:users.ldif")
            .and()
            .userSearchBase("ou=people")
            .userSearchFilter("(uid={0})")
            .groupSearchBase("ou=groups")
            .groupSearchFilter("member={0}")
            .passwordCompare()
            .passwordEncoder(getPasswordEncoder())
            .passwordAttribute("passcode");*/


    //Custom service auth
    auth.userDetailsService(userDetailsService)
    .passwordEncoder(getPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http.authorizeRequests()
            .antMatchers("/design", "/design/", "/orders", "/orders/*").hasRole("USER")
            .antMatchers("/h2-console/**", "/error", "/error/*", "/", "/**", "/*", "/***").permitAll()
            .and()
            .formLogin()
            .loginPage("/login")
            //.loginProcessingUrl("/authenticate")
            .usernameParameter("user")
            .passwordParameter("pwd")
            .and()//.csrf().disable()
            .headers().frameOptions().disable()
            .and().logout().logoutSuccessUrl("/");
  }

  @Bean
  public PasswordEncoder getPasswordEncoder()
  {
    return new BCryptPasswordEncoder();
  }
}
