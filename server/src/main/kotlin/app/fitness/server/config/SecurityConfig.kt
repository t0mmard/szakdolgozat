package app.fitness.server.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    fun configureAuth(auth : AuthenticationManagerBuilder){
        auth
            .inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN")
            .and()
    }

    override fun configure(http: HttpSecurity?) {
        if (http != null) {
            http.authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/check_login").permitAll()
                    .antMatchers("/h2/**").permitAll()//hasRole("ADMIN") TODO
                .and()
                    .formLogin().permitAll()

            http.cors().and().csrf().disable();

        }
    }
}