package fr.isima.server;

import fr.isima.business.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PlayerService playerService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        uncomment to disable CSRF
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/resources/**","/play").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler((request, response, authentication) -> {
                    request.getSession().setAttribute("username", authentication.getName());
                    playerService.setUserIsConnected(authentication.getName(),true);
                    response.sendRedirect("/main");
                }).permitAll()
                .and()
                .logout()
                .addLogoutHandler((request, response, authentication) -> {
                    playerService.setUserIsConnected(authentication.getName(),false);
                    playerService.setUserIsPlaying(authentication.getName(),false);
                })
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(playerService).passwordEncoder(passwordEncoder());
    }
}