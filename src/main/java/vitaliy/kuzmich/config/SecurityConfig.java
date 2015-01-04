package vitaliy.kuzmich.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*@Configuration
@EnableWebSecurity*/

public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().anyRequest()/*antMatchers("/admin*//**")*/
                .access("hasRole('ROLE_ADMIN')").and().formLogin()
                // .loginPage("/login").failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password");
               /* .and().logout().logoutSuccessUrl("/login?logout");*///.and().csrf().and().exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}