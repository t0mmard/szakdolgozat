package app.fitness.server.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

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
                    .antMatchers("/").permitAll()
                    .antMatchers("/h2/**").hasRole("ADMIN")
                .and()
                    .formLogin().permitAll()

//            http.csrf().disable()
//            http.headers().frameOptions().disable()
        }
    }
}