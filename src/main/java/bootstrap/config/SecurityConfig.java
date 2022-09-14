package bootstrap.config;

import bootstrap.handler.LoginSuccessHandler;
import bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // страница аутентификации доступна всем
                .authorizeRequests()
                .antMatchers("/js/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .and()
                .formLogin()
                // указывем страницу с формой логина
                .loginPage("/login")
                // указываем логику обработки при логине
                .successHandler(new LoginSuccessHandler())
                //указываем action с формы логина
                .loginProcessingUrl("/login")
                //указываем параметры логина и пароля с формы логина
                .usernameParameter("j_username")
                .usernameParameter("j_password")
                // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login?logout")
                //выключаем кроссдоменную секьюрность
                .and().csrf().disable();

        http
                //закрываем доступ на страницу авторизированным пользователям
                .authorizeRequests()
                // страница аутентификации доступна всем НЕзарегистрированным пользователям
                .antMatchers("/login").anonymous()
                .antMatchers("/init").anonymous()
                // указываем защищенные URL
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                // УБРАТЬ - ИСПОЛЬЗУЮ ДЛЯ ПРОВЕРКИ В POSTMAN
                .and()
                .httpBasic();
    }
}